package com.bailochan.response;

public class LoginResponse {
	
	private Integer userid;
	
	private String name;
	
	private String userType;
	
	private boolean isValidLonin;
	
	private String pwdChanged;

	private DasboardResponse dashboard;
	
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public DasboardResponse getDashboard() {
		return dashboard;
	}

	public void setDashboard(DasboardResponse dashboard) {
		this.dashboard = dashboard;
	}

	public boolean isValidLonin() {
		return isValidLonin;
	}

	public void setValidLonin(boolean isValidLonin) {
		this.isValidLonin = isValidLonin;
	}

	public String getPwdChanged() {
		return pwdChanged;
	}

	public void setPwdChanged(String pwdChanged) {
		this.pwdChanged = pwdChanged;
	}

	
	
	
}
