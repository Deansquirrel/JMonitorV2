package com.yuansong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuansong.pojo.HealthTaskConfig;
import com.yuansong.repository.BaseConfigRepository;

@Service
public class HealthTaskConfigService extends BaseConfigService<HealthTaskConfig> {
	
	@Autowired
	private BaseConfigRepository<HealthTaskConfig> repository;
	
	@Autowired
	private TaskManagerService taskManagerService;

	@Override
	public String check(HealthTaskConfig config) {
		if(config.getId().trim().equals("")) return "ID不允许为空";
		if(config.getCron().trim().equals("")) return "Cron不允许为空";
		return taskManagerService.testConfig(config);
	}

	@Override
	protected BaseConfigRepository<HealthTaskConfig> getRepository() {
		return repository;
	}

}
