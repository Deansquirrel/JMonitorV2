package com.yuansong.pojo;

public class CrmDzXfTestTaskConfig extends BaseTaskConfig {
	
	//定制服务地址
	private String address;
	//卡凭证
	private String passport;
	//卡凭证类型
	private int passportType;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public int getPassportType() {
		return passportType;
	}
	public void setPassportType(int passportType) {
		this.passportType = passportType;
	}
}
