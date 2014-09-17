package com.doumiao.joke.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.annotation.RequiredLogin;
import com.doumiao.joke.annotation.ResultTypeEnum;
import com.doumiao.joke.enums.ArticleType;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class PublishController {
	@Resource
	private JdbcTemplate jdbcTemplate;
	Log log = LogFactory.getLog(PublishController.class);
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public String detail(
			HttpServletRequest request,
			HttpServletResponse response) {
		return "/publish";
	}
	
	@ResponseBody
	@RequiredLogin(ResultTypeEnum.json)
	@RequestMapping(value = "/i/publish", method = RequestMethod.POST)
	public Result publish(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "picpath", required = false) String picpath,
			@RequestParam(value = "type") String type,
			@LoginMember Member m) {
		ArticleType typeE = null;
		// 验证参数有效性
		try {
			typeE = ArticleType.valueOf(type.toUpperCase());
		} catch (Exception e) {
			return new Result(false, "article.type.not.exist", "类型不正确", null);
		}

		if (StringUtils.isBlank(title)) {
			return new Result(false, "article.title.empty", "标题不能为空", null);
		}
		try{
			// 图片笑话
			if (typeE.equals(ArticleType.PIC)) {
				if (StringUtils.isBlank(picpath)) {
					return new Result(false, "article.picpath.empty", "图片不能为空",
							null);
				}
				jdbcTemplate.update("insert into joke_article(title,pic,type,member_id,status) values(?,?,?,?,?)",title,picpath,typeE.name(),m.getId(),0);
			}
			// 文本笑话
			if (typeE.equals(ArticleType.TEXT)) {
				if (StringUtils.isBlank(content)) {
					return new Result(false, "article.content.empty", "内容不能为空",
							null);
				}
				jdbcTemplate.update("insert into joke_article(title,content,type,member_id,status) values(?,?,?,?,?)",title,content,typeE.name(),m.getId(),2);
			}
		}catch(Exception e){
			log.error(e,e);
			return new Result(false, "faild", "发布失败", null);
		}
		return new Result(true, "success", "发布成功", null);
	}
}