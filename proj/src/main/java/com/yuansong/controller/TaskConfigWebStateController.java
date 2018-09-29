package com.yuansong.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yuansong.common.CommonFun;
import com.yuansong.pojo.WebStateTaskConfig;
import com.yuansong.service.BaseConfigService;

@Controller
@RequestMapping(value="/TaskConfig/WebState")
public class TaskConfigWebStateController extends BaseConfigController<WebStateTaskConfig> {
	
	private final Logger logger = Logger.getLogger(TaskConfigWebStateController.class);
	
	@Autowired
	private BaseConfigService<WebStateTaskConfig> mWebStateTaskConfigService;
	
	@RequestMapping(value="/List")
	public ModelAndView webStateTaskConfigList(Map<String, Object> model) {
		logger.debug("TaskConfigWebStateController webStateTaskConfigList");	
		return new ModelAndView("TaskConfigWebStateList", this.getListModel(model));
	}
	
	@RequestMapping(value="/Add",method=RequestMethod.GET)
	public ModelAndView webStateTaskConfigAdd(Map<String, Object> model) {
		logger.debug("TaskConfigWebStateController webStateTaskConfigAdd");
		return new ModelAndView("TaskConfigWebStateAdd", this.getAddGetModel(model));
	}
	
	@Transactional
	@RequestMapping(value="/Add",method=RequestMethod.POST)
	public ModelAndView webStateTaskConfigAddAction(
			@RequestParam("i-title") String title,
			@RequestParam("i-remark") String remark,
			@RequestParam("i-url") String url,
			@RequestParam("i-cron") String cron,
			@RequestParam("i-msgtitle") String msgTitle,
			@RequestParam("i-msgcontent") String msgContent,
			Map<String, Object> model) {
		logger.debug("TaskConfigWebStateController webStateTaskConfigAddAction");
		
		WebStateTaskConfig config = new WebStateTaskConfig();
		config.setId(CommonFun.UUID());
		config.setTitle(title.trim());
		config.setRemark(remark.trim());
		config.setUrl(url);
		config.setCron(cron);
		config.setMsgTitle(msgTitle);
		config.setMsgContent(msgContent);
		
		return new ModelAndView("responsePage", this.getAddPostModel(model, config));
	}
	
	@Transactional
	@RequestMapping(value="/Del",method=RequestMethod.POST)
	public ModelAndView webStateTaskConfigDelAction(
			@RequestParam("i-id") String id,
			Map<String, Object> model) {
		logger.debug("TaskConfigWebStateController webStateTaskConfigDelAction");
		logger.debug(id);
		
		WebStateTaskConfig config = new WebStateTaskConfig();
		config.setId(id);
		
		return new ModelAndView("responsePage", this.getDelModel(model, config));
	}
	
	@RequestMapping(value="/Detail/{taskId}")
	public ModelAndView webStateTaskConfigDetail(@PathVariable String taskId, Map<String, Object> model) {
		logger.debug("RootController webStateTaskConfigDetail - " + taskId);
		return new ModelAndView("TaskConfigWebStateDetail", this.getDetailModel(model, taskId));
	}

	@Override
	protected BaseConfigService<WebStateTaskConfig> getConfigService() {
		return mWebStateTaskConfigService;
	}

	@Override
	protected String getConfigTypeStr() {
		return "WebState";
	}
}
