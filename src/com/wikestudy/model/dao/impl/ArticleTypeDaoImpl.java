package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.wikestudy.model.dao.ArticleTypeDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.ArticleType;

public class ArticleTypeDaoImpl implements ArticleTypeDao{
	private Connection conn;
	
	public ArticleTypeDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int AddType(ArticleType type) throws Exception {
		String sql = "INSERT INTO t_article_type (a_type_name, a_type_des) VALUES (?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, type.getATypeName());
		
		pstmt.setString(2, type.getATypeDes());
		
		return new GenneralDbconn<ArticleType>().update(pstmt);
	}

	@Override
	public int deleteById(int id) throws Exception {
		String sql = "DELETE FROM t_article_type WHERE a_type_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);		
		
		return new GenneralDbconn<ArticleType>().update(pstmt);
	}

	@Override
	public int updateType(ArticleType type) throws Exception {
		String sql = "UPDATE t_article_type SET a_type_name =? , a_type_des = ? WHERE a_type_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, type.getATypeName());
		
		pstmt.setString(2, type.getATypeDes());
		
		pstmt.setInt(3, type.getATypeId());
		
		return new GenneralDbconn<ArticleType>().update(pstmt);
	}

	@Override
	public List<ArticleType> queryType() throws Exception {
		String sql = "SELECT * FROM t_article_type";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		return new GenneralDbconn<ArticleType>().query(ArticleType.class, pstmt);
	}

	@Override
	public ArticleType queryById(int id) throws Exception {
		String sql = "SELECT * FROM t_article_type WHERE a_type_id = ? ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		List<ArticleType> list =  new GenneralDbconn<ArticleType>().query(ArticleType.class, pstmt);
		
		if(list.size() <= 0)
			
			return null;
		
		return list.get(0);
	}

	@Override
	public String queryTypeByTypeId(int artTypeId) throws Exception {
		String sql = "SELECT a_type_name FROM t_article_type WHERE a_type_id = ? ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, artTypeId);
		
		ResultSet rs=pstmt.executeQuery();
		
		if(rs.next()) {
			return rs.getString("a_type_name");
		}
		return "";
	}

}
