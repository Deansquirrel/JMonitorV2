package com.yuansong.worker;

import java.util.List;

import org.apache.log4j.Logger;

import com.yuansong.notify.Notify;
import com.yuansong.pojo.BaseConfig;

public abstract class BaseWorkerAbstractImpl<T extends BaseConfig> implements BaseWorker {
	
	private final Logger logger = Logger.getLogger(BaseWorkerAbstractImpl.class);
	
	private T config = null;
	private List<Notify> list = null;
	
	public BaseWorkerAbstractImpl(T config, List<Notify> list) {
		this.config = config;
		this.list = list;
	}
	
	public T getConfig() {
		return config;
	}
	
	@Override
	public void run() {
		String msg = check();
		if(!msg.equals("")) {
			logger.debug(msg);
			for(Notify notify : list) {
				notify.send(msg);
			}
		}
	}
	
	protected abstract String check();

}
