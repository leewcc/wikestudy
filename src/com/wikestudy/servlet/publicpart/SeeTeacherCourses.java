package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;
import com.wikestudy.service.manager.TeacherService;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/common/user_center/teacher_courses_see")
public class SeeTeacherCourses extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SeeTeacherCourses() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取用户id、页数
		int uid = Integer.parseInt(request.getParameter("id"));
		boolean mt = true;  //Boolean.parseBoolean(request.getParameter("type"));
		int cp = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			
				
		//第二步：	获取个人信息,如果看的是自己，则跳转自己的主页
		HttpSession session = request.getSession();
		if (mt) {
			if (session.getAttribute("t") != null && ((Teacher) session.getAttribute("t")).getTeaId() == uid) {
				response.sendRedirect("../../teacher/common/my_courses_query");
				return;
			}
		}
		
		
		// 第二步： 初始化数据库连接、服务
		Connection conn = null;
		CourseManageService cms = null;
		TeacherService ts = null;
		StudentService ss = null;
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
			ts = new TeacherService(conn);
			ss = new StudentService(conn);
			
					
		//第三步：	获取用户信息
			if(mt){
				request.setAttribute("user", ts.queryOneTeacher(uid));
			}else {
				request.setAttribute("user", ss.getStudent(uid));
			}
			
			
		//第四步：	获取我的课程
			PageElem<Course> pe = cms.queryCouListByTea(uid, 2, cp);
					
					
			//第五步：	将课程set进去，转发到我的课程页面
					request.setAttribute("courses", pe);
					request.getRequestDispatcher("his_courses.jsp").forward(request, response);
				
				
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServletException();
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

}
