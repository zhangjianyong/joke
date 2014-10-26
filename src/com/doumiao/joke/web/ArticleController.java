package com.doumiao.joke.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.enums.Account;
import com.doumiao.joke.enums.WealthType;
import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Member;

@Controller
public class ArticleController {
	private static final Log log = LogFactory.getLog(ArticleController.class);
	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public String detail(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id") int id,
			@RequestParam(value = "inviter", defaultValue = "0", required = false) int inviter,
			@LoginMember Member m) {
		Map<String, Object> article = null;
		try {
			article = jdbcTemplate
					.queryForMap(
							"select a.*,m.nick,m.avatar from joke_article a,uc_member m where a.member_id = m.id and  a.id = ? and a.`status` = 2",
							id);
		} catch (EmptyResultDataAccessException edae) {
			return "/404";
		} catch (Exception e) {
			log.error(e, e);
		}
		int nextId = 0;
		try {
			nextId = jdbcTemplate
					.queryForInt(
							"select id from joke_article where id < ? and status = 2 order by id desc limit 0,1",
							id);
		} catch (EmptyResultDataAccessException e) {
		} catch (Exception e) {
			log.error(e, e);
		}
		int preId = 0;
		try {
			preId = jdbcTemplate
					.queryForInt(
							"select id from joke_article where id > ? and status = 2 order by id asc limit 0,1",
							id);
		} catch (EmptyResultDataAccessException e) {
		} catch (Exception e) {
			log.error(e, e);
		}
		// 记录邀请人

		if (inviter > 0) {
			String domain = Config.get("cookie_domain");
			int hours = Config.getInt("inviter_cookie_time", 24);
			CookieUtils.createCookie(response, domain, "inviter",
					String.valueOf(inviter), "/", hours * 3600, false);
		}

		// 广告
		List<Map<String, Object>> ads = jdbcTemplate
				.queryForList("select * from ad_script where id in(6,7,8,11,12,13)");
		Map<Object, Map<String, Object>> adMap = new HashMap<Object, Map<String, Object>>();
		Random r = new Random();
		for (Map<String, Object> ad : ads) {
			adMap.put("ad" + ad.get("id"), ad);
		}
		if (r.nextBoolean()) {
			Map<String, Object> o = adMap.get("ad13");
			adMap.put("ad13", adMap.get("ad12"));
			adMap.put("ad12", o);
		}
		// 评论及抽奖次数
		if (m != null && m.getId() > 0) {
			request.setAttribute(
					"draw_times",
					jdbcTemplate
							.queryForInt(
									"select count(1) from uc_account_log where member_id = ? and account = ? and wealth_type = ?",
									m.getId(), Account.S1.name(),
									WealthType.DRAW.name()));
		}
		request.setAttribute("article", article);
		request.setAttribute("ads", adMap);
		request.setAttribute(
				"hots",
				jdbcTemplate
						.queryForList("select a.*,m.nick,m.avatar from joke_article a,uc_member m where a.member_id=m.id and a.`status` = 2 order by up desc,id desc limit 0, 2"));
		request.setAttribute("nextId", nextId);
		request.setAttribute("preId", preId);
		return "/article";
	}
}