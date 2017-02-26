package com.wikestudy.model.pojo;

import java.sql.Timestamp;

// 笔记展示视图
public class NoteView {
	private int NDId;                    // 主键
	private String NDContent;     // 内容
	private Timestamp  NDReleTime;   // 发布时间
	private String secName;         // 课程节名字
	private String secNumber;    //   课程节序号
	public void setNDId(int nDId) {
		NDId = nDId;
	}
	public void setNDContent(String nDContent) {
		NDContent = nDContent;
	}
	public void setNDReleTime(Timestamp nDReleTime) {
		NDReleTime = nDReleTime;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public void setSecNumber(String secNumber) {
		this.secNumber = secNumber;
	}
	public int getNDId() {
		return NDId;
	}
	public String getNDContent() {
		return NDContent;
	}
	public Timestamp getNDReleTime() {
		return NDReleTime;
	}
	public String getSecName() {
		return secName;
	}
	public String getSecNumber() {
		return secNumber;
	}
	
	
	


	

	
	
}
