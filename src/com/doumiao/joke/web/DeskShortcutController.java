package com.doumiao.joke.web;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Member;

@Controller
public class DeskShortcutController {
	private static final Log log = LogFactory.getLog(DeskShortcutController.class);

	@ResponseBody
	@RequestMapping(value = "/shortcut")
	public void share(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		try {
			response.setHeader("Content-type","application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=YiXiaoQianJin.url");
			BufferedWriter sw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			sw.write("[DEFAULT]");
			sw.newLine();
			
			sw.write("BASEURL=");
			sw.write(Config.get("system_website_url"));
			sw.newLine();
			
			sw.write("[InternetShortcut]");
			sw.newLine();
			
			sw.write("URL=");
			sw.write(Config.get("system_website_url"));
			sw.write("?desk");
			sw.newLine();
			
			sw.write("IconFile=");
			sw.write(Config.get("system_website_url"));
			sw.write("/favicon.ico");
			sw.newLine();
			
			sw.write("IconIndex=1");
			sw.newLine();
			
			sw.write("[{000214A0-0000-0000-C000-000000000046}]");
			sw.newLine();
			
			sw.write("Prop3=19,2");
			sw.newLine();
			
			sw.flush();
			sw.close();
		} catch (Exception e) {
			log.error(e, e);
		}
	}
}