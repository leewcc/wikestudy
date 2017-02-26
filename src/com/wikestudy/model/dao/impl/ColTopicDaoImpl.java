package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.wikestudy.model.dao.ColTopicDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.ColTopic;
import com.wikestudy.model.pojo.PageElem;

public class ColTopicDaoImpl implements ColTopicDao{
	private Connection conn;
	private PreparedStatement pstmt = null;
	private GenneralDbconn<ColTopic> dbconn;
	
	public ColTopicDaoImpl(Connection conn) {
		this.conn = conn;
		dbconn = new GenneralDbconn<>();
	}
	
	@Override
	public int addColTopic(ColTopic ct) throws Exception {
		String sql = "INSERT INTO t_coltopic (col_user_id, col_user_enum, col_topic_id) VALUES (?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, ct.getColUserId());
		
		pstmt.setBoolean(2, ct.isColUserEnum());
		
		pstmt.setInt(3, ct.getColTopicId());
		
		return new GenneralDbconn<ColTopic>().update(pstmt);
	}

	@Override
	public int deleteById(int id) throws Exception {
		String sql = "DELETE FROM t_coltopic WHERE col_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return new GenneralDbconn<ColTopic>().update(pstmt);
	}

	@Override
	public int deleteByUserId(int userId, boolean isTea) throws Exception {
		String sql = "DELETE FROM t_coltopic WHERE col_user_id = ? AND col_user_enum = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, userId);
		
		pstmt.setBoolean(2, isTea);
		
		return new GenneralDbconn<ColTopic>().update(pstmt);
	}

	@Override
	public int deleteByTopicId(int topicId) throws Exception {
		String sql = "DELETE FROM t_coltopic WHERE col_topic_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicId);
		
		return new GenneralDbconn<ColTopic>().update(pstmt);
	}

	@Override
	public PageElem<ColTopic> queryByTopicId(int topicId,
			PageElem<ColTopic> pageElem) throws Exception {
		String sql = "SELECT COUNT(*) FROM t_coltopic WHERE col_topic_id = ?";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicId);
		
		GenneralDbconn<ColTopic> dbconn = new GenneralDbconn<ColTopic>();
		
		int rows = dbconn.getRows(pstmt);
		
		pageElem.setRows(rows);
		
		sql = "SELECT * FROM t_coltopic WHERE col_topic_id = ? LIMIT ?, ?  ";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicId);
		
		pstmt.setInt(2, pageElem.getStartSearch());
		
		pstmt.setInt(3, pageElem.getPageShow());
		
		List<ColTopic> list = dbconn.query(ColTopic.class, pstmt);
		
		pageElem.setPageElem(list);
		
		return pageElem;
	}

	private int getRows(int id, boolean type) throws Exception {
		int rows = 0;
		String sql = "SELECT COUNT(*) AS ROWS  FROM t_coltopic AS col , t_topic AS t, t_teacher AS u "
						+ "WHERE col_topic_id = t.top_id AND t.top_user_id = u.tea_id " 
						+ "AND top_user_enum = 1 AND col_user_id = ? AND col_user_enum = ? AND tea_delete = 1";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setBoolean(2, type);
		
		pstmt.setInt(1, id);
		
		rows += dbconn.getRows(pstmt);
		
		sql = "SELECT COUNT(*) AS ROWS  FROM t_coltopic AS col , t_topic AS t, t_student AS u "
				+ "WHERE col_topic_id = t.top_id AND t.top_user_id = u.stu_id " 
				+ "AND top_user_enum = 0 AND col_user_id = ? AND col_user_enum = ? AND stu_delete = 1";

		pstmt = conn.prepareStatement(sql);
		
		pstmt.setBoolean(2, type);
		
		pstmt.setInt(1, id);
		
		rows += dbconn.getRows(pstmt);
		
		return rows;
		
	}
	
	@Override
	public PageElem<ColTopic> queryByUserId(int userId, boolean isTea, PageElem<ColTopic> pageElem)
			throws Exception {
			
		int rows = getRows(userId, isTea);
		
		pageElem.setRows(rows);
		
		
		String sql = "SELECT col.*, t.top_tit,t.top_con, t.top_time, t.top_read_num AS read_num, t.top_ans_num AS answer_num,u.tea_name "
					+ "AS sender_name, u.tea_photo_url AS sender_photo,top_user_id AS sender_id, top_user_enum AS sender_type FROM "
					+ "t_coltopic AS col , t_topic AS t, t_teacher AS u WHERE col_topic_id = t.top_id AND t.top_user_id = u.tea_id "
					+ "AND top_user_enum = 1 AND col_user_id = ? AND col_user_enum = ? AND tea_delete = 1 "
					+ " union "
					+ "SELECT col.*, t.top_tit,t.top_con, t.top_time, t.top_read_num AS read_num, t.top_ans_num AS answer_num,u.stu_name  "
					+ "AS sender_name, u.stu_photo_url AS sender_photo,top_user_id AS sender_id, top_user_enum AS sender_type FROM "
					+ "t_coltopic AS col , t_topic AS t, t_student AS u WHERE col_topic_id = t.top_id AND t.top_user_id = u.stu_id "
					+ "AND top_user_enum = 0 AND col_user_id = ? AND col_user_enum = ? AND stu_delete = 1 LIMIT ?, ?";
		
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, userId);
		
		pstmt.setBoolean(2, isTea);
		
		pstmt.setInt(3, userId);
		
		pstmt.setBoolean(4, isTea);
		
		pstmt.setInt(5, pageElem.getStartSearch());
		
		pstmt.setInt(6, pageElem.getPageShow());
		
		List<ColTopic> list = dbconn.query(ColTopic.class, pstmt);
		
		pageElem.setPageElem(list);
		
		return pageElem;
	}
	
	public ColTopic queryByUser(ColTopic col) throws Exception{
		String sql = "SELECT * FROM t_coltopic WHERE col_user_id = ? AND "
				+ "col_user_enum = ? AND col_topic_id = ?";
		
		PreparedStatement pstmt = conn.prepareCall(sql);
		
		pstmt.setInt(1, col.getColUserId());
		
		pstmt.setBoolean(2, col.isColUserEnum());
		
		pstmt.setInt(3, col.getColTopicId());
		
		GenneralDbconn<ColTopic> dbconn = new GenneralDbconn<ColTopic>();
		
		List<ColTopic> list = dbconn.query(ColTopic.class, pstmt);
		
		if(!list.isEmpty())
			return list.get(0);
		
		return null;
	}

}
