package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.wikestudy.model.dao.AttenTopicDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.AttenTopic;
import com.wikestudy.model.pojo.PageElem;

public class AttenTopicImpl implements AttenTopicDao{
	private Connection conn;
	private PreparedStatement pstmt;
	private GenneralDbconn<AttenTopic> dbconn;
	
	public AttenTopicImpl(Connection conn) {
		this.conn = conn;
		dbconn = new GenneralDbconn<AttenTopic>();
	}
	
	@Override
	public PageElem<AttenTopic> queryCourse(int stuId,
			PageElem<AttenTopic> pageElem) throws Exception {
		String sql = "SELECT COUNT(com_id)as rows FROM t_comment WHERE com_user_id = ? AND "
				+ "com_user_enum = ?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, stuId);
		pstmt.setBoolean(2, false);
		pageElem.setRows(dbconn.getRows(pstmt));
		
		sql = "SELECT c.com_topic_id AS id,t.top_tit AS title,c.com_time AS sendtime FROM t_comment "
				+ "AS c,t_topic AS t WHERE c.com_topic_id = t.top_id AND com_user_id = ? AND com_user_enum = ? "
				+ "GROUP BY c.com_topic_id ORDER BY com_time DESC limit ?,?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, stuId);
		pstmt.setBoolean(2, false);
		pstmt.setInt(3, pageElem.getStartSearch());
		pstmt.setInt(4, pageElem.getPageShow());
		pageElem.setPageElem(dbconn.query(AttenTopic.class, pstmt));
		return pageElem;
		
		
	}

}
