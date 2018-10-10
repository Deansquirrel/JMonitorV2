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
		if(config.getId().trim().equals("")) return "ID不允许为空";
		if(config.getServer().trim().equals("")) return "Server不允许为空";
		if(config.getJql().trim().equals("")) return "Jql不允许为空";
		if(config.getUser().trim().equals("")) return "User不允许为空";
		if(config.getCron().trim().equals("")) return "Cron不允许为空";
		return taskManagerService.testConfig(config);
	}

	@Override
	protected BaseConfigRepository<JiraSearchTaskConfig> getRepository() {
		return repository;
	}

}
