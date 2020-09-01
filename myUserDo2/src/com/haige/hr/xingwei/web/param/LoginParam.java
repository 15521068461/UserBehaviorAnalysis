package com.haige.hr.xingwei.web.param;

public class LoginParam {
 
 
	
	 private  String userMobile;
	 private String userPassword;
	 private String imgCode;
	 private String imgCodeToken;
	 private int autoLogin;
	 
	 
	 public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getImgCode() {
		return imgCode;
	}
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	public String getImgCodeToken() {
		return imgCodeToken;
	}
	public void setImgCodeToken(String imgCodeToken) {
		this.imgCodeToken = imgCodeToken;
	}
	public int getAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(int autoLogin) {
		this.autoLogin = autoLogin;
	}
	
	
	 
}
