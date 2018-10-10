package com.yuansong.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yuansong.pojo.CrmDzXfTestTaskConfig;
import com.yuansong.repository.RowMapper.CrmDzXfTestConfigRowMapper;

@Repository
public class CrmDzXfTestConfigRepository extends BaseConfigRepository<CrmDzXfTestTaskConfig> {
	
	private static final String GET_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"      ,[FCron]" + 
			"      ,[FMsgTitle]" + 
			"      ,[FMsgContent]" + 
			"      ,[FAddress]" + 
			"      ,[FPassport]" + 
			"      ,[FPassportType]" + 
			"  FROM [CrmDzXfTestTaskConfig]" + 
			"  WHERE [FId] = ?";
	private static final String GET_LIST_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"      ,[FCron]" + 
			"      ,[FMsgTitle]" + 
			"      ,[FMsgContent]" + 
			"      ,[FAddress]" + 
			"      ,[FPassport]" + 
			"      ,[FPassportType]" + 
			"  FROM [CrmDzXfTestTaskConfig]";
	private static final String ADD_SQL = ""
			+ "INSERT INTO [CrmDzXfTestTaskConfig]" + 
			"           ([FId]" + 
			"           ,[FRemark]" + 
			"           ,[FTitle]" + 
			"           ,[FCron]" + 
			"           ,[FMsgTitle]" + 
			"           ,[FMsgContent]" + 
			"           ,[FAddress]" + 
			"           ,[FPassport]" + 
			"           ,[FPassportType])" + 
			"     VALUES" + 
			"           (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DEL_SQL = ""
			+ "DELETE FROM [CrmDzXfTestTaskConfig]" + 
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
	protected Object[] getAddParams(CrmDzXfTestTaskConfig config) {
		return new Object[] {
				config.getId(),
				config.getRemark(),
				config.getTitle(),
				config.getCron(),
				config.getMsgTitle(),
				config.getMsgContent(),
				config.getAddress(),
				config.getPassport(),
				config.getPassportType()
		};
	}

	@Override
	protected String getDelSql() {
		return DEL_SQL;
	}

	@Override
	protected Object[] getDelParams(CrmDzXfTestTaskConfig config) {
		return new Object[] {config.getId()};
	}

	@Override
	protected RowMapper<CrmDzXfTestTaskConfig> getRowMapper() {
		return new CrmDzXfTestConfigRowMapper();
	}

}
