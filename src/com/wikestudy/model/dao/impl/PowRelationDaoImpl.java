package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;

import com.wikestudy.model.dao.PowRelationDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.PowRelation;

public class PowRelationDaoImpl implements PowRelationDao{
	private Connection conn;
	
	public PowRelationDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int[] addRelation(List<PowRelation> relations) throws Exception {
		String sql = "INSERT INTO t_pow_relative (p_rel_tea_id, p_rel_pow_id) VALUES (?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		Iterator<PowRelation> it = relations.iterator();
		
		PowRelation pr = null;
		
		while(it.hasNext()) {
			
			pr = it.next();
			
			pstmt.setInt(1, pr.getpRelTeaId());
			
			pstmt.setInt(2, pr.getpRelPowId());
			
			pstmt.addBatch();
		}
		
		return new GenneralDbconn<PowRelation>().batch(pstmt);
	}

	@Override
	public int deleteRelationByTea(int teaId) throws Exception {
		String sql = "DELETE FROM t_pow_relative WHERE p_rel_tea_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, teaId);
		
		return new GenneralDbconn<PowRelation>().update(pstmt);
	}

	@Override
	public int deleteRelationByPow(int powId) throws Exception {
		String sql = "DELETE FROM t_pow_relative WHERE p_rel_pow_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, powId);
		
		return new GenneralDbconn<PowRelation>().update(pstmt);
	}

	@Override
	public List<PowRelation> queryByTea(int TeaId) throws Exception {
		String sql = "SELECT * FROM t_pow_relative WHERE p_rel_tea_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, TeaId);
		
		return new GenneralDbconn<PowRelation>().query(PowRelation.class, pstmt);
	}

	@Override
	public List<PowRelation> queryByPow(int powId) throws Exception {
		String sql = "SELECT * FROM t_pow_relative WHERE p_rel_pow_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, powId);
		
		return new GenneralDbconn<PowRelation>().query(PowRelation.class, pstmt);
	}

}
