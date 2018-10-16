package com.yuansong.controller;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.yuansong.pojo.BaseConfig;
import com.yuansong.service.BaseConfigService;

public abstract class BaseConfigController<T extends BaseConfig> {
	
//	private final Logger logger = Logger.getLogger(BaseConfigController.class);

	private Gson mGson = new Gson();
	
	protected Map<String, Object> getListModel(Map<String, Object> model) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> listItem;
		List<T> configList = getConfigService().getConfigList();
		
		for(T config : configList) {
			listItem = new HashMap<String, String>();
			listItem.put("id", config.getId());
			listItem.put("title", config.getTitle());
			listItem.put("remark", config.getRemark());
			list.add(listItem);
		}
		list.sort(new Comparator<Map<String, String>>(){
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				String str1 = o1.get("title") + o1.get("remark");
				String str2 = o2.get("title") + o2.get("remark");
				Collator instance = Collator.getInstance(Locale.CHINA);
				return instance.compare(str1, str2);
			}
		});

		
		model.put("list", list);
		
		List<String> menuList = new ArrayList<String>();
		menuList.add("TaskConfig");
		menuList.add(getConfigTypeStr());
		menuList.add("List");
		
		model.put("menulist", mGson.toJson(menuList));
		
		return model;
	}
	
	protected Map<String, Object> getAddGetModel(Map<String, Object> model) {		
		List<String> menuList = new ArrayList<String>();
		menuList.add("TaskConfig");
		menuList.add(getConfigTypeStr());
		menuList.add("Add");
		
		model.put("menulist", mGson.toJson(menuList));
		return model;
	}
	
	protected Map<String, Object> getAddPostModel(Map<String, Object> model, T config) {
		Map<String,String> data = new HashMap<String,String>();
		data.put("errCode", "0");
		data.put("errDesc","success");
		
		String check = getConfigService().check(config);
		if(!check.equals("")) {
			data.put("errCode", "1");
			data.put("errDesc",check);
		}
		else {
			try {
				getConfigService().addConfig(config);				
			}catch(Exception ex) {
				data.put("errCode", "1");
				data.put("errDesc",ex.getMessage());				
				ex.printStackTrace();
			}
		}
		
		model.put("info", mGson.toJson(data));
		return model;
	}
	
	protected Map<String, Object> getDelModel(Map<String, Object> model, T config) {
		Map<String,String> data = new HashMap<String,String>();
		data.put("errCode", "0");
		data.put("errDesc","success");
		
		try {
			getConfigService().delConfig(config);
		}catch(Exception ex) {
			data.put("errCode", "-1");
			data.put("errDesc",ex.getMessage());
			ex.printStackTrace();
		}
		
		model.put("info", mGson.toJson(data));
		return model;
	}
	
	protected Map<String, Object> getDetailModel(Map<String, Object> model, String taskId) {
		if(!taskId.trim().equals("")) {
			T config = getConfigService().getConfig(taskId);
			if(config != null) {
				model.put("config",config);
			}
		}
		
		List<String> menuList = new ArrayList<String>();
		menuList.add("TaskConfig");
		menuList.add(getConfigTypeStr());
		menuList.add("List");
		menuList.add("Detail");
		
		model.put("menulist", mGson.toJson(menuList));
		return null;
	}
	
	protected abstract BaseConfigService<T> getConfigService();
	
	protected abstract String getConfigTypeStr();
}
