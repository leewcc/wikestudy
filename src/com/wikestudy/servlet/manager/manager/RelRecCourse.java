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

import com.wikestudy.model.dao.RelCourse;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.QueryRelDelCourseService;

/**
 * 推荐，删除，发布课程
 */
@WebServlet("/dist/jsp/manager/course/rec_course_rel")
public class RelRecCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		Teacher tea = (Teacher)request.getSession().getAttribute("t");
		Integer currentPage = null;
		String type = request.getParameter("type"); // 1: 推荐课程; 2: 删除课程; 3:发布课程
		String flag = request.getParameter("flag"); // 1: 已推荐(已发布); 2: 未推荐(未发布)
		
		try {
			String currentPageS = request.getParameter("currentPage");
			if (currentPageS == null)
				currentPageS = "1";
			currentPage = Integer.parseInt(currentPageS);
		} catch (NumberFormatException e) {
			log.debug(e,e.fillInStackTrace());
			currentPage = 1;
		}
		
		System.out.println(currentPage);
		if (type == null) {
			type = "3";
		} else if (flag == null){
			flag = "2";
		}
		
		Connection conn = null;
		QueryRelDelCourseService qrdcs = null;
		
		PageElem<Course> peC = null;
		PageElem<RelCourse> peRC = null;
		String url = null;
		try {
			conn = DBSource.getConnection();
			qrdcs = new QueryRelDelCourseService(conn);
			
			if (type.equals("1")) {
				// 课程推荐与取消推荐
				if (flag.equals("1")) {
					// 查看已推荐的课程
					peRC = qrdcs.queryRecCourse(tea.getTeaId(), currentPage);
					url = "NonRecomCourse.jsp";
				}else {
					// 查看未推荐的课程
					peC = qrdcs.queryNonRecCourse(tea.getTeaId(), currentPage);
					url = "RecomCourse.jsp";
				}
			} else if (type.equals("2")) {
				// 查询已发布课程与未发布课程
				if (flag.equals("1")) {
					// 已发布课程
					peC = qrdcs.queryRelCourse(tea.getTeaId(), currentPage, (byte)1);
				} else {
					// 删除未发布课程
					peC = qrdcs.queryRelCourse(tea.getTeaId(), currentPage, (byte)0);
				}
				url = "DelCourse.jsp";
			} else if (type.equals("3")) {
				// 发布 未发布的课程
				peC = qrdcs.queryRelCourse(tea.getTeaId(), currentPage, (byte)0);
				url = "RelCourse.jsp";
			} else {
				// type is wrong
			}
			
			if (type.equals("1") && flag.equals("1"))
				request.setAttribute("pe", peRC);
			else
				request.setAttribute("pe", peC);
			
			request.setAttribute("type", type);
			request.setAttribute("flag", flag);
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e1) {
					log.debug(e1,e1.fillInStackTrace());
					e1.printStackTrace();
				}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
