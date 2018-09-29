package com.yuansong.notify;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.yuansong.common.HttpUtils;
import com.yuansong.pojo.DingNotifyConfig;

public class DingNotify implements Notify {
	
	private final Logger logger = Logger.getLogger(DingNotify.class);
	
	private DingNotifyConfig config = null;
	private Gson mGson = null;
	private HttpUtils httpUtils = null;
	
	
	public DingNotify(DingNotifyConfig config) {
		this.config = config;
		mGson = new Gson();
		httpUtils = new HttpUtils();
	}

	@Override
	public void send(String msg) {
		if(config.getRobotToken().equals("")) {
			logger.warn("robotToken is tempy");
			return;
		}
		
		String url = "https://oapi.dingtalk.com/robot/send?access_token=" + config.getRobotToken();
		
		Map<String,String> textContent = new HashMap<String, String>();
		textContent.put("content",msg);
		Map<String, Object> objMsg = new HashMap<String, Object>();
		objMsg.put("msgtype", "text");
		objMsg.put("text", textContent);
		
		String result = "";
		result = httpUtils.httpPostJson(url, mGson.toJson(objMsg));			
			
		DingMsgResult r = mGson.fromJson(result, DingMsgResult.class);
		if(r == null) {
			logger.debug("HttpResponse - [" + result + "]");
		}
		else if(r.errcode != 0) {
			logger.debug("errcode - " + String.valueOf(r.errcode));
			logger.debug("errmsg - " + r.errmsg);
		}
	}

	@Override
	public String getConfigId() {
		return config.getId();
	}
	
	private class DingMsgResult{
		int errcode;
		String errmsg;
	}


}
