package com.yuansong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuansong.pojo.JiraSearchTaskConfig;
import com.yuansong.repository.BaseConfigRepository;
import com.yuansong.repository.JiraSearchTaskConfigRepository;

@Service
public class JiraSearchTaskConfigService extends BaseConfigService<JiraSearchTaskConfig> {
	
	@Autowired
	private JiraSearchTaskConfigRepository repository;
	
	@Autowired
	private TaskManagerService taskManagerService;

	@Override
	public String check(JiraSearchTaskConfig config) {
		return taskManagerService.testConfig(config);
	}

	@Override
	protected BaseConfigRepository<JiraSearchTaskConfig> getRepository() {
		return repository;
	}

}
