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
import com.yuansong.pojo.JiraSearchTaskConfig;
import com.yuansong.service.BaseConfigService;

@Controller
@RequestMapping(value="/TaskConfig/JiraSearch")
public class TaskConfigJiraSearchController extends BaseConfigController<JiraSearchTaskConfig> {
	private final Logger logger = Logger.getLogger(TaskConfigJiraSearchController.class);
	
	@Autowired
	private BaseConfigService<JiraSearchTaskConfig> mJiraSearchTaskConfigService;

	@Override
	protected BaseConfigService<JiraSearchTaskConfig> getConfigService() {
		return mJiraSearchTaskConfigService;
	}

	@Override
	protected String getConfigTypeStr() {
		return "JiraSearch";
	}
	
	@RequestMapping(value="/List")
	public ModelAndView jiraSearchTaskConfigList(Map<String, Object> model) {
		logger.debug("JiraSearchController jiraSearchTaskConfigList");
		return new ModelAndView("TaskConfigJiraSearchList", this.getListModel(model));
	}
	
	@RequestMapping(value="/Add",method=RequestMethod.GET)
	public ModelAndView jiraSearchTaskConfigAdd(Map<String, Object> model) {
		logger.debug("JiraSearchController jiraSearchTaskConfigAdd");
		return new ModelAndView("TaskConfigJiraSearchAdd", this.getAddGetModel(model));
	}
	
	@Transactional
	@RequestMapping(value="/Add",method=RequestMethod.POST)
	public ModelAndView jiraSearchConfigAddAction(
			@RequestParam("i-title") String title,
			@RequestParam("i-remark") String remark,
			@RequestParam("i-server") String server,
			@RequestParam("i-jql") String jql,
			@RequestParam("i-user") String user,
			@RequestParam("i-pwd") String pwd,
			@RequestParam("i-cron") String cron,
			Map<String, Object> model) {
		logger.debug("JiraSearchController jiraSearchConfigAddAction");
		
		JiraSearchTaskConfig config = new JiraSearchTaskConfig();
		config.setId(CommonFun.UUID());
		config.setTitle(title.trim());
		config.setRemark(remark.trim());
		config.setServer(server.trim());
		config.setJql(jql.trim());
		config.setUser(user.trim());
		config.setPwd(pwd.trim());
		config.setCron(cron);
		
		return new ModelAndView("responsePage", this.getAddPostModel(model, config));
	}
	
	@Transactional
	@RequestMapping(value="/Del",method=RequestMethod.POST)
	public ModelAndView jiraSearchConfigDelAction(
			@RequestParam("i-id") String id,
			Map<String, Object> model) {
		logger.debug("JiraSearchController jiraSearchConfigDelAction");
		logger.debug(id);
		
		JiraSearchTaskConfig config = new JiraSearchTaskConfig();
		config.setId(id);
		
		return new ModelAndView("responsePage", this.getDelModel(model, config));
	}
	
	@RequestMapping(value="/Detail/{taskId}")
	public ModelAndView jiraSearchConfigDetail(@PathVariable String taskId, Map<String, Object> model) {
		logger.debug("JiraSearchController jiraSearchConfigDetail - " + taskId);
		return new ModelAndView("TaskConfigJiraSearchDetail", this.getDetailModel(model, taskId));
	}
	

}
