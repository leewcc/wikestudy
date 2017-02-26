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
 * 撤销已经推荐的课程：每年升学都可能会改变，但是如果已经推荐给了学生，再撤销时学生的个人课表中该课是不会取消的
 */
@WebServlet("/dist/jsp/Manager/course/RecommCourseServletNon")
public class RecommCourseServletNon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommCourseServletNon() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		Integer couId = null;
		String couIdS = request.getParameter("couId");
		if (couIdS == null) {
			couId = -1;
		}
		try {
			couId = Integer.parseInt(couIdS);
		} catch (NumberFormatException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			couId = -1;
		}
		
		Connection conn = null;
		CourseManageService cms = null;  new CourseManageService(conn);
	
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
		
			if (couId != -1 && cms.cancelRecCou(couId) != 0) {
				request.setAttribute("URL", "");
				request.setAttribute("message", "取消推荐的课程成功");
				request.getRequestDispatcher("../../common/Success.jsp").forward(request, response);
			} else {
				request.setAttribute("URL", "");
				request.setAttribute("message", "取消推荐的课程失败");
				request.getRequestDispatcher("../../common/Error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
				try {
					if (conn != null)
						conn.close();
					else {
						System.out.println("RecommCourseServletNon conn is null");
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
