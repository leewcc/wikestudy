package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;



import com.wikestudy.model.dao.CommentDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Comment;
import com.wikestudy.model.pojo.PageElem;

public class CommentDaoImpl implements CommentDao{
	private Connection conn;
	private GenneralDbconn<Comment> dbconn;
	private PreparedStatement pstmt;
	
	public CommentDaoImpl(Connection conn) {
		this.conn = conn;
		dbconn = new GenneralDbconn<>();
	}
	
	@Override
	public int comment_add(Comment c) throws Exception {
		String sql = "INSERT INTO t_comment (com_topic_id, com_con, com_user_id, "
				+ "com_user_enum, com_time, com_binding, com_receiver_id, com_receiver_type)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setInt(1, c.getComTopicId());
		
		pstmt.setString(2, c.getComCon());
		
		pstmt.setInt(3, c.getComUserId());
		
		pstmt.setBoolean(4, c.isComUserEnum());
		
		pstmt.setTimestamp(5, c.getComTime());
		
		pstmt.setInt(6, c.getComBinding());
		
		pstmt.setInt(7, c.getComReceiverId());
		
		pstmt.setBoolean(8, c.isComReceiverType());
		
		return new GenneralDbconn<Comment>().insert(pstmt);
	}

	@Override
	public int deleteById(int id) throws Exception {
		String sql = "DELETE FROM t_comment WHERE com_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return new GenneralDbconn<Comment>().update(pstmt);
	}

	@Override
	public int deleteByTopicId(int topicId) throws Exception {
		String sql = "DELETE FROM t_comment WHERE com_topic_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicId);
		
		return new GenneralDbconn<Comment>().update(pstmt);
	}

	@Override
	public int deleteByBinding(int binding) throws Exception {
		String sql = "DELETE FROM t_comment WHERE com_binding = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, binding);
		
		return new GenneralDbconn<Comment>().update(pstmt);
	}
	
	public int cancelBest(int topid) throws Exception{
		String sql = "UPDATE t_comment set com_best = 0 where com_topic_id = "
				+ "? and com_best = 1";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topid);
		
