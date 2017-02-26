package com.wikestudy.model.pojo;

import java.sql.Date;
import java.sql.Timestamp;


public class Topic {
	private int topId;
	private int topUserId;
	private int topSecId;
	private int labId;
	private boolean topUserEnum;
	private String topTit;
	private Timestamp topTime;
	private String topCon;
	private int topReadNum;
	private int topAnsNum;
	private boolean topIsUp;
	
	private String userName;
	private String photo;
	private String labelName;
	private String sec;
	public void setTopId(int topId) {
		this.topId = topId;
	}
	public void setTopUserId(int topUserId) {
		this.topUserId = topUserId;
	}
	public void setTopSecId(int topSecId) {
		this.topSecId = topSecId;
	}
	public void setLabId(int labId) {
		this.labId = labId;
	}
	public void setTopUserEnum(boolean topUserEnum) {
		this.topUserEnum = topUserEnum;
	}
	public void setTopTit(String topTit) {
		this.topTit = topTit;
	}
	public void setTopTime(Timestamp topTime) {
		this.topTime = topTime;
	}
	public void setTopCon(String topCon) {
		this.topCon = topCon;
	}
	public void setTopReadNum(int topReadNum) {
		this.topReadNum = topReadNum;
	}
	public void setTopAnsNum(int topAnsNum) {
		this.topAnsNum = topAnsNum;
	}
	public void setTopIsUp(boolean topIsUp) {
		this.topIsUp = topIsUp;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	public int getTopId() {
		return topId;
	}
	public int getTopUserId() {
		return topUserId;
	}
	public int getTopSecId() {
		return topSecId;
	}
	public int getLabId() {
		return labId;
	}
	public boolean isTopUserEnum() {
		return topUserEnum;
	}
	public String getTopTit() {
		return topTit;
	}
	public Timestamp getTopTime() {
		return topTime;
	}
	public String getTopCon() {
		return topCon;
	}
	public int getTopReadNum() {
		return topReadNum;
	}
	public int getTopAnsNum() {
		return topAnsNum;
	}
	public boolean isTopIsUp() {
		return topIsUp;
	}
	public String getUserName() {
		return userName;
	}
	public String getPhoto() {
		return photo;
	}
	public String getLabelName() {
		return labelName;
	}
	public String getSec() {
		return sec;
	}
}
