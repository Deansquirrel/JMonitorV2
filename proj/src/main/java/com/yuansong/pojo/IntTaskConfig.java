package com.yuansong.pojo;

public class IntTaskConfig extends BaseTaskConfig {
	
	private String server;
	private String port;
	private String dbName;
	private String dbUser;
	private String dbPwd;
	private String search;
	
	private int checkMax;
	private int checkMin;
	
	private String msgSearch;
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public int getCheckMax() {
		return checkMax;
	}
	public void setCheckMax(int checkMax) {
		this.checkMax = checkMax;
	}
	public int getCheckMin() {
		return checkMin;
	}
	public void setCheckMin(int checkMin) {
		this.checkMin = checkMin;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPwd() {
		return dbPwd;
	}
	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}
	public String getMsgSearch() {
		return msgSearch;
	}
	public void setMsgSearch(String msgSearch) {
		this.msgSearch = msgSearch;
	}

}
