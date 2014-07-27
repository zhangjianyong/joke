package com.doumiao.joke.lang;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class Function {

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {// nginx
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String template(String templateContent,
			Map<String, Object> params) {
		for (String key : params.keySet()) {
			templateContent = templateContent.replaceAll(
					"\\$\\{" + key + "\\}", (String) params.get(key));
		}
		return templateContent;
	}

	public static String joinMap(Map<String, String[]> params) {
		if (params == null || params.size() < 1) {
			return StringUtils.EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			sb.append(key).append("=")
					.append(StringUtils.join(params.get(key), ","));
		}
		return sb.toString();
	}
}
