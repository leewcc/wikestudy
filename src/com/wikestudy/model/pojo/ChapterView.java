package com.wikestudy.model.pojo;

import java.util.List;

public class ChapterView {
	private CouChapter chapter;
	private List<CouSection> sections;
	private List<Data> datas;
	public CouChapter getChapter() {
		return chapter;
	}
	public void setChapter(CouChapter chapter) {
		this.chapter = chapter;
	}
	public List<CouSection> getSections() {
		return sections;
	}
	public void setSections(List<CouSection> sections) {
		this.sections = sections;
	}
	public List<Data> getDatas() {
		return datas;
	}
	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}
	
}
