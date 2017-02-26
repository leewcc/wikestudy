package com.wikestudy.servlet.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.StudentCourseService;

/**
 * Servlet implementation class QueryStuCourse
 */
@WebServlet("/QueryStuCourse")
public class QueryStuCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryStuCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		Integer stuId = null;
		
		try {
			stuId = Integer.parseInt(request.getParameter("stuId"));
		} catch (Exception e) {
			
		}
		
		StudentCourseService scs = null;
		Connection conn = null;
		List<Course> list = null;
		try {
			conn = DBSource.getConnection();
			scs = new StudentCourseService(conn);
			list = scs.queryStuAll(stuId);
			
			
			request.setAttribute("courses", list);
			request.getRequestDispatcher("").forward(request, response);
			return ;
		} catch (Exception e) {
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
