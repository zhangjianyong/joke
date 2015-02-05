package com.doumiao.joke.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.doumiao.joke.enums.ArticleType;

@Component
public class CacheSchedule {
	private static final Log log = LogFactory.getLog(CacheSchedule.class);
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Scheduled(fixedDelay = 60000)
	protected void refreshConfig() {
		if (log.isDebugEnabled()) {
			log.debug("refresh config");
		}
		List<Map<String, Object>> configs = jdbcTemplate
				.queryForList("select * from joke_config");
		for (Map<String, Object> c : configs) {
			Config.set((String) c.get("key"), (String) c.get("value"));
		}
	}

	@Scheduled(fixedDelay = 600000)
	protected void refreshHotText() {
		if (log.isDebugEnabled()) {
			log.debug("refresh hot text");
		}
		List<Map<String, Object>> articles = jdbcTemplate
				.queryForList(
						"select a.*,m.nick,m.avatar from joke_article a,uc_member m where a.create_time > DATE_ADD(curdate(), INTERVAL -7 DAY) and a.member_id=m.id and a.`status` = 2 and type = ? order by up desc,id desc limit 0, 1000",
						ArticleType.TEXT.name());
		if (articles != null && articles.size() > 0) {
			Cache.set(Cache.Key.HOT_TEXT, articles);
		}
	}
	
	@Scheduled(fixedDelay = 600000)
	protected void refreshUpText() {
		if (log.isDebugEnabled()) {
			log.debug("refresh up text");
		}
		List<Map<String, Object>> articles = jdbcTemplate
				.queryForList(
						"select a.* from joke_article a where a.create_time > DATE_ADD(curdate(), INTERVAL -7 DAY) and a.`status` = 2 and type = ? order by up desc,id desc limit 0, 20",
						ArticleType.TEXT.name());
		if (articles != null && articles.size() > 0) {
			Cache.set(Cache.Key.UP_TEXT, articles);
		}
	}
	
	@Scheduled(fixedDelay = 600000)
	protected void refreshHotPic() {
		if (log.isDebugEnabled()) {
			log.debug("refresh hot pic");
		}
		List<Map<String, Object>> articles = jdbcTemplate
				.queryForList(
						"select a.*,m.nick,m.avatar from joke_article a,uc_member m where a.create_time > DATE_ADD(curdate(), INTERVAL -7 DAY) and a.member_id=m.id and a.`status` = 2 and type = ? order by up desc,id desc limit 0, 100",
						ArticleType.PIC.name());
		if (articles != null && articles.size() > 0) {
			Cache.set(Cache.Key.HOT_PIC, articles);
		}
	}

	@Scheduled(fixedDelay = 60000)
	protected void refreshAd() {
		if (log.isDebugEnabled()) {
			log.debug("refresh ad");
		}
		List<Map<String, Object>> ads = jdbcTemplate.queryForList(
				"select * from `ad_script` where `status` = 0");
		Map<String, Map<String,Map<String, Object>>> adMap = new HashMap<String, Map<String,Map<String, Object>>>();
		for (Map<String, Object> ad : ads) {
			String pos = (String)ad.get("position");
			Map<String,Map<String, Object>> l = adMap.get(pos);
			if(l==null){
				l = new HashMap<String,Map<String, Object>>();
				adMap.put(pos, l);
			}
			l.put("ad" + ad.get("id"), ad);
		}
		if (adMap.size() > 0) {
			Cache.set(Cache.Key.AD, adMap);
		}
	}
	
	@Scheduled(fixedDelay = 60000)
	protected void refreshLotteryLog() {
		if (log.isDebugEnabled()) {
			log.debug("refresh lottery log");
		}
		List<Map<String, Object>> lotteryLogs = jdbcTemplate
				.queryForList("select m.nick,a.wealth,date_format(a.create_time,'%T') time from uc_account_log a,uc_member m where a.wealth_type = 'DRAW' and a.account='S2' and m.id=a.member_id and a.create_time >= CURDATE() order by a.wealth desc limit 0, 30");
		if (lotteryLogs != null && lotteryLogs.size() > 0) {
			Cache.set(Cache.Key.LOTTERY_LOG, lotteryLogs);
		}
	}
}
