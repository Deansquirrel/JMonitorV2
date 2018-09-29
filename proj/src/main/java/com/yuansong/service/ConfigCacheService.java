package com.yuansong.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuansong.notify.DingNotify;
import com.yuansong.notify.Notify;
import com.yuansong.pojo.BaseNotifyConfig;
import com.yuansong.pojo.BaseTaskConfig;
import com.yuansong.pojo.DingNotifyConfig;
import com.yuansong.pojo.HealthTaskConfig;
import com.yuansong.pojo.IntTaskConfig;
import com.yuansong.pojo.JiraSearchTaskConfig;
import com.yuansong.pojo.WebStateTaskConfig;
import com.yuansong.worker.BaseWorker;
import com.yuansong.worker.HealthWorker;
import com.yuansong.worker.IntWorker;
import com.yuansong.worker.JiraSearchWorker;
import com.yuansong.worker.WebStateWorker;

@Service
public class ConfigCacheService {
	
	private final Logger logger = Logger.getLogger(ConfigCacheService.class);
	
	private List<String> taskConfigTypeList = null;
	private List<String> notifyConfigTypeList = null;
	
	//IntTaskConfig
	private Map<String, IntTaskConfig> mIntConfigMap = null;
	@Autowired
	private BaseConfigService<IntTaskConfig> mIntConfigService; 
	
	//HealthTaskConfig
	private Map<String, HealthTaskConfig> mHealthConfigMap = null;
	@Autowired
	private BaseConfigService<HealthTaskConfig> mHealthConfigService;
	
	//WebStateTaskConfig
	private Map<String, WebStateTaskConfig> mWebStateConfigMap = null;
	@Autowired
	private BaseConfigService<WebStateTaskConfig> mWebStateConfigService;
	
	//JiraSearchConfig
	private Map<String, JiraSearchTaskConfig> mJiraSearchConfigMap = null;
	@Autowired
	private BaseConfigService<JiraSearchTaskConfig> mJiraSearchConfigService;
	
	//DingNotifyConfig
	private Map<String, DingNotifyConfig> mDingConfigMap = null;
	@Autowired
	private BaseConfigService<DingNotifyConfig> mDingNotifyConfigService;
	
	public ConfigCacheService() {
		mIntConfigMap = new HashMap<String, IntTaskConfig>();
		mHealthConfigMap = new HashMap<String, HealthTaskConfig>();
		mWebStateConfigMap = new HashMap<String, WebStateTaskConfig>();
		mJiraSearchConfigMap = new HashMap<String, JiraSearchTaskConfig>();
		
		taskConfigTypeList = new ArrayList<String>();
		taskConfigTypeList.add("Int");
		taskConfigTypeList.add("Health");
		taskConfigTypeList.add("WebState");
		taskConfigTypeList.add("JiraSearch");
		
		mDingConfigMap = new HashMap<String, DingNotifyConfig>();
		
		notifyConfigTypeList = new ArrayList<String>();
		notifyConfigTypeList.add("Ding");
	}
	
	public List<String> getTaskConfigTypeList(){
		return taskConfigTypeList;
	}
	
	public List<String> getNotifyConfigTypeList(){
		return notifyConfigTypeList;
	}
	
	public interface GetTaskConfigAndService{
		<Config extends BaseTaskConfig> void getConfigAndService(BaseConfigService<Config> configService, Map<String, Config> configMap);
	}
	
	public void getTaskConfigAndService(String type, GetTaskConfigAndService callBack) {
		if(callBack != null) {
			switch(type) {
				case "Int":
					callBack.getConfigAndService(mIntConfigService, mIntConfigMap);
					break;
				case "Health":
					callBack.getConfigAndService(mHealthConfigService, mHealthConfigMap);
					break;
				case "WebState":
					callBack.getConfigAndService(mWebStateConfigService, mWebStateConfigMap);
					break;
				case "JiraSearch":
					callBack.getConfigAndService(mJiraSearchConfigService, mJiraSearchConfigMap);
					break;
				default:
					String msg = "未配置【" + type + "】对应的设置和服务对象";
					logger.error(msg);
					throw new RuntimeException(msg);
			}
		}
	}
	
	public interface GetNotifyConfigAndService{
		<NotifyConfig extends BaseNotifyConfig> void getConfigAndService(BaseConfigService<NotifyConfig> configService, Map<String, NotifyConfig> configMap);
	}
	
	public void getiNotifyConfigAndService(String type, GetNotifyConfigAndService callBack) {
		if(callBack != null) {
			switch(type) {
				case "Ding":
					callBack.getConfigAndService(mDingNotifyConfigService, mDingConfigMap);
					break;
				default:
					String msg = "未配置【" + type + "】对应的设置和服务对象";
					logger.error(msg);
					throw new RuntimeException(msg);
			}
		}
	}
	
	public <Config extends BaseTaskConfig> BaseWorker getWorker(Config config) throws Exception {
		switch(config.getClass().getSimpleName()) {
			case "IntTaskConfig":
				return new IntWorker((IntTaskConfig) config,getNotifyList());
			case "HealthTaskConfig":
				return new HealthWorker((HealthTaskConfig) config,getNotifyList());
			case "WebStateTaskConfig":
				return new WebStateWorker((WebStateTaskConfig) config, getNotifyList());
			case "JiraSearchTaskConfig":
				return new JiraSearchWorker((JiraSearchTaskConfig) config, getNotifyList());
			default:
				throw new Exception("未定义Config类型对应的Worker【" + config.getClass().getSimpleName() +"】");
		}
	}
	
	public <Config extends BaseNotifyConfig> Notify getNotify(Config config) throws Exception {
		switch(config.getClass().getSimpleName()) {
			case "DingNotifyConfig":
				return new DingNotify((DingNotifyConfig) config);
			default:
				throw new Exception("未定义Config类型对应的Notify【" + config.getClass().getSimpleName() +"】");
		}
	}
	
	private List<Notify> getNotifyList() throws Exception{
		List<Notify> list = new ArrayList<Notify>();
		
		for(String type : notifyConfigTypeList) {
			getiNotifyConfigAndService(type, new GetNotifyConfigAndService() {
				@Override
				public <NotifyConfig extends BaseNotifyConfig> void getConfigAndService(
						BaseConfigService<NotifyConfig> configService, Map<String, NotifyConfig> configMap) {
					 for(NotifyConfig config : configMap.values()) {
						 try {
							list.add(getNotify(config));
						} catch (Exception ex) {
							logger.error(ex.getMessage());
							ex.printStackTrace();
						}
					 }
				}
			});
		}	
		return list;
	}
}
