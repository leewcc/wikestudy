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

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.StudentCourseService;

/**
 * 返回学生已学的课程信息
 */
@WebServlet("/dist/jsp/student/course/course_center")
// 学生我的课程
// 就算是第一次点击也会提供当前页数
public class CourseCenter extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Log4JLogger log = new Log4JLogger("log4j.properties");

        Student user = (Student)request.getSession().getAttribute("s");
        String totalPage = request.getParameter("totalPage");
        
        // 当前页数, 若第一次打开某个选择, 则均为第一页
        Integer currentPage = null;
        // flag: 标志查询课程 1-被推荐; 2-正在学; 3-已学完
        Integer flag = null;
        String grade = user != null? user.getStuGrade() : null ;
        
        try {
            currentPage = Integer.parseInt(request.getParameter("currentPage")); 
            if (currentPage <= 0) {
            	currentPage = 1;
            }
        }  catch (Exception e) {
            currentPage = 1; 
        }
        
        try {
        	flag = Integer.parseInt(request.getParameter("flag"));
        } catch (Exception e) {
        	flag = 2;
        }
        
        
        
        Connection conn = null;
        StudentCourseService scs = null;
        PageElem<Course> pe = null;    // 存放即将返回的数据
        
        try {
            conn = DBSource.getConnection();
            scs = new StudentCourseService(conn);
            
            pe = scs.queryStuCourse(((Student)user).getStuId(), 
                    currentPage, flag, grade);
            
            request.setAttribute("pe", pe);
            request.setAttribute("cp", currentPage);
            request.setAttribute("flag", flag);
            request.getRequestDispatcher("course_check.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("查看我的课程异常");
            e.printStackTrace();
            log.debug(e,e.fillInStackTrace());
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
        // TODO Auto-generated method stub
    }

}
