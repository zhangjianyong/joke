package com.doumiao.joke.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.lang.HttpClientHelper;
import com.doumiao.joke.vo.Result;

@Controller
public class IdentifyCode {
	@ResponseBody
	@RequestMapping("/code")
	public Result code(HttpServletRequest request, HttpServletResponse response) {
		Result r = HttpClientHelper.controlPlatPost("/code", MapUtils.EMPTY_MAP);
		return r;
	}
}
