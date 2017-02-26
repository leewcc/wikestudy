package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.wikestudy.model.dao.CouRecommendDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.CouRecommend;

public class CouRecommendDaoImpl implements CouRecommendDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	
	public CouRecommendDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public int insertCouRecommend(CouRecommend couRecomment) throws Exception {
		String sql = "INSERT IGNORE INTO t_cou_recommend (cou_id, rec_grade) VALUES(?, ?)";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, couRecomment.getCouId());
		pstmt.setString(2, couRecomment.getRecGrade());
		
		return new GenneralDbconn<CouRecommend>().update(pstmt);
	}

	@Override
	public int delCouRecommend(int couId) {
		String sql = "DELETE  FROM t_cou_recommend WHERE cou_id = ?";
		
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
	public List<CouRecommend> queryCouRecommendByGrade(String grade)
			throws Exception {
		String sql = "SELECT * FROM t_cou_recommend WHERE rec_grade=?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, grade);
		
		return new GenneralDbconn<CouRecommend>().query(CouRecommend.class, pstmt);
	}

	@Override
	public int updataCouRecommend(int couId,  String recGrade)
			throws Exception {
		
		String sql = "UPDATE t_cou_recommend SET rec_grade = ? WHERE cou_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, couId);
		
		pstmt.setString(2, recGrade);
		
		return new GenneralDbconn<CouRecommend>().update(pstmt);
	}
	
	
}
