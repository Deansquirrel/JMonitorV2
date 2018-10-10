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
import com.yuansong.pojo.CrmDzXfTestTaskConfig;
import com.yuansong.service.BaseConfigService;

@Controller
@RequestMapping(value="/TaskConfig/CrmDzXfTest")
public class TaskConfigCrmDzXfTestController extends BaseConfigController<CrmDzXfTestTaskConfig> {
	
	private final Logger logger = Logger.getLogger(TaskConfigCrmDzXfTestController.class);
	
	@Autowired
	private BaseConfigService<CrmDzXfTestTaskConfig> mCrmDzXfTestTaskConfigService;

	@Override
	protected BaseConfigService<CrmDzXfTestTaskConfig> getConfigService() {
		return mCrmDzXfTestTaskConfigService;
	}

	@Override
	protected String getConfigTypeStr() {
		return "CrmDzXfTest";
	}
	
	@RequestMapping(value="/List")
	public ModelAndView configList(Map<String, Object> model) {
		logger.debug(this.getClass().getSimpleName()  + " configList");
		return new ModelAndView("TaskConfig" + getConfigTypeStr() + "List", this.getListModel(model));
	}
	
	@RequestMapping(value="/Add",method=RequestMethod.GET)
	public ModelAndView configAdd(Map<String, Object> model) {
		logger.debug(this.getClass().getSimpleName() + " configAdd");
		return new ModelAndView("TaskConfig" + getConfigTypeStr()  +"Add", this.getAddGetModel(model));
	}
	
	@Transactional
	@RequestMapping(value="/Add",method=RequestMethod.POST)
	public ModelAndView configAddAction(
			@RequestParam("i-title") String title,
			@RequestParam("i-remark") String remark,			
			@RequestParam("i-cron") String cron,
			@RequestParam("i-msgtitle") String msgTitle,
			@RequestParam("i-msgcontent") String msgContent,
			
			@RequestParam("i-address") String address,
			@RequestParam("i-passport") String passPort,
			@RequestParam("i-passporttype") String passportType,
			
			Map<String, Object> model) {
		logger.debug(this.getClass().getSimpleName() + " configAddAction");
		
		CrmDzXfTestTaskConfig config = new CrmDzXfTestTaskConfig();
		config.setId(CommonFun.UUID());
		config.setTitle(title.trim());
		config.setRemark(remark.trim());
		config.setAddress(address);
		config.setPassport(passPort);
		int iPassportType = -1;
		try {
			iPassportType = Integer.valueOf(passportType).intValue();
		}
		catch(Exception ex) {
			logger.warn("将字符串【" + passportType +"】转换为Int时出错：" + ex.getMessage());
			ex.printStackTrace();
		}
		config.setPassportType(iPassportType);
		config.setCron(cron);
		config.setMsgTitle(msgTitle);
		config.setMsgContent(msgContent);
		
		return new ModelAndView("responsePage", this.getAddPostModel(model, config));
	}
	
	@Transactional
	@RequestMapping(value="/Del",method=RequestMethod.POST)
	public ModelAndView configDelAction(
			@RequestParam("i-id") String id,
			Map<String, Object> model) {
		logger.debug(this.getClass().getSimpleName() + " configDelAction");
		logger.debug(id);
		
		CrmDzXfTestTaskConfig config = new CrmDzXfTestTaskConfig();
		config.setId(id);
		
		return new ModelAndView("responsePage", this.getDelModel(model, config));
	}
	
	@RequestMapping(value="/Detail/{taskId}")
	public ModelAndView configDetail(@PathVariable String taskId, Map<String, Object> model) {
		logger.debug(this.getClass().getSimpleName() + " configDetail - " + taskId);
		return new ModelAndView("TaskConfig" + getConfigTypeStr() + "Detail", this.getDetailModel(model, taskId));
	}
	

}
