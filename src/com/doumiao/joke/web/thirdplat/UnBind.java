package com.doumiao.joke.web.thirdplat;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.annotation.RequiredLogin;
import com.doumiao.joke.enums.Plat;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;
import com.doumiao.joke.web.DBUtils;

@Controller
public class UnBind {
	private static final Log log = LogFactory.getLog(UnBind.class);

	@Resource
	private DBUtils dbUtils;

	@RequiredLogin
	@RequestMapping(value ="/unbind/{plat}")
	public Result bind(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String plat,
			@LoginMember Member m) throws IOException {
		try {
			dbUtils.getTemplate()
					.update("delete from uc_member_thirdplat where member_id = ? and plat = ?",
							m.getId(), plat);
		} catch (Exception e) {
			log.error(e,e);
			return new Result(false, "unbind_error", "第三方账号解绑出错", null);
		}
		return new Result(true, "unbind_success", "第三方账号解绑成功", null); 
	}
}
