package com.wikestudy.servlet.publicpart;

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

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.HomeCourseService;

/**
 * Servlet implementation class course_show
 */
@WebServlet("/dist/jsp/common/course_show")
// 选课课程查询页面
public class ShowCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		System.out.println("SHowCourseServlet");
		
		// 0-全部; 1-初一; 2-初二; 3-初三; 4-公众
		String grade = request.getParameter("grade");
		// 0-全部
		String labelId = request.getParameter("labelId");
		// 1-推荐; 2-最新; 3-最热;
		String type = request.getParameter("type");
		// 当前页面
		String page = request.getParameter("cp");
		
		System.out.println("grade:" + grade);
		if (grade == null) 
			grade = "0";
		if (labelId == null)
			labelId = "0";
		if (type == null)
			type = "2";
		if (page == null)
			page = "1";
		
		Connection conn = null;
		HomeCourseService hcs = null;
		PageElem<Course> peCourse = null;
		List<Label> labelList = null;
		try {
			conn = DBSource.getConnection();
			hcs = new HomeCourseService(conn);
			
			//if (type.equals("1"))
				
				//peCourse = hcs.queryCourseSugg(Integer.parseInt(page), Integer.parseInt(labelId), grade);
			//else 
			// 推荐课程功能查看已取消
			peCourse = hcs.queryCourse(Integer.parseInt(page), type, grade, Integer.parseInt(labelId));
			
			labelList = hcs.queryLabel();
			
			
			request.setAttribute("labelList", labelList);
			request.setAttribute("peCourse", peCourse);
			request.setAttribute("grade", grade);
			request.setAttribute("labelId", labelId);
			request.setAttribute("type", type);
			request.setAttribute("cp", peCourse.getCurrentPage());
			request.setAttribute("tp", peCourse.getTotalPage());
			request.getRequestDispatcher("select_courseff.jsp").forward(request, response);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
