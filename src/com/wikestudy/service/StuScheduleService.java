package com.wikestudy.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.wikestudy.model.dao.StuScheduleDao;
import com.wikestudy.model.dao.impl.StuScheduleDaoImpl;
import com.wikestudy.service.student.StudentService;

public class StuScheduleService {
	private Connection  conn;
	private StuScheduleDao ssd;
	public StuScheduleService(Connection conn) {
		this.conn=conn;
		this.ssd=new StuScheduleDaoImpl(conn);
	}
	public StuScheduleService() {
		super();
	}
	public boolean updateQuestionStatus(int stuId, int secId) throws SQLException {
		return ssd.updateQuestionStatus(stuId,secId);
	}
	
	/**
	 * 检查考试情况
	 * @param stuId
	 * @param secId
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkAnswerStatus(int stuId, int secId) throws SQLException {
		
		return ssd.checkAnswerStatus(stuId, secId);
	}
	
}
