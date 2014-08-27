package com.doumiao.joke.annotation;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.doumiao.joke.coder.DESCoder;
import com.doumiao.joke.lang.CookieUtils;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Member;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

	@Resource
	private ObjectMapper objectMapper;
	
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
    	return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annotation : annotations) {
            if (LoginMember.class.isInstance(annotation)) {
                HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
                String loginuser_b64 = CookieUtils.readCookie(request, "loginuser");
        		Member loginUser = null;
    			String charset = StringUtils.defaultIfBlank(
    					Config.get("system_charset"), "utf-8");
    			String key = Config.get("system_cookie_key");
        		if (loginuser_b64 != null) {
        			try {
        				byte[] loginuser_c = DESCoder.decryptBASE64(loginuser_b64
        						.getBytes(charset));
        				byte[] loginuser = DESCoder.decrypt(loginuser_c,
        						key.getBytes(charset));
        				loginUser = objectMapper.readValue(loginuser, Member.class);
        				loginUser.setNick(URLDecoder.decode(loginUser.getNick(),charset));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		}
        		//loginUser = new Member();
        		//loginUser.setId(1);
        		return loginUser;
            }
        }
        return null;
    }
}