package com.wikestudy.model.dao;

import com.wikestudy.model.pojo.AttenTopic;
import com.wikestudy.model.pojo.PageElem;

public interface AttenTopicDao {
	public PageElem<AttenTopic> queryCourse(int stuId, PageElem<AttenTopic> pageElem) throws Exception;
}