		return new GenneralDbconn<Comment>().update(pstmt);
	}
	
	public int setBest(int comId, int topId, boolean isUp) throws Exception{
		String sql = "update t_comment set com_best = ? where com_id = ? "
				+ "and com_topic_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setBoolean(1, isUp);
		
		pstmt.setInt(2, comId);
		
		pstmt.setInt(3, topId);
		
		return new GenneralDbconn<Comment>().update(pstmt);
	}

	@Override
	public Comment queryById(int id) throws Exception {
		String sql = "SELECT c.*,s.stu_name AS sender,s.stu_photo_url AS photo FROM t_comment AS c,t_student AS "
				+ "s WHERE c.com_id = ? AND c.com_user_enum = 0 AND c.com_user_id = s.stu_id  "
				+ "UNION "
				+ "SELECT c.*,t.tea_name AS sender,t.tea_photo_url AS photo FROM t_comment AS c,t_teacher AS "
				+ "t WHERE c.com_id = ? AND c.com_user_enum = 1 AND c.com_user_id = t.tea_id ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		pstmt.setInt(2, id);
		
		List<Comment> list =  new GenneralDbconn<Comment>().query(Comment.class, pstmt);
		
		if(list.size() <= 0)
			
			return null;
		
		return list.get(0);
	}

	public int getRowsByTopic(int id) throws Exception {
		int row = 0;
		
		String sql = "SELECT COUNT(*) AS ROWS FROM t_comment AS c, t_student WHERE com_topic_id = ? AND com_binding = 0 AND "
				+ "com_user_id = stu_id AND com_user_enum = 0 AND stu_delete = 1";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		row += dbconn.getRows(pstmt);
		
		sql = "SELECT COUNT(*) AS ROWS FROM t_comment AS c, t_teacher WHERE com_topic_id = ? AND com_binding = 0 AND "
				+ "com_user_id = tea_id AND com_user_enum = 1 and tea_delete = 1";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		row += dbconn.getRows(pstmt);
		
		return row;
	}
	
	@Override
	public PageElem<Comment> queryByTopic(int topicId,
			PageElem<Comment> pageElem) throws Exception {
		
		
		int rows = getRowsByTopic(topicId);
		
		pageElem.setRows(rows);
		
		String sql = "SELECT c.*,s.stu_name AS sender,s.stu_photo_url AS photo FROM t_comment AS c,t_student AS "
				+ "s WHERE c.com_topic_id = ? AND c.com_user_enum = 0 AND c.com_user_id = s.stu_id  and c.com_binding = 0 and stu_delete = 1 "
				+ "UNION "
				+ "SELECT c.*,t.tea_name AS sender,t.tea_photo_url AS photo FROM t_comment AS c,t_teacher AS "
				+ "t WHERE c.com_topic_id = ? AND c.com_user_enum = 1 AND c.com_user_id = t.tea_id  and c.com_binding = 0 "
				+ "and tea_delete = 1 order by com_time desc limit ? , ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicId);
		
		pstmt.setInt(2, topicId);
		
		pstmt.setInt(3, pageElem.getStartSearch());
		
		pstmt.setInt(4, pageElem.getPageShow());
		
		List<Comment> list = dbconn.query(Comment.class, pstmt);
		
		pageElem.setPageElem(list);
		
		return pageElem;
	}

	@Override
	public List<Comment> queryByBinding(int Binding) throws Exception {
		String sql = "SELECT c.*,s.stu_name AS sender,s.stu_photo_url AS photo FROM t_comment AS c "
						+ "INNER JOIN t_student AS s ON s.stu_delete = 1 AND c.com_user_id = s.stu_id AND c.com_user_enum = 0  "
						+ "WHERE c.com_binding = ? "
						+ "UNION all "
						+ "SELECT c.*,t.tea_name AS sender,t.tea_photo_url AS photo FROM t_comment AS c "
						+ "INNER JOIN t_teacher AS t ON tea_delete = 1  AND c.com_user_id = t.tea_id AND c.com_user_enum = 1 "
						+ "WHERE c.com_binding = ? ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Binding);
		
		pstmt.setInt(2, Binding);
		
		return new GenneralDbconn<Comment>().query(Comment.class, pstmt);
	}

	@Override
	public PageElem<Comment> queryBySender(int userId, boolean userType,
			PageElem<Comment> pageElem) throws Exception {
		String sql = "SELECT COUNT(*) AS ROWS FROM t_comment WHERE com_user_id = ? AND com_user_enum = ?";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, userId);
		
		pstmt.setBoolean(2, userType);
		
		GenneralDbconn<Comment> dbconn = new GenneralDbconn<Comment>();
		
		int rows = dbconn.getRows(pstmt);
		
		pageElem.setRows(rows);
		
		if(userType)
			sql = "SELECT c.*,t.tea_name AS sender,t.tea_photo_url AS photo, top.top_tit FROM t_comment AS c, "
					+ "t_teacher AS t,t_topic AS top  WHERE c.com_user_id = ? AND c.com_user_enum = ? AND c.com_user_id = t.tea_id "
					+ "AND top.top_id = c.com_topic_id  ORDER BY com_time desc LIMIT ?, ?";
		else
			sql = "SELECT c.*,s.stu_name as sender,s.stu_photo_url as photo, top.top_tit FROM t_comment AS c,"
					+ "t_student AS s ,t_topic AS top WHERE "
					+ "c.com_user_id = ? AND c.com_user_enum = ? AND c.com_user_id = s.stu_id  AND top.top_id = c.com_topic_id ORDER "
					+ "BY com_time desc LIMIT ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, userId);
		
		pstmt.setBoolean(2, userType);
		
		pstmt.setInt(3, pageElem.getStartSearch());
		
		pstmt.setInt(4, pageElem.getPageShow());
		
		List<Comment> list = dbconn.query(Comment.class, pstmt);
		
		pageElem.setPageElem(list);
		
		return pageElem;
	}

	public int getReplysRow(int userId, boolean userType) throws Exception{
		int row = 0;
		String sql = "SELECT COUNT(*) AS ROWS FROM t_comment AS c ,  t_teacher AS t " 
						+ "WHERE com_receiver_id = ? AND com_receiver_type = ? AND t.tea_id = "
						+ "c.com_user_id AND c.com_user_enum = 1 AND tea_delete = 1";
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, userId);
		
		pstmt.setBoolean(2, userType);
		
		row = dbconn.getRows(pstmt);
		
		sql = "SELECT COUNT(*) AS ROWS FROM t_comment AS c ,  t_student AS s "
				+ "WHERE com_receiver_id = ? AND com_receiver_type = ? AND s.stu_id "
				+ "= c.com_user_id AND c.com_user_enum = 0 and stu_delete = 1";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, userId);
		
		pstmt.setBoolean(2, userType);
		
		row += dbconn.getRows(pstmt);
		
		return row;
	}
	
	@Override
	public PageElem<Comment> queryByReceiver(int userId, boolean userType,
			PageElem<Comment> pageElem) throws Exception {
		int rows = getReplysRow(userId, userType);
		
		pageElem.setRows(rows);
		
	
		String sql = "SELECT c.*,t.tea_name AS sender,t.tea_photo_url AS photo,top.top_tit FROM t_comment AS c, "
					+ "t_teacher AS t, t_topic AS top  WHERE c.com_receiver_id = ? AND c.com_receiver_type = ? AND c.com_user_id = t.tea_id "
					+ " AND top.top_id = c.com_topic_id and tea_delete = 1 "
					+ "union "
					+ "SELECT c.*,s.stu_name AS sender,s.stu_photo_url AS photo,top.top_tit FROM t_comment AS c, t_student AS s, t_topic AS top  WHERE "
					+ "c.com_receiver_id = ? AND c.com_receiver_type = ? AND c.com_user_id = s.stu_id  AND top.top_id = c.com_topic_id and stu_delete = 1 "
					+ "ORDER BY com_time desc LIMIT ?, ?";
		
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, userId);
		
		pstmt.setBoolean(2, userType);
		
		pstmt.setInt(3, userId);
		
		pstmt.setBoolean(4, userType);
		
		pstmt.setInt(5, pageElem.getStartSearch());
		
		pstmt.setInt(6, pageElem.getPageShow());
		
		List<Comment> list = dbconn.query(Comment.class, pstmt);
		
		pageElem.setPageElem(list);
		
		return pageElem;
	}
	
}
