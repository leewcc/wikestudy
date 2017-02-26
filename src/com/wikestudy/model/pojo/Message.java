package com.wikestudy.model.pojo;

import java.sql.Timestamp;

public class Message {
	private int messId;// 主键
	private int messMasterId;// 留言板主人id
	private boolean messMasterMark;// 主人的类型（0-学生 1-老师）
	private int messBinding;// 0-留言 其他-留言的回复//存的是留言的主键
	private String messContent;// 留言或评论的内容
	private int messSenderId;// 留言者的id
	private Timestamp messTime;// 发布时间
	private boolean messSenderMark;// 0-学生 1-老师
	private boolean messHasRead;// 0-未读 1-已读
	private String photo;
	private String sender;
	public void setMessId(int messId) {
		this.messId = messId;
	}
	public void setMessMasterId(int messMasterId) {
		this.messMasterId = messMasterId;
	}
	public void setMessMasterMark(boolean messMasterMark) {
		this.messMasterMark = messMasterMark;
	}
	public void setMessBinding(int messBinding) {
		this.messBinding = messBinding;
	}
	public void setMessContent(String messContent) {
		this.messContent = messContent;
	}
	public void setMessSenderId(int messSenderId) {
		this.messSenderId = messSenderId;
	}
	public void setMessTime(Timestamp messTime) {
		this.messTime = messTime;
	}
	public void setMessSenderMark(boolean messSenderMark) {
		this.messSenderMark = messSenderMark;
	}
	public void setMessHasRead(boolean messHasRead) {
		this.messHasRead = messHasRead;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public int getMessId() {
		return messId;
	}
	public int getMessMasterId() {
		return messMasterId;
	}
	public boolean isMessMasterMark() {
		return messMasterMark;
	}
	public int getMessBinding() {
		return messBinding;
	}
	public String getMessContent() {
		return messContent;
	}
	public int getMessSenderId() {
		return messSenderId;
	}
	public Timestamp getMessTime() {
		return messTime;
	}
	public boolean isMessSenderMark() {
		return messSenderMark;
	}
	public boolean isMessHasRead() {
		return messHasRead;
	}
	public String getPhoto() {
		return photo;
	}
	public String getSender() {
		return sender;
	}

}
