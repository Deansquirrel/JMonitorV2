package com.yuansong.global;

import java.util.HashMap;
import java.util.Map;

public class HealthRecorder {
	
	//健康检查协助内容
	private static String refreshConfigWarn = "";
	
	public static void setRefreshConfigWarn(String refreshConfigWarn) {
		HealthRecorder.refreshConfigWarn = refreshConfigWarn;
	}

	public static boolean isHealth() {
		StringBuilder sb = new StringBuilder();
		sb.append(refreshConfigWarn);
		return sb.toString().equals("");
	}
	
	public static Map<String,String> getHealthWarnMsg() {
		Map<String, String> result = new HashMap<String,String>();
		if(refreshConfigWarn.equals("")) {
			result.put("刷新配置文件时有错误", refreshConfigWarn);
		}
		return result;
	}
}
