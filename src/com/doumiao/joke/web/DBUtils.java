package com.doumiao.joke.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.doumiao.joke.enums.Account;
import com.doumiao.joke.enums.AccountLogStatus;
import com.doumiao.joke.enums.Plat;
import com.doumiao.joke.enums.WealthType;
import com.doumiao.joke.vo.Member;
import com.doumiao.joke.vo.Result;

@Component
public class DBUtils {
	private static final Log log = LogFactory.getLog(DBUtils.class);
	@Resource
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getTemplate() {
		return jdbcTemplate;
	}

	public Member createMember(final Member u) {
		final String sql = "insert into uc_member(name,email,mobile,password,status,remark) VALUES(?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = (PreparedStatement) connection
							.prepareStatement(sql, new String[] { "id" });
					int i = 0;
					ps.setString(++i, u.getName());
					ps.setString(++i, u.getEmail());
					ps.setString(++i, u.getMobile());
					ps.setString(++i, u.getPassword());
					ps.setInt(++i, u.getStatus());
					ps.setString(++i, u.getRemark());
					return ps;
				}
			}, keyHolder);
			int id = keyHolder.getKey().intValue();
			jdbcTemplate.update("insert into uc_account(member_id) VALUES(?)",
					new Object[] { id });
			u.setId(id);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
		return u;
	}

	/**
	 * 目前网站只支持第三方登录,所以先创建一个空用户,只有id,而后再绑定第三方
	 * 
	 * @param u
	 *            空用户
	 * @param params
	 *            第三方绑定信息
	 * @return 返回绑定后的用户
	 */
	public Member bindThirdPlat(Member u, Plat plat, String openId,
			String token, Map<String, String> params) {
		u = createMember(u);
		if (u == null) {
			return null;
		}
		try {
			jdbcTemplate
					.update("insert into uc_member_thirdplat(member_id, plat, open_id, token, ext1, ext2, ext3, ext4, ext5) values(?,?,?,?,?,?,?,?,?)",
							u.getId(), plat.toString(), openId, token,
							params.get("ext1"), params.get("ext2"),
							params.get("ext3"), params.get("ext4"),
							params.get("ext5"));
			return u;
		} catch (DataAccessException e) {
			log.error(e, e);
			return null;
		}
	}

	public List<Map<String, Object>> hots() {
		return jdbcTemplate
				.queryForList("select * from joke_article order by up desc,id desc limit 0, 6");
	}

	public Result sendScore(int uid, Account account, WealthType type, int wealth, AccountLogStatus status,
			String serialNumber, String subSerialNumber, String remark,
			String operator) {
		if (StringUtils.isBlank(serialNumber)
				|| StringUtils.isBlank(subSerialNumber)
				|| StringUtils.isBlank(operator)) {
			return new Result(false,"send_score_error","流水号,操作人均不能为空","");
		}
		try {
			jdbcTemplate
					.update("insert into uc_account_log_tmp"
							+ "(member_id, account, wealth_type, wealth, serial_number, sub_serial_number, remark, operator, status) "
							+ "values(?,?,?,?,?,?,?,?,?)", uid,
							account.toString(), type.toString(), wealth,
							serialNumber, subSerialNumber, remark, operator, status.getValue());
			return new Result(true,"send_score_success","积分发放成功","");
		} catch (DataAccessException e) {
			log.error(e, e);
			return new Result(false,"send_score_error","积分发放失败","");
		}
	}
}
