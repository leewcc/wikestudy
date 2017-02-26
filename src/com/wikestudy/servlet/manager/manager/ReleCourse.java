package com.wikestudy.servlet.manager.manager;

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
import com.wikestudy.service.manager.CourseManageService;

/**
 * Servlet implementation class ReleCourseServlet
 */
@WebServlet("/course_rele")
// 课程发布与取消发布
public class ReleCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Log4JLogger log = new Log4JLogger("log4j.properties");

		Connection conn = null;
		CourseManageService cms = null;
		
		
		Integer couId = null;
		
		try {
			String couIdS = request.getParameter("couId");
			if (couIdS == null) {
				System.out.println("couIdS is null");
				throw new NullPointerException();
			}
			couId = Integer.parseInt(couIdS);
		} catch(NumberFormatException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			
			System.exit(1);
		}
		
		
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);

			int result = cms.releaseCou(couId);
			
			if (result == 0) {
				System.out.println("课程已发布");
			}
			
			
			request.getRequestDispatcher("课程发布列表").forward(request, response);
		}catch(Exception e) {
			log.debug(e,e.fillInStackTrace());
			System.out.println("课程推荐异常");
			e.printStackTrace();
			
			
		} finally {
			try{
				conn.close();
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
