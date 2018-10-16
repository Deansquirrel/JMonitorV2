package com.yuansong.worker;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.yuansong.notify.Notify;
import com.yuansong.pojo.IntTaskConfig;

public class IntWorker extends BaseWorkerAbstractImpl<IntTaskConfig> {
	
	private final Logger logger = Logger.getLogger(IntWorker.class);
	
	private static final String dirverClass = "net.sourceforge.jtds.jdbc.Driver";

	public IntWorker(IntTaskConfig config, List<Notify> list) {
		super(config, list);
	}

	@Override
	protected String check() {
		
		IntTaskConfig taskConfig = getConfig();
		if(taskConfig == null) {
			logger.warn("IntTaskConfig is null");
			return "IntTaskConfig is null";
		}
		
		JdbcTemplate jdbcTemplate = null;
		Integer value;
		
		try {
			jdbcTemplate = getJdbcTemplate();
			value = jdbcTemplate.queryForObject(taskConfig.getSearch(), Integer.class);
		}catch(Exception ex) {
			ex.printStackTrace();
			StringBuilder sb = new StringBuilder();
			if(taskConfig.getMsgTitle() != null && !taskConfig.getMsgTitle().equals("")) {
				sb.append(taskConfig.getMsgTitle()).append("\n");
			}
			sb.append(ex.getMessage());
			return sb.toString();
		}
//		}catch (InvalidResultSetAccessException e){
//			e.printStackTrace();
//			return e.getMessage();
//		}catch (DataAccessException e){
//			e.printStackTrace();
//			return taskConfig.getMsgTitle() + "\n" + e.getMessage();
//		}
		
		
		if(value <= getConfig().getCheckMin() || value >= taskConfig.getCheckMax()) {
			String msg = taskConfig.getMsgContent();
			msg = msg.replace("title", String.valueOf(value));
			msg = msg.replace("Max", String.valueOf(taskConfig.getCheckMax()));
			msg = msg.replace("Min", String.valueOf(taskConfig.getCheckMin()));
			if(!taskConfig.getMsgTitle().equals("")) {
				msg = taskConfig.getMsgTitle() + "\n" + msg; 
			}
			if(taskConfig.getMsgSearch() != null && !taskConfig.getMsgSearch().equals("")) {
				try {
					jdbcTemplate = getJdbcTemplate();
					List<Map<String, Object>> list = jdbcTemplate.queryForList(taskConfig.getMsgSearch());
					for(Map<String, Object> msgData : list) {
						msg = msg + "\n";
						msg = msg + "--------------------";
						for(String key : msgData.keySet()) {
							msg = msg + "\n";
							msg = msg + key + " - " + String.valueOf(msgData.get(key));
						}
					}
				}catch(Exception ex) {
					ex.printStackTrace();
					msg = msg + "\n" + "--------------------";
					msg = msg + "\n" + "查询详细提示信息时遇到错误";
					msg = msg + "\n" + ex.getMessage();
				}
			}
			return msg;
		}
		else {
			return "";
		}
	}
	
	private JdbcTemplate getJdbcTemplate() {
		IntTaskConfig config = getConfig();
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource ();
		dataSource.setDriverClassName(dirverClass);
		dataSource.setUrl("jdbc:jtds:sqlserver://" + config.getServer() + ":" + config.getPort() +";DatabaseName=" + getConfig().getDbName());
		dataSource.setUsername(config.getDbUser());
		dataSource.setPassword(config.getDbPwd());
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setQueryTimeout(30);
		jdbcTemplate.setDataSource(dataSource);
		
		return jdbcTemplate;
	}
	
}
