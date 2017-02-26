package com.wikestudy.model.pojo;

import java.sql.Date;

public class Star {
	private int starId;					//id
	private int starStuId;				//学生id
	private String stuName;
	private String stuPhotoUrl;
	private long stuStudyHour;
	private Date starStuDate;		//成为一周之星的时间
	private static final String rootUrl="/wikestudy/dist/images/portrait/";
	public void setStarId(int starId) {
		this.starId = starId;
	}
	public void setStarStuId(int starStuId) {
		this.starStuId = starStuId;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public void setStuPhotoUrl(String stuPhotoUrl) {
		this.stuPhotoUrl = stuPhotoUrl;
	}
	public void setStuStudyHour(long stuStudyHour) {
		this.stuStudyHour = stuStudyHour;
	}
	public void setStarStuDate(Date starStuDate) {
		this.starStuDate = starStuDate;
	}
	public int getStarId() {
		return starId;
	}
	public int getStarStuId() {
		return starStuId;
	}
	public String getStuName() {
		return stuName;
	}
	/**
	 * 头像处理返回完整路径
	 */
	public String getStuPortraitUrl() {
		if(stuPhotoUrl==null ||"".equals(stuPhotoUrl)) {
			return rootUrl+"default.jpg";
		} else {
			return new  StringBuffer(rootUrl+"s"+starId+"/"+stuPhotoUrl).toString();
		}
	}
	public long getStuStudyHour() {
		return stuStudyHour;
	}
	public Date getStarStuDate() {
		return starStuDate;
	}
	
	

}
