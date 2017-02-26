package com.wikestudy.model.dao;

import com.wikestudy.model.pojo.ColTopic;
import com.wikestudy.model.pojo.PageElem;

public interface ColTopicDao {
	public int addColTopic(ColTopic ct) throws Exception;
	
	public int deleteById(int id) throws Exception;
	
	public int deleteByUserId(int userId, boolean isTea) throws Exception;
	
	public int deleteByTopicId(int topicId) throws Exception;
	
	public PageElem<ColTopic> queryByTopicId(int topicId, PageElem<ColTopic> pageElem) throws Exception;
	
	public PageElem<ColTopic> queryByUserId(int userId, boolean isTea, PageElem<ColTopic> pageElem) throws Exception;
	
	public ColTopic queryByUser(ColTopic col) throws Exception;
}
