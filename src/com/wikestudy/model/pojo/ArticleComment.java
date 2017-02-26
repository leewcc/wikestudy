package com.wikestudy.model.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;



public class ArticleComment {
	private int aComId;						//评论id
	private int aComArtId;					//文章id
	private int aComBinding;			//评论回复标志 0-评论 其他数字-对应数字评论的回复
	private int aComSenderId;//评论人id
	private String aComSender;//评论人
	private String aComSenderPhoto; //评论人的头像
	private int aComSenderMark;		//评论人标志 0-学生 1-老师 2-游客
	private String aComContent;//评论的内容
	private Timestamp aComTime;  
//	private String time;

	public void setAComId(int aComId) {
		this.aComId = aComId;
	}
	public void setAComArtId(int aComArtId) {
		this.aComArtId = aComArtId;
	}
	public void setAComBinding(int aComBinding) {
		this.aComBinding = aComBinding;
	}
	public void setAComSenderId(int aComSenderId) {
		this.aComSenderId = aComSenderId;
	}
	public void setAComSenderMark(int aComSenderMark) {
		this.aComSenderMark = aComSenderMark;
	}
	public void setAComContent(String aComContent) {
		this.aComContent = aComContent;
	}
	public void setAComTime(Timestamp aComTime) {
		this.aComTime = aComTime;
	}
	public void setAComSender(String aComSender) {
		this.aComSender = aComSender;
	}
	
	public String getAComSenderPhoto() {
		return aComSenderPhoto;
	}
	public void setAComSenderPhoto(String aComSenderPhoto) {
		this.aComSenderPhoto = aComSenderPhoto;
	}
	public int getAComId() {
		return aComId;
	}
	public int getAComArtId() {
		return aComArtId;
	}
	public int getAComBinding() {
		return aComBinding;
	}
	public int getAComSenderId() {
		return aComSenderId;
	}
	public int getAComSenderMark() {
		return aComSenderMark;
	}
	
//	public String getTime() {
//		return time;
//	}
//	public void setTime(Timestamp temp) {
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		this.time=df.format(aComTime);
//	}
	public String getAComContent() {
		return aComContent;
	}
	public String getAComTime() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(aComTime);
//		return aComTime;
	}
	
	public String getAComSender() {
		return aComSender;
	}

	@Override
	public String toString() {
		return "ArticleComment [aComId=" + aComId + ", aComArtId=" + aComArtId
				+ ", aComBinding=" + aComBinding + ", aComSenderId="
				+ aComSenderId + ", aComSenderMark=" + aComSenderMark
				+ ", aComContent=" + aComContent + ", aComTime=" + aComTime
				+ "]";
	}

	
	
	
	
}
