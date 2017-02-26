package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.wikestudy.model.dao.ArticleCommentDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.ArticleComment;
import com.wikestudy.model.pojo.PageElem;


public class ArticleCommentDaoImpl implements ArticleCommentDao{
	private Connection conn;
	
	public ArticleCommentDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int comment_add(ArticleComment comment) throws Exception {
		String sql = "INSERT INTO t_article_comment (a_com_art_id, a_com_binding, a_com_sender_id, "
				+ "a_com_sender_mark, a_com_content, a_com_time) VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, comment.getAComArtId());
		
		pstmt.setInt(2, comment.getAComBinding());
		
		pstmt.setInt(3, comment.getAComSenderId());
		
		pstmt.setInt(4, comment.getAComSenderMark());
		
		pstmt.setString(5, comment.getAComContent());
		
		pstmt.setTimestamp(6,Timestamp.valueOf(comment.getAComTime()) );
		
		
		return new GenneralDbconn<ArticleComment>().update(pstmt);
	}

	@Override
	public int deleteById(int id) throws Exception {
		String sql = "DELETE FROM t_article_comment WHERE a_com_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return new GenneralDbconn<ArticleComment>().update(pstmt);
	}

	@Override
	public int deleteByArtId(int artId) throws Exception {
		String sql = "DELETE FROM t_article_comment WHERE a_com_art_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, artId);
		
		return new GenneralDbconn<ArticleComment>().update(pstmt);
	}


	@Override
	public ArticleComment queryById(int id) throws Exception {
		String sql = "SELECT * FROM t_article_comment WHERE a_com_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		List<ArticleComment> list =  new GenneralDbconn<ArticleComment>().query(ArticleComment.class, pstmt);
		
		if(list.size() <= 0)
			
			return null;
		
		return list.get(0);
	}

	@Override
	public PageElem<ArticleComment> queryByArtId(int artId,
			PageElem<ArticleComment> pageElem) throws Exception {
		String sql = "SELECT COUNT(*)as rows FROM t_article_comment WHERE a_com_art_id = ? AND a_com_binding = 0";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, artId);
		
		GenneralDbconn<ArticleComment> dbconn = new GenneralDbconn<ArticleComment>();
		
		int rows = dbconn.getRows(pstmt);
		
		pageElem.setRows(rows);
		
		// 查询学生的
		sql = "SELECT tac.* ,st.stu_name as aComSender ,st.stu_photo_url  as aComSenderPhoto FROM t_article_comment  AS tac , t_student AS st WHERE tac.a_com_art_id = ? AND tac.a_com_binding = 0 AND tac.`a_com_sender_id`= 0 AND tac.`a_com_sender_id`=st.`stu_id` limit ?,?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, artId);
		
		pstmt.setInt(2, pageElem.getStartSearch());
		
		pstmt.setInt(3, pageElem.getPageShow());	
		List<ArticleComment> stuList = dbconn.query(ArticleComment.class, pstmt);
		
		//查询老师的
		sql ="SELECT tac.* ,tt.`tea_name` as aComSender , tt.`tea_photo_url` as aComSenderPhoto FROM t_article_comment  AS tac ,t_teacher AS tt WHERE tac.a_com_art_id = ? AND tac.a_com_binding = 0 AND tac.`a_com_sender_id`= 1 AND tac.`a_com_sender_id`=tt.`tea_id`  limit ?,?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, artId);
		
		pstmt.setInt(2, pageElem.getStartSearch());
		
		pstmt.setInt(3, pageElem.getPageShow());	
		List<ArticleComment> teaList = dbconn.query(ArticleComment.class, pstmt);
		
		//合二为一
		stuList.addAll(teaList);
		
		pageElem.setPageElem(stuList);
		
		return pageElem;
	}

	@Override
	public List<ArticleComment> queryByBinding(int Binding) throws Exception {
		
		//查询老师
		String sql ="SELECT tac.* ,tt.`tea_name` as aComSender ,tt.`tea_photo_url`  as aComSenderPhoto FROM t_article_comment  AS tac , t_teacher AS tt WHERE tac.a_com_binding = ? AND tac.`a_com_sender_id`= 1 AND tac.`a_com_sender_id`=tt.`tea_id`";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Binding);
		
		List<ArticleComment> teaComment=new GenneralDbconn<ArticleComment>().query(ArticleComment.class, pstmt);
		
		//查询学生
		sql = "SELECT tac.* ,st.stu_name as aComSender ,st.stu_photo_url as aComSenderPhoto FROM t_article_comment  AS tac , t_student AS st WHERE tac.a_com_binding = ? AND tac.`a_com_sender_id`= 0 AND tac.`a_com_sender_id`=st.`stu_id`";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Binding);
		
		List<ArticleComment> stuComment=new GenneralDbconn<ArticleComment>().query(ArticleComment.class, pstmt);
		
		//返回所有的内容
		stuComment.addAll(teaComment);
		return stuComment;
	}
	
	@Override
	public int deleteByBinding(int binding) throws Exception {
		String sql = "DELETE FROM t_article_comment WHERE a_com_biding = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, binding);
		
		return new GenneralDbconn<ArticleComment>().update(pstmt);
	}


	@Override
	public int deleteIdByArt(int i) throws Exception {
		List<Integer> l=new ArrayList<Integer>();
		String sql="delete from t_article_comment where a_com_art_id=?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, i);
		return  pstmt.executeUpdate();
	}



}
