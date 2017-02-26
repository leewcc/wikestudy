package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.objects.annotations.Where;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.wikestudy.model.dao.ArticleDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.PageElem;

public class ArticleDaoImpl implements ArticleDao{
	private Connection conn;
	
	public ArticleDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int addArticle(Article article) throws Exception {
		String sql = "INSERT INTO t_article (art_type_id, art_author_id, "
				+ "art_title, art_content, art_time) VALUE (?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, article.getArtTypeId());
		System.out.println(article.getArtTypeId());
		
		pstmt.setInt(2, article.getArtAuthorId());
		
		pstmt.setString(3, article.getArtTitle());
		
		pstmt.setString(4, article.getArtContent());
		
		pstmt.setTimestamp(5, Timestamp.valueOf(article.getArtTime()));
		
		return new GenneralDbconn<Article>().update(pstmt);
	}
	
	@Override
	public int insertArticle(Article article) throws SQLException {
		String sql = "SELECT return_article_id(?,?,?,?,?,0) AS last_id";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, article.getArtTypeId());
		
		pstmt.setInt(2, article.getArtAuthorId());
		
		pstmt.setString(3, article.getArtTitle());
		
		pstmt.setString(4, article.getArtContent());
		
		pstmt.setTimestamp(5, Timestamp.valueOf(article.getArtTime()));
		
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("last_id");
		}
		return 0;
		
	}

	@Override
	public int deleteById(int id) throws Exception {
		String sql = "DELETE FROM t_article WHERE art_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return new GenneralDbconn<Article>().update(pstmt);
	}

	@Override
	public int deleteByTypeId(int typeId) throws Exception {
		
		String sql="delete from t_article where art_type_id=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setInt(1, typeId);
		
		return new GenneralDbconn<Article>().update(pstmt);
	}
	
	@Override
	public int updateType(int id, int typeId) throws Exception {
		String sql = "UPDATE t_article SET art_type_id = ? WHERE art_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, typeId);
		
		pstmt.setInt(2, id);
		
		return new GenneralDbconn<Article>().update(pstmt);
	}

	@Override
	public int updateClick(int id) throws Exception {
		String sql = "UPDATE t_article SET art_click = art_click + 1  WHERE art_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return new GenneralDbconn<Article>().update(pstmt);
	}
	
	@Override
	public Article queryById(int id) throws Exception {
		String sql = "SELECT ta.*,tt.tea_name as author ,tat.a_type_name as artType FROM t_article AS ta,t_teacher AS tt,t_article_type AS tat WHERE tat.a_type_id=ta.art_type_id AND tt.tea_id=ta.art_author_id AND ta.art_id = ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		List<Article> list =  new GenneralDbconn<Article>().query(Article.class, pstmt);
		
		if(list.size() <= 0)
			return null;
		
		return list.get(0);
	}

	@Override
	public PageElem<Article> queryByType(int typeId, PageElem<Article> articles)
			throws Exception {
		String sql = "SELECT COUNT(*) AS ROWS FROM t_article WHERE art_type_id = ?";
		
		PreparedStatement pstmt = null;

		if(typeId<=1) {
			String sql1="SELECT a_type_id FROM t_article_type  ORDER BY a_type_id DESC LIMIT 1,1";
			pstmt=conn.prepareStatement(sql1);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
			typeId=rs.getInt("a_type_id");
			} else {
				typeId=1;
			}
		}
		
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, typeId);
		
		GenneralDbconn<Article> dbconn = new GenneralDbconn<Article>();
		
		int rows = dbconn.getRows(pstmt);
		
		articles.setRows(rows);
		
		sql = "SELECT ta.art_id,ta.art_type_id,ta.art_author_id,ta.art_title,ta.art_time,ta.art_click, tt.tea_name as author FROM t_article as ta, t_teacher as tt WHERE ta.art_type_id = ? and tt.tea_id=ta.art_author_id LIMIT ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, typeId);
		
		pstmt.setInt(2, articles.getStartSearch());
		
		pstmt.setInt(3, articles.getPageShow());
		
		List<Article> list = dbconn.query(Article.class, pstmt);
		
		articles.setPageElem(list);
		
		return articles;
	}

	@Override
	public PageElem<Article> queryByAuthor(int authorId,
			PageElem<Article> articles) throws Exception {
		String sql = "SELECT COUNT(*) as rows  FROM t_article WHERE art_author_id = ?";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, authorId);
		
		GenneralDbconn<Article> dbconn = new GenneralDbconn<Article>();
		
		int rows = dbconn.getRows(pstmt);
		
		articles.setRows(rows);
		
		sql = "SELECT * FROM t_article WHERE art_author_id = ? limit ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, authorId);
		
		pstmt.setInt(2, articles.getStartSearch());
		
		pstmt.setInt(3, articles.getPageShow());
		
		List<Article> list = dbconn.query(Article.class, pstmt);
		
		articles.setPageElem(list);
		
		return articles;
	}

	@Override
	public PageElem<Article> queryByTitle(String title,
			PageElem<Article> articles) throws Exception {
		String sql = "SELECT COUNT(*) AS rows FROM t_article WHERE art_title LIKE ?";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, "%" + title + "%");
		
		GenneralDbconn<Article> dbconn = new GenneralDbconn<Article>();
		
		int rows = dbconn.getRows(pstmt);
		
		
		articles.setRows(rows);
		//不要文章内容
		sql = "SELECT ta.art_id, ta.art_title,ta.art_time,ta.art_click , tt.tea_name as author FROM t_article as ta ,t_teacher as tt WHERE ta.art_title LIKE ? and ta.art_author_id =tt.tea_id LIMIT ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, "%" + title + "%");
		
		pstmt.setInt(2, articles.getStartSearch());
		
		pstmt.setInt(3, articles.getPageShow());
		
		List<Article> list = dbconn.query(Article.class, pstmt);
		
		articles.setPageElem(list);
		
		return articles;
	}

	@Override
	public List<Integer> queryIdByType(int typeId) throws Exception {
		String sql="select art_id from t_article where art_type_id=?";
		PreparedStatement pstmt= conn.prepareStatement(sql);
		pstmt.setInt(1, typeId);
		
		ResultSet rs=pstmt.executeQuery();
		List<Integer> l=new ArrayList<Integer>();
		while(rs.next()) {
			l.add((Integer)rs.getInt("art_id"));
		}
		
		return l;
	}

	/**
	 * 返回点击量
	 */
	@Override
	public ResultSet queryClickNumByArtId(int artId) throws SQLException {
		String sql="select a.art_click ,t.a_type_name from t_article as a ,t_article_type as t where a.art_id=? and t.a_type_id=a.art_type_id ";
		PreparedStatement pstmt= conn.prepareStatement(sql);
		pstmt.setInt(1, artId);
		
		ResultSet rs=pstmt.executeQuery();
		
		return rs;
	}





	
}
