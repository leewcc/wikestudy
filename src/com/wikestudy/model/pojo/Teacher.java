package com.wikestudy.model.pojo;

import java.io.Serializable;

public class Teacher implements Serializable{

	private static final long serialVersionUID = 1L;
	private int teaId;//主键
	private boolean teaType;//身份类型（0-超级管理员，1-老师）
	private String teaNumber;//工号
	private  String teaName;//姓名
	private String teaPassword;//密码
	private String teaGender;//性别
	private String teaIntroduction;//简介
	private String teaPhotoUrl;//头像路径
	private static final String rootUrl="/wikestudy/dist/images/portrait/";

	private boolean teaDelete;//true-1-删除 false-0-未删除
	public int getTeaId() {
		return teaId;
	}
	public void setTeaId(int teaId) {
		this.teaId = teaId;
	}
	public boolean isTeaType() {
		return teaType;
	}
	public void setTeaType(boolean teaType) {
		this.teaType = teaType;
	}
	public String getTeaNumber() {
		return teaNumber;
	}
	public void setTeaNumber(String teaNumber) {
		this.teaNumber = teaNumber;
	}
	public String getTeaName() {
		return teaName;
	}
	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}
	public String getTeaPassword() {
		return teaPassword;
	}
	public void setTeaPassword(String teaPassword) {
		this.teaPassword = teaPassword;
	}
	public String getTeaGender() {
		return teaGender;
	}
	public void setTeaGender(String teaGender) {
		this.teaGender = teaGender;
	}
	public String getTeaIntroduction() {
		return teaIntroduction;
	}
	public void setTeaIntroduction(String teaIntroduction) {
		this.teaIntroduction = teaIntroduction;
	}

	public boolean isTeaDelete() {
		return teaDelete;
	}
	public void setTeaDelete(boolean teaDelete) {
		this.teaDelete = teaDelete;
	}
	

	
	public String getTeaPhotoUrl() {
		return teaPhotoUrl;
	}
	public void setTeaPhotoUrl(String teaPhotoUrl) {
		this.teaPhotoUrl = teaPhotoUrl;
	}
	/**
	 * 头像处理返回完整路径
	 */
	public String getTeaPortraitUrl() {
		if("".equals(teaPhotoUrl)||teaPhotoUrl==null) {
			return rootUrl+"default.jpg";
		} else {
			return new  StringBuffer(rootUrl+"t"+teaId+"/"+teaPhotoUrl).toString();
		}
	}
	
	
	
}
