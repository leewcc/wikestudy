package com.wikestudy.service.publicpart;

import java.sql.Connection;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;

public class CourseService {
	private Connection conn;
	private CourseDao cd;
	private final int shownum = 10;
	
	public CourseService(Connection conn){
		this.conn = conn;
		cd = new CourseDaoImpl(this.conn);
	}
	
	
	public PageElem<Course> query(int labId, String grade, boolean sort, int cp, boolean isRelease) throws Exception{
		//第一步：初始化分页对象
		PageElem<Course> pageElem = new PageElem<Course>();
		pageElem.setCurrentPage(cp);
		pageElem.setPageShow(8);
		
		//如果课程类型和标签类型都为0，则查找所有课程
		if("0".equals(grade) && 0 == labId){
			return cd.queryCourseAll(sort, pageElem,isRelease);
		
			
		//否则按对应的类型查找课程
		}else{
			if(!"0".equals(grade) && labId != 0)
				return cd.queryByGradeAndLabel(sort, labId, grade, pageElem,isRelease); 
			else if(labId != 0)
				return cd.queryByLabel(sort, labId, pageElem, isRelease);
			else
				return cd.queryByGrade(sort, grade, pageElem,isRelease);
		}
	}
	
	public Course queryById(int id) throws Exception{
		return cd.queryCourseById(id);
	}
	
	public int updateRelease(Course c){
		return cd.updateCouRelease(c.getCouId(), !c.isCouRelease());
	}
	
	
	public int deleteCourse(int id) throws Exception{
		return cd.delCourse(id);
	}
}
