package com.wikestudy.service.publicpart;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wikestudy.model.dao.CouChapterDao;
import com.wikestudy.model.dao.CouSectionDao;
import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.DataDao;
import com.wikestudy.model.dao.TeacherDao;
import com.wikestudy.model.dao.impl.CouChapterDaoImpl;
import com.wikestudy.model.dao.impl.CouSectionDaoImpl;
import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.dao.impl.DataDaoImpl;
import com.wikestudy.model.dao.impl.TeacherDaoImpl;
import com.wikestudy.model.pojo.ChapterView;
import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.CouSection;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.CourseView;
import com.wikestudy.model.pojo.Data;
import com.wikestudy.model.pojo.DataView;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.SectionView;
import com.wikestudy.model.pojo.Teacher;

public class DataService {
	private Connection conn;
	private DataDao dd;
	private TeacherDao td;
	private CourseDao cd;
	private CouChapterDao ccd;
	private CouSectionDao csd;
	private final int showNum = 10;
	
	public DataService(Connection conn) {
		this.conn = conn;
		dd = new DataDaoImpl(this.conn);
		td = new TeacherDaoImpl(this.conn);
		cd = new CourseDaoImpl(this.conn);
		ccd = new CouChapterDaoImpl(this.conn);
		csd = new CouSectionDaoImpl(this.conn);
	}
	
	public int updateDownload(int id) throws Exception {
		return dd.updateDownLoad(id, 1);
	}
	
	public int insert(Data d) throws Exception{
		int id = dd.addData(d);
		if(id > 0){
			d.setDataId(id);
			return id;
		}else
			return 0;
	}
	
	public int update(Data d) throws Exception{
		return dd.updateName(d.getDataId(), d.getDataName());
	}
	
	
	public int delete(Data d)throws Exception{
		File file = new File(d.getDataRoot());
		
		if(file.exists()){
			file.delete();
			return dd.deleteData(d.getDataId());
		}
		
		return dd.deleteData(d.getDataId());
	}
	
	
	public DataView query(int id, boolean type) throws Exception {
		DataView dv = new DataView();
		int cid = 0;
		if(type){
			CouChapter cc = ccd.queryCouChapterById(id);
			
			if(cc == null)
				return null;
			cid = cc.getCouId();
			dv.setBindingName(cc.getChaName());
			
		}else{
			CouSection cs = csd.queryCouSectionBySec(id);
			
			if(cs == null)
				return null;
			
			CouChapter cc = ccd.queryCouChapterById(cs.getChaId());
			if(cc == null)
				return null;
			
			cid = cc.getCouId();
			dv.setBindingName(cs.getSecName());
		}
		
		Course c = cd.queryCourseById(cid);
		if(c == null)
			return null;
		
		Teacher t = td.queryTeacher(c.getTeacherId());
		List<Data> datas = queryByBinding(id, type);
		dv.setCourse(c);
		dv.setTeacher(t);
		dv.setDatas(datas);
		return dv;
	}
	
//	//获取课时的资料目录
//	public CourseView getCourseData(int courseId)throws Exception{
//		CourseView couV = new CourseView();
//		
//		//第一步：	根据课程id获取课程
//		Course c = cd.queryCourseById(courseId);
//		
//		
//		//第二步：	判断课程是否获取成功,失败则返回null
//		//					若存在则继续填充课程的资料信息
//		if(null == c)
//			return null;
//		
//		
//		//第三步：	获取课程负责的老师
//		Teacher t = td.queryTeacher(c.getTeacherId());
//		
//		
//		//第四步：	获取课程的章节
//		List<CouChapter> chaps = ccd.queryCouChapter(courseId);
//		
//		
//		//第五步：	初始化章节、课时等变量
//		List<ChapterView> cvL = new ArrayList<ChapterView>();
//		List<SectionView> svL = null;
//		CouChapter chap = null; 						// 记录章节
//		CouSection sec = null; 							// 记录课时
//		List<CouSection> csL = null; 				// 记录章节下的课时
//		List<Data> chaDatas = null; 				// 记录章节下的资料
//		ChapterView cv = null; 							// 章节视图对象
//		SectionView sv = null; 							// 课时视图对象
//		List<Data> secDatas = null; 				// 记录课时下的资料
//		
//		
//		//第六步：	迭代章节，获取章节下的课时和资料
//		Iterator<CouChapter> it = chaps.iterator();
//		while(it.hasNext()){
//			cv = new ChapterView();
//			chap = it.next();
//			csL = csd.queryCouSectionByCha(chap.getChaId());		
//			
//		//第七步：	迭代课时，获取课时下的资料
//			svL = new ArrayList<SectionView>();
//			Iterator<CouSection> cit = csL.iterator();
//			while(cit.hasNext()){
//				sv = new SectionView();
//				sec = cit.next();
//				secDatas = dd.queryByBinding(sec.getSecId(), true);
//				
//				
//		//第八步：	将获取回来的课时数据set进课时视图对象中
//				sv.setSection(sec);
//				sv.setDatas(secDatas);
//				svL.add(sv);
//			}
//			
//			
//		//第九步：	将获取回来的章节数据set进章节视图对象中
//			cv.setChapter(chap);
//			cv.setDatas(chaDatas);
//			cv.setSections(svL);
//			cvL.add(cv);
//		}
//		
//		
//		//第十步：将获取回来的课时数据set进课程视图对象中
//		couV.setCourse(c);
//		couV.setChapters(cvL);
//		couV.setTea(t);
//		return couV;
//					
//	}
	
	//获取课时的资料目录
	public CourseView getCourseCata(int courseId)throws Exception{
		CourseView couV = new CourseView();

		// 第一步： 根据课程id获取课程
		Course c = cd.queryCourseById(courseId);

		// 第二步： 判断课程是否获取成功,失败则返回null
		// 若存在则继续填充课程的资料信息
		if (null == c)
			return null;

		// 第三步： 获取课程负责的老师
		Teacher t = td.queryTeacher(c.getTeacherId());

		// 第四步： 获取课程的章节
		List<CouChapter> chaps = ccd.queryCouChapter(courseId);

		// 第五步： 初始化章节、课时等变量
		List<ChapterView> cvL = new ArrayList<ChapterView>();
		List<SectionView> svL = null;
		CouChapter chap = null; // 记录章节
		List<CouSection> csL = null; // 记录章节下的课时

		ChapterView cv = null; // 章节视图对象

		// 第六步： 迭代章节，获取章节下的课时和资料
		Iterator<CouChapter> it = chaps.iterator();
		while (it.hasNext()) {
			cv = new ChapterView();
			chap = it.next();
			csL = csd.queryCouSectionByCha(chap.getChaId());

			// 第九步： 将获取回来的章节数据set进章节视图对象中
			cv.setChapter(chap);
			cv.setSections(csL);
			cvL.add(cv);
		}

		// 第十步：将获取回来的课时数据set进课程视图对象中
		couV.setCourse(c);
		couV.setChapters(cvL);
		couV.setTea(t);
		return couV;

	}
	 
		
	public List<Data> queryByBinding( int binding, boolean type) throws Exception{
		return dd.queryByBinding(binding, type);
		
	}

	public Data queryById(int dataId) throws Exception{
		return dd.queryById(dataId);
	}
}
