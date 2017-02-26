package com.wikestudy.model.dao.impl;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.wikestudy.model.dao.AnswerDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Answer;

public class AnswerDaoImpl implements AnswerDao{
	private Connection conn=null;
	
	public AnswerDaoImpl(Connection conn) {
		this.conn=conn;
	}

	@Override
	public boolean addAnswer(List<Answer> answer) throws Exception {
		
		
		String sql= "insert into t_answer(question_id,answer,student_id) values(?,?,?)";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		Iterator<Answer> it= answer.iterator();
		
		Answer a=null;
		
		while(it.hasNext()) {
			a=it.next();
			pstmt.setInt(1,a.getQuestionId());
			pstmt.setString(2,a.getAnswer());
			pstmt.setInt(3, a.getStudentId());
			pstmt.addBatch();
		}
		return new GenneralDbconn<Answer>().batch(pstmt).length>0;
	}

	private String installId(int count) throws Exception{		
		StringBuilder sb = new StringBuilder("(");
		
		for(int i = 0; i < count; i++) {
			
			sb.append("?, ");
			
		}
		
		return sb.substring(0,sb.lastIndexOf(",")) + ")";
		
	}
	@Override
	public int delAnswer(List<Integer> answerid) throws Exception {
		if(answerid.size()<=0) return 0;
		String sql="delete from t_answer where ans_id in "+installId (answerid.size());
		PreparedStatement pstmt =conn.prepareStatement(sql);
		Iterator<Integer> it=answerid.iterator();
		for(int i=1;it.hasNext();i++) {
			pstmt.setInt(i,it.next());
		}
		return new GenneralDbconn<Answer>().update(pstmt);
	}



	@Override
	public int updateAnswer(List<Answer> answer) throws Exception {
		if(answer==null) return 0;
		String sql="update t_answer set question_id=? ,answer=?,student_id=? where ans_id=?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		for(Answer a:answer) {
			pstmt.setInt(1,a.getQuestionId());
			pstmt.setString(2,a.getAnswer());
			pstmt.setInt(3,a.getStudentId());
			pstmt.setInt(4,a.getAnsId());
			pstmt.addBatch();
		}
		return pstmt.executeBatch()[0];
	}

	@Override
	public Answer queryAnswer(int studentid, int questionid) throws Exception {
		String sql="select * from t_answer where student_id=? and question_id=?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, studentid);
		pstmt.setInt(2, questionid);
		List<Answer> list=new GenneralDbconn<Answer>().query(Answer.class,pstmt);
		if(list.size()<=0) return null;
		return list.get(0);
		
	}

	@Override
	public int delAnsByCouId(int couId) {
		
		String sql = "DELETE FROM t_answer "
				+ " USING  t_answer,  t_cou_chapter, t_cou_section, t_question "
				+ " WHERE t_cou_chapter.cou_id = ? AND t_cou_chapter.cha_id = t_cou_section.cha_id "
				+ " AND (   (t_question.cha_sec_type = 0 AND t_question.cha_sec_id = t_cou_chapter.cha_id)  "
				+ " OR  (t_question.cha_sec_type = 1 AND t_question.cha_sec_id = t_cou_section.sec_id) ) "
				+ " AND  t_question.que_id = t_answer.question_id";
		

		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
}
