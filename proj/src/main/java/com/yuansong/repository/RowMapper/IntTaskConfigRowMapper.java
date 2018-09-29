package com.yuansong.repository.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.yuansong.pojo.IntTaskConfig;

public class IntTaskConfigRowMapper implements RowMapper<IntTaskConfig> {

	@Override
	public IntTaskConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		IntTaskConfig config = new IntTaskConfig();
		config.setId(rs.getString("FId"));
		config.setServer(rs.getString("FServer"));
		config.setPort(rs.getString("FPort"));
		config.setDbName(rs.getString("FDbName"));
		config.setDbUser(rs.getString("FDbUser"));
		config.setDbPwd(rs.getString("FDbPwd"));
		config.setSearch(rs.getString("FSearch"));
		config.setCron(rs.getString("FCron"));
		config.setCheckMax(rs.getInt("FCheckMax"));
		config.setCheckMin(rs.getInt("FCheckMin"));
		config.setMsgTitle(rs.getString("FMsgTitle"));
		config.setMsgContent(rs.getString("FMsgContent"));
		config.setRemark(rs.getString("FRemark"));
		config.setTitle(rs.getString("FTitle"));
		return config;
	}

}
