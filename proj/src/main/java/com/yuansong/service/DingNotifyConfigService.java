package com.yuansong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuansong.pojo.DingNotifyConfig;
import com.yuansong.repository.BaseConfigRepository;

@Service
public class DingNotifyConfigService extends BaseConfigService<DingNotifyConfig> {
	
	@Autowired
	private BaseConfigRepository<DingNotifyConfig> repository;

	@Override
	public String check(DingNotifyConfig config) {
		if(config.getId().trim().equals("")) return "ID不允许为空";
		if(config.getRobotToken().trim().equals("")) return "RobotToken不允许为空";
		return "";
	}

	@Override
	protected BaseConfigRepository<DingNotifyConfig> getRepository() {
		return repository;
	}

}
