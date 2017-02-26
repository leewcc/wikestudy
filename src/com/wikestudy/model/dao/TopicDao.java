package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Topic;

public interface TopicDao {
	
	public int insertTopic(Topic topic) throws Exception;
	
	public int deleteTopic(int topId) throws Exception;
	
	public int deleteTopicByUser(int id, boolean type) throws Exception;
	
	public int updateAnswer(int topId, int answerNum) throws Exception;
	
	public int updataRead(int topId, int readNum) throws Exception;
	
	public int updateTopic(int topId, Topic topic) 
			throws Exception;
	
	public int setUp(int topId, boolean isUp) throws Exception;
	
	public Topic queryById(int topId)throws Exception;
	
	/**
	 * 用户查询个人发布话题
	 * @param topUserId
	 * @param topUserEnum
	 * @return
	 * @throws Exception
	 */
	public PageElem<Topic> queryTopicByUser(int topUserId, boolean topUserEnum, PageElem<Topic> pe)
			throws Exception;
	
	/**
	 * 根据标签查询
	 * @param labId
	 * @param flag-1: 发布时间; 2: 用户访问量; 3: 回答数
	 * @return
	 * @throws Exception
	 */
	public PageElem<Topic> queryTopicByLabel(int labId, int flag, PageElem<Topic>pe)
			throws Exception;
	
	/**
	 * 查询全部话题
	 * @param labId
	 * @param flag
	 * @param pe
	 * @return
	 * @throws Exception
	 */
	public PageElem<Topic> queryTopicAll(int flag, PageElem<Topic>pe) throws Exception;

	/**
	 * 根据课程couId修改相关全部话题
	 * @param couId
	 * @return
	 */
	public int changeTopicByCouId(int couId); 
	
	/**
	 * 根据课程id 查询相关全部话题，根据时间从大到小排序——天信
	 * @param couId
	 * @param pe
	 * @return
	 */
	public PageElem<Topic> queryTopicByCouId(int couId, PageElem<Topic> pe);
	
	public PageElem<Topic> queryAll(int flag, PageElem<Topic> pageElem) throws Exception;
	
	public PageElem<Topic> queryByLabel(int flag, int labId, PageElem<Topic> pageElem) throws Exception;
	
	public PageElem<Topic> queryByKey(String key, PageElem<Topic> pageElem) throws Exception;
	
	public int getRowsAll() throws Exception;
	
	public List<Topic> queryAll(PageElem<Topic> pe) throws Exception;
	
	public int getRowsLabel(int label) throws Exception;
	
	public List<Topic> queryByLabel(int label, PageElem<Topic> pe) throws Exception ;
}
