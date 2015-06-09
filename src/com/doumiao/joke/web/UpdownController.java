package com.doumiao.joke.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.annotation.RequiredLogin;
import com.doumiao.joke.annotation.ResultTypeEnum;
import com.doumiao.joke.lang.HttpClientHelper;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class UpdownController {

	private static final Log log = LogFactory.getLog(UpdownController.class);
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private ObjectMapper objectMapper;

	@ResponseBody
	@RequiredLogin(ResultTypeEnum.json)
	@RequestMapping(value = "/updown")
	public Result up(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "t") String type,
			@RequestParam(value = "a") String aid, @LoginMember Member u) {
		String _type = type.toLowerCase();
		if (!_type.equals("down") && !_type.equals("up")) {
			log.error("opertion is not up or donw");
			return new Result(false, "faild", "系统错误", "");
		}
		// 调取后台接口点评
		Map<String, String> params = new HashMap<String, String>(3);
		params.put("type", _type);
		params.put("articleId", aid);
		params.put("uid", String.valueOf(u.getId()));
		return HttpClientHelper.controlPlatPost("/updown", params);
	}
}