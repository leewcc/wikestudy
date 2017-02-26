package com.wikestudy.model.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.wikestudy.model.util.TimeToot;


public class Article {
	private int artId;						//文章id
	private int artAuthorId;				//作者id
	private int artTypeId;				//文章类型id
	private String author;				//作者
	private String artType;				//文章类型
	private String artTitle;			//文章标题
	private String artContent;	//文章内容
	private Timestamp artTime;				//发布时间
	private int artClick;					//点击量
	
	public void setArtId(int artId) {
		this.artId = artId;
	}
	public void setArtTypeId(int artTypeId) {
		this.artTypeId = artTypeId;
	}
	public void setArtTitle(String artTitle) {
		this.artTitle = artTitle;
	}
	public void setArtContent(String artContent) {
		this.artContent = artContent;
	}
	public void setArtTime(Timestamp artTime) {
		this.artTime = artTime;
	}
	public void setArtClick(int artClick) {
		this.artClick = artClick;
	}
	public int getArtId() {
		return artId;
	}
	public int getArtTypeId() {
		return artTypeId;
	}
	public String getArtTitle() {
		return artTitle;
	}
	public String getArtContent() {
		return artContent;
	}
	public String getArtTime() {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(artTime);
	}
	public int getArtClick() {
		return artClick;
	}
	public String getArtType() {
		return artType;
	}
	public void setArtType(String artType) {
		this.artType = artType;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getArtAuthorId() {
		return artAuthorId;
	}
	public void setArtAuthorId(int artAuthorId) {
		this.artAuthorId = artAuthorId;
	}
	public String getTime(){
		return TimeToot.format(artTime.getTime());
	}
	
	
}
