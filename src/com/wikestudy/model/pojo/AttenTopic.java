package com.wikestudy.model.pojo;

import java.sql.Timestamp;

public class AttenTopic {
	private int id;
	private String title;
	private Timestamp sendtime;
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSendtime(Timestamp time) {
		this.sendtime = time;
	}
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public Timestamp getTime() {
		return sendtime;
	}
	
}
