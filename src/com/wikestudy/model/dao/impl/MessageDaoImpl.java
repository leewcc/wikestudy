package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.wikestudy.model.dao.MessageDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Message;
import com.wikestudy.model.pojo.PageElem;





public class MessageDaoImpl implements MessageDao{
	Connection conn;
	PreparedStatement pstmt = null;
	GenneralDbconn<Message> dbconn = null;
	
	public MessageDaoImpl(Connection conn) {
		this.conn=conn;
		dbconn = new GenneralDbconn<Message>();
	}
	
	
	private String installId(int count) throws Exception{		
		StringBuilder sb = new StringBuilder("(");
		
		for(int i = 0; i < count; i++) {
			
			sb.append("?, ");
			
		}
		
		return sb.substring(0,sb.lastIndexOf(",")) + ")";
		
	}

	@Override
	public int addMessage(Message message) throws Exception {
		String sql ="insert into t_message(mess_master_id,mess_master_mark,mess_binding,"
				+ "mess_content,mess_sender_id,mess_time,mess_sender_mark,mess_has_read)"
				+ "values(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, message.getMessMasterId());
		pstmt.setBoolean(2, message.isMessMasterMark());
		pstmt.setInt(3, message.getMessBinding());
		pstmt.setString(4, message.getMessContent());
		pstmt.setInt(5, message.getMessSenderId());
		pstmt.setTimestamp(6, message.getMessTime());
		pstmt.setBoolean(7, message.isMessSenderMark());
		pstmt.setBoolean(8, message.isMessHasRead());
		return new GenneralDbconn<Message>().update(pstmt);
	}

	@Override
	public int delMessage(int messid) throws Exception {
		String sql="delete  from t_message where mess_id = ?";;
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, messid);
		return dbconn.update(pstmt);
	}

	private int getRows(int id, boolean type) throws Exception {
		int rows = 0;
		String sql = "SELECT COUNT(*) AS ROWS FROM t_message "
				+ "INNER JOIN t_teacher ON tea_delete = 1 AND mess_sender_id = tea_id AND mess_sender_mark = 1 "
				+ "WHERE	mess_master_mark = ?  AND mess_master_id = ? AND mess_binding = 0 ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setBoolean(1, type);
		pstmt.setInt(2, id);
		rows += dbconn.getRows(pstmt);
		
		sql = "SELECT COUNT(*) AS ROWS FROM t_message "
				+ "INNER JOIN t_student ON stu_delete = 1 AND mess_sender_id = stu_id AND mess_sender_mark = 0 "
				+ "WHERE	mess_master_mark = ?  AND mess_master_id = ? AND mess_binding = 0 ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setBoolean(1, type);
		pstmt.setInt(2, id);
		rows += dbconn.getRows(pstmt);
		return rows;
	}
	
	@Override
	public PageElem<Message> queryMessages(int messmasterid, boolean masterType, PageElem<Message> pageElem) throws Exception {
		pageElem.setRows(getRows(messmasterid, masterType));
		
		
		String sql = "(SELECT m.*,u.stu_photo_url AS photo,u.stu_name AS sender FROM t_message AS m  "
				+ "INNER JOIN t_student AS u ON stu_delete = 1 AND m.mess_sender_id = u.stu_id AND m.mess_sender_mark = 0  "
				+ "WHERE m.mess_master_mark = ? AND m.mess_master_id = ? AND m.mess_binding = 0 ORDER BY mess_time DESC LIMIT  ?,?) "
				+ "UNION ALL "
				+ "(SELECT m.*,u.tea_photo_url AS photo,u.tea_name AS sender FROM t_message AS m "
				+ "INNER JOIN t_teacher AS u ON tea_delete = 1 AND m.mess_sender_id = u.tea_id AND m.mess_sender_mark = 1 "
				+ "WHERE m.mess_master_mark = ?  AND m.mess_master_id = ? AND m.mess_binding = 0 ORDER BY mess_time DESC LIMIT ?,?) "
				+ "ORDER BY mess_time DESC LIMIT ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setBoolean(1, masterType);
		pstmt.setInt(2, messmasterid);
		pstmt.setInt(3, pageElem.getStartSearch());
		pstmt.setInt(4, pageElem.getPageShow());
		pstmt.setBoolean(5, masterType);
		pstmt.setInt(6, messmasterid);
		pstmt.setInt(7, pageElem.getStartSearch());
		pstmt.setInt(8, pageElem.getPageShow());
		pstmt.setInt(9, pageElem.getPageShow());
		List<Message> list = dbconn.query(Message.class, pstmt);
		pageElem.setPageElem(list);
		
		
		return pageElem;
	}

	@Override
	public int addBinding(Message message) throws Exception {
		String sql ="insert into t_message(mess_master_id,mess_master_mark,mess_binding,"
				+ "mess_content,mess_sender_id,mess_time,mess_sender_mark,mess_has_make)"
				+ "values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, message.getMessMasterId());
		pstmt.setBoolean(2, message.isMessMasterMark());
		pstmt.setInt(3, message.getMessBinding());
		pstmt.setString(4, message.getMessContent());
		pstmt.setInt(5, message.getMessSenderId());
		pstmt.setTimestamp(6, message.getMessTime());
		pstmt.setInt(7, message.getMessSenderId());
		pstmt.setBoolean(8, message.isMessHasRead());
		return new GenneralDbconn<Message>().insert(pstmt);
	}

	@Override
	public  int delBinding(int messBinding) throws Exception {
		String sql="delete  from t_message where mess_binding = ?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, messBinding);
		return new GenneralDbconn<Message>().update(pstmt);
	}

	public Message queryMessage(int messid)throws Exception{
		String sql = "select * from t_message where mess_id = ?";
				
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, messid);
		
		List<Message> list = new GenneralDbconn<Message>().query(Message.class, pstmt);
		
		if(list.size() <= 0)
			return null;
		
		return list.get(0);
	}
	
	
	@Override
	public List<Message> queryBinding(int messbinding) throws Exception {
		String sql="SELECT m.*,u.stu_photo_url AS photo,u.stu_name AS sender FROM t_message AS m "
				+ "INNER JOIN t_student AS u ON stu_delete = 1 AND m.mess_sender_id = u.stu_id AND m.mess_sender_mark = 0 "
				+ "WHERE m.mess_binding = ? "
				+ "UNION ALL "
				+ "SELECT m.*,u.tea_photo_url AS photo,u.tea_name AS sender FROM t_message AS m "
				+ "INNER JOIN t_teacher AS u ON tea_delete = 1 AND m.mess_sender_id = u.tea_id AND m.mess_sender_mark = 1 "
				+ "WHERE m.mess_binding = ?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, messbinding);
		pstmt.setInt(2, messbinding);
		List<Message> messages=dbconn.query(Message.class, pstmt);
		return messages;
		
	}

}
