package com.doumiao.joke.web.uc;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.doumiao.joke.schedule.Cache;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class ExchangeController {
	private static final Log log = LogFactory.getLog(ExchangeController.class);
	@Resource
	private JdbcTemplate jdbcTemplate;

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
			account = jdbcTemplate.queryForMap(
					"select s2 from uc_account where member_id = ?", uid);
		} catch (EmptyResultDataAccessException erdae) {
			log.error("user don't have account : " + uid);
		} catch (Exception e) {
			log.error(e, e);
		}
		Map<String, Object> alipayAccount = null;
		try {
			alipayAccount = jdbcTemplate
					.queryForMap(
							"select * from uc_thirdplat_account where member_id = ? and plat = ?",
							uid, Plat.ALIPAY.name());
		} catch (EmptyResultDataAccessException erdae) {
		} catch (Exception e) {
			log.error(e, e);
		}
		request.setAttribute("account", account);
		request.setAttribute("alipayAccount", alipayAccount);

		@SuppressWarnings("unchecked")
		Map<String, Map<String, Map<String, Object>>> adMap = (Map<String, Map<String, Map<String, Object>>>) Cache
				.get(Cache.Key.AD);
		request.setAttribute("ads", adMap.get("list"));
		return "/uc/exchange";
	}

	@RequiredLogin
	@ResponseBody
	@RequestMapping("/uc/i/alipay_exchange")
	public Result alipayExchange(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m,
			@RequestParam(value = "act") final String account,
			@RequestParam(value = "val") final int wealth) {

		String[] patterns = new String[] {
				"^(1[3-8])\\d{9}$",
				"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$" };
		boolean isAccount = false;
		for (String p : patterns) {
			Pattern pattern = Pattern.compile(p);
			Matcher matcher = pattern.matcher(account);
			if (matcher.find()) {
				isAccount = true;
				break;
			}
		}

		if (!isAccount) {
			return new Result(false, "account.error", "请填写正确的支付宝账户(手机号或邮箱).", null);
		}
		// 调取后台接口发放奖品
		Map<String, String> params = new HashMap<String, String>(3);
		params.put("uid", String.valueOf(m.getId()));
		params.put("account", account);
		params.put("wealth", String.valueOf(wealth));
		params.put("plat", Plat.ALIPAY.name());
		return HttpClientHelper.controlPlatPost("/exchange", params);
	}
}
