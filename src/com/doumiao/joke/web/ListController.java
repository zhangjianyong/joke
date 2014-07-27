package com.doumiao.joke.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ListController {
	@Resource
	DBUtils dbUtils;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int rows = 30;
		List<Map<String, Object>> articles = dbUtils
				.getTemplate()
				.queryForList(
						"select id,title,pic,summary,type,down,up,create_time from joke_article order by id desc limit 0,?",rows);
		int count = dbUtils.getTemplate().queryForInt(
				"select count(1) from joke_article");
		request.setAttribute("page", 1);
		request.setAttribute("count", count / rows + 1);
		request.setAttribute("articles", articles);
		request.setAttribute("hots", dbUtils.hots());
		return "/list";
	}

	@RequestMapping(value = "/new/{page}", method = RequestMethod.GET)
	public String news(@PathVariable int page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (page <= 1) {
			return "redirect:/";
		}
		int rows = 30;
		int count = dbUtils.getTemplate().queryForInt(
				"select count(1) from joke_article");
		request.setAttribute("page", page);
		request.setAttribute("count", count / rows + 1);
		List<Map<String, Object>> articles = dbUtils
				.getTemplate()
				.queryForList(
						"select id,title,pic,summary,type,down,up,create_time from joke_article order by id desc limit ?,?",
						(page - 1) * rows, rows);
		request.setAttribute("articles", articles);
		request.setAttribute("hots", dbUtils.hots());
		return "/list";
	}
}