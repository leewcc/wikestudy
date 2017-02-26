package com.wikestudy.model.pojo;

import java.sql.Date;

// 学生个人课时进度
public class StuSchedule {
	private int schId;           // 主键
	private int stuId;           // 学生Id
	private int secId;            // 课时Id 
	private String secCondition; // 课时状态 1: 未学; 2: 正在学; 3: 已学完
	private String secTime;   // 视频上次的播放时间
	private boolean secExam; // 标志课时/章节是否已测试
	                                           //  0: 未测试; 1: 已测试
	private int    chaId;      //  章Id
	public void setSchId(int schId) {
		this.schId = schId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public void setSecId(int secId) {
		this.secId = secId;
	}
	public void setSecCondition(String secCondition) {
		this.secCondition = secCondition;
	}
	public void setSecTime(String secTime) {
		this.secTime = secTime;
	}
	public void setSecExam(boolean secExam) {
		this.secExam = secExam;
	}
	public void setChaId(int chaId) {
		this.chaId = chaId;
	}
	public int getSchId() {
		return schId;
	}
	public int getStuId() {
		return stuId;
	}
	public int getSecId() {
		return secId;
	}
	public String getSecCondition() {
		return secCondition;
	}
	public String getSecTime() {
		return secTime;
	}
	public boolean isSecExam() {
		return secExam;
	}
	public int getChaId() {
		return chaId;
	}
	
	
	
}
