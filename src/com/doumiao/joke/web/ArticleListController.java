package com.doumiao.joke.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doumiao.joke.enums.ArticleType;
import com.doumiao.joke.schedule.Config;

@Controller
public class ArticleListController {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String articles(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "t") String type,
			@RequestParam(value = "p", required = false, defaultValue = "1") int page)
			throws Exception {
		ArticleType tyepE = ArticleType.valueOf(type.toUpperCase());
		if (tyepE == null) {
			return "redirect:/404";
		}
		int rows = Integer.parseInt(StringUtils.defaultIfBlank(
				Config.get("row_count_per_page"), "30"));
		int count = jdbcTemplate.queryForInt(
				"select count(1) from joke_article where type = ?",
				tyepE.name());
		int pages = count / rows + 1;
		if (page > pages) {
			return "redirect:/" + tyepE.name() + "/" + pages;
		}
		List<Map<String, Object>> articles = jdbcTemplate
				.queryForList(
						"select id, title, pic, content, type, down, up, create_time from joke_article where type = ? and is_show = 1 order by id desc limit ?,?",
						tyepE.name(), (page - 1) * rows, rows);
		request.setAttribute("page", page);
		request.setAttribute("count", pages);
		request.setAttribute("articles", articles);
		request.setAttribute("config", Config.getConfig());
		request.setAttribute(
				"hots",
				jdbcTemplate
						.queryForList(
								"select * from joke_article where type = ? order by up desc,id desc limit 0, 6",
								ArticleType.PIC.name()));
		return "/list";
	}
}