package com.doumiao.joke.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Ad {

	private static final Log log = LogFactory.getLog(Ad.class);
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private ObjectMapper objectMapper;

	@ResponseBody
	@RequestMapping(value = "/ad/{id}")
	public Map<String, Object> ad(HttpServletRequest request,
			HttpServletResponse response,@PathVariable int id) {
		try {
			return jdbcTemplate.queryForMap(
					"select * from ad_script where status = 0 and id = ?", id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
	}

	@RequestMapping(value = "/adshow")
	public String adShow(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = true) int id) {
		try {
			Map<String, Object> ad = jdbcTemplate.queryForMap(
					"select * from ad_script where status = 0 and id = ?", id);
			return (String) ad.get("tpl");
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
	}
}
