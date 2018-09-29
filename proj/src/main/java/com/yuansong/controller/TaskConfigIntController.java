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
import com.yuansong.pojo.IntTaskConfig;
import com.yuansong.service.BaseConfigService;

@Controller
@RequestMapping(value="/TaskConfig/Int")
public class TaskConfigIntController extends BaseConfigController<IntTaskConfig> {

	private final Logger logger = Logger.getLogger(TaskConfigIntController.class);
	
	@Autowired
	private BaseConfigService<IntTaskConfig> mIntTaskConfigService;
	
	@RequestMapping(value="/List")
	public ModelAndView intTaskConfigList(Map<String, Object> model) {
		logger.debug("TaskConfigIntController intTaskConfigList");	
		return new ModelAndView("TaskConfigIntList", this.getListModel(model));
	}
	
	@RequestMapping(value="/Add",method=RequestMethod.GET)
	public ModelAndView intTaskConfigAdd(Map<String, Object> model) {
		logger.debug("TaskConfigIntController intTaskConfigAdd");
		return new ModelAndView("TaskConfigIntAdd", this.getAddGetModel(model));
	}
	
	@Transactional
	@RequestMapping(value="/Add",method=RequestMethod.POST)
	public ModelAndView intTaskConfigAddAction(
			@RequestParam("i-title") String title,
			@RequestParam("i-remark") String remark,
			@RequestParam("i-server") String server,
			@RequestParam("i-port") String port,
			@RequestParam("i-dbname") String dbName,
			@RequestParam("i-user") String user,
			@RequestParam("i-pwd") String pwd,
			@RequestParam("i-search") String search,
			@RequestParam("i-cron") String cron,
			@RequestParam("i-checkmax") String checkMax,
			@RequestParam("i-checkmin") String checkMin,
			@RequestParam("i-msgtitle") String msgTitle,
			@RequestParam("i-msgcontent") String msgContent,
			Map<String, Object> model) {
		
		logger.debug("TaskConfigIntController intTaskConfigAddAction");
		
		IntTaskConfig config = new IntTaskConfig();
		config.setId(CommonFun.UUID());
		config.setTitle(title.trim());
		config.setRemark(remark.trim());
		config.setServer(server.trim());
		config.setPort(port.trim());
		config.setDbName(dbName.trim());
		config.setDbUser(user.trim());
		config.setDbPwd(pwd.trim());
		config.setSearch(search.trim());
		config.setCron(cron);
		config.setCheckMax(Integer.valueOf(checkMax.trim()));
		config.setCheckMin(Integer.valueOf(checkMin.trim()));
		config.setMsgTitle(msgTitle);
		config.setMsgContent(msgContent);
		return new ModelAndView("responsePage", this.getAddPostModel(model, config));
	}
	
	@Transactional
	@RequestMapping(value="/Del",method=RequestMethod.POST)
	public ModelAndView intTaskConfigDelAction(
			@RequestParam("i-id") String id,
			Map<String, Object> model) {
		logger.debug("TaskConfigIntController intTaskConfigDelAction");
		
		IntTaskConfig config = new IntTaskConfig();
		config.setId(id);
		
		return new ModelAndView("responsePage", this.getDelModel(model, config));
	}
	
	@RequestMapping(value="/Detail/{taskId}")
	public ModelAndView intTaskConfigDetail(@PathVariable String taskId, Map<String, Object> model) {
		logger.debug("TaskConfigIntController intTaskConfigDetail");	
		return new ModelAndView("TaskConfigIntDetail", this.getDetailModel(model, taskId));
	}

	@Override
	protected BaseConfigService<IntTaskConfig> getConfigService() {
		return mIntTaskConfigService;
	}

	@Override
	protected String getConfigTypeStr() {
		return "Int";
	}
	
}
