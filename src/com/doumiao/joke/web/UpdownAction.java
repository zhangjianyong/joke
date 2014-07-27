package com.doumiao.joke.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.annotation.RequiredLogin;
import com.doumiao.joke.annotation.ResultTypeEnum;
import com.doumiao.joke.enums.Account;
import com.doumiao.joke.enums.AccountLogStatus;
import com.doumiao.joke.enums.WealthType;
import com.doumiao.joke.lang.SerialNumberGenerator;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Controller
public class UpdownAction {

	private static final Log log = LogFactory.getLog(UpdownAction.class);
	@Resource
	DBUtils dbUtils;

	@ResponseBody
	@RequiredLogin(ResultTypeEnum.json)
	@RequestMapping(value = "/up/{aid}")
	public Result up(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int aid, @LoginMember Member u) {
		int uid = u.getId();
		try {
			int count = dbUtils
					.getTemplate()
					.queryForInt(
							"select count(1) from joke_article_updown where article_id = ? and member_id = ?",
							aid, uid);
			// 已顶沉过
			if (count > 0) {
				return new Result(false, "up_before", "已投过票", "");
			}
			dbUtils.getTemplate().update(
					"update joke_article set up = up +1 where id = ?", aid);
			dbUtils.getTemplate()
					.update("insert into joke_article_updown(article_id,member_id,type) values(?,?,?)",
							aid, uid, 0);
			// 发放积分
			String[] serialNumber = SerialNumberGenerator.generate(1);
			dbUtils.sendScore(uid, Account.S1, WealthType.UPDOWN, 1,
					AccountLogStatus.VALID, serialNumber[0], serialNumber[1], "",
					"00000000");
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "up_no", "系统错误", "");
		}
		return new Result(true, "up_ok", "投票成功", "");
	}

	@ResponseBody
	@RequiredLogin(ResultTypeEnum.json)
	@RequestMapping("/down/{aid}")
	public Result down(HttpServletRequest request,
			HttpServletResponse response, @PathVariable int aid,
			@LoginMember Member u) {
		int uid = u.getId();
		try {
			int count = dbUtils
					.getTemplate()
					.queryForInt(
							"select count(1) from joke_article_updown where article_id = ? and member_id = ?",
							aid, uid);
			// 已顶沉过
			if (count > 0) {
				return new Result(false, "down_before", "已投过票", "");
			}
			dbUtils.getTemplate().update(
					"update joke_article set down = down +1 where id = ?", aid);
			dbUtils.getTemplate()
					.update("insert into joke_article_updown(article_id,member_id,type) values(?,?,?)",
							aid, uid, 1);
			// 发放积分
			String[] serialNumber = SerialNumberGenerator.generate(1);
			dbUtils.sendScore(uid, Account.S1, WealthType.UPDOWN, 1,
					AccountLogStatus.VALID, serialNumber[0], serialNumber[1], "",
					"00000000");
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "down_no", "系统错误", "");
		}
		return new Result(true, "down_ok", "投票成功", "");
	}
}