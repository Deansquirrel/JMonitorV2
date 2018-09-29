package com.yuansong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuansong.pojo.WebStateTaskConfig;
import com.yuansong.repository.BaseConfigRepository;

@Service
public class WebStateTaskConfigService extends BaseConfigService<WebStateTaskConfig> {
	
	@Autowired
	private BaseConfigRepository<WebStateTaskConfig> repository;
	
	@Autowired
	private TaskManagerService taskManagerService;

	@Override
	public String check(WebStateTaskConfig config) {
		return taskManagerService.testConfig(config);
	}

	@Override
	protected BaseConfigRepository<WebStateTaskConfig> getRepository() {
		return repository;
	}

}
