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
 * 课程推荐
 */
@WebServlet("/dist/jsp/Manager/course/RecommCourseServlet")
public class RecommCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommCourseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Log4JLogger log = new Log4JLogger("log4j.properties");
	
		String couGrade = "4";   // 默认推荐给所有人
		Integer couId = null;
		try {
			couId = Integer.parseInt(request.getParameter("couId"));
		} catch (Exception e) {
			request.getRequestDispatcher("QueryCourse").forward(request, response);
			return ;
		}
		
		Connection conn = null;
		CourseManageService cms = null;
	
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
		
			if (cms.recommCourse(couId, couGrade) != 0) {
				request.getRequestDispatcher("QueryCourse").forward(request, response);
				return ;
			}
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
				try {
					if (conn != null)
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
		
	}

}
