package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.wikestudy.model.dao.RecordDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Record;
import com.wikestudy.model.pojo.Student;

public class RecordDaoImpl implements RecordDao{
	private PreparedStatement pstmt;
	private GenneralDbconn<Record> dbconn;
	private Connection conn;
	
	public RecordDaoImpl(Connection conn) {
		dbconn = new GenneralDbconn<>();
		this.conn = conn;
	}
	
	private String installId(int count) {
		if(count <= 0)
			return "(0)";
		
		StringBuilder sb = new StringBuilder("(");
		
		for(int i = 0; i < count; i++) {
			
			sb.append("?, ");
			
		}
		
		return sb.substring(0,sb.lastIndexOf(",")) + ")";
	}
	
	@Override
	public int selectRecordCount(List<Integer> esixts) throws Exception {
		int count = esixts.size();
		String idS = installId(count);
		String sql = "SELECT count(r.id) AS rows FROM t_record AS r, t_student AS t WHERE t.stu_id = r.user_id "
				+ "AND t.stu_delete = 1 and user_id not in " + idS;
		
		pstmt = conn.prepareStatement(sql);
		
		for(int i = 1; i <= count; i++)
			pstmt.setInt(i, esixts.get(i - 1));
		
		return dbconn.getRows(pstmt);
	}
	
	@Override
	public List<Record> selectRecord(List<Integer> esixts,int start, int end) throws Exception {
		int count = esixts.size();
		String idS = installId(count);
		
		String sql = "SELECT r.*, t.stu_name AS name FROM t_record AS r, t_student AS t WHERE t.stu_id = r.user_id "
				+ "AND t.stu_delete = 1 and user_id not in " + idS + " ORDER BY r.study_time desc , r.dis_num desc LIMIT ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		for(int i = 1; i <= count; i++)
			pstmt.setInt(i, esixts.get(i - 1));
		
		pstmt.setInt(count + 1, start);
		
		pstmt.setInt(count + 2, end);
		
		return dbconn.query(Record.class, pstmt);
	}

	@Override
	public int update(Record r) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int create(int id) throws Exception {
		String sql = "INSERT INTO t_record (user_id) VALUES (?)";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return dbconn.insert(pstmt);	
	}

	@Override
	public int[] create(List<Integer> ids) throws Exception {
		String sql = "INSERT INTO t_record (user_id) VALUES (?)";
		
		pstmt = conn.prepareStatement(sql);
		
		for(Integer id : ids) {
			pstmt.setInt(1, id);
			pstmt.addBatch();
		}
		
		
		
		return dbconn.batch(pstmt);
	}
	
	@Override
	public Record find(int id) throws Exception {
		String sql = "SELECT * FROM t_record WHERE user_id = ? ";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		List<Record> r = dbconn.query(Record.class, pstmt);
		
		if(r.isEmpty())
			return null;
		
		return r.get(0);
	}

	@Override
	public int delete(int id) throws Exception {
		String sql = "delete from t_record where user_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return dbconn.insert(pstmt);	
	}

	@Override
	public int updateStudyTime(int id, long time) throws Exception {
		String sql = "UPDATE t_record SET study_time = study_time + ? WHERE user_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setLong(1, time);
		
		pstmt.setInt(2, id);
		
		return dbconn.update(pstmt);
	}

	@Override
	public int updateDidNum(int id, int time) throws Exception {
		String sql = "UPDATE t_record SET dis_num = dis_num + ? WHERE user_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, time);
		
		pstmt.setInt(2, id);
		
		return dbconn.update(pstmt);
	}

	@Override
	public int confirm() throws Exception {
		String sql = "UPDATE t_record SET study_time = 0 AND dis_num = 0";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		return dbconn.update(pstmt);
	}
	
}
