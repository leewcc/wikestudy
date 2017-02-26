package com.wikestudy.service.publicpart;

import java.sql.Connection;

import com.wikestudy.model.dao.CouChapterDao;
import com.wikestudy.model.dao.impl.CouChapterDaoImpl;
import com.wikestudy.model.pojo.CouChapter;

public class CouCharService {
	private Connection conn;
	private CouChapterDao ccd;
	
	public CouCharService(Connection conn) {
		this.conn = conn;
		ccd = new CouChapterDaoImpl(conn);
	}
	
	public CouChapter getChapter(int chaId) {
		return ccd.queryCouChapterById(chaId);
	}
}
