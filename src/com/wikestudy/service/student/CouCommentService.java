package com.wikestudy.service.student;

import java.sql.Connection;

import com.wikestudy.model.dao.NoteDisDao;
import com.wikestudy.model.dao.impl.NoteDisDaoImpl;
import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.PageElem;

public class CouCommentService {
	private Connection conn=null;
	private NoteDisDao ndDao=null;

	public CouCommentService(Connection conn) {
		this.conn = conn;
		ndDao=new NoteDisDaoImpl(this.conn);
	}
	/**
	 * 存评论或者笔记
	 */
	public int saveCouComment (NoteDis noteDis) throws Exception {
		return ndDao.insertNoteDis(noteDis);
	}
	
	public PageElem<NoteDis> queryCouComment(int secId,int cp) throws Exception {
		PageElem<NoteDis> all=new PageElem<NoteDis>();
		int showNum=10;//每一页展示的条数
		all.setCurrentPage(cp);
		all.setPageShow(10);
		all=ndDao.querySecComment(secId, all);
		
		
		return all;
	}
	
}
