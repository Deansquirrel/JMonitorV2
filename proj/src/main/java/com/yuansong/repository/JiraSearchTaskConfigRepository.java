package com.yuansong.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yuansong.pojo.JiraSearchTaskConfig;
import com.yuansong.repository.RowMapper.JiraSearchTaskConfigRowMapper;

@Repository
public class JiraSearchTaskConfigRepository extends BaseConfigRepository<JiraSearchTaskConfig> {

	private static final String GET_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FServer]" + 
			"      ,[FJql]" + 
			"      ,[FUser]" + 
			"      ,[FPwd]" + 
			"      ,[FCron]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [JiraSearchConfig]" + 
			"  WHERE [FId] = ?";
	private static final String GET_LIST_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FServer]" + 
			"      ,[FJql]" + 
			"      ,[FUser]" + 
			"      ,[FPwd]" + 
			"      ,[FCron]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [JiraSearchConfig]";
	private static final String ADD_SQL = ""
			+ "INSERT INTO [JiraSearchConfig]" + 
			"           ([FId]" + 
			"           ,[FServer]" + 
			"           ,[FJql]" + 
			"           ,[FUser]" + 
			"           ,[FPwd]" + 
			"           ,[FCron]" + 
			"           ,[FRemark]" + 
			"           ,[FTitle])" + 
			"     VALUES" + 
			"           (?,?,?,?,?,?,?,?)";
	private static final String DEL_SQL = ""
			+ "DELETE FROM [JiraSearchConfig]" + 
			"    WHERE [FId] = ?";
	
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
	protected Object[] getAddParams(JiraSearchTaskConfig config) {
		return new Object[] {
				config.getId(),
				config.getServer(),
				config.getJql(),
				config.getUser(),
				config.getPwd(),
				config.getCron(),
				config.getRemark(),
				config.getTitle()
		};
	}

	@Override
	protected String getDelSql() {
		return DEL_SQL;
	}

	@Override
	protected Object[] getDelParams(JiraSearchTaskConfig config) {
		return new Object[] {config.getId()};
	}

	@Override
	protected RowMapper<JiraSearchTaskConfig> getRowMapper() {
		return new JiraSearchTaskConfigRowMapper();
	}

}
