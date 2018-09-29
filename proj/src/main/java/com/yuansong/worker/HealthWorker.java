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
		String msg = taskConfig.getMsgContent();
		if(!taskConfig.getMsgTitle().equals("")) {
			msg = taskConfig.getMsgTitle() + "\n" + msg;
		}
		
		if(!HealthRecorder.isHealth()) {
			msg = msg + "\n" + "\n" + HealthRecorder.getHealthWarnMsg();
		}
		
		return DateTool.getDateStr() + "\n" + CommonFun.getInternetIp() + "\n" + msg;
	}

}
