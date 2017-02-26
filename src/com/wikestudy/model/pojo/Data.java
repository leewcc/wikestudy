package com.wikestudy.model.pojo;

public class Data {
	
	private int dataId;						//资料id
	private int dataCourseId;			//课程绑定id
	private int dataBinding;			//资料绑定id
	private boolean dataMark;		//0-资源章节  1-课时资源
	private String dataName;			//资料名		
	private long dataSize;				//资料大小
	private String dataRoot;			//资料路径
	private String dataDes;				//资料描述
	private int dataDownload;		//下载量
	
	public void setDataId(int dataId) {
		this.dataId = dataId;
	}
	public void setDataCourseId(int dataCourseId) {  
		this.dataCourseId = dataCourseId;
	}
	public void setDataBinding(int dataBinding) {
		this.dataBinding = dataBinding;
	}
	public void setDataMark(boolean dataMark) {
		this.dataMark = dataMark;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public void setDataSize(long dataSize) {
		this.dataSize = dataSize;
	}
	public void setDataRoot(String dataRoot) {
		this.dataRoot = dataRoot;
	}
	public void setDataDes(String dataDes) {
		this.dataDes = dataDes;
	}
	public void setDataDownload(int dataDownLoad) {
		this.dataDownload = dataDownLoad;
	}
	public int getDataId() {
		return dataId;
	}
	public int getDataCourseId() {
		return dataCourseId;
	}
	public int getDataBinding() {
		return dataBinding;
	}
	public boolean isDataMark() {
		return dataMark;
	}
	public String getDataName() {
		return dataName;
	}
	public long getDataSize() {
		return dataSize;
	}
	public String getDataRoot() {
		return dataRoot;
	}
	public String getDataDes() {
		return dataDes;
	}
	public int getDataDownload() {
		return dataDownload;
	}
	public String getSuffix(){
		return dataRoot.substring(dataRoot.lastIndexOf("."));
	}
	
	
	
}
