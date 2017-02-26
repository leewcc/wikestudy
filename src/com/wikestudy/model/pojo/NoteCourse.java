package com.wikestudy.model.pojo;

// 视图—笔记查看 By 课程
public class NoteCourse {
	private int couId;
	private String couName; // 课程名
	private String couPricUrl; // 课程图片
	private int noteNumber; // 笔记数量
	
	public String getCouName() {
		return couName;
	}
	public void setCouName(String couName) {
		this.couName = couName;
	}
	public String getCouPricUrl() {
		return couPricUrl;
	}
	public void setCouPricUrl(String couPricUrl) {
		this.couPricUrl = couPricUrl;
	}
	public int getNoteNumber() {
		return noteNumber;
	}
	public void setNoteNumber(int noteNumber) {
		this.noteNumber = noteNumber;
	}
	public int getCouId() {
		return couId;
	}
	public void setCouId(int couId) {
		this.couId = couId;
	}
	
	
}
