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
	public ModelAndView configList(Map<String, Object> model) {
		logger.debug("NotifyConfigDingController configList");
		return new ModelAndView("MessageSenderConfigDingList", this.getListModel(model));
	}
	
	@RequestMapping(value="/Add",method=RequestMethod.GET)
	public ModelAndView configAdd(Map<String, Object> model) {
		logger.debug("NotifyConfigDingController configAdd");
		return new ModelAndView("MessageSenderConfigDingAdd", this.getAddGetModel(model));
	}
	
	@Transactional
	@RequestMapping(value="/Add",method=RequestMethod.POST)
	public ModelAndView configAddAction(
			@RequestParam("i-title") String title,
			@RequestParam("i-remark") String remark,
			@RequestParam("i-robottoken") String robotToken,
			Map<String, Object> model) {
		logger.debug("NotifyConfigDingController configAddAction");
		
		DingNotifyConfig config = new DingNotifyConfig();
		config.setId(CommonFun.UUID());
		config.setTitle(title.trim());
		config.setRemark(remark.trim());
		config.setRobotToken(robotToken);
		
		return new ModelAndView("responsePage", this.getAddPostModel(model, config));
	}
	
	@Transactional
	@RequestMapping(value="/Del",method=RequestMethod.POST)
	public ModelAndView configDelAction(
			@RequestParam("i-id") String id,
			Map<String, Object> model) {
		logger.debug("NotifyConfigDingController configDelAction");
		logger.debug(id);
		
		DingNotifyConfig config = new DingNotifyConfig();
		config.setId(id);

		return new ModelAndView("responsePage", this.getDelModel(model, config));
	}
	
	@RequestMapping(value="/Detail/{taskId}")
	public ModelAndView configDetail(@PathVariable String taskId, Map<String, Object> model) {
		logger.debug("NotifyConfigDingController configDetail - " + taskId);
		return new ModelAndView("MessageSenderConfigDingDetail", this.getDetailModel(model, taskId));
	}
}
