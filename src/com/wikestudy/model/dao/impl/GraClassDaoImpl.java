package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.wikestudy.model.dao.GraClassDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.GraClass;

public class GraClassDaoImpl implements GraClassDao{
	private Connection conn;
	
	public GraClassDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int updateNum(GraClass classes) throws Exception {
		String sql = "UPDATE t_class SET cla_grade = ?, cla_class_num = ? WHERE cla_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, classes.getClaGrade());
		
		pstmt.setInt(2, classes.getClaClassNum());
		
		pstmt.setInt(3, classes.getClaId());
		
		return new GenneralDbconn<GraClass>().update(pstmt);
	}

	@Override
	public GraClass queryClass(int claId) throws Exception {
		String sql = "SELECT * FROM t_class WHERE cla_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, claId);
		
		List<GraClass> list = new GenneralDbconn<GraClass>().query(GraClass.class, pstmt);
		
		if(list.size() <= 0)
			return null;
		
		return list.get(0);
		
	}

}
