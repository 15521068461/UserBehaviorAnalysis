package com.haige.hr.xingwei.service.bean;

import java.sql.Timestamp;

public class VO4Label {
   
   private long Id;
   private String labelName;
   private String labelExplain;
   private int dataStatus;
   private Timestamp dataCreateAt;
   private long dataCreateUserId;
   private Timestamp dataUpdateAt;
   private long dataUpdateUserId;
public long getId() {
	return Id;
}
public void setId(long id) {
	Id = id;
}
public String getLabelName() {
	return labelName;
}
public void setLabelName(String labelName) {
	this.labelName = labelName;
}
public String getLabelExplain() {
	return labelExplain;
}
public void setLabelExplain(String labelExplain) {
	this.labelExplain = labelExplain;
}
public int getDataStatus() {
	return dataStatus;
}
public void setDataStatus(int dataStatus) {
	this.dataStatus = dataStatus;
}
public Timestamp getdataCreateAt() {
	return dataCreateAt;
}
public void setdataCreateAt(Timestamp dataCreateAt) {
	this.dataCreateAt = dataCreateAt;
}
public long getDataCreateUserId() {
	return dataCreateUserId;
}
public void setDataCreateUserId(long dataCreateUserId) {
	this.dataCreateUserId = dataCreateUserId;
}
public Timestamp getDataUpdateAt() {
	return dataUpdateAt;
}
public void setDataUpdateAt(Timestamp dataUpdateAt) {
	this.dataUpdateAt = dataUpdateAt;
}
public long getDataUpdateUserId() {
	return dataUpdateUserId;
}
public void setDataUpdateUserId(long dataUpdateUserId) {
	this.dataUpdateUserId = dataUpdateUserId;
}
	
	
	
}
