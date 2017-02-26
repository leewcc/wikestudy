package com.wikestudy.model.pojo;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.logging.impl.Log4JLogger;

public class Photo {

	
	Log4JLogger log = new Log4JLogger("log4j.properties");
	private int x;//x轴的起点
	private int y;//y轴的起点
	private int w;//x轴的宽度
	private int h;//y轴的高度
	
	private int wid;//设置的宽度
	private int hei;//设置的高度
	
	private String oldUrl;//图片路径
	private String saveUrl;//图片保存路径
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getWid() {
		return wid;
	}
	public void setWid(int wid) {
		this.wid = wid;
	}
	public int getHei() {
		return hei;
	}
	public void setHei(int hei) {
		this.hei = hei;
	}
	
	public String getOldUrl() {
		return oldUrl;
	}
	public void setOldUrl(String oldUrl) {
		//在set的地方设置
		if("\\".equals(File.separator)) {
			this.oldUrl = oldUrl.replaceAll("/", "\\\\");
		} else if("/".equals(File.separator)) {//  /的情况
			this.oldUrl = oldUrl.replaceAll("\\\\", "/");
		} else {
			this.oldUrl = oldUrl.replaceAll("\\", File.separator).replaceAll("/", File.separator);
		}
		
	}
	public String getSaveUrl() {
		return saveUrl;
	}
	public void setSaveUrl(String saveUrl) {
		//在set的地方设置
		if("\\".equals(File.separator)) {
			this.saveUrl = saveUrl.replaceAll("/", "\\\\");
		} else if("/".equals(File.separator)) {//  /的情况
			this.saveUrl= saveUrl.replaceAll("\\\\", "/");
			this.saveUrl=this.saveUrl.replaceAll("//", "/");
		} else {
			this.saveUrl=saveUrl.replaceAll("\\\\", File.separator).replaceAll("/", File.separator);
		}
	}
	public Photo(int x, int y, int w, int h, int wid, int hei, String oldUrl,
			String saveUrl) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.wid = wid;
		this.hei = hei;
		this.setSaveUrl(saveUrl);
		this.setOldUrl(oldUrl);

	}
	public Photo() {
		super();
	}

	public static String returnUrl(String url) {
		//在set的地方设置
		if("\\".equals(File.separator)) {
			return url.replaceAll("/", "\\\\");
		} else if("/".equals(File.separator)) {//  /的情况
			url=url.replaceAll("\\\\", "/");
			return url.replaceAll("//", "/");
		} else {
			return url.replaceAll("\\\\", File.separator).replaceAll("/", File.separator);
		}
		
	}
}
