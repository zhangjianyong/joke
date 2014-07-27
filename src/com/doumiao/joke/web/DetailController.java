package com.doumiao.joke.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DetailController {
	private static final Log log = LogFactory.getLog(DetailController.class);
	@Resource
	DBUtils dbUtils;

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> article = dbUtils
				.getTemplate()
				.queryForMap(
						"select id,title,pic,summary,type,down,up,create_time from joke_article where  id = ?",
						id);
		int nextId = 0;
		try {
			nextId = dbUtils
					.getTemplate()
					.queryForInt(
							"select id from joke_article where id < ? order by id desc limit 0,1",
							id);
		} catch (EmptyResultDataAccessException e) {
		} catch (Exception e) {
			log.error(e, e);
		}
		int preId = 0;
		try {
			preId = dbUtils
					.getTemplate()
					.queryForInt(
							"select id from joke_article where id > ? order by id asc limit 0,1",
							id);
		} catch (EmptyResultDataAccessException e) {
		} catch (Exception e) {
			log.error(e, e);
		}
		request.setAttribute("article", article);
		request.setAttribute("hots", dbUtils.hots());
		request.setAttribute("nextId", nextId);
		request.setAttribute("preId", preId);
		return "/detail";
	}
}