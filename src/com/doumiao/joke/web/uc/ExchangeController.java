package com.doumiao.joke.web.uc;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.annotation.RequiredLogin;
import com.doumiao.joke.enums.Plat;
import com.doumiao.joke.lang.HttpClientHelper;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class ExchangeController {
	private static final Log log = LogFactory.getLog(ExchangeController.class);
	@Resource
	private JdbcTemplate jbcTemplate;

	@Resource
	private DataSource dataSource;

	@Resource
	private PlatformTransactionManager transactionManager;

	@RequiredLogin
	@RequestMapping("/uc/exchange")
	public String exchange(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		int uid = m.getId();
		Map<String, Object> account = null;
		try {
			account = jbcTemplate.queryForMap(
					"select s2 from uc_account where member_id = ?", uid);
		} catch (EmptyResultDataAccessException erdae) {
			log.error("user don't have account : " + uid);
		} catch (Exception e) {
			log.error(e, e);
		}
		Map<String, Object> alipayAccount = null;
		try {
			alipayAccount = jbcTemplate
					.queryForMap(
							"select * from uc_thirdplat_account where member_id = ? and plat = ?",
							uid, Plat.ALIPAY.name());
		} catch (EmptyResultDataAccessException erdae) {
		} catch (Exception e) {
			log.error(e, e);
		}
		request.setAttribute("account", account);
		request.setAttribute("alipayAccount", alipayAccount);
		request.setAttribute("config", Config.getConfig());
		return "/uc/exchange";
	}

	@RequiredLogin
	@ResponseBody
	@RequestMapping("/uc/i/alipay_exchange")
	public Result alipayExchange(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m,
			@RequestParam(value = "act") final String account,
			@RequestParam(value = "val") final int wealth) {
		// 调取后台接口发放奖品
		Map<String, String> params = new HashMap<String, String>(3);
		params.put("uid", String.valueOf(m.getId()));
		params.put("account", account);
		params.put("wealth", String.valueOf(wealth));
		try {
			HttpClientHelper.controlPlatPost("/alipay_exchange", params);
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "faild", "对换失败", null);
		}
		return new Result(true, "success", "对换成功", null);
	}
}
