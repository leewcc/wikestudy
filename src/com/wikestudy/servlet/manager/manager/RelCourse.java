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
import com.wikestudy.service.manager.RelCourseManageService;

/**
 * 发布课程
 */
@WebServlet("/dist/jsp/manager/course/course/course")

public class RelCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		Integer couId = null;
		try {
			String s = request.getParameter("couId");
			if (s == null) {
				System.out.println("RelCourseServlet couIdS is null");
				System.exit(1);
			}
			couId = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			request.setAttribute("URL", "");
			request.setAttribute("message", "发布课程失败, 课程信息错误");
			request.getRequestDispatcher("../../common/Error.jsp").forward(request, response);
			return ;
		}
		
		Connection conn = null;
		RelCourseManageService rcms = null;
		
		try {
			conn = DBSource.getConnection();
			rcms = new RelCourseManageService(conn);
			if (rcms.relCourse(couId) == -1) {
				request.setAttribute("URL", "");
				request.setAttribute("message", "发布课程失败, 课程信息错误");
				request.getRequestDispatcher("../../common/Error.jsp").forward(request, response);
				return ;
			}else {
				request.setAttribute("URL", "");
				request.setAttribute("message", "发布课程成功");
				request.getRequestDispatcher("../../common/Success.jsp").forward(request, response);
			}
		}catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
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
		
	}

}
