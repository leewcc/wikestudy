package com.wikestudy.service.manager;

import java.awt.print.Pageable;
import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.RecordDao;
import com.wikestudy.model.dao.impl.RecordDaoImpl;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Record;

public class RecordService {
	private Connection conn;
	private RecordDao rd;
	
	public RecordService(Connection conn) {
		this.conn = conn;
		rd = new RecordDaoImpl(conn);
	}
	
	
	
	public PageElem<Record> selectRecord(int page, List<Integer> esists) throws Exception{
		PageElem<Record> pe = new PageElem<>();
		pe.setCurrentPage(page);
		pe.setRows(rd.selectRecordCount(esists));
		pe.setPageShow(10);
		pe.setPageElem(rd.selectRecord(esists, pe.getStartSearch(), pe.getPageShow()));
		return pe;
	}
	
	public int confirm() throws Exception {
		return rd.confirm();
	}
	
	public int updateStudy(int id, long time) throws Exception {
		return rd.updateStudyTime(id, time);
	}
	
	public int updateDis(int id, int time) throws Exception {
		return rd.updateDidNum(id, time);
	}
}
