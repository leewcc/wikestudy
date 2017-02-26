package com.wikestudy.model.pojo;

import java.sql.Date;
import java.util.List;

public class StarView {
	private Date date;
	private List<Student> stars;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Student> getStars() {
		return stars;
	}
	public void setStars(List<Student> stars) {
		this.stars = stars;
	}
	
	
}
