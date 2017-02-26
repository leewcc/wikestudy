package com.wikestudy.model.pojo;

import java.util.List;

// 课程的全部信息
// 课程，课程章，课程节
// 只用来存放数据
public class CourseInfor {
	private Course cou;   // 课程信息
	private List<CouChapter> couChapterList;  // 课程章节信息
	private List<CouSection> couSectionList;   // 课程课时信息
	
	public Course getCou() {
		return cou;
	}
	public void setCou(Course cou) {
		this.cou = cou;
	}
	public List<CouChapter> getCouChapterList() {
		return couChapterList;
	}
	public void setCouChapterList(List<CouChapter> couChapterList) {
		this.couChapterList = couChapterList;
	}
	public List<CouSection> getCouSectionList() {
		return couSectionList;
	}
	public void setCouSectionList(List<CouSection> couSectionList) {
		this.couSectionList = couSectionList;
	}
	
	
}
