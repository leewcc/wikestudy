package com.wikestudy.service.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wikestudy.model.dao.CouChapterDao;
import com.wikestudy.model.dao.CouRecommendDao;
import com.wikestudy.model.dao.CouSectionDao;
import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.LabelDao;
import com.wikestudy.model.dao.StuCourseDao;
import com.wikestudy.model.dao.TeacherDao;
import com.wikestudy.model.dao.impl.CouChapterDaoImpl;
import com.wikestudy.model.dao.impl.CouRecommendDaoImpl;
import com.wikestudy.model.dao.impl.CouSectionDaoImpl;
import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.dao.impl.LabelDaoImpl;
import com.wikestudy.model.dao.impl.StuCourseDaoImpl;
import com.wikestudy.model.dao.impl.TeacherDaoImpl;
import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.CouRecommend;
import com.wikestudy.model.pojo.CouSection;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.CourseInfor;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.StuCourse;
import com.wikestudy.model.pojo.Teacher;

public class CourseManageService {
	private Connection conn = null;
	private CourseDao cDao = null;
	private CouChapterDao ccDao = null;
	private CouSectionDao csDao = null;
	private CouRecommendDao crDao = null;
	private TeacherDao tDao = null;
	private StuCourseDao scDao = null;
	private LabelDao lDao = null;
	
	
	private final int managerQueryCoursePage = 9;  // 管理员课程查询页面的PageShow 
	
	public CourseManageService(Connection conn) {
		this.conn = conn;
		cDao = new CourseDaoImpl(this.conn);
		ccDao = new CouChapterDaoImpl(this.conn);
		csDao = new CouSectionDaoImpl(this.conn);
		crDao = new CouRecommendDaoImpl(this.conn);
		tDao = new TeacherDaoImpl(this.conn);
		scDao = new StuCourseDaoImpl(this.conn);
		lDao = new LabelDaoImpl(this.conn);
	}
	
	// 创建课程
	public int createCourse(Course cou)  {
		return cDao.insertCourse(cou);
	}
	
	// 创建课程时-拿取标签类型
	public List<Label> queryLabel() {
		
		return lDao.queryLabelAll();
	}
	
	// 管理员管理课程时的课程查询页面
	public PageElem<Course> queryCouListByTea(int teacherId, int type, int page)  {
		PageElem<Course> pe = new PageElem<Course>();
		
		pe.setCurrentPage(page);
		
		pe.setPageShow(12);
		
		return cDao.queryCourseTea(teacherId, type, pe);
	}
	
	// 管理员管理课程——详细信息
	public CourseInfor queryCouByCouId(int couId) throws Exception {
		// 管理课程： 课程详细信息页面
		
		Course cou =  cDao.queryCourseById(couId);
		if (cou == null) {
			// 未创建课程
			return null;
		}
		// 拿去章节列表
		List<CouChapter> couChapterList =  ccDao.queryCouChapter(couId);
		// 拿去课时列表
		List<CouSection> couSectionList = csDao.queryCouSection(couId);
		
		
		
		CourseInfor couInfor = new CourseInfor();
		
		couInfor.setCou(cou);
		couInfor.setCouChapterList(couChapterList);
		couInfor.setCouSectionList(couSectionList);
		
		return couInfor;
	}
	
	// 推荐课程
	public int recommCourse(int couId, String recGrade) throws Exception {
		CouRecommend couRecomment = new CouRecommend();
		couRecomment.setCouId(couId);
		couRecomment.setRecGrade(recGrade);
		return crDao.insertCouRecommend(couRecomment);
	}
	
	// 取消推荐课程
	public int cancelRecCou(int couId) throws Exception{
		return crDao.delCouRecommend(couId);
	}
	
	// 发布课程
	public int releaseCou(int couId) throws Exception {
		return cDao.updateCouRelease(couId, true);
	}
	

	// 根据老师id查询老师信息
	public Teacher queryTeacherInfor(int teaId) throws Exception {
		return tDao.queryTeacher(teaId);
	}
	
	public StuCourse queryStuCourse(int couId, int stuId) throws Exception {
		return scDao.queryStuScheduleOnly(stuId, couId);
	}
	
	
	// 管理员管理课程信息的页面——更改课程信息, 章节信息,课时信息
	public List<Integer> updateCouAll(Course cou, List<CouChapter> upChaList,List<CouSection> upSecList,
			List<CouChapter> inChaList, List<CouSection> inSecList,
			List<Integer> delChaIdList, List<Integer> delSecIdList) {
		
		 List<Integer> list = new ArrayList<Integer>();
		 
		 if (cDao.updateCourse(cou.getCouId(), cou) == -1)
			 return null;
		 if (ccDao.updateCouChapterS(upChaList) == null)
			 return null;
		 
		 if (csDao.updateCouSectionS(upSecList) == null)
			 return null;
		 
		 // 要同时为新建的章节创建对应的文件夹; 且每个inChaList都均有一个 list (chaId)相对应
		 if ((list = ccDao.insertCouChaAll(inChaList)) == null)
			 return null;
		 
		 // 只有那些章节与课时均为新建的情况下，才去检测该情况
		 for(int i = 0; i < inSecList.size() && inSecList.get(i).getChaId() == -1; i++) {
			 int chaNumber = inSecList.get(i).getChaNumber();
			 // 默认inChaList的chaNumber顺序是从小到大
			 for (int j = 0; j < inChaList.size(); j++) {
				 if (chaNumber == inChaList.get(j).getChaNumber()) {
					 inSecList.get(i).setChaId(list.get(j));
					 break;
				 }
			 }
		 }
		 
		 if (csDao.insertCouSecList(inSecList) == -1)
			 return null;
		 
		 // 删除对应的文件夹
		 if (ccDao.delCouChapter(delChaIdList) == null)
			 return null;
		 if (csDao.delCouSecBySecList(delSecIdList) == null)
			 return null;
		 
		 return list;
		 
	}
	
	public CouChapter queryCouChapterById(int chaId) throws Exception{
		return ccDao.queryCouChapterById(chaId);
	}
	
	public CouSection queryCouSectionById(int secId) throws Exception {
		return csDao.queryCouSectionBySec(secId);
	}
	
	public List<CouSection> queryCouSectionByCha(int chaId) throws Exception{
		return csDao.queryCouSectionByCha(chaId);
	}

	public void queryCouMedia( int chaId, int secId, 
			CouChapter cc, CouSection cs) throws Exception {
		csDao.queryCouMedia( chaId, secId, cc, cs);
	} 
	
}
