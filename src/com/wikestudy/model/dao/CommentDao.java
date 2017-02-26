package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.Comment;
import com.wikestudy.model.pojo.PageElem;

public interface CommentDao {
	public int comment_add(Comment c) throws Exception;
	
	public int deleteById(int id) throws Exception;
	
	public int deleteByTopicId(int topicId) throws Exception;
	
	public int deleteByBinding(int binding) throws Exception;
	
	public int cancelBest(int topid) throws Exception;
	
	public int setBest(int comId, int topId, boolean isUp) throws Exception;
	
	public Comment queryById(int id) throws Exception;
	
	public PageElem<Comment> queryByTopic(int topicId, PageElem<Comment> pageElem) throws Exception;
	
	public List<Comment> queryByBinding(int Binding) throws Exception;
	
	public PageElem<Comment> queryBySender(int userId, boolean userType, PageElem<Comment> pageElem) throws Exception;

	public PageElem<Comment> queryByReceiver(int userId, boolean userType, PageElem<Comment> pageElem) throws Exception;

	
}
