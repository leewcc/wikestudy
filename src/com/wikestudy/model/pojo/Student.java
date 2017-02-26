package com.wikestudy.model.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Student implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int stuId;								//学生id
	private String stuNumber;			//学号
	private String stuName;				//姓名
	private String stuPassword;			//密码
	private String stuGrade;				//年级	
	private String stuClass;			//班级
	private String stuGender;				//性别
	private String stuSignature;			//个性签名
	private String stuPhotoUrl;			//头像

	private int stuDiscussionNum;	//讨论次数	
	private long stuStudyHour;				//学习时间
	private boolean stuDelete;					//删除标志
	private static final String rootUrl="/wikestudy/dist/images/portrait/";
	
	
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public void setStuNumber(String stuNumber) {
		this.stuNumber = stuNumber;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public void setStuPassword(String stuPassword) {
		this.stuPassword = stuPassword;
	}
	public void setStuGrade(String stuGrade) {
		switch (stuGrade) {
		case "初一":
		case "1":
			this.stuGrade = "1";
			break;
		case "初二":
		case "2":
			this.stuGrade = "2";
			break;
		case " 初三":
		case "3":
			this.stuGrade = "3";
			break;
		default:
			this.stuGrade = "1";
			break;
		}
	}
	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}
	public void setStuGender(String stuGender) {
		this.stuGender = stuGender;
	}
	public void setStuSignature(String stuSignature) {
		this.stuSignature = stuSignature;
	}
	public void setStuPhotoUrl(String stuPhotoUrl) {
		this.stuPhotoUrl = stuPhotoUrl;
	}
	public void setStuDiscussionNum(int stuDiscussionNum) {
		this.stuDiscussionNum = stuDiscussionNum;
	}

	public void setStuStudyHour(long stuStudyHour) {
		this.stuStudyHour = stuStudyHour;
	}
	public void setStuDelete(boolean stuDelete) {
		this.stuDelete = stuDelete;
	}
	public int getStuId() {
		return stuId;
	}
	public String getStuNumber() {
		return stuNumber;
	}
	public String getStuName() {
		return stuName;
	}
	public String getStuPassword() {
		return stuPassword;
	}
	public String getStuGrade() {
		return stuGrade;
	}
	public String getStuClass() {
		return stuClass;
	}

	public String getStuGender() {
		return stuGender;
	}
	public String getStuSignature() {	
		return stuSignature;
	}

	
	public String getStuPhotoUrl() {
		return stuPhotoUrl;
	}
	public int getStuDiscussionNum() {
		return stuDiscussionNum;
	}
	public long getStuStudyHour() {
		return stuStudyHour;
	}
	public boolean isStuDelete() {
		return stuDelete;
	}
	public String getGrade() {
		if(stuGrade == null)
			return "";
		
		switch (stuGrade) {
		case "1":
			return "初一";

		case "2":
			return "初二";
			
		case "3":
			return "初三";
			
		default:
			return "";
		}
	}
	
	
	/**
	 * 头像处理返回完整路径
	 */
	public String getStuPortraitUrl() {
		if(stuPhotoUrl==null ||"".equals(stuPhotoUrl)) {
			return rootUrl+"default.jpg";
		} else {
			return new  StringBuffer(rootUrl+"s"+stuId+"/"+stuPhotoUrl).toString();
		}
	}
	
	public String getSign(){
		if(stuSignature == null || "".equals(stuSignature))
			return "这家伙很懒，什么都没留下";
		
		return stuSignature;
	}
	

	@Override
	public int hashCode() {
		return stuNumber.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Student) {
			Student s = (Student)obj;
			if(stuNumber.equals(s.getStuNumber()))
				return true;
		}
		
		return false;
	}
	 
	
	
	public static String passwordCheck(String password) {
		if(password == null || "".equals(password))
			return "请输入密码";
		else if(password.length() > 15 || password.length() < 6)
			return "密码长度必须为6-15位";
		else
			return "";
	}
	
}
