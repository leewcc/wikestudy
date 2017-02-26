package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.StuCourse;
import com.wikestudy.model.pojo.StuSchedule;

// 学生个人课程表
public interface StuCourseDao {
	
	/**
	 * 增加个人课程表数据
	 * @param stuCourse-新增个人课程信息
	 * @return true; false
	 * @throws Exception
	 */
	public int insertStuCourse(StuCourse stuCourse);
	
	/**
	 * 根据课程Id, 删除个人课程, 笔记 评论一概不删，但是应该删除个人进度
	 * @param couId-删除的课程Id
	 * @return true; false
	 * @throws Exception
	 */
	public int delStuCourse(int couId);
	
	/**
	 * 学生自己删除已选课程的相关信息
	 * @param stuId
	 * @param couId
	 * @return
	 */
	public int delStuCourseByStuIdAndCouId(int stuId, int couId);
	
	/**
	 * 分页查询学生选课信息
	 * @param stuId-学生Id
	 * @param page-当前页数
	 * @return List<StuCourse>-学生个人选课信息集合
	 * @throws Exception
	 */
	public PageElem<StuCourse> queryStuCourseAll(int stuId, PageElem<StuCourse> pe)
		throws Exception;
	
	
	public List<Course> queryAllStuCourse(int stuId) throws Exception ;
	
	/**
	 * 判断学生是否选课
	 * @param stuId
	 * @param couId
	 * @return
	 */
	public int isSelected(int stuId, int couId) ;
	/**
	 * 分页分类查询学生选课信息
	 * @param stuId
	 * @param pe
	 * @param flag-true: 学生正在学习中; false-学生已学完课程
	 * @return
	 * @throws Exception
	 */
	public PageElem<StuCourse> queryStuCourseSort(int stuId, 
			PageElem<StuCourse> pe, boolean flag) throws Exception;
	
	// 根据标签类型查询课程吗= =，看来这里需要一个视图
	// 个人课程表,  课程表, 标签表
	
	
	
	public StuCourse queryStuScheduleOnly(int stuId, int couId) 
			throws Exception;
}
