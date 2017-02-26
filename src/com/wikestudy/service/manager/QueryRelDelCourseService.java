package com.wikestudy.service.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.RelCourse;
import com.wikestudy.model.dao.StuScheduleDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;

// 
public class QueryRelDelCourseService {
	private Connection conn;
	private StuScheduleDao ssDao = null;
	private CourseDao cDao = null;
	private PageElem<Course> peC;
	private PageElem<RelCourse> peRC;
	private GenneralDbconn<RelCourse> gdbRC;
	private GenneralDbconn<Course> gdbC;
	public QueryRelDelCourseService(Connection conn) {
		this.conn = conn;
		
		gdbRC = new GenneralDbconn<RelCourse>();
		peRC = new PageElem<RelCourse>();
		peRC.setPageShow(8);
		gdbC = new GenneralDbconn<Course>();
		peC = new PageElem<Course>();
		peC.setPageShow(8);
	}
	
	// 查看已推荐的课程
	public PageElem<RelCourse> queryRecCourse(int teaId, int currentPage) {
		String sqlF = "SELECT COUNT(c.cou_id) AS rows FROM t_course AS c, t_cou_recommend AS sr WHERE "
				+ " c.teacher_id = ? AND c.cou_id = sr.cou_id AND c.cou_release = 1";
		String sqlS = "SELECT c.cou_id,c.cou_pric_url,c.cou_name,sr.rec_grade FROM t_course AS c, t_cou_recommend AS sr WHERE "
				+ " c.teacher_id = ? AND c.cou_id = sr.cou_id AND c.cou_release = 1 LIMIT ?,?";
		
		peRC.setCurrentPage(currentPage);
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, teaId);
			System.out.println(pstmt);
			peRC.setRows(gdbRC.getRows(pstmt));
			
			pstmt = conn.prepareStatement(sqlS);
			pstmt.setInt(1, teaId);
			pstmt.setInt(2, peRC.getStartSearch());
			pstmt.setInt(3, peRC.getPageShow());
			System.out.println(pstmt);
			peRC.setPageElem(gdbRC.query(RelCourse.class, pstmt));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return peRC;
	}
	
	// 查询未推荐的课程（一个表有，另一个表没有）而且是已经发布的
	public PageElem<Course> queryNonRecCourse(int teaId, int currentPage) {
		String sqlF = "SELECT COUNT(t_course.cou_id) AS rows FROM t_course " +  
		"LEFT JOIN t_cou_recommend ON t_course.cou_id = t_cou_recommend.cou_id " + 
		"where t_cou_recommend.cou_id IS NULL AND t_course.teacher_id = ? AND t_course.cou_release = 1";
		
		
		String sqlS = "SELECT c.cou_id,c.cou_name,c.cou_pric_url, c.cou_study_num FROM t_course AS c "+
			" LEFT JOIN t_cou_recommend ON c.cou_id = t_cou_recommend.cou_id " +
				" where t_cou_recommend.cou_id IS NULL AND c.teacher_id = ? AND c.cou_release = 1 LIMIT ?,?";
		
		peC.setCurrentPage(currentPage);
		try {
			PreparedStatement pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, teaId);
			System.out.println(pstmt);
			peC.setRows(gdbC.getRows(pstmt));
			
			pstmt = conn.prepareStatement(sqlS);
			pstmt.setInt(1, teaId);
			pstmt.setInt(2, peC.getStartSearch());
			pstmt.setInt(3, peC.getPageShow());
			System.out.println(pstmt);
			peC.setPageElem(gdbC.query(Course.class, pstmt));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return peC;
	}
	
	// 查询已发布的课程  flag: 0 未发布; 1:已发布
	public PageElem<Course> queryRelCourse(int teaId, int currentPage, byte flag) {
		String sqlF = "SELECT count(cou_id) AS rows FROM t_course WHERE teacher_id = ? AND cou_release = ?";
		
		String sqlS = "SELECT cou_id, cou_name,cou_pric_url,cou_study_num AS rows FROM t_course "
				+ "WHERE teacher_id = ? AND cou_release = ? limit ?,?";
		
		peC.setCurrentPage(currentPage);
		try {
			PreparedStatement pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, teaId);
			pstmt.setByte(2, flag);
			System.out.println(pstmt);
			peC.setRows(gdbC.getRows(pstmt));
			
			
			pstmt = conn.prepareStatement(sqlS);
			pstmt.setInt(1, teaId);
			pstmt.setByte(2, flag);
			pstmt.setInt(3, peC.getStartSearch());
			pstmt.setInt(4, peC.getPageShow());
			System.out.println(pstmt);
			peC.setPageElem(gdbC.query(Course.class, pstmt));
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return peC;
	}
	
	
}
