package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import com.wikestudy.model.dao.StarDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Star;

public class StarDaoImpl implements StarDao{
	private Connection conn;
	private PreparedStatement pstmt = null;
	private GenneralDbconn<Star> dbconn = null;
	
	public StarDaoImpl(Connection conn) {
		this.conn = conn;
		dbconn = new GenneralDbconn<Star>();
	}
	
	@Override
	public int[] addStar(List<Star> stars) throws Exception {
		String sql = "INSERT INTO t_star (star_stu_id, star_stu_date) VALUES (?, ?)";
		
		pstmt = conn.prepareStatement(sql);
		
		Iterator<Star> it = stars.iterator();
		
		Star s = null;
		
		while(it.hasNext()) {
			s = it.next();
			
			pstmt.setInt(1, s.getStarStuId());
			
			pstmt.setDate(2, s.getStarStuDate());
			
			pstmt.addBatch();			
		}
		
		return dbconn.batch(pstmt);
	}

	@Override
	public int deleteStarById(int id) throws Exception {
		String sql = "DELETE FROM t_star WHERE star_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return dbconn.update(pstmt);
	}

	private String installId(int count) throws Exception{		
		StringBuilder sb = new StringBuilder("(");
		
		for(int i = 0; i < count; i++) {
			
			sb.append("?, ");
			
		}
		
		return sb.substring(0,sb.lastIndexOf(",")) + ")";
		
	}
	
	public int deleteStarByIds(List<Integer> ids) throws Exception{
		int count = ids.size();
		
		String sql = "delete from t_star where star_id in " + installId(count);
		
		pstmt = conn.prepareStatement(sql);
		
		for(int i = 1; i <= count; i++)
			pstmt.setInt(i, ids.get(i - 1));
		
		return dbconn.update(pstmt);
	}
	
	@Override
	public int deleteStarByTime(Date date) throws Exception {
		String sql = "DELETE FROM t_star star_stu_date = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setDate(1, date);
		
		return dbconn.update(pstmt);
	}

	@Override
	public List<Star> queryByDate(Date date) throws Exception {
		String sql = "SELECT s.* ,stu_name,stu_photo_url,stu_study_hour FROM t_star AS s, t_student AS stu "
				+ "WHERE s.star_stu_id = stu.stu_id and  star_stu_date = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setDate(1, date);
		
		return dbconn.query(Star.class, pstmt);
	}

	@Override
	public List<Star> queryAll(int star, int num) throws Exception {
		String sql = "SELECT * FROM t_star limit ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, star);
		
		pstmt.setInt(2, num);
		
		return dbconn.query(Star.class, pstmt);
	}

	@Override
	public int queryRows(Date date) throws Exception {
		String sql = "SELECT COUNT(star_id) as rows  FROM t_star WHERE star_stu_date = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setDate(1, date);
		
		return dbconn.getRows(pstmt);
	}

}
