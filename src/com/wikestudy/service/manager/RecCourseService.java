package com.wikestudy.service.manager;

import java.sql.Connection;

import com.wikestudy.model.dao.CouRecommendDao;
import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.impl.CouRecommendDaoImpl;
import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.pojo.CouRecommend;
import com.wikestudy.model.pojo.Course;

public class RecCourseService {
	private CouRecommendDao crd = null;
	CourseDao cd = null;
	private Connection conn = null;
	
	public RecCourseService(Connection conn) {
		this.conn = conn;
		crd = new CouRecommendDaoImpl(conn);
		cd = new CourseDaoImpl(conn);
	}
	
	public int recomment(int id) throws Exception{
		Course c = cd.queryCourseById(id);
		
		if(c != null && c.isRecomment()){
			return crd.delCouRecommend(id);
		}else{
			CouRecommend cr = new CouRecommend();
			cr.setCouId(id);
			cr.setRecGrade(c.getCouGrade());
			return crd.insertCouRecommend(cr);
		}
	}
}
