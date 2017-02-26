package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.HomeCourseService;

/**
 * Ajax学生选修课程。增加学生课程表, 增加选课人数
 */
@WebServlet("/dist/jsp/common/course_selectT")
public class SelectCourseT extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		
		Integer  couId = null;
		Student student = (Student) request.getSession().getAttribute("s");
		Teacher teacher = (Teacher) request.getSession().getAttribute("t");
		
		boolean flag = true;
		String message = null;
		String url = null;
		if (student == null && teacher == null) {
			message = "用户当前未登录";
			url = "/wikestudy/dist/jsp/student/account/student_login";
			flag = false;
		}
		try {
			couId = Integer.parseInt((String) request.getParameter("couId"));
		} catch (Exception e) {
			message = "请求参数错误";
			url = "/wikestudy/dist/jsp/common/course_show?grade=0&labelId=0&type=3";
			flag = false;
		}
		if (!flag) {
			request.setAttribute("message", message);
			request.setAttribute("URL", url);
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return ;
		}
		HomeCourseService hcs = null;
		Connection conn = null;
		try {
			conn = DBSource.getConnection();
			conn.setAutoCommit(false);
			
			hcs = new HomeCourseService(conn);
			if( hcs.insertStuCourse(student.getStuId(), couId) == -1 
					|| new CourseDaoImpl(conn).updateStudyNum(couId) == -1
					|| hcs.insertStuSchedule(student.getStuId(), couId) == -1) {
				conn.rollback();
			}
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}

		request.getRequestDispatcher("course_select?couId="+couId).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
