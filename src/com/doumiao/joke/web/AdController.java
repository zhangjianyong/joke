package com.doumiao.joke.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.doumiao.joke.vo.Result;

@Controller
public class AdController {

	private static final Log log = LogFactory.getLog(AdController.class);
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
	@ResponseBody
	@RequestMapping(value = "/ads/{_id}")
	public Result ads(HttpServletRequest request,
			HttpServletResponse response,@PathVariable String _id) {
		String[] ids = _id.split("_");
		List<Map<String,Object>> adList = ListUtils.EMPTY_LIST;
		try {
			adList = jdbcTemplate.queryForList(
					"select * from ad_script where status = 0 and id in ("+StringUtils.join(ids,",")+")");
		} catch (EmptyResultDataAccessException e) {
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false,"system_error","系统错误",null);
		}
		return new Result(true,"success","获取广告成功",adList);
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
