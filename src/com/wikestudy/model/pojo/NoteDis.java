package com.wikestudy.model.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.wikestudy.model.util.InputUtil;
import com.wikestudy.model.util.TimeToot;

// 课程笔记/ 评论
public class NoteDis {
	private int NDId;                    // 主键
	private int stuId;                    // 学生Id
	private String stuName;  			//学生姓名
	private String stuPhoto;			//学生头像
	private int secId;                    // 课时Id
	private String NDContent;     // 内容
	private Timestamp   NDReleTime;   // 发布时间
	private String TimestampString;//发布时间的字符串
	private boolean   NDMark;         //  0-笔记; 1-评论
	private String time;

	public void setNDId(int nDId) {
		NDId = nDId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public String getStuPhoto() {
		return stuPhoto;
	}
	public void setStuPhoto(String stuPhoto) {
		this.stuPhoto = stuPhoto;
	}
	public void setSecId(int secId) {
		this.secId = secId;
	}
	public void setNDContent(String nDContent) {
		NDContent = nDContent;
	}
	public void setNDReleTime(Timestamp nDReleTime) {
		NDReleTime = nDReleTime;
	}
	public void setNDMark(boolean nDMark) {
		NDMark = nDMark;
	}

	public int getNDId() {
		return NDId;
	}
	public int getStuId() {
		return stuId;
	}
	public int getSecId() {
		return secId;
	}
	public String getNDContent() {
		return NDContent;
	}
	public String  getNDReleTime() {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return sd.format(NDReleTime);
	}
	public boolean isNDMark() {
		return NDMark;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	public String getTime(){
		return TimeToot.format(NDReleTime.getTime());
	}
}
