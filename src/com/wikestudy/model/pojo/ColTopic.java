package com.wikestudy.model.pojo;

import java.sql.Timestamp;

public class ColTopic {
	private int colId;
	private int colUserId;
	private boolean colUserEnum;
	private int colTopicId;
	
	//此部分是对应话题发起者的相关内容
	private String senderName;
	private String senderPhoto;
	private int senderId;
	private boolean senderType;
	
	//此部分数据是话题的相关内容
	private String topTit;
	private String topCon;
	private Timestamp topTime;
	private int readNum;
	private int answerNum;
	public void setColId(int colId) {
		this.colId = colId;
	}
	public void setColUserId(int colUserId) {
		this.colUserId = colUserId;
	}
	public void setColUserEnum(boolean colUserEnum) {
		this.colUserEnum = colUserEnum;
	}
	public void setColTopicId(int colTopicId) {
		this.colTopicId = colTopicId;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public void setSenderPhoto(String senderPhoto) {
		this.senderPhoto = senderPhoto;
	}
	public void setTopTit(String topTit) {
		this.topTit = topTit;
	}
	public void setTopCon(String topCon) {
		this.topCon = topCon;
	}
	public void setTopTime(Timestamp topTime) {
		this.topTime = topTime;
	}	
	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}
	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}
	public int getColId() {
		return colId;
	}
	public int getColUserId() {
		return colUserId;
	}
	public boolean isColUserEnum() {
		return colUserEnum;
	}
	public int getColTopicId() {
		return colTopicId;
	}
	public String getSenderName() {
		return senderName;
	}
	public String getSenderPhoto() {
		return senderPhoto;
	}
	public String getTopTit() {
		return topTit;
	}
	public String getTopCon() {
		return topCon;
	}
	public int getReadNum() {
		return readNum;
	}
	public int getAnswerNum() {
		return answerNum;
	}
	public Timestamp getTopTime() {
		return topTime;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public boolean isSenderType() {
		return senderType;
	}
	public void setSenderType(boolean senderType) {
		this.senderType = senderType;
	}
	
	
}
