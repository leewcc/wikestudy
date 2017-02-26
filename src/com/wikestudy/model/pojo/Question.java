package com.wikestudy.model.pojo;

public class Question {
	public int queId;//问题id
	public int queNum;//问题序号
	public String queType;//问题类型
	public String queContent;//问题内容
	public String queOption;//问题选项
	public String queAnswer;//问题答案
	public String stuAnswer;//学生的回答
	public String queExplain;//问题解释
	public int queCorrectNum;//问题正确人数
	public int quePersonNum;//问题回答人数
	public int makerId;//问题制定者
	public String chaSecType;//问题归属类型
	public int chaSecId;//问题归属id
	public void setQueId(int queId) {
		this.queId = queId;
	}
	public void setQueNum(int queNum) {
		this.queNum = queNum;
	}
	public void setQueType(String queType) {
		this.queType = queType;
	}
	public void setQueContent(String queContent) {
		this.queContent = queContent;
	}
	public void setQueOption(String queOption) {
		this.queOption = queOption;
	}
	public void setQueAnswer(String queAnswer) {
		this.queAnswer = queAnswer;
	}
	public void setQueExplain(String queExplain) {
		this.queExplain = queExplain;
	}
	public void setQueCorrectNum(int queCorrectNum) {
		this.queCorrectNum = queCorrectNum;
	}
	public void setQuePersonNum(int quePersonNum) {
		this.quePersonNum = quePersonNum;
	}
			
	public void setMakerId(int makerId) {
		this.makerId = makerId;
	}
	public void setChaSecType(String chaSecType) {
		this.chaSecType = chaSecType;
	}
	public void setChaSecId(int chaSecId) {
		this.chaSecId = chaSecId;
	}
	public int getQueId() {
		return queId;
	}
	public int getQueNum() {
		return queNum;
	}
	public String getQueType() {
		return queType;
	}
	public String getQueContent() {
		return queContent;
	}
	public String getQueOption() {
		return queOption;
	}
	public String getQueAnswer() {
		return queAnswer;
	}
	public String getQueExplain() {
		return queExplain;
	}
	public int getQueCorrectNum() {
		return queCorrectNum;
	}
	public int getQuePersonNum() {
		return quePersonNum;
	}
	public int getMakerId() {
		return makerId;
	}
	public String getChaSecType() {
		return chaSecType;
	}
	public int getChaSecId() {
		return chaSecId;
	}
	public String getStuAnswer() {
		return stuAnswer;
	}
	public void setStuAnswer(String stuAnswer) {
		this.stuAnswer = stuAnswer;
	}
	
	
	
}
