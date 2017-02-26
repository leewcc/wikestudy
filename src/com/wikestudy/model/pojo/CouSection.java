package com.wikestudy.model.pojo;

import com.sun.media.jfxmedia.Media;

// 课程节数据表
public class CouSection {
	private int secId;                    // 主键
	private int chaId;                   // 课程章Id
	private String secNumber;        //   课时序号
	private String secVideoUrl;  //  视频URL
	private String secName;       //   课时名字
	private int chaNumber ;//
	private static final String rootUrl="/wikestudy/dist/videos/lesson/";
	public void setSecId(int secId) {
		this.secId = secId;
	}
	public void setChaId(int chaId) {
		this.chaId = chaId;
	}
	public void setSecNumber(String secNumber) {
		this.secNumber = secNumber;
	}
	public void setSecVideoUrl(String secVideoUrl) {
		this.secVideoUrl = secVideoUrl;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public void setChaNumber(int chaNumber) {
		this.chaNumber = chaNumber;
	}
	
	public int getChaNumber() {
		return chaNumber;
	}
	public int getSecId() {
		return secId;
	}
	public int getChaId() {
		return chaId;
	}
	public String getSecNumber() {
		return secNumber;
	}
	public String getSecVideoUrl() {
		if (secVideoUrl == null) 
			return "";
		return secVideoUrl;
	}
	public String getSecName() {
		return secName;
	}
	public CouSection(int secId, int chaId, String secNumber,
			String secVideoUrl, String secName) {
		super();
		this.secId = secId;
		this.chaId = chaId;
		this.secNumber = secNumber;
		this.secVideoUrl = secVideoUrl;
		this.secName = secName;
	}
	public CouSection() {
		super();
	}
	
	/**
	 * 返回全路径
	 * @return
	 */
	public String getMediaUrl() {
		return secVideoUrl;
	}
	
	
}
