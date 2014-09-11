package com.doumiao.joke.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doumiao.joke.enums.ArticleType;
import com.doumiao.joke.enums.OrderType;
import com.doumiao.joke.schedule.Config;

@Controller
public class ArticleListController {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String articles(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "t") String type,
			@RequestParam(value = "p", required = false, defaultValue = "1") int page,
			@RequestParam(value = "o", required = false, defaultValue = "new") String order)
			throws Exception {
		ArticleType typeE = null;
		OrderType orderE = null;
		// 验证链接有效性
		try {
			typeE = ArticleType.valueOf(type.toUpperCase());
			orderE = OrderType.valueOf(order.toUpperCase());
		} catch (Exception e) {
			return "/404";
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.clear(Calendar.MILLISECOND);
		Date today = c.getTime();
		List<Object> countParams = new ArrayList<Object>();
		List<Object> listParams = new ArrayList<Object>();
		String countSql = "select count(1) from joke_article a where a.`status` = ? ";
		String listSql = "select a.*,m.nick,m.avatar from joke_article a,uc_member m where a.member_id=m.id and a.`status` = ? ";
		countParams.add(2);
		listParams.add(2);
		if (ArticleType.ALL != typeE) {
			countSql += " and a.`type` = ? ";
			listSql += " and a.`type` = ? ";
			countParams.add(typeE.name());
			listParams.add(typeE.name());
		}
		if (OrderType.NEW != orderE) {
			countSql += " and unix_timestamp(a.create_time) >= ? and unix_timestamp(a.create_time) < ? ";
			listSql += " and unix_timestamp(a.create_time) >= ? and unix_timestamp(a.create_time) < ? ";
			c.add(Calendar.DAY_OF_MONTH, orderE.getValue());
			Date end = c.getTime();
			countParams.add(today.getTime() / 1000);
			countParams.add(end.getTime() / 1000);
			listParams.add(today.getTime() / 1000);
			listParams.add(end.getTime() / 1000);
		}

		if (OrderType.NEW == orderE) {
			listSql += " order by a.id desc limit ?,?";
		} else {
			listSql += " order by a.up desc limit ?,?";
		}

		int rows = Config.getInt("row_count_per_page",30);

		int count = jdbcTemplate.queryForInt(countSql, countParams.toArray());
		int pages = count / rows + 1;
		// 超过总页数,跳转到最后一页
		if (page > pages) {
			return "redirect:/" + typeE.name() + "/" + pages;
		}

		listParams.add((page - 1) * rows);
		listParams.add(rows);
		List<Map<String, Object>> articles = jdbcTemplate.queryForList(listSql,listParams.toArray());
		request.setAttribute("page", page);
		request.setAttribute("count", pages);
		request.setAttribute("articles", articles);
		request.setAttribute(
				"hots",
				jdbcTemplate
						.queryForList(
								"select * from joke_article where `type` = ? and `status` = 2 order by up desc,id desc limit 0, 6",
								ArticleType.PIC.name()));
		if(ArticleType.ALL == typeE && page==1){
			return "/index";
		}
		return "/list";
	}
}