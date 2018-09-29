package com.yuansong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuansong.pojo.IntTaskConfig;
import com.yuansong.repository.BaseConfigRepository;

@Service
public class IntTaskConfigService extends BaseConfigService<IntTaskConfig> {
	
	@Autowired
	private BaseConfigRepository<IntTaskConfig> repository;
	
	@Autowired
	private TaskManagerService taskManagerService;

	@Override
	public String check(IntTaskConfig config) {
		return taskManagerService.testConfig(config);
	}

	@Override
	protected BaseConfigRepository<IntTaskConfig> getRepository() {
		return repository;
	}

}
