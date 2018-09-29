package com.yuansong.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yuansong.pojo.WebStateTaskConfig;
import com.yuansong.repository.RowMapper.WebStateTaskConfigRowMapper;

@Repository
public class WebStateTaskConfigRepository extends BaseConfigRepository<WebStateTaskConfig> {
	
	private static final String GET_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FUrl]" + 
			"      ,[FCron]" + 
			"      ,[FMsgTitle]" + 
			"      ,[FMsgContent]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [WebStateTaskConfig]" +
			"  WHERE [FId] = ?";
	private static final String GET_LIST_SQL = ""
			+ "SELECT [FId]" + 
			"      ,[FUrl]" + 
			"      ,[FCron]" + 
			"      ,[FMsgTitle]" + 
			"      ,[FMsgContent]" + 
			"      ,[FRemark]" + 
			"      ,[FTitle]" + 
			"  FROM [WebStateTaskConfig]";
	private static final String ADD_SQL = ""
			+ "INSERT INTO [WebStateTaskConfig]" + 
			"           ([FId]" + 
			"           ,[FUrl]" + 
			"           ,[FCron]" + 
			"           ,[FMsgTitle]" + 
			"           ,[FMsgContent]" + 
			"           ,[FRemark]" + 
			"           ,[FTitle])" + 
			"     VALUES" + 
			"           (?,?,?,?,?,?,?)";
	private static final String DEL_SQL = ""
			+ "DELETE FROM [WebStateTaskConfig]" + 
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
	protected Object[] getAddParams(WebStateTaskConfig config) {
		return new Object[] {
				config.getId(),
				config.getUrl(),
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
	protected Object[] getDelParams(WebStateTaskConfig config) {
		return new Object[] {config.getId()};
	}

	@Override
	protected RowMapper<WebStateTaskConfig> getRowMapper() {
		return new WebStateTaskConfigRowMapper();
	}

}
