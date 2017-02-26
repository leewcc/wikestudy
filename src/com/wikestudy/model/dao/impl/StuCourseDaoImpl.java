package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.wikestudy.model.dao.StuCourseDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.StuCourse;
import com.wikestudy.model.pojo.StuSchedule;

public class StuCourseDaoImpl implements StuCourseDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	
	public StuCourseDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	public List<Course> queryAllStuCourse(int stuId) throws Exception {
		String sql = "SELECT c.cou_id, c.cou_name, c.cou_pric_url FROM t_course as c, t_stu_course as sc WHERE sc.stu_id = ? AND sc.cou_id = c.cou_id";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, stuId);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Course> list = new LinkedList<Course>();
		
		while (rs.next()) {
			Course course = new Course();
			
			course.setCouId(rs.getInt(1));
			course.setCouName(rs.getString(2));
			course.setCouPricUrl(rs.getString(3));
			
			list.add(course);
		}
		
		
		rs.close();
		pstmt.close();
		
		return list;
	}
	
	@Override
	public int isSelected(int stuId, int couId) {
		
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(stu_cou_id) AS rows FROM t_stu_course "
					+ " WHERE stu_id = ? AND cou_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, stuId);
			pstmt.setInt(2, couId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("rows");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && pstmt != null) {
					rs.close();
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return 0;
	}
	
	@Override
	public int insertStuCourse(StuCourse stuCourse)  {
		String sql = "INSERT IGNORE INTO t_stu_course (stu_id, cou_id, cou_finish) "
				+ "VALUES (?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(2, stuCourse.getCouId());
			pstmt.setInt(1, stuCourse.getStuId());
			pstmt.setBoolean(3, stuCourse.isCouFinish());
			
			return new GenneralDbconn<StuCourse>().update(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return -1;
	}

	@Override
	public int delStuCourse(int couId) {
		String sql = "DELETE  FROM t_stu_course WHERE cou_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	@Override
	public int delStuCourseByStuIdAndCouId(int stuId, int couId) {
		String sql = "DELETE FROM t_stu_course WHERE stu_id = ? AND cou_id = ?";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, stuId);
			pstmt.setInt(2, couId);
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return -1;
	}

	@Override
	public PageElem<StuCourse> queryStuCourseAll(int stuId,
			PageElem<StuCourse> pe) throws Exception {
		GenneralDbconn<StuCourse> gdb = new GenneralDbconn<StuCourse>();
		
		String sqlF = "SELECT COUNT(cou_id) as rows FROM t_stu_course "
				+ "WHERE stu_id=?";
		
		pstmt = conn.prepareStatement(sqlF);
		
		pe.setRows(gdb.getRows(pstmt));
		
		String sqlS = "SELECT * FROM t_stu_course WHERE stu_id=? LIMIT ?, ?";
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setInt(1, stuId);
		pstmt.setInt(2, pe.getStartSearch());
		pstmt.setInt(3, pe.getPageShow());
		
		pe.setPageElem(gdb.query(StuCourse.class, pstmt));
		
		return pe;
	}

	@Override
	public PageElem<StuCourse> queryStuCourseSort(int stuId,
			PageElem<StuCourse> pe, boolean flag) throws Exception {
		GenneralDbconn<StuCourse> gdb = new GenneralDbconn<StuCourse>();
		
		String sqlF = null;
		String sqlS = null;
		// 根据正在学的课程查找
		if (flag == true) {
			sqlF = "SELECT COUNT(cou_id) as rows  FROM t_stu_course "
					+ "WHERE stu_id=? ";
			sqlS = "SELECT * FROM t_stu_course WHERE stu_id=?  LIMIT ?,?";
		}
		else {
			sqlF = "SELECT COUNT(cou_id) as rows FROM t_stu_course "
					+ "WHERE stu_id=? ";		
			sqlS = "SELECT * FROM t_stu_course WHERE stu_id=?  LIMIT ?,?";
		}
		
		pstmt = conn.prepareStatement(sqlF);
		
		pstmt.setInt(1, stuId);
		System.out.println(pstmt);
		pe.setRows(gdb.getRows(pstmt));
		
		if (pe.getCurrentPage() < 0 || pe.getCurrentPage() > pe.getTotalPage()) {
			System.out.println("StuCourseDaoImpl queryStuCourseSort当前页数错误");
			pe.setCurrentPage(1);
			pe.setRows(1);
		}
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setInt(1, stuId);
		pstmt.setInt(2, pe.getStartSearch());
		pstmt.setInt(3, pe.getPageShow());
		System.out.println(pstmt);
		pe.setPageElem(gdb.query(StuCourse.class, pstmt));
		
		return pe;
	}
	
	@Override
	public StuCourse queryStuScheduleOnly(int stuId, int couId) 
			throws Exception {
		GenneralDbconn<StuCourse> gdb = new GenneralDbconn<StuCourse>();
		
		String sql = "SELECT * FROM t_stu_course WHERE stu_id = ? AND cou_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, stuId);
		pstmt.setInt(2, couId);
		
		List<StuCourse> stuCourseList = gdb.query(StuCourse.class, pstmt);
		if (stuCourseList == null || stuCourseList.size() == 0) 
			return null;
		
		
		return stuCourseList.get(0); // 如果没有选课, 则 return null
		
	}

}
