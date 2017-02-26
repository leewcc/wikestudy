package com.wikestudy.model.pojo;

import java.util.List;

public class CommentView {
	private Comment comment;
	private List<Comment> comReply;
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public void setComReply(List<Comment> comReply) {
		this.comReply = comReply;
	}
	public Comment comment_get() {
		return comment;
	}
	public List<Comment> getComReply() {
		return comReply;
	}
	
	
}
