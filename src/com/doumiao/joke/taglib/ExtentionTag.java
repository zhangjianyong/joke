package com.doumiao.joke.taglib;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.doumiao.joke.lang.TemplateResponse;

public class ExtentionTag extends RequestContextAwareTag {
	private static final long serialVersionUID = -8834805201822247428L;
	private static final Log log = LogFactory.getLog(ExtentionTag.class);
	int eid;
	String var;

	public void setEid(int eid) {
		this.eid = eid;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public int doStartTagInternal() throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) this.getRequestContext()
				.getWebApplicationContext().getBean("jdbcTemplate");
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext
				.getResponse();
		JspWriter out = pageContext.getOut();
		Map<String, Object> extention = null;
		String content = null;
		try {
			extention = jdbcTemplate
					.queryForMap(
							"select * from joke_extentions where  id = ? and status = 0",
							eid);
			// 如没有模板
			String tpl = (String) extention.get("tpl");
			if (StringUtils.isBlank(tpl)) {
				content = (String) extention.get("content");
			} else {
				ByteArrayOutputStream buf = new ByteArrayOutputStream(3000);
				TemplateResponse tr = new TemplateResponse(response, buf);
				RequestDispatcher rd = request
						.getRequestDispatcher("/jsp/extentions/" + tpl + ".jsp");
				tr.setCharacterEncoding("UTF-8");
				rd.include(request, tr);
				tr.flushBuffer();
				content = buf.toString();
			}
		} catch (EmptyResultDataAccessException e) {
			content = StringUtils.EMPTY;
		} catch (Exception e) {
			log.error(e, e);
			content = StringUtils.EMPTY;
		}
		try {
			pageContext.setAttribute(var, content);
		} catch (Exception e) {
			log.error(e, e);
		}
		return Tag.SKIP_BODY;
	}
}
