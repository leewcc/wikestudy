package com.wikestudy.service.publicpart;

import java.sql.Connection;

import com.wikestudy.model.dao.ColTopicDao;
import com.wikestudy.model.dao.impl.ColTopicDaoImpl;
import com.wikestudy.model.pojo.ColTopic;
import com.wikestudy.model.pojo.PageElem;

public class ColTopicService {
	private Connection conn;
	private ColTopicDao ctd;
	private final int shownum = 10;
	
	public ColTopicService(Connection conn){
		this.conn = conn;
		ctd = new ColTopicDaoImpl(this.conn);
	}

	public int SetCol(ColTopic col) throws Exception{
		ColTopic c = ctd.queryByUser(col);
		if(c == null)
			return insert(col);
		else
			return delete(c.getColId());			
	}
	
	private int insert(ColTopic col)throws Exception{
		return ctd.addColTopic(col);
	}
	
	public int delete(int colId) throws Exception{
		return ctd.deleteById(colId);
	}
	
	public PageElem<ColTopic>queryMyCols(int cp, int userId, boolean userType) throws Exception{
		PageElem<ColTopic> pageElem = new PageElem<ColTopic>();
		pageElem.setCurrentPage(cp);
		pageElem.setPageShow(shownum);
		return ctd.queryByUserId(userId, userType, pageElem);
	}
	
	public boolean hasAtten(ColTopic col) throws Exception{
		ColTopic c = ctd.queryByUser(col);
		if(c != null)
			return true;
		else
			return false;
	}
}
