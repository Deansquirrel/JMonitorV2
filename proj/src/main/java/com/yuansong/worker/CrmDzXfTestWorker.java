package com.yuansong.worker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.yuansong.common.DateTool;
import com.yuansong.notify.Notify;
import com.yuansong.pojo.CrmDzXfTestTaskConfig;

public class CrmDzXfTestWorker extends BaseWorkerAbstractImpl<CrmDzXfTestTaskConfig> {
	
	private final Logger logger = Logger.getLogger(CrmDzXfTestWorker.class);
	
	private final Gson mGson = new Gson();

	public CrmDzXfTestWorker(CrmDzXfTestTaskConfig config, List<Notify> list) {
		super(config, list);
	}

	@Override
	protected String check() {
		CrmDzXfTestTaskConfig config = getConfig();
		String msg = "";
		try {
			msg = subXfTest(config);			
		}
		catch(Exception ex) {
			logger.warn(ex.getMessage());
			ex.printStackTrace();
			StringBuilder sb = new StringBuilder();
			sb.append("定制服务消费接口测试遇到异常：");
			sb.append("\n").append(config.getAddress());
			sb.append("\n").append(ex.getMessage());
			return sb.toString();
		}
		
		return msg;
	}
	
	private String subXfTest(CrmDzXfTestTaskConfig config) throws Exception {
		
		Map<String, Map<String, Object>> data = new HashMap<String, Map<String, Object>>();
		
		Map<String, Object> ywCore = new HashMap<String, Object>();
		ywCore.put("Oprtime",DateTool.getDateStr("yyyy-MM-dd HH:mm:ss"));
		ywCore.put("Oprbrid", 10001);
		ywCore.put("Oprbrname", "测试请求");
		ywCore.put("Oprxfje", 1000000);
		
		data.put("YwCore", ywCore);
		
		Map<String, Object> ywInfo = new HashMap<String, Object>();
		ywInfo.put("Oprywsno", "YW" + DateTool.getDateStr("yyyyMMdd") + "01");
		ywInfo.put("Oprppid", 10001);
		ywInfo.put("Oprppname", "");
		ywInfo.put("Oprid", 182);
		ywInfo.put("Oprname", "管理员");
		ywInfo.put("Oprywdate", DateTool.getDateStr("yyyy-MM-dd") + " 00:00:00");
		ywInfo.put("Oprskbrid", 0);
		ywInfo.put("Oprskbrname", "");
		ywInfo.put("Oprskppid", 0);
		ywInfo.put("Oprskppname", "");
		ywInfo.put("Oprywwindow", "测试请求");
		ywInfo.put("Oprywbno", "");
		ywInfo.put("Oprsummary", "");
		
		data.put("YwInfo", ywInfo);
		
		OutputStreamWriter out = null;
        BufferedReader in = null;
		
		long startTime = 0;
		long endTime = System.currentTimeMillis();
		int httpCode = -1;
		
		try {
			startTime = System.currentTimeMillis();
			
			URL realUrl = new URL(config.getAddress());  
	        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
	        
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	        
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Accept", "application/json");
	        conn.setRequestProperty("passporttype", String.valueOf(config.getPassportType()));
	        conn.setRequestProperty("passport", String.valueOf(config.getPassport()));
	        
	        conn.setConnectTimeout(30000);
	        conn.setReadTimeout(30000);
	        conn.setUseCaches(false);
	        
	        conn.connect();
	        out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
	        out.write(mGson.toJson(data));
	        out.flush();     
	        
	        httpCode = conn.getResponseCode();
	        
	        String result = "";
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
	        if(result.equals("")) logger.debug("返回内容为空");
	        
	        endTime = System.currentTimeMillis();
		}
		catch(Exception ex) {
			throw ex;
		}
		finally {
			if(out != null) {
				out.close();
			}
			if(in != null) {
				in.close();
			}
		}
		
		logger.debug(config.getAddress());
		logger.debug(httpCode);
		logger.debug(endTime - startTime);
		
		if(httpCode == 200 && (endTime - startTime < 5 * 1000)) {
			return "";
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("定制服务消费接口测试：");
			if(!config.getMsgTitle().equals("")) {
				sb.append("\n").append(config.getMsgTitle());
			}
			if(!config.getMsgContent().equals("")) {
				sb.append("\n").append(config.getMsgContent());
			}
			sb.append("\n").append(config.getAddress());
			sb.append("\n").append("返回码：").append(String.valueOf(httpCode));
			sb.append("\n").append("用时：").append(String.valueOf(endTime - startTime));
			
			return sb.toString();
		}
	}
}
