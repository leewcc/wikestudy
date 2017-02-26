package com.wikestudy.servlet.manager.course;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.enums.Role;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;

/**
 * Servlet implementation class ChangeCourseServletFir
 */
// 课程管理的第一步——课程查看，根据老师id返回课程信息
@WebServlet("/dist/jsp/manager/course/course_fir_change")
public class ChangeCourseFir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ChangeCourseFir() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger l = new Log4JLogger("log4j.properties");
		Integer type = null;
		Integer page = null;
		try {
			String typeS = request.getParameter("type");
			type = Integer.parseInt(typeS);
		} catch (Exception e) {
			type = 1;
			l.debug(e,e.fillInStackTrace());
		}
		
		try {
			String pageS = request.getParameter("page");
			page = Integer.parseInt(pageS);
		} catch (Exception e) {
			page = 1;
			l.debug(e,e.fillInStackTrace());
		}
		
		
		Teacher tea = (Teacher) request.getSession().getAttribute("t");
		Role role = (Role) request.getSession().getAttribute("role");
		
		int teacherId = tea.getTeaId();
		Connection conn = null;
		CourseManageService cms = null;
		PageElem<Course>  pe = null;
		
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);

			pe = cms.queryCouListByTea(teacherId, type, page);
			if (pe == null) {
				System.out.println("course_fir_change pe is null");
				System.exit(1);
			}
			
			request.setAttribute("type", "" + type);
			request.setAttribute("pe", pe);
			request.getRequestDispatcher("course_check.jsp").forward(request, response);
			
		} catch (Exception e) {
			l.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) 
					conn.close();
			} catch (SQLException e) {
				l.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
