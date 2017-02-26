package com.wikestudy.model.pojo;

import java.util.List;

public class MessageView {
	private Message message;
	private List<Message> messReply;
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public List<Message> getMessReply() {
		return messReply;
	}
	public void setMessReply(List<Message> messReply) {
		this.messReply = messReply;
	}
	
}
