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
import com.yuansong.pojo.HealthTaskConfig;
import com.yuansong.service.BaseConfigService;

@Controller
@RequestMapping(value="/TaskConfig/Health")
public class TaskConfigHealthController extends BaseConfigController<HealthTaskConfig> {
	
	private final Logger logger = Logger.getLogger(TaskConfigHealthController.class);
	
	@Autowired
	private BaseConfigService<HealthTaskConfig> mHealthTaskConfigService;

	@RequestMapping(value="/List")
	public ModelAndView healthTaskConfigList(Map<String, Object> model) {
		logger.debug("TaskConfigHealthController healthTaskConfigList");
		return new ModelAndView("TaskConfigHealthList", this.getListModel(model));
	}
	
	
	@RequestMapping(value="/Add",method=RequestMethod.GET)
	public ModelAndView healthTaskConfigAdd(Map<String, Object> model) {
		logger.debug("TaskConfigHealthController healthTaskConfigAdd");
		return new ModelAndView("TaskConfigHealthAdd", this.getAddGetModel(model));
	}
	
	@Transactional
	@RequestMapping(value="/Add",method=RequestMethod.POST)
	public ModelAndView healthTaskConfigAddAction(
			@RequestParam("i-title") String title,
			@RequestParam("i-remark") String remark,			
			@RequestParam("i-cron") String cron,
			@RequestParam("i-msgtitle") String msgTitle,
			@RequestParam("i-msgcontent") String msgContent,
			Map<String, Object> model) {
		logger.debug("TaskConfigHealthController healthTaskConfigAddAction");
		
		HealthTaskConfig config = new HealthTaskConfig();
		config.setId(CommonFun.UUID());
		config.setTitle(title.trim());
		config.setRemark(remark.trim());
		config.setCron(cron);
		config.setMsgTitle(msgTitle);
		config.setMsgContent(msgContent);
		
		return new ModelAndView("responsePage", this.getAddPostModel(model, config));
	}
	
	@Transactional
	@RequestMapping(value="/Del",method=RequestMethod.POST)
	public ModelAndView healthTaskConfigDelAction(
			@RequestParam("i-id") String id,
			Map<String, Object> model) {
		logger.debug("TaskConfigHealthController healthTaskConfigDelAction");
		logger.debug(id);
		
		HealthTaskConfig config = new HealthTaskConfig();
		config.setId(id);
		
		return new ModelAndView("responsePage", this.getDelModel(model, config));
	}
	
	@RequestMapping(value="/Detail/{taskId}")
	public ModelAndView healthTaskConfigDetail(@PathVariable String taskId, Map<String, Object> model) {
		logger.debug("TaskConfigHealthController healthTaskConfigDetail - " + taskId);
		return new ModelAndView("TaskConfigHealthDetail", this.getDetailModel(model, taskId));
	}


	@Override
	protected BaseConfigService<HealthTaskConfig> getConfigService() {
		return mHealthTaskConfigService;
	}


	@Override
	protected String getConfigTypeStr() {
		return "Health";
	}
}
