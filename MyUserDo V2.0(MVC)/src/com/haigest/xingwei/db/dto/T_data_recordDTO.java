package com.haigest.xingwei.db.dto;

/** 
 * t_data_record表-数据传输对象
 * @author 灯芯科技 李远念
 */  

public class T_data_recordDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// field属性

	private java.lang.Long id;
	private java.lang.String recordIp;
	private java.lang.String recordUserId;
	private java.lang.String recordLabels;
	private java.lang.String dataDetails;
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

	public void setRecordIp(java.lang.String _recordIp) {
		this.recordIp = _recordIp;
	}

	public void setRecordUserId(java.lang.String _recordUserId) {
		this.recordUserId = _recordUserId;
	}

	public void setRecordLabels(java.lang.String _recordLabels) {
		this.recordLabels = _recordLabels;
	}

	public void setDataDetails(java.lang.String _dataDetails) {
		this.dataDetails = _dataDetails;
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

	public java.lang.String getRecordIp() {
		return this.recordIp;
	}

	public java.lang.String getRecordUserId() {
		return this.recordUserId;
	}

	public java.lang.String getRecordLabels() {
		return this.recordLabels;
	}

	public java.lang.String getDataDetails() {
		return this.dataDetails;
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
