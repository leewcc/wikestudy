package com.wikestudy.service.student;

import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.AnswerDao;
import com.wikestudy.model.dao.impl.AnswerDaoImpl;
import com.wikestudy.model.pojo.Answer;

public class AnswerService {
	private Connection conn;
	private AnswerDao ad;
	public AnswerService(Connection conn) {
		this.conn=conn;
		ad=new AnswerDaoImpl(conn);
	}
	/**
	 * 批量增加测试题回答
	 * @param answer
	 */
	public boolean addAnswer(List<Answer> answer)throws Exception {
		return ad.addAnswer(answer);
	}
	/**
	 * 批量删除测试题回答
	 * @param answerid
	 */
	public int delAnswer(List<Integer> answerid)throws Exception {
		return ad.delAnswer(answerid);
	}
	/**
	 * 批量查询测试题回答
	 * @param answerid
	 */
	public Answer queryAnswer(int studentid,int questionid) throws Exception {
		return ad.queryAnswer(studentid, questionid);
	}
	/**
	 * 批量更新测试题回答
	 * @param answer
	 */
	public int updateAnswer(List<Answer> answer)throws Exception {
		return ad.updateAnswer(answer);
	}	 
	
}
