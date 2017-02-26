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

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;
import com.wikestudy.service.student.StudentCourseService;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/common/user_center/stu_courses_see")
public class SeeStuCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SeeStuCourse() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid = Integer.parseInt(request.getParameter("id"));
		boolean mt = false;
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		//第二步：	获取个人信息,如果看的是自己，则跳转自己的主页
		HttpSession session = request.getSession();
		
		if (session.getAttribute("s") != null && ((Student) session.getAttribute("s")).getStuId() == uid) {
			response.sendRedirect("../../student/course/course_center");
			return;
		}
        

        // flag: 标志查询课程 1-被推荐; 2-正在学; 3-已学完
        Integer flag = null;
        String grade = null;
        
        try {
            
            String flagS = request.getParameter("flag");  
            flag = Integer.parseInt(flagS);
        }  catch (Exception e) {
            e.printStackTrace();
            flag = 2;
        }
        
        Connection conn = null;
        TeacherService teas = null;
		StudentService ss = null;
        StudentCourseService scs = null;
        PageElem<Course> pe = null;    // 存放即将返回的数据
        
        try {
            conn = DBSource.getConnection();
            scs = new StudentCourseService(conn);
            ss = new StudentService(conn);
			teas = new TeacherService(conn);
			
			if(mt){
				request.setAttribute("user", teas.queryOneTeacher(uid));
			}else {
				request.setAttribute("user", ss.getStudent(uid));
			}
            
            
            pe = scs.queryStuCourse(uid, cp, flag, grade);
            
            request.setAttribute("courses", pe);
            
            request.getRequestDispatcher("student_courses.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("查看我的课程异常");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                else 
                    System.out.println("course_center 中的conn 为空,sql 查询错误");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
