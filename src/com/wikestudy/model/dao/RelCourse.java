package com.wikestudy.model.dao;

public class RelCourse {
	private int couId;
	private String couName;
	private String couPricUrl;
	private String recGrade;
	
	public void setCouId(int couId) {
		this.couId = couId;
	}
	public void setCouName(String couName) {
		this.couName = couName;
	}
	public void setCouPricUrl(String couPricUrl) {
		this.couPricUrl = couPricUrl;
	}
	public void setRecGrade(String recGrade) {
		this.recGrade = recGrade;
	}
	
	
	public int getCouId() {
		return couId;
	}
	public String getCouName() {
		return couName;
	}
	public String getCouPricUrl() {
		return couPricUrl;
	}
	public String getRecGrade() {
		return recGrade;
	}
	
	
	
}
