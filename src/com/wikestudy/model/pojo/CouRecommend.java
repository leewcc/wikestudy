package com.wikestudy.model.pojo;

import com.wikestudy.model.enums.Grade;


// 课程推荐表
public class CouRecommend {
	private int      recId;           // 主键Id
	private int      couId;          // 课程表Id
	private String recGrade;    //  被推荐的年级
	
	public void setRecId(int recId) {
		this.recId = recId;
	}
	public void setCouId(int couId) {
		this.couId = couId;
	}
	public void setRecGrade(String recGrade) {
		this.recGrade = recGrade;
	}
	
	public int getRecId() {
		return recId;
	}
	public int getCouId() {
		return couId;
	}
	public String getRecGrade() {
		return recGrade;
	}
	
	
	
	
	
}
