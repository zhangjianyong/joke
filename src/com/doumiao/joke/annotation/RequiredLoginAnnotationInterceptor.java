package com.doumiao.joke.annotation;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.lang.Function;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Result;

public class RequiredLoginAnnotationInterceptor extends
		HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HandlerMethod handler2 = (HandlerMethod) handler;
		RequiredLogin login = handler2.getMethodAnnotation(RequiredLogin.class);
		if (null == login) {
			return true;
		}
		String loginuser_b64 = CookieUtils.readCookie(request, "loginuser");
		if (null == loginuser_b64) {
			if (login.value() == ResultTypeEnum.page) {
				Map<String, String[]> paramMap = request.getParameterMap();
				Map<String, String[]> params = new HashMap<String, String[]>();
				params.putAll(paramMap);
				params.put(
						"ref",
						new String[] { StringUtils.defaultIfBlank(
								request.getRequestURI(), "/") });
				String paramStr = Function.joinMap(params);
//				response.sendRedirect("/login?" + paramStr);
				response.sendRedirect(Config.get("system_website_url", "/"));
			} else if (login.value() == ResultTypeEnum.json) {
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=UTF-8");
				OutputStream out = response.getOutputStream();
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,
						"utf-8"));
				Result msg = new Result(true, "login_no", "请登录", "");
				pw.println(new ObjectMapper().writeValueAsString(msg));
				pw.flush();
				pw.close();
			}
			return false;
		}
		return true;
	}
}