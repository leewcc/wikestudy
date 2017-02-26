package com.wikestudy.model.pojo;

import java.sql.Timestamp;

public class DiscussView {
	// 评论视图
	private int nDId;
	private int stuId;
	private int secId;
	private String nDContent;
	private Timestamp nDReleTime;
	private boolean nDMark;
	private String stuName;
	private String stuPhotoUrl;
	private String secNumber;
	private String secName;
	
	public void setNDId(int nDId) {
		this.nDId = nDId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public void setSecId(int secId) {
		this.secId = secId;
	}
	public void setNDContent(String nDContent) {
		this.nDContent = nDContent;
	}
	public void setNDReleTime(Timestamp nDReleTime) {
		this.nDReleTime = nDReleTime;
	}
	public void setNDMark(boolean nDMark) {
		this.nDMark = nDMark;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public void setStuPhotoUrl(String stuPhotoUrl) {
		this.stuPhotoUrl = stuPhotoUrl;
	}
	public void setSecNumber(String secNumber) {
		this.secNumber = secNumber;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public int getNDId() {
		return nDId;
	}
	public int getStuId() {
		return stuId;
	}
	public int getSecId() {
		return secId;
	}
	public String getNDContent() {
		return nDContent;
	}
	public Timestamp getNDReleTime() {
		return nDReleTime;
	}
	public boolean isNDMark() {
		return nDMark;
	}
	public String getStuName() {
		return stuName;
	}
	public String getStuPortraitUrl(){
		return stuPhotoUrl;
	}
	public String getSecNumber() {
		return secNumber;
	}
	public String getSecName() {
		return secName;
	}

	
	
	
	// 评论只是学生评论, 如果是老师评论呢
	
	
}
