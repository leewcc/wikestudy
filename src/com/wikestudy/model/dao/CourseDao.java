package com.wikestudy.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;

public interface CourseDao {
	
	/**
	 *  向数据库中插入整个课程
	 * @param cou-含有课程信息的对象
	 * @return true-数据表增加一行课程数据; false-操作失败
	 * @throws Exception
	 */
	public int insertCourse(Course cou);
	
	/**
	 * 根据课程Id, 重新更改课程信息为cou新内容
	 * @param couId-要更新的课程Id
	 * @param cou-提交的课程信息
	 * @return true-课程信息更新成功; false-课程信息更新失败;
	 * @throws Exception
	 */
	public int updateCourse(int couId, Course cou) ;
	
	
	public int updateStudyNum(int couId);
	
	public PageElem<Course> queryCourseByKey(String key, PageElem<Course> pe);
	/**
	 * 只是单纯的根据课程id更新该课程学习人数
	 * @param couId
	 * @param flag-true: 自增; false: 自减
	 * @return 若cou_id 未找到, 则返回0
	 * @throws Exception
	 */
	public int updateCouStudyNum(int couId, boolean flag) throws Exception;
	
	/**
	 * 更新课程是否发布
	 * @param couId
	 * @param flag: true-变成发布状态; flag-变成未发布状态
	 * @return
	 * @throws Exception
	 */
	public int updateCouRelease(int couId, boolean flag);
	
	/**
	 * 根据课程Id,删除该课程
	 * @param couId-要删除的课程Id
	 * @return true-数据表中课程,课时章节,课程评论,课程笔记,课程讨论,课程资料删除
	 *                   false-删除失败
	 * @throws Exception
	 */
	public int delCourse(int couId);
	
	/**
	 * 根据课程Id查询课程信息
	 * @param couId-要查询的课程Id
	 * @return Course-包含课程信息的对象
	 * @throws Exception
	 */
	public Course queryCourseById(int couId) ;
	
	/**
	 * 查询当前页的课程列表
	 * @param page-当前页数
	 * @param PageElem: 要查找页数, 每页要查找数据
	 * @return PageElem<Course>-当前页课程列表集合, 当前页,总页数
	 * @throws Exception
	 */
	public PageElem<Course> queryCourseAll(PageElem<Course> pe, boolean flag) 
			throws Exception;
	
	/**
	 * 分类查询当前页课程列表
	 * @param page:当前页数
	 * @param labelId-查询的类型Id;  0-全部
	 * @param Grade- 0-全部; 1-初一; 2-初二; 3-初三; 4-公众
	 * @param type- 1-推荐; 2-最新; 3-最热;(该方法不会查询推荐课程; 推荐课程
	 *                                                                       涉及到多表查询，见queryCourseSugg())
	 * @return PageElem<Course>-该类型下课程信息集合
	 * @throws Exception
	 */
	public PageElem<Course> queryCourseSort(int labelId, PageElem<Course> pe,
			String type, String couGrade) throws Exception;
	
	/**
	 * 根据老师Id查询老师创建的课程
	 * @param teacherId-创建课程的老师Id
	 * @param flag:1-全部课程; 2-未发布课程; 3-已发布课程
	 * @return 该老师创建的课程集合
	 * @throws Exception
	 */
	public PageElem<Course> queryCourseTea(int teacherId, int flag, PageElem<Course> pe);

	/**
	 * 
	 * @param couIdList
	 * @param pe
	 * @return
	 * @throws Exception
	 */
	public PageElem<Course> queryCourseStu(List<Integer> couIdList, PageElem<Course> pe)
		throws Exception;
	
	/**
	 * 查询被推荐的课程信息：联合查询
	 * @param pe: 
	 * @param labelId-查询的类型Id;  0-全部
	 * @param Grade- 0-全部; 1-初一; 2-初二; 3-初三; 4-公众
	 * @return
	 */
	public PageElem<Course> queryCourseSugg(PageElem<Course> pe, int labelId, 
			String grade) throws Exception;
	
	
	//获取所有课程
	public PageElem<Course> queryCourseAll(boolean flag, PageElem<Course> pageElem, boolean isRelease) throws Exception;
	
	
	//根据课程类型获取课程
	public PageElem<Course> queryByGrade(boolean flag, String grade, PageElem<Course> pageElem, boolean isRelease) throws Exception;
	
	
	//根据标签类型获取课程
	public PageElem<Course> queryByLabel(boolean flag, int labId, PageElem<Course> pageElem, boolean isRelease) throws Exception;
	
	
	//根据课程类型与标签类型胡哦哦去标签
	public PageElem<Course> queryByGradeAndLabel(boolean flag, int labId, String grade, PageElem<Course> pageElem, boolean isRelease) throws Exception;

	public boolean updateCoursePhoto(int parseInt, String photoUrl) throws SQLException;
	
}
