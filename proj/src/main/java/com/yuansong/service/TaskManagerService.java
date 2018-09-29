package com.yuansong.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yuansong.notify.Notify;
import com.yuansong.pojo.BaseNotifyConfig;
import com.yuansong.pojo.BaseTaskConfig;
import com.yuansong.service.ConfigCacheService.GetNotifyConfigAndService;
import com.yuansong.service.ConfigCacheService.GetTaskConfigAndService;
import com.yuansong.worker.BaseWorker;

@Service
public class TaskManagerService {
	
	private final Logger logger = Logger.getLogger(TaskManagerService.class);
	
	@Autowired
    private TaskScheduler mScheduler;
	
	@Autowired
	private ConfigCacheService objectService;
		
	private Map<String, ScheduledFuture<?>> mList;
	
	private Gson mGson = new Gson();
	
	public TaskManagerService() {
		mList = new HashMap<String, ScheduledFuture<?>>();
	}
	
	public synchronized void refresh() {
		refreshNofity();
		refreshConfig();
	}
		
	public synchronized <Config extends  BaseTaskConfig> String testConfig(Config config) {
		ScheduledFuture<?> future = null;
		BaseWorker worker = null;
		try {
			worker = objectService.getWorker(config);
			future = mScheduler.schedule(worker, new CronTrigger(config.getCron()));
		}
		catch(Exception ex) {
			return ex.getMessage();
		}
		finally{
			if(future != null) {
				future.cancel(true);
			}
		}
		return "";
	}
	
	public Map<String, Collection<? extends BaseTaskConfig>> getTaskConfigList(){
		Map<String, Collection<? extends BaseTaskConfig>> data = new HashMap<String, Collection<? extends BaseTaskConfig>>();
		for(String type : objectService.getTaskConfigTypeList()) {
			objectService.getTaskConfigAndService(type, new GetTaskConfigAndService() {
				@Override
				public <Config extends BaseTaskConfig> void getConfigAndService(BaseConfigService<Config> configService,
						Map<String, Config> configMap) {
					data.put(type, configMap.values());
				}
			});
		}
		return data;
	}
	
	public Map<String, Collection<? extends BaseNotifyConfig>> getNofityConfigList(){
		Map<String, Collection<? extends BaseNotifyConfig>> data = new HashMap<String, Collection<? extends BaseNotifyConfig>>();
		for(String type : objectService.getNotifyConfigTypeList()) {
			objectService.getiNotifyConfigAndService(type, new GetNotifyConfigAndService() {
				@Override
				public <NotifyConfig extends BaseNotifyConfig> void getConfigAndService(
						BaseConfigService<NotifyConfig> configService, Map<String, NotifyConfig> configMap) {
					 data.put(type, configMap.values());
				}
			});
		}
		return data;
	}
	
	private void refreshNofity() {
		for(String type: objectService.getNotifyConfigTypeList()) {
			objectService.getiNotifyConfigAndService(type, new GetNotifyConfigAndService() {
				@Override
				public <NotifyConfig extends BaseNotifyConfig> void getConfigAndService(
						BaseConfigService<NotifyConfig> configService, Map<String, NotifyConfig> configMap) {
					refreshNofity(configService, configMap);
				}
			});
		}
	}
	
	private void refreshConfig() {
		for(String type : objectService.getTaskConfigTypeList()) {
			objectService.getTaskConfigAndService(type, new GetTaskConfigAndService() {
				@Override
				public <Config extends BaseTaskConfig> void getConfigAndService(BaseConfigService<Config> configService,
						Map<String, Config> configMap) {
					refreshConfig(configService, configMap);
				}
			});
		}
	}
	
	private void addTask(String taskId, BaseWorker worker, String cron) {
		if(mList.containsKey(taskId)) {
			logger.warn("the taskId[" + taskId + "] is existed.");
			cancelTask(taskId);
		}
		ScheduledFuture<?> future = null;
		try {
			future = mScheduler.schedule(worker, new CronTrigger(cron));			
		}
		catch(Exception ex) {
			logger.warn(ex.getMessage());
			ex.printStackTrace();
		}
		if(future != null) {
			mList.put(taskId, future);
			logger.info("Task " + taskId + "  is added");
		}
	}
	
	private void cancelTask(String taskId) {
		if(mList.containsKey(taskId)) {
			mList.get(taskId).cancel(true);
			mList.remove(taskId);
			logger.info("Task " + taskId + "  canceled");
		}
	}
	
	private void clearAllTaskConfig() {
		for(String type : objectService.getTaskConfigTypeList()) {
			objectService.getTaskConfigAndService(type, new GetTaskConfigAndService() {
				@Override
				public <Config extends BaseTaskConfig> void getConfigAndService(BaseConfigService<Config> configService,
						Map<String, Config> configMap) {
					configMap.clear();
				}
			});
		}
	}
	
	private void cancelAllTask() {
		for(String taskId :mList.keySet()) {
			cancelTask(taskId);			
		}
	}
	
