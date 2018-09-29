package com.yuansong.service;

import java.util.List;

import com.yuansong.pojo.BaseConfig;
import com.yuansong.repository.BaseConfigRepository;

public abstract class BaseConfigService<T extends BaseConfig>{
	
//	private final Logger logger = Logger.getLogger(BaseConfigService.class);
	
	public T getConfig(String id) {
		return getRepository().getConfig(id);
	}
	
	public List<T> getConfigList(){
		return getRepository().getConfigList();
	}
	
	public int addConfig(T config) {
		return getRepository().addConfig(config);
	}
	
	public int delConfig(T config) {
		return getRepository().delConfig(config);
	}
	
	public abstract String check(T config);
	
	protected abstract BaseConfigRepository<T> getRepository();

}
