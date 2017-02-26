package com.wikestudy.service.publicpart;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import com.wikestudy.model.dao.*;
import com.wikestudy.model.dao.impl.*;
import com.wikestudy.model.pojo.*;

public class HomeCourseService {
	
	private CourseDao cDao = null;
	private LabelDao lDao = null;
	private CouChapterDao ccDao = null;
	private CouSectionDao csDao =null;
	private NoteDisDao ndDao = null;
	private TopicDao tDao = null;
	private TeacherDao teaDao = null;
	private StuCourseDao scDao = null;
	private StuScheduleDao ssDao = null;

	public HomeCourseService(Connection conn) {
		cDao = new CourseDaoImpl(conn);
		lDao = new LabelDaoImpl(conn);
		ccDao = new CouChapterDaoImpl(conn);
		csDao =  new CouSectionDaoImpl(conn);
		ndDao = new NoteDisDaoImpl(conn);
		tDao = new TopicDaoImpl(conn);
		teaDao = new TeacherDaoImpl(conn);
		scDao = new StuCourseDaoImpl(conn);
		ssDao = new StuScheduleDaoImpl(conn);
	}
	
	public PageElem<Course> queryCourse(int page, String type, String grade, int labelId) 
		throws Exception {
		 PageElem<Course> pe = new PageElem<Course>();
		 pe.setCurrentPage(page);
		 // 设置每页展示的数目
		 pe.setPageShow(8);
		 pe = cDao.queryCourseSort(labelId, pe, type, grade);
		 
		 return pe;
	}
	
	public List<Label> queryLabel() throws Exception {
		List<Label> labelList = new LinkedList<Label>();
		
		labelList = lDao.queryLabelAll();
		
		return labelList;
	}
	
	public PageElem<Course> queryCourseSugg(int page, int labelId, String grade)
		throws Exception {
		 PageElem<Course> pe = new PageElem<Course>();
		 pe.setCurrentPage(page);
		 // 设置每页展示的数目
		 pe.setPageShow(8);
		 
		 pe = cDao.queryCourseSugg(pe, labelId, grade);
		 
		 return pe;
	}
	
	public Course queryCouByCouId(int couId) throws Exception {
		Course cou = null;
		
		cou = cDao.queryCourseById(couId);
		
		return cou;
	}
	
	public List<ChapSec> queryChapSecByCouId(int couId)
		throws Exception {
		// 返回章节和课程信息
		List<ChapSec> csList = new LinkedList<ChapSec>();
		List<CouChapter> ccList = ccDao.queryCouChapter(couId);
		
		List<CouSection> couSecList = null;
		ChapSec chapSec = null;
		for (CouChapter couChapter:ccList) {
			couSecList = csDao.queryCouSectionByCha(couChapter.getChaId());
			chapSec = new ChapSec();
			chapSec.setCouChapter(couChapter);
			chapSec.setCouSection(couSecList);
			csList.add(chapSec);
		}
		
		return csList;
	}
	
	public PageElem<DiscussView> queryDissView(int couId, int page) {
		// 查询讨论
		PageElem<DiscussView> pe = new PageElem<DiscussView>();
		
		pe.setCurrentPage(page);
		pe.setPageShow(7);
		
		pe = ndDao.queryDisByCouId(couId, pe);
		
		return pe;
	}
	
	public PageElem<TopicView> queryTopicView(int couId, int page) {
		PageElem<TopicView> pe = new PageElem<TopicView>();
		
		pe.setCurrentPage(page);
		pe.setPageShow(7);
		
		TopicView tv = null;
		
		return pe;
	}
	
	public Teacher queryTea(int teaid) throws Exception {
		return teaDao.queryTeacher(teaid);
	}
	
	// 查询该课程是否已被学习
	public StuCourse queryStuCourseByStu(int stuId, int couId) throws Exception {
		
		return scDao.queryStuScheduleOnly(stuId, couId);
	}
	
	// 更新选课信息:注意重复数据问题
	public int insertStuCourse(int stuId, int couId) {
		StuCourse stuCourse = new StuCourse();
		
		stuCourse.setCouId(couId);
		stuCourse.setStuId(stuId);
		stuCourse.setCouFinish(false);
		
		return scDao.insertStuCourse(stuCourse);
	}

	public int insertStuSchedule(int stuId, int couId) {

		List<CouSection> couSections = csDao.queryCouSection(couId);
		StuSchedule stuSchedule = null;
		for (CouSection cs: couSections) {
			stuSchedule = new StuSchedule();
			stuSchedule.setChaId(cs.getChaId());
			stuSchedule.setSecId(cs.getSecId());
			stuSchedule.setSecCondition("1");
			stuSchedule.setStuId(stuId);
			stuSchedule.setSecExam(false);
			stuSchedule.setSecTime("");
			if (ssDao.insertStuSchedule(stuSchedule) == -1) {
				return -1;
			}
		}

		return 1;
	}

	// 根据课程id查找课程相关话题
	public PageElem<Topic> queryTopicByCouId(int couId, int currentPage) {
		PageElem<Topic> pe = new PageElem<Topic>();
		
		pe.setCurrentPage(currentPage);
		pe.setPageShow(10);
	
		return tDao.queryTopicByCouId(couId, pe);
	}
}
