package com.wikestudy.model.pojo;

// 天信专用
// 功能: 已知课时id, 拿到课时名字, 课程名字，遍历三个表
public class SecToCou {
	private int secId;
	private int secName;
	private String couName;
	private int couId;
	
	public void setSecId(int secId) {
		this.secId = secId;
	}
	public void setSecName(int secName) {
		this.secName = secName;
	}
	public void setCouName(String couName) {
		this.couName = couName;
	}
	
	public void setCouId(int couId) {
		this.couId = couId;
	}
	
	public int getSecId() {
		return secId;
	}
	public int getSecName() {
		return secName;
	}
	public String getCouName() {
		return couName;
	}
	public int getCouId() {
		return couId;
	}
	
}
