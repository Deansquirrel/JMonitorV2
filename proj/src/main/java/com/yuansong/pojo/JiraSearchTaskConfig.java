package com.yuansong.pojo;

public class JiraSearchTaskConfig extends BaseTaskConfig {
	
	private String server;
	private String jql;
	private String user;
	private String pwd;
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getJql() {
		return jql;
	}
	public void setJql(String jql) {
		this.jql = jql;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Override
	public String getMsgTitle() {
		return "";
	}
	
	@Override
	public void setMsgTitle(String msgTitle) {
	}
	
	@Override
	public String getMsgContent() {
		return "";
	}
	@Override
	public void setMsgContent(String msgContent) {
	}

}
