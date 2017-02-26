package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.StudentCourseService;

/**
 *  删除我的课程
 */
@WebServlet("/dist/jsp/student/course/stu_course_delete")

public class DeleteStuCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteStuCourse() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		
		Student user = (Student)request.getSession().getAttribute("s");
		
		String currentPage = request.getParameter("page");
		String type = request.getParameter("flag");
		Integer couId = null;
		
		
		try {
	       	couId = Integer.valueOf(request.getParameter("couId"));
	    } catch (Exception e) {
        	request.setAttribute("URL", "/wikestudy/dist/jsp/student/course/course_center?flag="+type+"page"+currentPage);
            request.setAttribute("message", "请求参数错误");
            request.getRequestDispatcher("../../common/Error.jsp").forward(request, response);
            return ;
	    }

		boolean flag = true;
        Connection conn = null;
        StudentCourseService scs = null;
		
		try {
            conn = DBSource.getConnection();
            scs = new StudentCourseService(conn);
            
            conn.setAutoCommit(false);
            
            if (flag != false && scs.delStuCourse(user.getStuId(), couId) != -1) {
            	conn.commit();
            	response.sendRedirect("course_center?flag=" + type + "&currentPage=" + currentPage);
            	return ;
            } else {
            	conn.rollback();
            	request.setAttribute("URL", "/wikestudy/WebRoot/Page/Student/StudentCourse/course_center?flag="+type+"page"+currentPage);
                request.setAttribute("message", "删除失败");
                request.getRequestDispatcher("../../common/Error.jsp").forward(request, response);
                return ;
            }
            
		} catch (Exception e) {
			
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
