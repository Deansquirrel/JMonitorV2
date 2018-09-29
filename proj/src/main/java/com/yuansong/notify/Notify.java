package com.yuansong.notify;

public interface Notify {
	public String getConfigId();
	public void send(String msg);
}
