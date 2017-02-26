package com.wikestudy.model.pojo;

public class TopicView {
	private Topic topic;
	private Teacher teacher;
	private Student student;
	private PageElem<CommentView> comments;
	private Label label;
	private CouSection section;
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setComments(PageElem<CommentView> comments) {
		this.comments = comments;
	}
	public void setLabel(Label label) {
		this.label = label;
	}
	public void setSection(CouSection section) {
		this.section = section;
	}
	public Topic getTopic() {
		return topic;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public Student getStudent() {
		return student;
	}
	public PageElem<CommentView> comment_gets() {
		return comments;
	}
	public Label getLabel() {
		return label;
	}
	public CouSection getSection() {
		return section;
	}
	
	
	
	
	
	
}
