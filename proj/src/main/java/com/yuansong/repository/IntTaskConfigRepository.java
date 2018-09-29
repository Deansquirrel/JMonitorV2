package com.yuansong.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yuansong.pojo.IntTaskConfig;
import com.yuansong.repository.RowMapper.IntTaskConfigRowMapper;


@Repository
public class IntTaskConfigRepository extends BaseConfigRepository<IntTaskConfig> {
	
	private static final String GET_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FServer]" + 
			"      ,[FPort]" + 
			"      ,[FDbName]" + 
			"      ,[FDbUser]" + 
			"      ,[FDbPwd]" + 
			"      ,[FSearch]" + 
			"      ,[FCron]" + 
			"      ,[FCheckMax]" + 
			"      ,[FCheckMin]" + 
			"      ,[FMsgTitle]" + 
			"      ,[FMsgContent]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [IntTaskConfig]" + 
			"  WHERE [FId] = ?";
	
	private static final String GET_LIST_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FServer]" + 
			"      ,[FPort]" + 
			"      ,[FDbName]" + 
			"      ,[FDbUser]" + 
			"      ,[FDbPwd]" + 
			"      ,[FSearch]" + 
			"      ,[FCron]" + 
			"      ,[FCheckMax]" + 
			"      ,[FCheckMin]" + 
			"      ,[FMsgTitle]" + 
			"      ,[FMsgContent]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [IntTaskConfig]";
	
	private static final String ADD_SQL = ""
			+ "INSERT INTO [IntTaskConfig]" + 
			"           ([FId]" + 
			"           ,[FServer]" + 
			"           ,[FPort]" + 
			"           ,[FDbName]" + 
			"           ,[FDbUser]" + 
			"           ,[FDbPwd]" + 
			"           ,[FSearch]" + 
			"           ,[FCron]" + 
			"           ,[FCheckMax]" + 
			"           ,[FCheckMin]" + 
			"           ,[FMsgTitle]" + 
			"           ,[FMsgContent]" + 
			"           ,[FRemark]" + 
			"           ,[FTitle])" + 
			"     VALUES" + 
			"           (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String DEL_SQL = ""
			+ "DELETE FROM [IntTaskConfig]" + 
			"      WHERE [FId] = ?";

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
	protected Object[] getAddParams(IntTaskConfig config) {
		return new Object[] {
				config.getId(),
				config.getServer(),
				config.getPort(),
				config.getDbName(),
				config.getDbUser(),
				config.getDbPwd(),
				config.getSearch(),
				config.getCron(),
				config.getCheckMax(),
				config.getCheckMin(),
				config.getMsgTitle(),
				config.getMsgContent(),
				config.getRemark(),
				config.getTitle()
		};
	}

	@Override
	protected String getDelSql() {
		return DEL_SQL;
	}

	@Override
	protected Object[] getDelParams(IntTaskConfig config) {
		return new Object[] {config.getId()};
	}

	@Override
	protected RowMapper<IntTaskConfig> getRowMapper() {
		return new IntTaskConfigRowMapper();
	}

}
