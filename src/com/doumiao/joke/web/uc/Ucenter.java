package com.doumiao.joke.web.uc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.annotation.RequiredLogin;
import com.doumiao.joke.annotation.ResultTypeEnum;
import com.doumiao.joke.enums.Account;
import com.doumiao.joke.schedule.Cache;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class Ucenter {
	private static final Log log = LogFactory.getLog(Ucenter.class);

	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequiredLogin
	@RequestMapping("/uc/score")
	public String score(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		int uid = m.getId();
		@SuppressWarnings("unchecked")
		Map<String, Object> account = MapUtils.EMPTY_MAP;
		try {
			account = jdbcTemplate.queryForMap(
					"select s1,s2 from uc_account where member_id = ?", uid);
		} catch (Exception e) {
			log.error(e, e);
			account.put("s1", "账户异常");
		}
		request.setAttribute("user", m);
		request.setAttribute("account", account);
		
		@SuppressWarnings("unchecked")
		Map<String, Map<String, Map<String, Object>>> adMap = (Map<String, Map<String, Map<String, Object>>>) Cache
				.get(Cache.Key.AD);
		request.setAttribute("ads", adMap.get("list"));
		return "/uc/score";
	}

	// 以下为接口
	@ResponseBody
	@RequiredLogin(ResultTypeEnum.json)
	@RequestMapping("/uc/i/score")
	public Result interfaceScore(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		int uid = m.getId();
		Map<String, Object> account = new HashMap<String,Object>();
		try {
			account = jdbcTemplate.queryForMap(
					"select s1,s2 from uc_account where member_id = ?", uid);
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "interface_score_faild", "获取用户积分失败", null);
		}

		int scoreUsePerDraw = Config.getInt("score_use_per_draw", 5);
		account.put("drawtimes",
				Math.ceil((Integer) account.get("s1") / scoreUsePerDraw));
		return new Result(true, "interface_score_success", "获取用户积分", account);
	}

	@RequiredLogin
	@ResponseBody
	@RequestMapping("/uc/i/scorelog")
	public Result scoreLog(
			HttpServletRequest request,
			HttpServletResponse response,
			@LoginMember Member m,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		Map<String, Object> pageInfo = new HashMap<String, Object>(2);
		int rowCount = 20;
		int uid = m.getId();
		try {
			int count = jdbcTemplate
					.queryForInt(
							"select count(1) from uc_account_log where account = ? and member_id = ?",
							Account.S2.name(), uid);
			int pagecount = count % rowCount == 0 ? count / rowCount : count
					/ rowCount + 1;
			pageInfo.put("pagecount", pagecount);
			pageInfo.put("rowcount", rowCount);
			pageInfo.put("page", page);
			List<Map<String, Object>> logs = jdbcTemplate
					.queryForList(
							"select wealth_type, account, wealth, wealth_balance, status, remark, create_time, wealth_time from uc_account_log "
									+ "where account = ? and member_id = ? order by id desc limit ?,?",
							Account.S2.name(), uid, (page - 1) * rowCount,
							rowCount);
			pageInfo.put("list", logs);
			return new Result(true, "score_load_ok", "积分加载成功", pageInfo);
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "score_load_faild", "积分加载出错", null);
		}
	}

	@RequiredLogin
	@ResponseBody
	@RequestMapping("/uc/i/thirdscorelog")
	public Result thirdAccountLog(
			HttpServletRequest request,
			HttpServletResponse response,
			@LoginMember Member m,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		Map<String, Object> pageInfo = new HashMap<String, Object>(2);
		int rowCount = 20;
		int uid = m.getId();
		try {
			int count = jdbcTemplate
					.queryForInt(
							"select count(1) from uc_thirdplat_account_log where member_id = ?",
							uid);
			int pagecount = count % rowCount == 0 ? count / rowCount : count
					/ rowCount + 1;
			pageInfo.put("pagecount", pagecount);
			pageInfo.put("rowcount", rowCount);
			pageInfo.put("page", page);
			List<Map<String, Object>> logs = jdbcTemplate.queryForList(
					"select * from uc_thirdplat_account_log "
							+ "where member_id = ? order by id desc limit ?,?",
					uid, (page - 1) * rowCount, rowCount);
			pageInfo.put("list", logs);
			return new Result(true, "score_load_ok", "积分加载成功", pageInfo);
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "score_load_faild", "积分加载出错", null);
		}
	}
}
