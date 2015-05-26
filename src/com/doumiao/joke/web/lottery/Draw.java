package com.doumiao.joke.web.lottery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.enums.Account;
import com.doumiao.joke.enums.AccountLogStatus;
import com.doumiao.joke.enums.WealthType;
import com.doumiao.joke.lang.HttpClientHelper;
import com.doumiao.joke.schedule.Cache;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class Draw {

	private static final Log log = LogFactory.getLog(Draw.class);
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	ObjectMapper objectMapper;

	@RequestMapping(value = "/lottery/draw")
	public String show(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		request.setAttribute("config", Config.getConfig());
		@SuppressWarnings("unchecked")
		Map<String, Map<String, Map<String, Object>>> adMap = (Map<String, Map<String, Map<String, Object>>>) Cache
				.get(Cache.Key.AD);
		Map<String, Map<String, Object>> drawAd = adMap.get("draw");
		request.setAttribute("ads", drawAd);

		List<Map<String, Object>> hots = new ArrayList<Map<String, Object>>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> l = (List<Map<String, Object>>) Cache
				.get(Cache.Key.HOT_PIC);
		if (l != null && l.size() > 2) {
			Random rand = new Random();
			int seed = rand.nextInt(l.size() - 2);
			for (int i = 0; i < 2; i++) {
				hots.add(l.get(i + seed));
			}
		}
		request.setAttribute("hots", hots);
		request.setAttribute("draws", Cache.get(Cache.Key.LOTTERY_LOG));
		return "/lottery/draw";
	}

	@ResponseBody
	@RequestMapping(value = "/lottery/i/draw")
	public Result go(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "key") String key, @LoginMember Member m) {
		log.info("lottery draw:" + m.getId());
		if (StringUtils.isBlank(code)) {
			return new Result(false, "faild", "请输入", null);
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("code", code);
		param.put("key", key);
		Result checkCode = HttpClientHelper
				.controlPlatPost("/checkCode", param);
		if (!checkCode.isSuccess()) {
			return checkCode;
		}
		int uid = m.getId();
		String group = "1";
		int drawCountPerDay = Config.getInt("draw_count_per_day", 3);
		int scoreUsePerDraw = Config.getInt("score_use_per_draw", 5);
		try {
			int drawTimesTotal = jdbcTemplate.queryForInt(
					"select s1 from uc_account where member_id = ?", uid);
			if (drawTimesTotal < scoreUsePerDraw) {
				return new Result(false, "faild",
						"你目前没有抽奖机会,点评10个笑话，可获得1次抽奖机会", null);
			}
			// 验证是否有抽奖资格
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.clear(Calendar.MILLISECOND);
			Date today = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, 1);
			Date tomorrow = c.getTime();
			int drawTimesToday = jdbcTemplate
					.queryForInt(
							"select count(1) from uc_account_log where member_id = ? "
									+ "and wealth_type = ? and account = ? and unix_timestamp(create_time) >= ? and unix_timestamp(create_time) < ?",
							uid, WealthType.DRAW.name(), Account.S1.name(),
							today.getTime() / 1000, tomorrow.getTime() / 1000);
			if (drawTimesToday >= drawCountPerDay) {
				return new Result(false, "faild", "每天可抽奖" + drawCountPerDay
						+ "次", null);
			}

			// 读取奖品及中奖率
			List<Map<String, Object>> options = jdbcTemplate
					.queryForList(
							"select * from uc_lottery_draw where `group` = ? and chance >= 0",
							group);
			int size = options.size();
			Map<Integer, Map<String, Object>> awards = new HashMap<Integer, Map<String, Object>>(
					size);
			float chance_array[][] = new float[size][3];
			float end = 0.0f;
			float[] defaultChance = new float[3];
			int defaultChanceCount = 0;
			float chanceSum = 0;
			for (int i = 0; i < size; i++) {
				Map<String, Object> o = options.get(i);
				float chance = (Float) o.get("chance");
				chanceSum += chance;
				int awardId = (Integer) o.get("id");
				awards.put(awardId, o);
				chance_array[i][0] = end;
				end += chance;
				chance_array[i][1] = end;
				chance_array[i][2] = awardId;
				if (chance == 0) {
					defaultChanceCount++;
					defaultChance = chance_array[i];
				}
			}
			if (defaultChanceCount > 1 || chanceSum > 100) {
				log.error("default chance must less than 1. chance sum must less than 100. draw group:"
						+ group);
				return new Result(false, "faild", "抽奖失败,奖励设置不正确", null);
			}
			if (defaultChanceCount == 0 && chanceSum < 100) {
				log.error("must have default chance,when chance sum less than 100. draw group:"
						+ group);
				return new Result(false, "faild", "抽奖失败,奖励设置不正确", null);
			}
			if (defaultChanceCount == 1) {
				defaultChance[0] = end;
				defaultChance[1] = 100.0f;
			}

			// 开始抽奖
			double result = Math.random() * 100;
			int awardId = 0;
			for (int i = 0; i < chance_array.length; i++) {
				float[] chance = chance_array[i];
				if (result >= chance[0] && result < chance[1]) {
					awardId = (int) chance[2];
					break;
				}
			}
			Map<String, Object> award = awards.get(awardId);
			int wealth = Integer.parseInt((String) award.get("value"));

			// 生成中奖流水
			Map<String, String> params = new HashMap<String, String>(2);
			List<Map<String, Object>> accountLog = new ArrayList<Map<String, Object>>(
					1);
			Map<String, Object> l = new HashMap<String, Object>(1);
			l.put("u", uid);
			l.put("a", Account.S1);
			l.put("t", WealthType.DRAW);
			l.put("w", -scoreUsePerDraw);
			l.put("s", AccountLogStatus.PAYED.name());
			l.put("r", "");
			l.put("o", "system");
			accountLog.add(l);
			if (wealth != 0) {
				Map<String, Object> _l = new HashMap<String, Object>(1);
				_l.put("u", uid);
				_l.put("a", Account.S2);
				_l.put("t", WealthType.DRAW);
				_l.put("w", wealth);
				_l.put("s", AccountLogStatus.PAYED.name());
				_l.put("r", "");
				_l.put("o", "system");
				accountLog.add(_l);
			}
			
			params.put("accountLog",
					objectMapper.writeValueAsString(accountLog));
			// 调取后台接口发放奖品
			Result r = HttpClientHelper.controlPlatPost("/pay", params);
			if (r.isSuccess()) {
				return new Result(true, "success", "抽奖成功", award);
			} else {
				return new Result(false, "faild", "系统错误,请联系管理员", null);
			}
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "faild", "系统错误,请联系管理员", null);
		}
	}
}
