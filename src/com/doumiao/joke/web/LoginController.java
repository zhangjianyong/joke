package com.doumiao.joke.web;

import java.io.ByteArrayOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.lang.TemplateResponse;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Result;

@Controller
public class LoginController {
	private static final Log log = LogFactory.getLog(LoginController.class);

	@ResponseBody
	@RequestMapping(value = "/login")
	public Result login(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "p", defaultValue = "login", required = false) String page) {
		try {
			ByteArrayOutputStream buf = new ByteArrayOutputStream(3000);
			TemplateResponse tr = new TemplateResponse(response, buf);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/div/"
					+ page + ".jsp");
			request.setAttribute("control", Config.get("system_control_url",""));
			tr.setCharacterEncoding("UTF-8");
			rd.include(request, tr);
			tr.flushBuffer();
			return new Result(true, "login_page_ok", "登录页加载成功", buf.toString());
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "login_page_faild", "登录页加载失败", "");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Result loginOut(HttpServletRequest request,
			HttpServletResponse response) {
		String domain = Config.get("system_domain","");
		CookieUtils.deleteCookie(response, domain, "loginuser");
		CookieUtils.deleteCookie(response, domain, "user");
		return new Result(true, "login_out_ok", "退出登录", "");
	}
}