package com.yuansong.repository.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yuansong.pojo.DingNotifyConfig;

public class DingNotifyConfigRowMapper implements RowMapper<DingNotifyConfig> {

	@Override
	public DingNotifyConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		DingNotifyConfig config = new DingNotifyConfig();
		config.setId(rs.getString("FId"));
		config.setRobotToken(rs.getString("FRobotToken"));
		config.setRemark(rs.getString("FRemark"));
		config.setTitle(rs.getString("FTitle"));
		return config;
	}

}
