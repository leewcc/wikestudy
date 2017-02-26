
package com.wikestudy.model.pojo;


import java.sql.Timestamp;

public class Course {
	private int couId;                //  课程id
	private int teacherId;         //   老师id
	private int labelId;            //    标签id
	private String couName;   //    课程名
	private String couBrief;     //    课程简介
	private String couAnno;    //    课程公告
	private Timestamp couReleTime;//    课程发布时间 
	private String couGrade;   //     课程类型
	private String couPricUrl;//    课程封面url
	private int couStudyNum;//    课程学习人数
	private boolean couRelease;   //    课程发布
	private boolean couUpdated;  // 判断课程是否更新完毕0-未完毕, 1-完毕
	private int recomment;
	private static final String rootUrl="/wikestudy/dist/images/lesson/";
	
	public void setCouId(int couId) {
		this.couId = couId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}
	public void setCouName(String couName) {
		this.couName = couName;
	}
	public void setCouBrief(String couBrief) {
		this.couBrief = couBrief;
	}
	public void setCouAnno(String couAnno) {
		this.couAnno = couAnno;
	}
	public void setCouReleTime(Timestamp couReleTime) {
		this.couReleTime = couReleTime;
	}
	public void setCouGrade(String couGrade) {
		this.couGrade = couGrade;
	}
	public void setCouPricUrl(String couPricUrl) {
		this.couPricUrl = couPricUrl;
	}
	public void setCouStudyNum(int couStudyNum) {
		this.couStudyNum = couStudyNum;
	}
	public void setCouRelease(boolean couRelease) {
		this.couRelease = couRelease;
	}
	public void setCouUpdated(boolean couUpdated) {
		this.couUpdated = couUpdated;
	}
	
	public void setRecomment(int recomment) {
		this.recomment = recomment;
	}
	
	public int getCouId() {
		return couId;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public int getLabelId() {
		return labelId;
	}
	public String getCouName() {
		return couName;
	}
	public String getCouBrief() {
		if (couBrief == null)
			return "''";
		return couBrief;
	}
	public String getCouAnno() {
		if (couAnno == null)
			return "";
		return couAnno;
	}
	public Timestamp getCouReleTime() {
		return couReleTime;
	}
	public String getCouGrade() {
		return couGrade;
	}
	public String getCouPricUrl() {
		if (couPricUrl == null)
			return "";
		return couPricUrl;
	}
	public String getCouAllUrl() {
		if(couPricUrl==null||"".equals(couPricUrl)) {
			return "/wikestudy/dist/images/lesson/default.jpg";
		}else  {
			return "/wikestudy/dist/images/lesson/"+couPricUrl;
		}
	}
	
	public int getCouStudyNum() {
		return couStudyNum;
	}
	public boolean isCouRelease() {
		return couRelease;
	}
	public boolean isCouUpdated() {
		return couUpdated;
	}
	
	public String getAllUrl () {
		return new StringBuffer(rootUrl+couPricUrl).toString();
	}
	public String getGrade(){
		String grade = "";
		grade = couGrade == null ? "" : ("1".equals(couGrade) ? "初一" : 
			("2".equals(couGrade) ? "初二" : ("3".equals(couGrade)? "初三" : ("4".equals(couGrade)? "公选课" : ""))));
		return grade;
	}
	public boolean isRecomment() {
		return recomment > 0 ? true : false;
	}
	

	


	
	
}
