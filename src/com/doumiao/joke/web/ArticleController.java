package com.doumiao.joke.web;

import java.util.ArrayList;
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
import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.schedule.Cache;
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
			if (article.get("title") == null) {
				Object content = article.get("content");
				if (content != null) {

				} else {

				}
			}
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
		Random r = new Random();
		@SuppressWarnings("unchecked")
		Map<String, Map<String, Map<String, Object>>> adMap = (Map<String, Map<String, Map<String, Object>>>) Cache
				.get(Cache.Key.AD);
		Map<String, Map<String, Object>> articleAd = adMap.get("article");
		if (r.nextBoolean()) {
			Map<String, Object> o = articleAd.get("ad13");
			articleAd.put("ad13", articleAd.get("ad12"));
			articleAd.put("ad12", o);
		}
		request.setAttribute("article", article);
		request.setAttribute("ads", articleAd);

		List<Map<String, Object>> hots = new ArrayList<Map<String, Object>>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> l = (List<Map<String, Object>>) Cache
				.get(Cache.Key.HOT_TEXT);
		if (l != null && l.size() > 10) {
			Random rand = new Random();
			int seed = rand.nextInt(l.size() - 10);
			for (int i = 0; i < 10; i++) {
				hots.add(l.get(i + seed));
			}
		}

		/* 取出签到总次数 */
		int qCount = 0;
		try {
			qCount = jdbcTemplate.queryForInt(
					"select s3 from uc_account where member_id = ?", m.getId());
		} catch (EmptyResultDataAccessException e) {
			qCount = 0;
		} catch (Exception e) {
			log.error(e, e);
		}

		float ratio = Config.getInt("ad_change_ratio", 10);
		int rand = new Random().nextInt(100);
		if (m.getId() > 0 && qCount >= Config.getInt("updown_times", 100) && ratio >= rand) {
			request.setAttribute("interval",
					Config.getInt("ad_updownbutton_interval", 20));
		}
		request.setAttribute("hots_text", hots);
		request.setAttribute("nextId", nextId);
		request.setAttribute("preId", preId);
		request.setAttribute("upText", Cache.get(Cache.Key.HOT_TEXT));
		return "/article";
	}
}