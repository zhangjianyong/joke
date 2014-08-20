package com.doumiao.joke.web;

import java.io.ByteArrayOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.lang.TemplateResponse;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class ShareController {
	private static final Log log = LogFactory.getLog(ShareController.class);

	@ResponseBody
	@RequestMapping(value = "/share")
	public Result share(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		try {
			ByteArrayOutputStream buf = new ByteArrayOutputStream(3000);
			TemplateResponse tr = new TemplateResponse(response, buf);
			RequestDispatcher rd = request
					.getRequestDispatcher("/jsp/share_div.jsp");
			tr.setCharacterEncoding("UTF-8");
			request.setAttribute("website", Config.get("system_website_url"));
			request.setAttribute("uid", m==null?0:m.getId());
			rd.include(request, tr);
			tr.flushBuffer();
			return new Result(true, "success", "加载成功", buf.toString());
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "faild", "加载失败", "");
		}
	}
}