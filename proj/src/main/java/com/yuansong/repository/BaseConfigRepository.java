package com.yuansong.repository;

import java.util.List;

import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.yuansong.pojo.BaseConfig;

public abstract class BaseConfigRepository<T extends BaseConfig> {
	
	private final Logger logger = Logger.getLogger(BaseConfigRepository.class);
	
	private Gson mGson = null;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public BaseConfigRepository() {
		mGson = new Gson();
	}
	
	protected abstract String getGetSql();
	protected abstract String getGetListSql();
	protected abstract String getAddSql();
	protected abstract Object[] getAddParams(T config);
	protected abstract String getDelSql();
	protected abstract Object[] getDelParams(T config);
	
	protected abstract RowMapper<T> getRowMapper();
	
	public T getConfig(String id) {
		List<T> list = jdbcTemplate.query(getGetSql(), new Object[] {id}, getRowMapper());			
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
		return jdbcTemplate.query(getGetListSql(), getRowMapper());
	}
	
	public int addConfig(T config) {
		logger.info("AddConfig - " + mGson.toJson(config));
		return jdbcTemplate.update(getAddSql(), getAddParams(config));
	}
	
	public int delConfig(T config) {
		logger.info("DelConfig - " + mGson.toJson(config));
		return  jdbcTemplate.update(getDelSql(), getDelParams(config));
	}
}
