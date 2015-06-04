package com.doumiao.joke.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doumiao.joke.enums.ArticleType;
import com.doumiao.joke.enums.OrderType;
import com.doumiao.joke.schedule.Cache;
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
			@RequestParam(value = "o", required = false, defaultValue = "new") String order,
			@RequestParam(value = "pos", required = false, defaultValue = "") String pos,
			@RequestParam(value = "adv", required = false, defaultValue = "") String adv)
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
		String countSql = "SELECT count(1) FROM `joke_article` WHERE `status` = ? ";
		String listSql = "SELECT `id` FROM `joke_article` WHERE `status` = ? ";
		countParams.add(2);
		listParams.add(2);
		if (ArticleType.ALL != typeE) {
			countSql += " AND `type` = ? ";
			listSql += " AND `type` = ? ";
			countParams.add(typeE.name());
			listParams.add(typeE.name());
		}
		if (OrderType.NEW != orderE) {
			countSql += " AND unix_timestamp(`create_time`) >= ? AND unix_timestamp(`create_time`) < ? ";
			listSql += " AND unix_timestamp(`create_time`) >= ? AND unix_timestamp(`create_time`) < ? ";
			c.add(Calendar.DAY_OF_MONTH, -orderE.getValue());
			Date end = c.getTime();
			countParams.add(end.getTime() / 1000);
			countParams.add(today.getTime() / 1000);
			listParams.add(end.getTime() / 1000);
			listParams.add(today.getTime() / 1000);

		}

		if (OrderType.NEW == orderE) {
			listSql += " ORDER BY `id` DESC LIMIT ?,?";
		} else {
			listSql += " ORDER BY `up` DESC LIMIT ?,?";
		}

		listSql = "SELECT j.*, u.`nick`, u.`avatar` FROM `joke_article` j, `uc_member` u, ("+listSql+") c WHERE j.`id` = c.`id` AND j.`member_id` = u.`id`";
		int rows = Config.getInt("row_count_per_page", 30);

		int count = jdbcTemplate.queryForInt(countSql, countParams.toArray());
		int pages = count / rows + 1;
		// 超过总页数,跳转到最后一页
		if (page > pages) {
			return "redirect:/" + typeE.name() + "/" + pages;
		}

		listParams.add((page - 1) * rows);
		listParams.add(rows);
		List<Map<String, Object>> articles = jdbcTemplate.queryForList(listSql,
				listParams.toArray());
		request.setAttribute("page", page);
		request.setAttribute("count", pages);
		request.setAttribute("articles", articles);

		@SuppressWarnings("unchecked")
		Map<String, Map<String, Map<String, Object>>> adMap = (Map<String, Map<String, Map<String, Object>>>) Cache
				.get(Cache.Key.AD);
		request.setAttribute("ads", adMap.get("list"));
		List<Map<String, Object>> hots = new ArrayList<Map<String, Object>>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> l = (List<Map<String, Object>>) Cache
				.get(Cache.Key.HOT_PIC);
		if (l != null && l.size() > 2) {
			Random rand = new Random();
			int seed = rand.nextInt(l.size() - 2);
			for (int i = 0; i < 2; i++) {
				hots.add(l.get(i + seed));
			}
		}
		request.setAttribute("hots", hots);
		if (StringUtils.equals(pos, "index") && StringUtils.equals(adv, "0")) {
			request.setAttribute("ads", adMap.get("index"));
			return "/index";
		}
		return "/list";
	}
}