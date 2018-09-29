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
		return "";
	}

	@Override
	protected BaseConfigRepository<DingNotifyConfig> getRepository() {
		return repository;
	}

}
