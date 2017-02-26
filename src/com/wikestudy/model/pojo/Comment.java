package com.wikestudy.model.pojo;

import java.sql.Timestamp;

public class Comment {
	private int comId;//主键
	private int comTopicId;//路基话题主键
	private int comBinding;//话题回复
	private String comCon;
	private int comUserId;//评论人id
	private boolean comUserEnum;//评论人类型
	private int comReceiverId;//被回复人id
	private boolean comReceiverType; //被回复人类型
	private Timestamp comTime;//评论时间
	private boolean comBest;
	private boolean comHasRead;
	private String sender;
	private String photo;
	private String topTit;
	public void setComId(int comId) {
		this.comId = comId;
	}
	public void setComTopicId(int comTopicId) {
		this.comTopicId = comTopicId;
	}
	public void setComBinding(int comBinding) {
		this.comBinding = comBinding;
	}
	public void setComCon(String comCon) {
		this.comCon = comCon;
	}
	public void setComUserId(int comUserId) {
		this.comUserId = comUserId;
	}
	public void setComUserEnum(boolean comUserEnum) {
		this.comUserEnum = comUserEnum;
	}
	public void setComReceiverId(int comReceiverId) {
		this.comReceiverId = comReceiverId;
	}
	public void setComReceiverType(boolean comReceiverType) {
		this.comReceiverType = comReceiverType;
	}
	public void setComTime(Timestamp comTime) {
		this.comTime = comTime;
	}
	public void setComBest(boolean comBest) {
		this.comBest = comBest;
	}
	public void setComHasRead(boolean comHasRead) {
		this.comHasRead = comHasRead;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setTopTit(String topTit) {
		this.topTit = topTit;
	}
	public int getComId() {
		return comId;
	}
	public int getComTopicId() {
		return comTopicId;
	}
	public int getComBinding() {
		return comBinding;
	}
	public String getComCon() {
		return comCon;
	}
	public int getComUserId() {
		return comUserId;
	}
	public boolean isComUserEnum() {
		return comUserEnum;
	}
	public int getComReceiverId() {
		return comReceiverId;
	}
	public boolean isComReceiverType() {
		return comReceiverType;
	}
	public Timestamp getComTime() {
		return comTime;
	}
	public boolean isComBest() {
		return comBest;
	}
	public boolean isComHasRead() {
		return comHasRead;
	}
	public String getSender() {
		return sender;
	}
	public String getPhoto() {
		return photo;
	}
	public String getTopTit() {
		return topTit;
	}
	
	
	
}
