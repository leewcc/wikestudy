package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Question;

import jdk.nashorn.internal.runtime.ECMAErrors;

/**
 *  测试题的操作
 *   @author CHEN
 */
public interface QuestionDao {
	/**
	 * 批量增加问题
	 * @param questions
	 */
	public int[] addQuestion(List<Question> questions)throws Exception ;
	/**
	 * 批量删除问题
	 * @param queids
	 */
	public int delQuestion(List<Integer> queids)throws Exception;
	/**
	 * 批量查询问题
	 * @param queid
	 */
	List<Question> queryQuestion(String chasectype, int chasecid)throws Exception;
	//public List<Question> queryQuestion(List<Integer> queid) throws Exception;
	/**
	 * 批量更新问题
	 * @param question
	 */
	public int[] updateQuestion(List<Question> question)throws Exception;
	
	/**
	 * 根据课程id删除该课程下全部问题
	 * @param couId
	 * @return
	 */
	public int delQuesByCouId(int couId);
	public int checkAnswerStatus(int stuId, int queId)throws Exception;
	public boolean searchSecId(int secId) throws Exception;
	
	public int deleteBySec(int sec) throws Exception;
	
	public int updateQuesSitu(int id, String answer) throws Exception;
}
