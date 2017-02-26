package com.wikestudy.servlet.manager.course;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.RelCourseManageService;

/**
 * 删除未发布的课程
 */
@WebServlet("/dist/jsp/manager/course/non_rel_course_del")
public class DelNonRelCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelNonRelCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		Integer couId = null;
		
		try {
			String s = request.getParameter("couId");
			if (s == null) {
				System.out.println("DelNonRelCourse couIdS is null");
				System.exit(1);
			}
				
			
			couId = Integer.parseInt(s);
			
		} catch (NumberFormatException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		
	
		Connection conn = null;
		RelCourseManageService rcms = null;
		
		try {
			conn = DBSource.getConnection();
			
			conn.setAutoCommit(false);
			
			rcms = new RelCourseManageService(conn);
			
			if (rcms.delNonRelCouByCouId(couId) == -1)
				conn.rollback();
			
			request.getRequestDispatcher("").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		} finally {
			
				try {
					if (conn != null)
						conn.close();
					else {
						System.out.println("DelNonRelCourse conn is null");
					}
				} catch (SQLException e) {
					log.debug(e,e.fillInStackTrace());
					e.printStackTrace();
				}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
