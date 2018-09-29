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
import com.yuansong.pojo.DingNotifyConfig;
import com.yuansong.service.BaseConfigService;

@Controller
@RequestMapping(value="/MessageSender/Ding")
public class NotifyConfigDingController extends BaseConfigController<DingNotifyConfig> {
	
	private final Logger logger = Logger.getLogger(NotifyConfigDingController.class);
	
	@Autowired
	private BaseConfigService<DingNotifyConfig> mDingNotifyConfigService;

	@Override
	protected BaseConfigService<DingNotifyConfig> getConfigService() {
		return mDingNotifyConfigService;
	}

	@Override
	protected String getConfigTypeStr() {
		return "Ding";
	}
	
	@RequestMapping(value="/List")
	public ModelAndView dingMessageConfigList(Map<String, Object> model) {
		logger.debug("NotifyConfigDingController dingMessageConfigList");
		return new ModelAndView("MessageSenderConfigDingList", this.getListModel(model));
	}
	
	@RequestMapping(value="/Add",method=RequestMethod.GET)
	public ModelAndView dingMessageConfigAdd(Map<String, Object> model) {
		logger.debug("NotifyConfigDingController dingMessageConfigAdd");
		return new ModelAndView("MessageSenderConfigDingAdd", this.getAddGetModel(model));
	}
	
	@Transactional
	@RequestMapping(value="/Add",method=RequestMethod.POST)
	public ModelAndView dingMessageConfigAddAction(
			@RequestParam("i-title") String title,
			@RequestParam("i-remark") String remark,
			@RequestParam("i-robottoken") String robotToken,
			Map<String, Object> model) {
		logger.debug("NotifyConfigDingController dingMessageConfigAddAction");
		
		DingNotifyConfig config = new DingNotifyConfig();
		config.setId(CommonFun.UUID());
		config.setTitle(title.trim());
		config.setRemark(remark.trim());
		config.setRobotToken(robotToken);
		
		return new ModelAndView("responsePage", this.getAddPostModel(model, config));
	}
	
	@Transactional
	@RequestMapping(value="/Del",method=RequestMethod.POST)
	public ModelAndView webStateTaskConfigDelAction(
			@RequestParam("i-id") String id,
			Map<String, Object> model) {
		logger.debug("NotifyConfigDingController webStateTaskConfigDelAction");
		logger.debug(id);
		
		DingNotifyConfig config = new DingNotifyConfig();
		config.setId(id);

		return new ModelAndView("responsePage", this.getDelModel(model, config));
	}
	
	@RequestMapping(value="/Detail/{taskId}")
	public ModelAndView dingMessageConfigDetail(@PathVariable String taskId, Map<String, Object> model) {
		logger.debug("NotifyConfigDingController webStateTaskConfigDetail - " + taskId);
		return new ModelAndView("MessageSenderConfigDingDetail", this.getDetailModel(model, taskId));
	}
}
