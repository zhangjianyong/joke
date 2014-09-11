package com.doumiao.joke.web;

import java.util.Map;

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

import com.doumiao.joke.enums.ArticleType;
import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.schedule.Config;

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
			@RequestParam(value = "inviter", defaultValue = "0", required = false) int inviter) {
		Map<String, Object> article=null;
		try{
		article = jdbcTemplate
				.queryForMap(
						"select a.*,m.nick,m.avatar from joke_article a,uc_member m where a.member_id = m.id and  a.id = ? and a.`status` = 2",
						id);
		}catch(EmptyResultDataAccessException edae){
			return "/404";
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
			int hours = 
					Config.getInt("inviter_cookie_time", 24);
			String domain = Config.get("cookie_domain","");
			CookieUtils.createCookie(response, domain, "inviter",
					String.valueOf(inviter), "/", hours * 3600, false);
		}

		request.setAttribute("article", article);
		request.setAttribute(
				"hots",
				jdbcTemplate
						.queryForList(
								"select id, pic from joke_article where type = ? and status = 2 order by up desc,id desc limit 0, 6",
								ArticleType.PIC.name()));
		request.setAttribute("nextId", nextId);
		request.setAttribute("preId", preId);
		return "/article";
	}
}