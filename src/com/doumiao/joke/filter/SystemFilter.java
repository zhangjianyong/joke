package com.doumiao.joke.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.schedule.Config;

public class SystemFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		request.setAttribute("config", Config.getConfig());
		String v = CookieUtils.readCookie((HttpServletRequest) request,
				"firstcome");
		boolean firstcome = false;
		if (v == null) {
			firstcome = true;
		}
		request.setAttribute("firstcome", firstcome);
		CookieUtils
				.createCookie((HttpServletResponse) response, (String) Config
						.get("cookie_domain"), "firstcome", null, "",
						Integer.parseInt((String) Config
								.get("new_user_show_div_days")) * 86400, false);
		filterChain.doFilter(request, response);
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
