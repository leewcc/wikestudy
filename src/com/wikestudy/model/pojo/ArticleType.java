package com.wikestudy.model.pojo;

public class ArticleType { 
	
	private int aTypeId;					//文章类型id
	private String aTypeName;		//类型名
	private String aTypeDes;			//类型描述
	
	public void setATypeId(int aTypeId) {
		this.aTypeId = aTypeId;
	}
	public void setATypeName(String aTypeName) {
		this.aTypeName = aTypeName;
	}
	public void setATypeDes(String aTypeDes) {
		this.aTypeDes = aTypeDes;
	}
	public int getATypeId() {
		return aTypeId;
	}
	public String getATypeName() {
		return aTypeName;
	}
	public String getATypeDes() {
		return aTypeDes;
	}
	
	

}
