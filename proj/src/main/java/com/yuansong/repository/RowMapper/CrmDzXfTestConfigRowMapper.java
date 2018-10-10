package com.yuansong.repository.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yuansong.pojo.CrmDzXfTestTaskConfig;

public class CrmDzXfTestConfigRowMapper implements RowMapper<CrmDzXfTestTaskConfig> {

	@Override
	public CrmDzXfTestTaskConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		CrmDzXfTestTaskConfig config = new CrmDzXfTestTaskConfig();
		config.setAddress(rs.getString("FAddress"));
		config.setPassport(rs.getString("FPassport"));
		config.setPassportType(rs.getInt("FPassportType"));
		
		config.setCron(rs.getString("FCron"));
		config.setMsgTitle(rs.getString("FMsgTitle"));
		config.setMsgContent(rs.getString("FMsgContent"));
		
		config.setId(rs.getString("FId"));
		config.setTitle(rs.getString("FTitle"));
		config.setRemark(rs.getString("FRemark"));
		
		return config;
	}

}
