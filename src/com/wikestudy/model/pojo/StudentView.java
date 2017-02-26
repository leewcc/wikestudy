package com.wikestudy.model.pojo;

import java.util.List;

public class StudentView {
	private Student student;
	private List<Course> myCourses;
	private List<Topic> myTopics;
	
	public Student getStudent() {
		return student;
	}
	public List<Course> getMyCourses() {
		return myCourses;
	}
	public List<Topic> getMyTopics() {
		return myTopics;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setMyCourses(List<Course> myCourses) {
		this.myCourses = myCourses;
	}
	public void setMyTopics(List<Topic> myTopics) {
		this.myTopics = myTopics;
	}
}
