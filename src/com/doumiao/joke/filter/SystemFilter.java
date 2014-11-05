package com.doumiao.joke.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.schedule.Cache;
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
			CookieUtils
			.createCookie((HttpServletResponse) response, Config
					.get("cookie_domain",""), "firstcome", "t", "/",
					Config
							.getInt("new_user_show_div_days",1) * 24*60*60, false);
		}
		request.setAttribute("firstcome", firstcome);
		@SuppressWarnings("unchecked")
		Map<String, Map<String,Map<String, Object>>> adMap = (Map<String, Map<String,Map<String, Object>>>) Cache
				.get(Cache.Key.AD);
		request.setAttribute("footAds", adMap.get("foot"));
		filterChain.doFilter(request, response);
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
