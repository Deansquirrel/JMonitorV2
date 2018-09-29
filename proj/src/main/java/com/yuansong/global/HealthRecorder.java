package com.yuansong.global;

public class HealthRecorder {
	
	//健康检查协助内容
		private static String refreshConfigWarn = "";

		public static boolean isHealth() {
			StringBuilder sb = new StringBuilder();
			sb.append(refreshConfigWarn);
			return sb.toString().equals("");
		}
		
		public static String getHealthWarnMsg() {
			StringBuilder sb = new StringBuilder();
			sb.append("刷新配置文件时有错误 - ").append(refreshConfigWarn);
			return sb.toString();
		}

		public static void setRefreshConfigWarn(String refreshConfigWarn) {
			HealthRecorder.refreshConfigWarn = refreshConfigWarn;
		}

}