	private void resetTask(String taskId, BaseWorker taskWorker, String cron) {
		cancelTask(taskId);
		addTask(taskId, taskWorker, cron);
	}
	
	private interface GetDiffCallBack{
		void getAddList(List<String> list);
		void getDelList(List<String> list);
		void getKeepList(List<String> list);
	}
	
	private void getDifConfig(Set<String> oldList, Set<String> newList, GetDiffCallBack callBack) {
		if(callBack != null) {
			List<String> listAdd = new ArrayList<String>();
			List<String> listDel = new ArrayList<String>();
			List<String> listKeep = new ArrayList<String>();
			
			for(String id : oldList) {
				if(newList.contains(id)) {
					listKeep.add(id);
				}
				else {
					listDel.add(id);
				}
			}
			
			for(String id : newList) {
				if(!oldList.contains(id)) {
					listAdd.add(id);
				}
			}
			
			callBack.getAddList(listAdd);
			callBack.getDelList(listDel);
			callBack.getKeepList(listKeep);
		}
	}

	
	private <Config extends BaseTaskConfig> void refreshConfig(BaseConfigService<Config> service, Map<String, Config> configMap) {
		
		Map<String, Config> configNewMap = new HashMap<String, Config>();
		for(Config config : service.getConfigList()) {
			configNewMap.put(config.getId(), config);
		}
		
		getDifConfig(configMap.keySet(), configNewMap.keySet(), new GetDiffCallBack() {
			@Override
			public void getAddList(List<String> list) {
				for(String id : list) {
					Config config = configNewMap.get(id);
					logger.info("get new " + config.getClass().getSimpleName() + " - " + mGson.toJson(config));
					BaseWorker worker = null;
					try {
						worker = objectService.getWorker(config);
					}
					catch(Exception ex) {
						throw new RuntimeException(ex.getMessage());
					}
					addTask(config.getId(), worker , config.getCron());
					if(mList.containsKey(config.getId())) {
						configMap.put(config.getId(), config);						
					}
				}
			}
			@Override
			public void getDelList(List<String> list) {
				for(String id : list) {
					Config config = configMap.get(id);
					logger.info("del old " + config.getClass().getSimpleName() +" - " + mGson.toJson(config));
					configMap.remove(id);
					cancelTask(id);
				}
			}
			@Override
			public void getKeepList(List<String> list) {
				for(String id : list) {
					Config newConfig = configNewMap.get(id);
					Config oldConfig = configMap.get(id);
					if(!mGson.toJson(newConfig).equals(mGson.toJson(oldConfig))) {
						logger.info("update " + newConfig.getClass().getSimpleName() + " - " + mGson.toJson(newConfig));
						BaseWorker worker = null;
						try {
							worker = objectService.getWorker(newConfig);
						}
						catch(Exception ex) {
							throw new RuntimeException(ex.getMessage());
						}
						resetTask(newConfig.getId(), worker, newConfig.getCron());
						if(mList.containsKey(newConfig.getId())) {
							configMap.put(newConfig.getId(), newConfig);							
						}
					}
				}
			}
		});
	}
	
	private <Config extends BaseNotifyConfig, S extends BaseConfigService<Config>, NofityWorker extends Notify>  void refreshNofity(S service, Map<String, Config> configMap) {
		Map<String, Config> configNewMap = new HashMap<String, Config>();
		
		List<Config> list = null;
		list = service.getConfigList();
		for(Config config : list) {
			configNewMap.put(config.getId(), config);
		}
		
		getDifConfig(configMap.keySet(), configNewMap.keySet(), new GetDiffCallBack() {
			@Override
			public void getAddList(List<String> list) {
				for(String id : list) {
					Config config = configNewMap.get(id);
					logger.info("get new " + config.getClass().getSimpleName() + " - " + mGson.toJson(config));
					configMap.put(config.getId(), config);
				}
				if(list.size() > 0) {
					cancelAllTask();
					clearAllTaskConfig();
				}
			}
			@Override
			public void getDelList(List<String> list) {
				for(String id : list) {
					logger.info("del old DingNotifyConfig - " + mGson.toJson(configMap.get(id)));
					configMap.remove(id);
				}
				if(list.size() > 0) {
					cancelAllTask();
					clearAllTaskConfig();
				}
			}
			@Override
			public void getKeepList(List<String> list) {
				boolean check = false;
				for(String id : list) {
					Config newConfig = configNewMap.get(id);
					Config oldConfig = configMap.get(id);
					if(!mGson.toJson(newConfig).equals(mGson.toJson(oldConfig))) {
						logger.info("update " + newConfig.getClass().getSimpleName() + " - " + mGson.toJson(newConfig));
						check = true;
						configMap.put(newConfig.getId(), newConfig);
					}
				}
				if(check) {
					cancelAllTask();
					clearAllTaskConfig();
				}
			}
		});
	}
}
