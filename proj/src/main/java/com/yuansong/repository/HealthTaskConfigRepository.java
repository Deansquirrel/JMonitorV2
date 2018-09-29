package com.yuansong.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yuansong.pojo.HealthTaskConfig;
import com.yuansong.repository.RowMapper.HealthTaskConfigRowMapper;

@Repository
public class HealthTaskConfigRepository extends BaseConfigRepository<HealthTaskConfig> {
	
	private static final String GET_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FCron]" + 
			"      ,[FMsgTitle]" + 
			"      ,[FMsgContent]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [HealthTaskConfig]" + 
			"  WHERE [FId] = ?";
	private static final String GET_LIST_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FCron]" + 
			"      ,[FMsgTitle]" + 
			"      ,[FMsgContent]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [HealthTaskConfig]";
	private static final String ADD_SQL = ""
			+ "INSERT INTO [HealthTaskConfig]" + 
			"           ([FId]" + 
			"           ,[FCron]" + 
			"           ,[FMsgTitle]" + 
			"           ,[FMsgContent]" + 
			"           ,[FRemark]" + 
			"           ,[FTitle])" + 
			"     VALUES" + 
			"           (?,?,?,?,?,?)";
	private static final String DEL_SQL = ""
			+ "DELETE FROM [HealthTaskConfig]" + 
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
	protected Object[] getAddParams(HealthTaskConfig config) {
		return new Object[] {
				config.getId(),
				config.getCron(),
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
	protected Object[] getDelParams(HealthTaskConfig config) {
		return new Object[] {config.getId()};
	}

	@Override
	protected RowMapper<HealthTaskConfig> getRowMapper() {
		return new HealthTaskConfigRowMapper();
	}

}
