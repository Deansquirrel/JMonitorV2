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
		if(config.getId().trim().equals("")) return "ID不允许为空";
		if(config.getServer().trim().equals("")) return "Server不允许为空";
		if(config.getPort().trim().equals("")) return "Port不允许为空";
		if(config.getDbName().trim().equals("")) return "DbName不允许为空";
		if(config.getDbUser().trim().equals("")) return "DbUser不允许为空";
		if(config.getDbPwd().trim().equals("")) return "DbPwd不允许为空";
		if(config.getPort().trim().equals("")) return "Port不允许为空";
		if(config.getSearch().trim().equals("")) return "Search不允许为空";
		if(config.getCron().trim().equals("")) return "Cron不允许为空";
		if((config.getMsgTitle() + config.getMsgContent()).equals("")) return "msgTitle和msgContent不能都为空";
		
		return taskManagerService.testConfig(config);
	}

	@Override
	protected BaseConfigRepository<IntTaskConfig> getRepository() {
		return repository;
	}

}
