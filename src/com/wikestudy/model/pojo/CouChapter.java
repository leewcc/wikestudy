package com.wikestudy.model.pojo;

// 课程章数据表
public class CouChapter {
	private int chaId;              // 主键
	private int couId;              // 课程表Id
	private int chaNumber;   // 章节序号  第一章...
	private String chaName;  // 课程章名
	private String chaIntro;   //  课程介绍
	public void setChaId(int chaId) {
		this.chaId = chaId;
	}
	public void setCouId(int couId) {
		this.couId = couId;
	}
	public void setChaNumber(int chaNumber) {
		this.chaNumber = chaNumber;
	}
	public void setChaName(String chaName) {
		this.chaName = chaName;
	}
	public void setChaIntro(String chaIntro) {
		this.chaIntro = chaIntro;
	}
	public int getChaId() {
		return chaId;
	}
	public int getCouId() {
		return couId;
	}
	public int getChaNumber() {
		return chaNumber;
	}
	public String getChaName() {
		return chaName;
	}
	public String getChaIntro() {
		return chaIntro;
	}
	public CouChapter(int chaId, int couId, int chaNumber, String chaName,
			String chaIntro) {
		super();
		this.chaId = chaId;
		this.couId = couId;
		this.chaNumber = chaNumber;
		this.chaName = chaName;
		this.chaIntro = chaIntro;
	}
	public CouChapter() {
		super();
	}
	

	
	
	
}
