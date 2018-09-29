package com.yuansong.repository;

import java.util.List;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.yuansong.pojo.BaseConfig;

public abstract class BaseConfigRepository<T extends BaseConfig> {
	
//	private final Logger logger = Logger.getLogger(BaseConfigRepository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	protected abstract String getGetSql();
	protected abstract String getGetListSql();
	protected abstract String getAddSql();
	protected abstract Object[] getAddParams(T config);
	protected abstract String getDelSql();
	protected abstract Object[] getDelParams(T config);
	
	protected abstract RowMapper<T> getRowMapper();
	
	public T getConfig(String id) {
		List<T> list = null;
		try {
			list = jdbcTemplate.query(getGetSql(), new Object[] {id}, getRowMapper());			
		}
		catch(Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		if(list.size() == 1 ) {
			return list.get(0);
		}
		else {
			if(list.size() == 0) {
				return null;
			}
			else {
				throw new RuntimeException("查询返回异常。【同一ID返回多个配置】");
			}
		}
	}
	
	public List<T> getConfigList(){
		List<T> list =null;
		try{
			list = jdbcTemplate.query(getGetListSql(), getRowMapper());
		}
		catch(Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return list;
	}
	
	public int addConfig(T config) {
		int row = -1;
		try {
			row = jdbcTemplate.update(getAddSql(), getAddParams(config));
		}
		catch(Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return row;
	}
	
	public int delConfig(T config) {
		int row = -1;
		try {
			row =  jdbcTemplate.update(getDelSql(), getDelParams(config));
		}
		catch(Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return row;
	}
}
