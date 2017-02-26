package com.wikestudy.service.manager;

import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.QuestionDao;
import com.wikestudy.model.dao.impl.QuestionDaoImpl;
import com.wikestudy.model.pojo.Question;

public class QuestionService {
	
	private Connection conn;
	private QuestionDao qd;
	
	public QuestionService (Connection conn) {
		this.conn=conn;
		qd=new QuestionDaoImpl(conn);
	}
	
	/**
	 * 批量增加问题
	 * @param questions
	 */
	public int[] addQuestion(List<Question> questions)throws Exception {
		return qd.addQuestion(questions);
	}
	
	public int deleteBySec(int secId)throws Exception{
		return qd.deleteBySec(secId);
	}
	
	/**
	 * 批量删除问题
	 * @param queids
	 */
	public int delQuestion(List<Integer> queids)throws Exception {
		return qd.delQuestion(queids);
	}
	/**
	 * 批量查询问题
	 * @param queid
	 */
	List<Question> queryQuestion(String chasectype, int chasecid)throws Exception {
		return qd.queryQuestion(chasectype, chasecid);
	}
	/**
	 * 批量更新问题
	 * @param question
	 */
	public int[] updateQuestion(List<Question> question)throws Exception {
		return qd.updateQuestion(question);
	}

	public boolean searchSecId(int secId) throws Exception {

		return qd.searchSecId(secId);
	}
}
