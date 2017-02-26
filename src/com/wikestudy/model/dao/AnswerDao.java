package com.wikestudy.model.dao;

import java.util.List;



import org.apache.commons.collections.bag.SynchronizedBag;

/**
 * 	测试题回答的操作
 * 	@author CHEN
 */
import com.wikestudy.model.pojo.Answer;
import com.wikestudy.model.pojo.PageElem;

public interface AnswerDao {
	/**
	 * 批量增加测试题回答
	 * @param answer
	 */
	public  boolean addAnswer(List<Answer> answer)throws Exception;
	/**
	 * 批量删除测试题回答
	 * @param answerid
	 */
	public int delAnswer(List<Integer> answerid)throws Exception;
	/**
	 * 批量查询测试题回答
	 * @param answerid
	 */
	public Answer queryAnswer(int studentid,int questionid) throws Exception;
	//public List<Answer> queryAnswer(List<Integer> answerid)throws Exception;
	/**
	 * 批量更新测试题回答
	 * @param answer
	 */
	public int updateAnswer(List<Answer> answer)throws Exception;
	
	/**
	 * 根据课程id删除所有答案
	 * @param couId
	 * @return
	 */
	public int delAnsByCouId(int couId);	
}
