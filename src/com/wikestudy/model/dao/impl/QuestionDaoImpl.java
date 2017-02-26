package com.wikestudy.model.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wikestudy.model.dao.QuestionDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Question;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.servlet.student.getNotes;

public class QuestionDaoImpl implements QuestionDao {

	private Connection conn;
	PreparedStatement pstmt = null;
	private GenneralDbconn<Question> dbconn = null;
	
	
	public QuestionDaoImpl(Connection conn) {
		this.conn = conn;
		dbconn = new GenneralDbconn<>();
	}

	private String installId(int count) throws Exception {
		StringBuilder sb = new StringBuilder("(");

		for (int i = 0; i < count; i++) {

			sb.append("?, ");

		}

		return sb.substring(0, sb.lastIndexOf(",")) + ")";

	}

	@Override
	public int[] addQuestion(List<Question> questions) throws Exception {
		String sql = "insert into t_question(que_num,que_type,que_content,que_option,que_answer,que_explain,"
				+ "que_correct_num,que_person_num,cha_sec_id,cha_sec_type,maker_id)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		Iterator it = questions.iterator();
		Question q = null;
		while (it.hasNext()) {
			q = (Question) it.next();
			pstmt.setInt(1, q.getQueNum());
			pstmt.setString(2, q.getQueType());
			pstmt.setString(3, q.getQueContent());
			pstmt.setString(4, q.getQueOption());
			pstmt.setString(5, q.getQueAnswer());
			pstmt.setString(6, q.getQueExplain());
			pstmt.setInt(7, q.getQueCorrectNum());
			pstmt.setInt(8, q.getQuePersonNum());
			pstmt.setInt(9, q.getChaSecId());
			pstmt.setString(10, q.getChaSecType());
			pstmt.setInt(11, q.getMakerId());
			pstmt.addBatch();
		}
		return new GenneralDbconn<Question>().batch(pstmt);
	}

	@Override
	public int delQuestion(List<Integer> queids) throws Exception {
		if (queids.size() <= 0)
			return 0;
		String sql = "delete from t_question where que_id in "
				+ installId(queids.size());
		PreparedStatement pstmt = conn.prepareStatement(sql);

		Iterator<Integer> it = queids.iterator();

		for (int i = 1; it.hasNext(); i++)
			pstmt.setInt(i, it.next());

		return new GenneralDbconn<Question>().update(pstmt);
	}

	@Override
	public List<Question> queryQuestion(String chasectype,int chasecid)
			throws Exception {
		String sql="select *  from t_question where cha_sec_type =? and cha_sec_id=?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, chasectype);
		pstmt.setInt(2, chasecid);
		List<Question> questions=new GenneralDbconn().query(Question.class, pstmt);
		return questions;		
		
	}

	@Override
	public int [] updateQuestion(List<Question> question) throws Exception {
		String sql="update t_question set que_num=?,que_type=?, que_content=? ,que_option=? ,"
				+ "que_answer=?, que_explain=?,"
				+ "que_correct_num=? ,que_person_num=? ,cha_sec_id=?, cha_sec_type=? ,maker_id=? where que_id=?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		for(Question q:question) {
			pstmt.setInt(1, q.getQueNum());
			pstmt.setString(2, q.getQueType());
			pstmt.setString(3, q.getQueContent());
			pstmt.setString(4, q.getQueOption());
			pstmt.setString(5, q.getQueAnswer());
			pstmt.setString(6, q.getQueExplain());
			pstmt.setInt(7, q.getQueCorrectNum());
			pstmt.setInt(8, q.getQuePersonNum());
			pstmt.setInt(9, q.getChaSecId());
			pstmt.setString(10, q.getChaSecType());
			pstmt.setInt(11,q.getMakerId());
			pstmt.setInt(12,q.getQueId());
			pstmt.addBatch();
		}
		return new GenneralDbconn<Question>().batch(pstmt);
	}

	@Override
	public int delQuesByCouId(int couId) {
		String sql = "DELETE FROM t_question "
				+ " USING t_question,  t_cou_chapter, t_cou_section "
				+ " WHERE t_cou_chapter.cou_id = ? AND t_cou_chapter.cha_id = t_cou_section.cha_id "
				+ " AND (   (t_question.cha_sec_type = '0' AND t_question.cha_sec_id = t_cou_chapter.cha_id)  "
				+ " OR  (t_question.cha_sec_type = '1' AND t_question.cha_sec_id = t_cou_section.sec_id) ) ";
		
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

	@Override
	public int checkAnswerStatus(int stuId, int secId) throws Exception{
		String sql="select count(*) as rows from t_answer where cha_sec_id=? and student_id=?" ;
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, secId);
			pstmt.setInt(2, stuId);
			return dbconn.getRows(pstmt);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean searchSecId(int secId) throws Exception {
		String sql="select * from t_question where cha_sec_id = ?";
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, secId);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int deleteBySec(int sec) throws Exception {
		String sql = "delete from t_question where cha_sec_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, sec);
		
		return dbconn.update(pstmt);
	}

	@Override
	public int updateQuesSitu(int id, String answer) throws Exception {
		String sql = "UPDATE t_question SET que_person_num = que_person_num + 1, "
				+ "que_correct_num = que_correct_num + (que_answer = ?) WHERE que_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, answer);
		
		pstmt.setInt(2, id);
		
		return dbconn.update(pstmt);
	}

	
	
	
	
}

