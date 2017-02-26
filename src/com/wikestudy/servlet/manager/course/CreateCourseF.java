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

import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;

/**
 * Servlet implementation class create_course_f
 */
@WebServlet("/dist/jsp/teacher/course/create_course_f")
// 创建课程的前一步- 拿取所以标签类型
public class CreateCourseF extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCourseF() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		CourseManageService cms = null;
		List<Label> labelList = null;
		Log4JLogger log = new Log4JLogger("log4j.properties");
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
			labelList = cms.queryLabel();
			if (labelList == null) {
				System.out.println("create_course_f labelList is null");
				System.exit(1);
			}
			request.setAttribute("imageUrl", (String)request.getAttribute("imageUrl"));
			request.setAttribute("labels", labelList);
			request.getRequestDispatcher("course_create.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("create_course_f 数据库连接异常");
			log.debug(e,e.fillInStackTrace());
		} finally {
			try {
				if (conn != null)
					conn.close();
				else 
					System.out.println("course_create conn is null");
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
