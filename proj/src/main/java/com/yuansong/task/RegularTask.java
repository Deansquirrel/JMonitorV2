package com.yuansong.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yuansong.global.HealthRecorder;
import com.yuansong.service.TaskManagerService;

@Component
public class RegularTask {
	
	private final Logger logger = Logger.getLogger(RegularTask.class);
	
	public RegularTask() {
		logger.info("RegularTask Init");
	}
	
	@Autowired
	private TaskManagerService taskManagerService;
	
	@Scheduled(cron="0 0/1 * * * ?")
	public void configReresh() {
		logger.debug("Config Refresh");
		try {
			taskManagerService.refresh();
			HealthRecorder.setRefreshConfigWarn("");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			HealthRecorder.setRefreshConfigWarn(ex.getMessage());
		}
		
	}

}
