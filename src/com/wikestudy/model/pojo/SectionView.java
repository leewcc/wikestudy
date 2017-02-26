package com.wikestudy.model.pojo;

import java.util.List;

public class SectionView {
	private CouSection section;
	private List<Data> datas;
	public CouSection getSection() {
		return section;
	}
	public void setSection(CouSection section) {
		this.section = section;
	}
	public List<Data> getDatas() {
		return datas;
	}
	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}
	
}
