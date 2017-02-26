package com.wikestudy.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.wikestudy.model.pojo.StuSchedule;

// 学生个人课时进度表
public interface StuScheduleDao {
	
	/**
	 * 插入新的课时进度
	 * @param stuSchedule-课时进度信息
	 * @return true; false
	 * @throws Exception
	 */
	public int insertStuSchedule(StuSchedule stuSchedule);
	
	/**
	 * 根据课时集合id删除课时; 与是不是课程集合无关
	 * @param secIdList-课程下的所有课时Id集合
	 * @return true; false
	 * @throws Exception
	 */
	public int[] delStuSchedule(List<Integer> secIdList) 
		throws Exception;
	
	/**
	 * 删除章节集合id; 与是否是一个课程集合无关
	 * @param chaIdList
	 * @return
	 * @throws Exception
	 */
	public int[] delStuScheduleByCha(List<Integer> chaIdList)
			throws Exception; 
	/**
	 * 按章节查询该章节下所有课时进度信息
	 * @param secIdList-某章节下的所有课时Id集合
	 * @return List<StuSchedule>-该章节下所有课时进度信息集合
	 * @throws Exception
	 */
	public List<StuSchedule> queryStuSchedule(List<Integer> secIdList)
		throws Exception;
	
	/**
	 * 根据学生id 和 课程id搜索被选择的课程信息
	 * @param stuId
	 * @param couId
	 * @return 如果未选择该课程, 则返回null
	 * @throws Exception
	 */
	public StuSchedule queryStuScheduleOnly(int stuId, int couId) 
			throws Exception;
	/**
	 * 根据课时Id更新课时进度
	 * @param secId-要更新的课时Id信息
	 * @param stuSchedule-要更新的课时信息
	 * @return true; false
	 * @throws Exception
	 */
	public int updateStuSchedule(int secId, StuSchedule stuSchedule) 
			throws Exception;

	/**
	 * 根据couId删除所有学生个人进度下的相关课程
	 * @param couId
	 * @return
	 */
	public int delStuSchByCouId(int couId);
	
	
	public int delStuSchByStuIdAndCouId(int stuId, int couId);

	public boolean updateQuestionStatus(int stuId, int secId) throws SQLException;

	public boolean checkAnswerStatus(int stuId, int secId)throws SQLException;

	
}
