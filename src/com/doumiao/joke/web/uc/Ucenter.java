package com.doumiao.joke.web.uc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.annotation.RequiredLogin;
import com.doumiao.joke.annotation.ResultTypeEnum;
import com.doumiao.joke.enums.Account;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;
import com.doumiao.joke.web.DBUtils;

@Controller
public class Ucenter {
	private static final Log log = LogFactory.getLog(Ucenter.class);
	@Resource
	private DBUtils dbUtils;

	// @RequiredLogin(ResultTypeEnum.json)
	@RequestMapping("/ucenter/info")
	public String info(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		request.setAttribute("user", m);
		return "/uc/info";
	}

	@RequiredLogin
	@RequestMapping("/ucenter/score")
	public String score(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		int uid = m.getId();
		Map<String, Object> account = dbUtils.getTemplate().queryForMap(
				"select s1,s2 from uc_account where member_id = ?", uid);
		int uncheck = dbUtils
				.getTemplate()
				.queryForInt(
						"select sum(wealth) from uc_account_log_tmp where account = ? and member_id = ?",
						Account.S2.name(), uid);
		account.put("uncheck", uncheck);
		request.setAttribute("user", m);
		request.setAttribute("account", account);
		return "/uc/score";
	}

	@RequiredLogin
	@ResponseBody
	@RequestMapping("/ucenter/scorelog")
	public Result scoreLog(
			HttpServletRequest request,
			HttpServletResponse response,
			@LoginMember Member m,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		Result result = new Result();
		Map<String, Object> pageInfo = new HashMap<String, Object>(2);
		result.setContent(pageInfo);
		int rowCount = 20;
		int uid = m.getId();
		try {
			int count = dbUtils.getTemplate().queryForInt(
					"select count(1) from uc_account_log where account = ? and member_id = ?",
					Account.S2.name(), uid);
			int pagecount = count % rowCount == 0 ? count / rowCount : count
					/ rowCount + 1;
			pageInfo.put("pagecount", pagecount);
			pageInfo.put("rowcount", rowCount);
			pageInfo.put("page", page);
			List<Map<String, Object>> logs = dbUtils
					.getTemplate()
					.queryForList(
							"select wealth_type, account, wealth, wealth_balance, status, remark, create_time, wealth_time from uc_account_log "
									+ "where account = ? and member_id = ? order by id desc limit ?,?",
									Account.S2.name(), uid, (page - 1) * rowCount, rowCount);
			pageInfo.put("list", logs);
			result.setResult(true);
		} catch (Exception e) {
			log.error(e, e);
			result.setResult(false);
			result.setCode("score_load_faild");
			result.setCode("积分加载出错");
		}

		return result;
	}

	@RequiredLogin
	@RequestMapping("/ucenter/delayscore")
	public String scoredelay(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		return "/uc/delay_score";
	}

	@RequiredLogin
	@RequestMapping("/ucenter/exchange")
	public String exchange(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		int uid = m.getId();
		Map<String, Object> account = null;
		try {
			account = dbUtils.getTemplate().queryForMap(
					"select s1 from uc_account where member_id = ?", uid);
		} catch (EmptyResultDataAccessException erdae) {
			log.error("user don't have account : " + uid);
		} catch (Exception e) {
			log.error(e, e);
		}
		Map<String, Object> alipay = null;
		try {
			alipay = dbUtils
					.getTemplate()
					.queryForMap(
							"select ext1, ext2 from uc_member_thirdplat where member_id = ? and plat = 'alipay'",
							uid);
		} catch (EmptyResultDataAccessException erdae) {
		} catch (Exception e) {
			log.error(e, e);
		}
		request.setAttribute("account", account);
		request.setAttribute("alipay", alipay);
		return "/uc/exchange";
	}

	@RequiredLogin
	@RequestMapping("/ucenter/binding")
	public String binding(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		return "/uc/binding";
	}

	// 以下为接口
	@ResponseBody
	@RequiredLogin(ResultTypeEnum.json)
	@RequestMapping("/ucenter/interface/score")
	public Result interfaceScore(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		int uid = m.getId();
		Map<String, Object> account = dbUtils.getTemplate().queryForMap(
				"select s1,s2 from uc_account where member_id = ?", uid);
		int uncheck = dbUtils
				.getTemplate()
				.queryForInt(
						"select sum(wealth) from uc_account_log_tmp where account = ? and member_id = ?",
						Account.S2.name(), uid);
		account.put("uncheck", uncheck);
		account.put("drawtimes",Math.ceil((Integer)account.get("s1")/5));
		return new Result(true, "interface_score_success", "获取用户积分", account);
	}
}
