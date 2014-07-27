package com.doumiao.joke.web.thirdplat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.coder.DESCoder;
import com.doumiao.joke.enums.Plat;
import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.web.DBUtils;
import com.doumiao.joke.web.thirdplat.alipay.services.AlipayOauth;

@Controller
public class AlipayLogin {
	private static final Log log = LogFactory.getLog(AlipayLogin.class);

	@Resource
	private DBUtils dbUtils;
	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private Map<String, String> config;

	@RequestMapping("/alipaybind")
	public String bind(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "t", required = false) String target)
			throws IOException {
		try {
			String sessionId = request.getSession().getId();
			String referer = request.getHeader("Referer");
			target = (target == null ? referer : target);
			dbUtils.getTemplate()
					.update("insert into joke_thirdplat_redirect(session,target,plat) values(?,?,?) on duplicate key update target=?",
							sessionId, target, "alipay", target);
			String authUrl = new AlipayOauth().authorizeUrl(request);
			return "redirect:" + authUrl;
		} catch (Exception e) {
			log.error(e, e);
			return "redirect:/alipaybindfaild?t=" + target;
		}
	}

	@RequestMapping("/alipaybindafter")
	public String bindAfter(HttpServletRequest request,
			HttpServletResponse response,@LoginMember Member m) throws IOException {
		String sessionId = request.getSession().getId();
		String target = "/";
		try {
			target = dbUtils
					.getTemplate()
					.queryForObject(
							"select target from joke_thirdplat_redirect where session = ?",
							String.class, sessionId);
		} catch (EmptyResultDataAccessException erdae) {
		} catch (Exception e) {
			log.error(e, e);
		}
		Map<String, String> userInfo = new AlipayOauth().userInfo(request);
		if (userInfo == null) {
			return "redirect:/alipaybindfaild?t=" + target;
		}

		String openId = userInfo.get("user_id");
		String token = userInfo.get("token");
		Map<String, String> params = new HashMap<String, String>(3);
		params.put("ext1", userInfo.get("real_name"));
		params.put("ext2", userInfo.get("email"));
		int memberId = 0;
		try {
			memberId = dbUtils
					.getTemplate()
					.queryForInt(
							"SELECT member_id as id FROM uc_member_thirdplat  WHERE plat=? and open_id=? and token=?",
							Plat.ALIPAY.toString(), openId, token);
		} catch (EmptyResultDataAccessException erdae) {
		} catch (DataAccessException e) {
			log.error(e, e);
			return "redirect:/alipaybindfaild?t=" + target;
		}
		
		Member u = new Member();
		if(memberId != 0){
			if(m!=null && m.getId()!=memberId){
				return "redirect:/alipaybindfaild?t=" + target;
			}
			u.setId(memberId);
		} else {
			if(m!=null){
				u.setId(m.getId());
			}else{
				u = dbUtils.bindThirdPlat(u, Plat.ALIPAY, openId, token, params);
				if (u == null) {
					return "redirect:/alipaybindfaild?t=" + target;
				}
			}
		}
		
		try {
			String key = config.get("cookie_key");
			String charset = config.get("charset");
			String domain = config.get("domain");
			String json = objectMapper.writeValueAsString(u);
			byte[] des = DESCoder.encrypt(json.getBytes(charset),
					key.getBytes(charset));
			byte[] cookie_b = DESCoder.encryptBASE64(des);
			CookieUtils.createCookie(response, domain, "loginuser", new String(
					cookie_b, charset), "/", 86400, false);
			CookieUtils.createCookie(response, domain, "user", json, "/",
					Integer.MAX_VALUE, false);
		} catch (Exception e) {
			log.error(e, e);
			return "redirect:/alipaybindfaild?t=" + target;
		}
		return "redirect:" + target;
	}

	@RequestMapping("/alipaybindfaild")
	public String qqbindfaild(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return "/thirdplat/alipay_login_faild";
	}

}
