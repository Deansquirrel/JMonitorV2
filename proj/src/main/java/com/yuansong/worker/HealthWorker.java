package com.yuansong.worker;

import java.util.List;

import org.apache.log4j.Logger;

import com.yuansong.common.CommonFun;
import com.yuansong.common.DateTool;
import com.yuansong.global.HealthRecorder;
import com.yuansong.notify.Notify;
import com.yuansong.pojo.HealthTaskConfig;

public class HealthWorker extends BaseWorkerAbstractImpl<HealthTaskConfig> {
	
	private final Logger logger = Logger.getLogger(HealthWorker.class);

	public HealthWorker(HealthTaskConfig config, List<Notify> list) {
		super(config, list);
	}

	@Override
	protected String check() {
		HealthTaskConfig taskConfig = getConfig();
		if(taskConfig == null) {
			logger.warn("HealthConfig is null");
			return "HealthConfig is null";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(DateTool.getDateStr());
		sb.append("\n");
		sb.append(CommonFun.getInternetIp());
    	if(!taskConfig.getMsgTitle().equals("")) {
    		sb.append("\n");
    		sb.append(taskConfig.getMsgTitle());
    	}
    	if(!taskConfig.getMsgContent().equals("")) {
    		sb.append("\n");
    		sb.append(taskConfig.getMsgContent());
    	}
    	if(!HealthRecorder.isHealth()) {
    		sb.append("\n").append("\n");
    		sb.append(HealthRecorder.getHealthWarnMsg());
		}		
		return sb.toString();
	}

}
