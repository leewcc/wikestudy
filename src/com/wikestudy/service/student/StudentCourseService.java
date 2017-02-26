package com.wikestudy.service.student;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.wikestudy.model.dao.CouRecommendDao;
import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.NoteDisDao;
import com.wikestudy.model.dao.StuCourseDao;
import com.wikestudy.model.dao.StuScheduleDao;
import com.wikestudy.model.dao.impl.CouRecommendDaoImpl;
import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.dao.impl.NoteDisDaoImpl;
import com.wikestudy.model.dao.impl.StuCourseDaoImpl;
import com.wikestudy.model.dao.impl.StuScheduleDaoImpl;
import com.wikestudy.model.pojo.CouRecommend;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.StuCourse;

public class StudentCourseService {
	private Connection conn = null;
	private StuCourseDao scDao = null;
	private CouRecommendDao crDao = null;
	private CourseDao cDao = null;
	private StuScheduleDao ssDao = null;
	private NoteDisDao nDisDao = null;
	
	public StudentCourseService(Connection conn) {
		this.conn = conn;
		scDao = new StuCourseDaoImpl(this.conn);
		crDao = new CouRecommendDaoImpl(this.conn);
		cDao = new CourseDaoImpl(this.conn);
		ssDao = new StuScheduleDaoImpl(this.conn);
		nDisDao = new NoteDisDaoImpl(this.conn);
	}
	
	// 选课，如果重复选课，则忽略
	public int insertStuCourse(int stuId, int couId) 
		throws Exception {
		
		StuCourse stuCourse = new StuCourse();
		stuCourse.setCouId(couId);
		stuCourse.setStuId(stuId);
		stuCourse.setCouFinish(false);
		
		return scDao.insertStuCourse(stuCourse);
	}
	
	// 查看个人课程 
	// flag-标志: 1-被推荐的课程; 2-正在学习的课程; 3-已学完的课程
	public PageElem<Course> queryStuCourse(int stuId, int currentPage, 
			int flag, String grade) throws Exception {
		
		PageElem<StuCourse> pe = new PageElem<StuCourse>();
		pe.setCurrentPage(currentPage);
		pe.setPageShow(10);
		
		List<StuCourse> scList = null;
		
		PageElem<Course> peCourse = new PageElem<Course>();
		
		List<Integer> couIdList = new LinkedList<Integer>();
		
		if (flag == 1) {
			List<CouRecommend> crList = crDao.queryCouRecommendByGrade(grade);
			
			for (CouRecommend cr : crList) 
				couIdList.add(cr.getCouId());
			
			
		} else if (flag == 2){
			scList = scDao.queryStuCourseSort(stuId, pe, true).getPageElem();
			
			 for (StuCourse sc:scList) 
				 couIdList.add(sc.getCouId());
			 
		} else {
			scList = scDao.queryStuCourseSort(stuId, pe, false).getPageElem();
			 for (StuCourse sc:scList) 
				 couIdList.add(sc.getCouId());
			 
		}
		
		// 如果couIdList 的长度为0，则peCourse的 getPageElem is null
		
		peCourse.setCurrentPage(currentPage);
		peCourse.setPageShow(pe.getPageShow());
		peCourse.setRows(pe.getRows());
		peCourse = cDao.queryCourseStu(couIdList, peCourse);
		
		return peCourse;
	}
	
	// 学生删除个人已学课程，删除对应的观看记录和做题记录，但不删除话题与笔记
	public int delStuCourse(int stuId, int couId) {
		// 删除学习记录 
		if (ssDao.delStuSchByStuIdAndCouId(stuId, couId) != -1 &&
		// 删除已选课程
		scDao.delStuCourseByStuIdAndCouId(stuId, couId) != -1 && 
		
		nDisDao.delNoteByCouId(couId) != -1)
			return 1;
		
		
		return -1;
	}
	
	
	public List<Course> queryStuAll(int stuId) throws Exception {
		
		
		
		return scDao.queryAllStuCourse(stuId);
	}
}
