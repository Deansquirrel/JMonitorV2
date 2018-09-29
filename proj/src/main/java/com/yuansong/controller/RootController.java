package com.yuansong.controller;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yuansong.pojo.BaseNotifyConfig;
import com.yuansong.pojo.BaseTaskConfig;
import com.yuansong.service.TaskManagerService;
import com.yuansong.task.RegularTask;

@Controller
@RequestMapping(value="/")
public class RootController {

	private final Logger logger = Logger.getLogger(RootController.class);
	
	private final Gson mGson = new Gson();
	
	@Autowired
	private RegularTask task;
	
	@Autowired
	private TaskManagerService taskManagerService;
	
	@RequestMapping(value="/")
	public ModelAndView defaultPage(Map<String, Object> model){
		logger.debug("跳转到 Current");
		return new ModelAndView("redirect:/Current");
	}
	
	@RequestMapping(value="/Current")
	public ModelAndView current(Map<String, Object> model){
		logger.debug("RootController current");
		
		List<Map<String, String>> configList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> notifyList = new ArrayList<Map<String, String>>();
		Map<String, String> listItem;
		
		Map<String, Collection<? extends BaseTaskConfig>> taskConfigMap = taskManagerService.getTaskConfigList();
		
		for(String type : taskConfigMap.keySet()) {
			Collection<? extends BaseTaskConfig> list = taskConfigMap.get(type);
			for(BaseTaskConfig config : list) {
				listItem = new HashMap<String, String>();
				listItem.put("id", config.getId());
				listItem.put("title", config.getTitle());
				listItem.put("remark", config.getRemark());
				listItem.put("type", type);
				listItem.put("configUrl", "/TaskConfig/" + type + "/Detail/" + config.getId());
				configList.add(listItem);
			}
		}
		
		configList.sort(new Comparator<Map<String, String>>(){
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				String str1 = o1.get("type") + o1.get("title") + o1.get("remark");
				String str2 = o2.get("type") + o2.get("title") + o2.get("remark");
				Collator instance = Collator.getInstance(Locale.CHINA);
				return instance.compare(str1, str2);
			}
		});
		
		model.put("configList", configList);
		
		Map<String, Collection<? extends BaseNotifyConfig>> notifyConfigMap = taskManagerService.getNofityConfigList();
		
		for(String type : notifyConfigMap.keySet()) {
			Collection<? extends BaseNotifyConfig> list = notifyConfigMap.get(type);
			for(BaseNotifyConfig config : list) {
				listItem = new HashMap<String, String>();
				listItem.put("id", config.getId());
				listItem.put("title", config.getTitle());
				listItem.put("remark", config.getRemark());
				listItem.put("type", type);
				listItem.put("configUrl", "/MessageSender/" + type + "/Detail/" + config.getId());
				notifyList.add(listItem);
			}
		}
		
		notifyList.sort(new Comparator<Map<String, String>>(){
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				String str1 = o1.get("type") + o1.get("title") + o1.get("remark");
				String str2 = o2.get("type") + o2.get("title") + o2.get("remark");
				Collator instance = Collator.getInstance(Locale.CHINA);
				return instance.compare(str1, str2);
			}
		});
		
		model.put("notifyList", notifyList);
		
		List<String> menuList = new ArrayList<String>();
		menuList.add("Current");
		
		model.put("menulist", mGson.toJson(menuList));
		
		return new ModelAndView("CurrList",model);
	} 
	
	@RequestMapping(value="/PageNotFound")
	public ModelAndView pageNotFound(Map<String, Object> model){
		logger.debug("RootController PageNotFound");
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", 404);
		data.put("message", "page not found");
		
		model.put("info",mGson.toJson(data));
		
		return new ModelAndView("responsePage",model);
	}
	
	@RequestMapping(value="/Refresh")
	public ModelAndView refresh(Map<String, Object> model){
		logger.debug("RootController refresh");
		task.configReresh();
		model.put("info", "success");
		
		return new ModelAndView("responsePage",model);
	}
	
	@RequestMapping(value="/Test")
	public ModelAndView test(Map<String, Object> model){
		logger.debug("RootController test");
				
		model.put("info", "success");
		
		return new ModelAndView("responsePage",model);
	}
	
//	@RequestMapping(value="/testPage")
//	public ModelAndView testPage(Map<String, Object> model){
//		logger.info("RootController testPage");
//		model.put("info", "testPage");
//
//		return new ModelAndView("testPage",model);
//	}
//	
//	@RequestMapping(value="/testPageOne")
//	public ModelAndView testPageOne(Map<String, Object> model){
//		logger.info("RootController testPageOne");
//		model.put("info", "testPageOne");
//
//		return new ModelAndView("testPage",model);
//	}
//	
//	@RequestMapping(value="/testErrorPage")
//	public ModelAndView testErrorPage(Map<String, Object> model){
//		logger.info("RootController testErrorPage");
//		model.put("info", "testPageOne");
//
//		throw new RuntimeException("sfesf");
////		return new ModelAndView("testPage",model);
//	}
	
}
