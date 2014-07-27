package com.doumiao.joke.web.lottery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doumiao.joke.annotation.LoginMember;
import com.doumiao.joke.enums.Account;
import com.doumiao.joke.enums.AccountLogStatus;
import com.doumiao.joke.enums.WealthType;
import com.doumiao.joke.lang.SerialNumberGenerator;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;
import com.doumiao.joke.web.DBUtils;

@Controller
public class Draw {

	private static final Log log = LogFactory.getLog(Draw.class);
	@Resource
	private DBUtils dbUtils;

	@Resource
	ObjectMapper objectMapper;

	@RequestMapping(value = "/lottery/draw")
	public String show(HttpServletRequest request,
			HttpServletResponse response, @LoginMember Member m) {
		return "/lottery/draw";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/lottery/draw/go")
	public Result go(HttpServletRequest request, HttpServletResponse response,
			@LoginMember Member m) {
		int uid = m.getId();
		String group = "1";
		try {
			List<Map<String, Object>> options = dbUtils
					.getTemplate()
					.queryForList(
							"select * from uc_lottery_draw where `group` = ? and chance >= 0",
							group);
			int size = options.size();
			Map<Integer, Map<String, Object>> awards = new HashMap<Integer, Map<String, Object>>(
					size);
			float chance_array[][] = new float[size][3];
			float end = 0.0f;
			float[] defaultChance = new float[3];
			int defaultChanceCount = 0;
			float chanceSum = 0;
			for (int i = 0; i < size; i++) {
				Map<String, Object> o = options.get(i);
				float chance = (Float) o.get("chance");
				chanceSum += chance;
				int awardId = (Integer) o.get("id");
				awards.put(awardId, o);
				chance_array[i][0] = end;
				end += chance;
				chance_array[i][1] = end;
				chance_array[i][2] = awardId;
				if (chance == 0) {
					defaultChanceCount++;
					defaultChance = chance_array[i];
				}
			}
			if (defaultChanceCount > 1 || chanceSum > 100) {
				log.error("default chance must less than 1. chance sum must less than 100. draw group:"
						+ group);
				return new Result(false, "draw_error", "抽奖失败", "");
			}
			if (defaultChanceCount == 0 && chanceSum < 100) {
				log.error("must have default chance,when chance sum less than 100. draw group:"
						+ group);
				return new Result(false, "draw_error", "抽奖失败", "");
			}
			if (defaultChanceCount == 1) {
				defaultChance[0] = end;
				defaultChance[1] = 100.0f;
			}
			double result = Math.random() * 100;
			int awardId = 0;
			for (int i = 0; i < chance_array.length; i++) {
				float[] chance = chance_array[i];
				if (result >= chance[0] && result < chance[1]) {
					awardId = (int) chance[2];
					break;
				}
			}
			Map<String, Object> award = awards.get(awardId);
			Map<String, Integer> a = objectMapper.readValue(
					(String) award.get("content"), Map.class);
			int wealth = a.get("coin");
			String[] serialNumber = SerialNumberGenerator.generate(2);
			dbUtils.sendScore(uid, Account.S1, WealthType.DRAW, -5,
					AccountLogStatus.VALID, serialNumber[0], serialNumber[1], "",
					"00000000");
			if (wealth != 0) {
				dbUtils.sendScore(uid, Account.S2, WealthType.DRAW, wealth,
						AccountLogStatus.VALID, serialNumber[0], serialNumber[2], "",
						"00000000");
			}
			return new Result(true, "draw_success", "抽奖成功", award);
		} catch (Exception e) {
			log.error(e, e);
			return new Result(false, "draw_error", "抽奖失败", "");
		}
	}
}
