package com.wikestudy.service.manager;

import java.sql.Connection;

import com.wikestudy.model.dao.AttenTopicDao;
import com.wikestudy.model.dao.CommentDao;
import com.wikestudy.model.dao.TopicDao;
import com.wikestudy.model.dao.impl.AttenTopicImpl;
import com.wikestudy.model.dao.impl.CommentDaoImpl;
import com.wikestudy.model.dao.impl.TopicDaoImpl;
import com.wikestudy.model.pojo.AttenTopic;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Topic;

public class TopicManagerService {
	private Connection conn;
	private TopicDao td;
	private AttenTopicDao atd;
	private CommentDao cd;
	
	public TopicManagerService(Connection  conn){
		this.conn = conn;
		td = new TopicDaoImpl(this.conn);
		cd = new CommentDaoImpl(this.conn);
		atd = new AttenTopicImpl(conn);
	}
	
	public int delete(int topicId) throws Exception{
		conn.setAutoCommit(false);
		
		try{
			td.deleteTopic(topicId);
			cd.deleteByTopicId(topicId);
			conn.commit();
			return 1;
		}catch(Exception e){
			conn.rollback();
			return 0;
		}
	}
	
	public int setUp(int topicId, boolean isUp) throws Exception{
		return td.setUp(topicId, isUp);
	}
	
	public PageElem<AttenTopic> queryTopics(int stuId) throws Exception{
		PageElem<AttenTopic> pe = new PageElem<AttenTopic>();
		pe.setCurrentPage(1);
		pe.setPageShow(5);
		return atd.queryCourse(stuId, pe);
	}
}
