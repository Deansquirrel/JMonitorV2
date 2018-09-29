package com.yuansong.repository.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yuansong.pojo.HealthTaskConfig;

public class HealthTaskConfigRowMapper implements RowMapper<HealthTaskConfig> {

	@Override
	public HealthTaskConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		HealthTaskConfig config = new HealthTaskConfig();
		config.setId(rs.getString("FId"));
		config.setCron(rs.getString("FCron"));
		config.setMsgTitle(rs.getString("FMsgTitle"));
		config.setMsgContent(rs.getString("FMsgContent"));
		config.setRemark(rs.getString("FRemark"));
		config.setTitle(rs.getString("FTitle"));
		return config;
	}

}
