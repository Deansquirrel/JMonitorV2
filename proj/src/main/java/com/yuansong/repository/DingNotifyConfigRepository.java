package com.yuansong.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yuansong.pojo.DingNotifyConfig;
import com.yuansong.repository.RowMapper.DingNotifyConfigRowMapper;

@Repository
public class DingNotifyConfigRepository extends BaseConfigRepository<DingNotifyConfig> {
	
	private static final String GET_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FRobotToken]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [DingMessageSender]" + 
			"  WHERE [FId] = ?";
	private static final String GET_LIST_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FRobotToken]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [DingMessageSender]";
	private static final String ADD_SQL = ""
			+ "INSERT INTO [DingMessageSender]" + 
			"           ([FId]" + 
			"           ,[FRobotToken]" + 
			"           ,[FRemark]" + 
			"           ,[FTitle])" + 
			"     VALUES" + 
			"           (? , ? , ? , ?)";
	private static final String DEL_SQL = ""
			+ "DELETE FROM [DingMessageSender]" + 
			"  WHERE [FId] = ?";

	@Override
	protected String getGetSql() {
		return GET_SQL;
	}

	@Override
	protected String getGetListSql() {
		return GET_LIST_SQL;
	}

	@Override
	protected String getAddSql() {
		return ADD_SQL;
	}

	@Override
	protected Object[] getAddParams(DingNotifyConfig config) {
		return new Object[] {
				config.getId(),
				config.getRobotToken(),
				config.getRemark(),
				config.getTitle()
		};
	}

	@Override
	protected String getDelSql() {
		return DEL_SQL;
	}

	@Override
	protected Object[] getDelParams(DingNotifyConfig config) {
		return new Object[] {config.getId()};
	}

	@Override
	protected RowMapper<DingNotifyConfig> getRowMapper() {
		return new DingNotifyConfigRowMapper();
	}

}
