package com.wikestudy.service.manager;


import java.sql.Connection;
import java.sql.SQLException;

import com.wikestudy.model.dao.AnswerDao;
import com.wikestudy.model.dao.CouChapterDao;
import com.wikestudy.model.dao.CouRecommendDao;
import com.wikestudy.model.dao.CouSectionDao;
import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.DataDao;
import com.wikestudy.model.dao.NoteDisDao;
import com.wikestudy.model.dao.QuestionDao;
import com.wikestudy.model.dao.StuCourseDao;
import com.wikestudy.model.dao.StuScheduleDao;
import com.wikestudy.model.dao.TopicDao;
import com.wikestudy.model.dao.impl.AnswerDaoImpl;
import com.wikestudy.model.dao.impl.CouChapterDaoImpl;
import com.wikestudy.model.dao.impl.CouRecommendDaoImpl;
import com.wikestudy.model.dao.impl.CouSectionDaoImpl;
import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.dao.impl.DataDaoImpl;
import com.wikestudy.model.dao.impl.NoteDisDaoImpl;
import com.wikestudy.model.dao.impl.QuestionDaoImpl;
import com.wikestudy.model.dao.impl.StuCourseDaoImpl;
import com.wikestudy.model.dao.impl.StuScheduleDaoImpl;
import com.wikestudy.model.dao.impl.TopicDaoImpl;


/**
 * 管理已发布的课程
 * @author tianx
 *
 */
public class RelCourseManageService {
	private Connection conn;
	
	private CourseDao cDao;
	private CouSectionDao csDao;
	private CouChapterDao ccDao;
	
	private CouRecommendDao crDao;
	
	private DataDao dDao;
	private NoteDisDao ndDao;
	
	
	private TopicDao tDao;
	
	private StuScheduleDao ssDao;
	private StuCourseDao scDao;
	
	private QuestionDao qDao;
	private AnswerDao aDao;
	
	public RelCourseManageService(Connection conn) {
		this.conn = conn;
		
		cDao  = new CourseDaoImpl(conn);
		ccDao = new CouChapterDaoImpl(conn);
		csDao = new CouSectionDaoImpl(conn);
		
		crDao = new CouRecommendDaoImpl(conn);
		
		dDao  = new DataDaoImpl(conn);
		ndDao = new NoteDisDaoImpl(conn);
		tDao  = new TopicDaoImpl(conn);
		
		ssDao = new StuScheduleDaoImpl(conn);
		scDao = new StuCourseDaoImpl(conn);
		
		qDao  = new QuestionDaoImpl(conn);
		aDao  = new AnswerDaoImpl(conn);
		
	}
	
	
	public int delReleCou(int couId) throws SQLException {
		
		int a;// 删除答案
		if ((a = aDao.delAnsByCouId(couId)) == 0) {
			System.out.println("该课程下不存在相关answer ");
		}

		
		int q;// 删除测试
		if ((q = qDao.delQuesByCouId(couId)) == 0) {
			System.out.println("该课程下不存在相关question ");
		}

		
		int sc;// 删除学生个人课程
		if ((sc = scDao.delStuCourse(couId)) == 0) {
			System.out.println("该课程未被选择 ");
		}

		
		int ss;// 删除学生个人课程进度表
		if ((ss = ssDao.delStuSchByCouId(couId)) == 0) {
			System.out.println("该课程下不存在相关学生学习进度 ");
		}
		
		
		int nd;// 删除课程相关笔记
		if ((nd = ndDao.delNoteByCouId(couId)) == 0) {
			System.out.println("该课程下不存在相关笔记和评论 ");
		}

		
		int d;// 删除课程相关资料和视频
		if ((d = dDao.delCouDataAll(couId)) == 0) {
			System.out.println("该课程下不存在相关资料和视频 ");
		}

		
		int cr;// 删除推荐课程
		if ((cr = crDao.delCouRecommend(couId)) == 0) {
			System.out.println("该课程未被推荐 ");
		}

		int cs;// 删除课时
		if ((cs = csDao.delCouSecByCouId(couId)) == 0) {
			System.out.println("该课程下不存在相关课时 ");
		}

		
		int cc;// 删除章节
		if ((cc = ccDao.delCouChaByCouId(couId)) == 0) {
			System.out.println("该课程下不存在相关章节 ");
		}

		
		int c;// 删除课程
		if ((c = cDao.delCourse(couId)) == 0) {
			System.out.println("该课程下不存在相关course： 操作错误 ");
		}

		if (a < 0 || q < 0 || sc < 0 || ss < 0 || 
				nd < 0 || d < 0 || cr < 0 || cs < 0 || cc < 0 || c < 0) {
			return  -1;
		}
		
		return 1;
	}

	// 删除未发布课程的相关信息: 课程，章节，课时，资料
	public int delNonRelCouByCouId(int couId) {
		if (dDao.delCouDataAll(couId) == -1)
			return -1;
		if (csDao.delCouSecByCouId(couId) == -1)
			return -1;
			
		if (ccDao.delCouChaByCouId(couId) == -1)
			return -1;
		
		if (cDao.delCourse(couId) == -1)
			return -1;
		
		return 1;
	}
	
	
	// 发布课程
	public int relCourse (int couId) {
		
		return cDao.updateCouRelease(couId, true);
	}
	
	
	
	// 如何对发布的课程进行信息的修改？？？
}
