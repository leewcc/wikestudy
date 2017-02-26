package com.wikestudy.model.pojo;

import java.util.List;

public class PageElem<E> {
	private int currentPage;	//当前页数
	private int pageShow;		//当前页展示多少条数据
	private int startSearch;
	private int rows;		        //总记录数
	private int totalPage;		//总页数
	private List<E> pageElem;	//页数对应的实体数据
	public int getCurrentPage() {
		if (currentPage <= 0)
			return 1;
		if (totalPage > 0 && currentPage > totalPage)
			return totalPage;
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageShow() {
		return pageShow;
	}
	public void setPageShow(int pageShow) {
		this.pageShow = pageShow;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getTotalPage() {
		totalPage = rows / pageShow;
		if (rows % pageShow != 0)
			totalPage++;
		
		return totalPage == 0 ? 1 : totalPage;
	}

	public List<E> getPageElem() {
		return pageElem;
	}
	public void setPageElem(List<E> pageElem) {
		this.pageElem = pageElem;
	}
	public int getStartSearch() {
		if (currentPage <= 0)
			currentPage = 1;
		return (currentPage - 1) * pageShow;
	}

	
	
}
