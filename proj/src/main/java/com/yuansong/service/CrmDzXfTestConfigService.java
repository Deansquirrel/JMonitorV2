package com.yuansong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuansong.pojo.CrmDzXfTestTaskConfig;
import com.yuansong.repository.BaseConfigRepository;

@Service
public class CrmDzXfTestConfigService extends BaseConfigService<CrmDzXfTestTaskConfig> {
	
	@Autowired
	private BaseConfigRepository<CrmDzXfTestTaskConfig> repository;
	
	@Autowired
	private TaskManagerService taskManagerService;

	@Override
	public String check(CrmDzXfTestTaskConfig config) {
		if(config.getId().trim().equals("")) return "ID不允许为空";
		if(config.getCron().trim().equals("")) return "Cron不允许为空";
		if(config.getPassport().trim().equals("")) return "PassPortType不允许为空";
		return taskManagerService.testConfig(config);
	}

	@Override
	protected BaseConfigRepository<CrmDzXfTestTaskConfig> getRepository() {
		return repository;
	}

}
