package com.wikestudy.model.pojo;

import java.util.List;

public class ChapSec {
	private CouChapter couChapter;
	private List<CouSection> couSection;
	
	
	public CouChapter getCouChapter() {
		return couChapter;
	}
	public void setCouChapter(CouChapter couChapter) {
		this.couChapter = couChapter;
	}
	public List<CouSection> getCouSection() {
		return couSection;
	}
	public void setCouSection(List<CouSection> couSection) {
		this.couSection = couSection;
	}
	
	
}	
