package com.doumiao.joke.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.annotation.RequiredLogin;
import com.doumiao.joke.annotation.ResultTypeEnum;
import com.doumiao.joke.enums.Account;
import com.doumiao.joke.enums.AccountLogStatus;
import com.doumiao.joke.enums.WealthType;
import com.doumiao.joke.lang.HttpClientHelper;
import com.doumiao.joke.lang.SerialNumberGenerator;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class UpdownController {

	private static final Log log = LogFactory.getLog(UpdownController.class);
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private ObjectMapper objectMapper;

	@ResponseBody
	@RequiredLogin(ResultTypeEnum.json)
	@RequestMapping(value = "/updown")
	public Result up(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "t") String type,
			@RequestParam(value = "a") int aid, @LoginMember Member u) {
		String _type = type.toLowerCase();
		if (!_type.equals("down") && !_type.equals("up")) {
			log.error("opertion is not up or donw");
			return new Result(false, "faild", "系统错误", "");
		}
		int uid = u.getId();
		try {
			// 1检查该会员今天是否顶沉过该内容
			int count = jdbcTemplate
					.queryForInt(
							"select count(1) from joke_article_updown where article_id = ? and member_id = ?",
							aid, uid);
			if (count > 0) {
				return new Result(false, "faild", "已投过票", null);
			}

			// 2检查此次操作应距上次10秒钟以上
			Calendar cc = Calendar.getInstance();
			cc.add(Calendar.SECOND, -10);
			int _count = jdbcTemplate
					.queryForInt(
							"select count(1) from joke_article_updown where member_id = ? and unix_timestamp(create_time) > ?",
							uid, cc.getTimeInMillis() / 1000);
			if (_count > 0) {
				return new Result(false, "faild", "慢一点，看完效果再点评吧", null);
			}
			// 3记为一次顶沉操作
			jdbcTemplate.update("update joke_article set " + _type + " = "
					+ _type + " +1 where id = ?", aid);
			jdbcTemplate
					.update("insert into joke_article_updown(article_id,member_id,type) values(?,?,?)",
							aid, uid, _type);
			// 4检查当日积分是否满额
			int scoreUpDownPerTime = Config.getInt("score_up_down_per_time", 1);
			int scoreUpDownMaxPerDay = Config.getInt(
					"score_up_down_max_per_day", 15);
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.clear(Calendar.MILLISECOND);
			Date today = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, 1);
			Date tomorrow = c.getTime();
			int scoreUpDownToday = jdbcTemplate
					.queryForInt(
							"select sum(wealth) from uc_account_log where member_id = ? "
									+ "and wealth_type = ? and account = ? and unix_timestamp(create_time) >= ? and unix_timestamp(create_time) < ?",
							uid, WealthType.UPDOWN.name(), Account.S1.name(),
							today.getTime() / 1000, tomorrow.getTime() / 1000);

			// 5如果不满额,发放积分
			if (scoreUpDownToday <= scoreUpDownMaxPerDay) {
				String[] serialNumber = SerialNumberGenerator.generate(1);
				// 生成中奖流水
				List<Map<String, Object>> logs = new ArrayList<Map<String, Object>>(
						2);
				Map<String, Object> l = new HashMap<String, Object>(1);
				l.put("u", uid);
				l.put("a", Account.S1);
				l.put("t", WealthType.UPDOWN);
				l.put("w", scoreUpDownPerTime);
				l.put("s", AccountLogStatus.PAYED.name());
				l.put("sn", serialNumber[0]);
				l.put("ssn", serialNumber[1]);
				l.put("r", "");
				l.put("o", "system");
				logs.add(l);

				// 调取后台接口发放奖品
				Map<String, String> params = new HashMap<String, String>(1);
				params.put("accountLog", objectMapper.writeValueAsString(logs));
				return HttpClientHelper.controlPlatPost("/pay", params);
			}
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "faild", "系统错误", null);
		}
		return new Result(true, "success", "投票成功", null);
	}
}