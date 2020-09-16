package com.haigest.xingwei.db.dto;

/** 
 * t_user_base表-数据传输对象
 * @author 灯芯科技 李远念
 */  

public class T_user_baseDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// field属性

	private java.lang.Long id;
	private java.lang.String userMobile;
	private java.lang.String userPassword;
	private java.lang.String userName;
	private java.lang.Integer userSex;
	private java.lang.String userLogo;
	private java.lang.String userIntro;
	private java.lang.Integer dataStatus;
	private java.sql.Timestamp dataCreateAt;
	private java.lang.Long dataCreateUserId;
	private java.sql.Timestamp dataUpdateAt;
	private java.lang.Long dataUpdateUserId;
	private java.lang.String extFiled;

	// setter方法

	public void setId(java.lang.Long _id) {
		this.id = _id;
	}

	public void setUserMobile(java.lang.String _userMobile) {
		this.userMobile = _userMobile;
	}

	public void setUserPassword(java.lang.String _userPassword) {
		this.userPassword = _userPassword;
	}

	public void setUserName(java.lang.String _userName) {
		this.userName = _userName;
	}

	public void setUserSex(java.lang.Integer _userSex) {
		this.userSex = _userSex;
	}

	public void setUserLogo(java.lang.String _userLogo) {
		this.userLogo = _userLogo;
	}

	public void setUserIntro(java.lang.String _userIntro) {
		this.userIntro = _userIntro;
	}

	public void setDataStatus(java.lang.Integer _dataStatus) {
		this.dataStatus = _dataStatus;
	}

	public void setDataCreateAt(java.sql.Timestamp _dataCreateAt) {
		this.dataCreateAt = _dataCreateAt;
	}

	public void setDataCreateUserId(java.lang.Long _dataCreateUserId) {
		this.dataCreateUserId = _dataCreateUserId;
	}

	public void setDataUpdateAt(java.sql.Timestamp _dataUpdateAt) {
		this.dataUpdateAt = _dataUpdateAt;
	}

	public void setDataUpdateUserId(java.lang.Long _dataUpdateUserId) {
		this.dataUpdateUserId = _dataUpdateUserId;
	}

	public void setExtFiled(java.lang.String _extFiled) {
		this.extFiled = _extFiled;
	}

	// getter方法

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getUserMobile() {
		return this.userMobile;
	}

	public java.lang.String getUserPassword() {
		return this.userPassword;
	}

	public java.lang.String getUserName() {
		return this.userName;
	}

	public java.lang.Integer getUserSex() {
		return this.userSex;
	}

	public java.lang.String getUserLogo() {
		return this.userLogo;
	}

	public java.lang.String getUserIntro() {
		return this.userIntro;
	}

	public java.lang.Integer getDataStatus() {
		return this.dataStatus;
	}

	public java.sql.Timestamp getDataCreateAt() {
		return this.dataCreateAt;
	}

	public java.lang.Long getDataCreateUserId() {
		return this.dataCreateUserId;
	}

	public java.sql.Timestamp getDataUpdateAt() {
		return this.dataUpdateAt;
	}

	public java.lang.Long getDataUpdateUserId() {
		return this.dataUpdateUserId;
	}

	public java.lang.String getExtFiled() {
		return this.extFiled;
	}

}
