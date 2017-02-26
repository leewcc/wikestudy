package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wikestudy.model.dao.StuScheduleDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.StuSchedule;

public class StuScheduleDaoImpl implements StuScheduleDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private GenneralDbconn<StuSchedule> gdb = null;
	
	public StuScheduleDaoImpl(Connection conn) {
		this.conn = conn;
		gdb = new GenneralDbconn<StuSchedule>();
	}

	@Override
	public int insertStuSchedule(StuSchedule stuSchedule) {
		try {
			String sql = "INSERT INTO t_stu_schedule "
					+ " (stu_id, sec_id, sec_condition, sec_time, sec_exam, cha_id) "
					+ " VALUES (?,?,?,?,?,?)" ;
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, stuSchedule.getStuId());
			pstmt.setInt(2, stuSchedule.getSecId());
			pstmt.setString(3, stuSchedule.getSecCondition());
			pstmt.setString(4, stuSchedule.getSecTime());
			pstmt.setBoolean(5, stuSchedule.isSecExam());
			pstmt.setInt(6, stuSchedule.getChaId());
			
			return gdb.update(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override // 根据课时删
	public int[] delStuSchedule(List<Integer> secIdList) throws Exception {
		String sql = "DELETE FROM t_stu_schedule WHERE sec_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		for (Integer secId: secIdList) {
			pstmt.setInt(1, secId);
			
			pstmt.addBatch();
		}
		
		return gdb.batch(pstmt);
	}
	
	@Override // 根据章节删
	public int[] delStuScheduleByCha(List<Integer> chaIdList) throws Exception {
		String sql = "DELETE FROM t_stu_schedule WHERE cha_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		for (Integer chaId: chaIdList) {
			pstmt.setInt(1, chaId);
			
			pstmt.addBatch();
		}
		
		return gdb.batch(pstmt);
	}
	
	@Override // 一个课程下的所有课时
	public List<StuSchedule> queryStuSchedule(List<Integer> secIdList)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM t_stu_schedule WHERE sec_id in(");
		
		for (int i = 0; i < secIdList.size(); i++) 
			sql.append("?,");
		
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		
		pstmt = conn.prepareStatement(sql.toString());
		
		int i = 1;
		for (Integer secId:secIdList) {
			pstmt.setInt(i, secId);
			i++;
		}
		
		return gdb.query(StuSchedule.class, pstmt);
	}
	

	@Override
	public int delStuSchByCouId(int couId) {

		String sql = "DELETE FROM t_stu_schedule "
				+ " USING t_stu_schedule, t_cou_chapter, t_cou_section "
				+ " WHERE t_cou_chapter.cou_id = ? AND t_cou_chapter.cha_id = t_cou_section.cha_id "
				+ " AND (  (t_stu_schedule.sec_id = t_cou_section.sec_id) OR (t_stu_schedule.cha_id = t_cou_chapter.cha_id))";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			System.out.println(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return -1;
	}
	
	@Override
	public int delStuSchByStuIdAndCouId(int stuId, int couId) {
		String sql = "DELETE FROM t_stu_schedule  USING t_stu_schedule , t_cou_chapter AS cc, t_cou_section AS cs "
				+ " WHERE cc.cou_id = ? AND cc.cha_id = cs.cha_id AND t_stu_schedule.stu_id = ?"
				+ " AND ( (cs.sec_id = t_stu_schedule.sec_id) OR (cc.cha_id = t_stu_schedule.cha_id) ) ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, couId);
			pstmt.setInt(2, stuId);
			
			System.out.println(pstmt);
			
			return gdb.update(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	@Override
	public int updateStuSchedule(int secId, StuSchedule stuSchedule)
			throws Exception {
		String sql = "UPDATE t_stu_schedule SET stu_id=?, "
				+ "sec_id=?, sec_condition=?, sec_time=?, sec_exam=?, cha_id=?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, stuSchedule.getStuId());
		pstmt.setInt(2, stuSchedule.getSecId());
		pstmt.setString(3, stuSchedule.getSecCondition());
		pstmt.setString(4, stuSchedule.getSecTime());
		pstmt.setBoolean(5, stuSchedule.isSecExam());
		pstmt.setInt(6, stuSchedule.getChaId());
		
		return gdb.update(pstmt);
	}

	@Override
	public StuSchedule queryStuScheduleOnly(int stuId, int couId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateQuestionStatus(int stuId, int secId) throws SQLException {
		String sql="update t_stu_schedule set sec_exam=1 where stu_id= ? and sec_id=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setInt(1, stuId);
		pstmt.setInt(2, secId);
		
		return pstmt.executeUpdate()>=0;
	}

	@Override
	public boolean checkAnswerStatus(int stuId, int secId) throws SQLException {
		String sql="select sec_exam from t_stu_schedule where stu_id=? and sec_id=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setInt(1, stuId);
		pstmt.setInt(2, secId);
		
		ResultSet rs=pstmt.executeQuery();
		byte isExam=1;
		if(rs.next()) {
			isExam=rs.getByte("sec_exam");
		} 
		return isExam==1;
	}
	
	

	
}
