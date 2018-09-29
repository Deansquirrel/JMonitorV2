package com.yuansong.repository.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yuansong.pojo.JiraSearchTaskConfig;

public class JiraSearchTaskConfigRowMapper implements RowMapper<JiraSearchTaskConfig> {

	@Override
	public JiraSearchTaskConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		JiraSearchTaskConfig config = new JiraSearchTaskConfig();
		config.setId(rs.getString("FId"));
		config.setServer(rs.getString("FServer"));
		config.setJql(rs.getString("FJql"));
		config.setUser(rs.getString("FUser"));
		config.setPwd(rs.getString("FPwd"));
		config.setCron(rs.getString("FCron"));
		config.setRemark(rs.getString("FRemark"));
		config.setTitle(rs.getString("FTitle"));
		
		return config;
	}

}
