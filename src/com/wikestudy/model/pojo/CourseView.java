package com.wikestudy.model.pojo;

import java.util.List;

public class CourseView {
	private Course course;
	private Teacher tea;
	private List<ChapterView> chapters;
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public List<ChapterView> getChapters() {
		return chapters;
	}
	public void setChapters(List<ChapterView> chapters) {
		this.chapters = chapters;
	}
	public Teacher getTea() {
		return tea;
	}
	public void setTea(Teacher tea) {
		this.tea = tea;
	}
}
