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

import com.wikestudy.model.enums.Role;
import com.wikestudy.model.pojo.ChapSec;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.StuCourse;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.HomeCourseService;

/**
 * Servlet implementation class course_selectS
 */
@WebServlet("/dist/jsp/common/course_s_select")
// 选课课程信息介绍
public class SelectCourseS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		String couId = request.getParameter("couId");
		String flag = request.getParameter("flag");
		Object user = null;
		String type = "1"; // 默认为1
		Role role =(Role) request.getSession().getAttribute("role");
		
		
		Integer couIdInt = null;
		
		try {
			couIdInt = Integer.parseInt(couId);
			
		} catch (ClassCastException e) {
			e.printStackTrace();
			couIdInt = new Integer(1);
		}
		
		Connection conn = null;
		HomeCourseService hcs = null;
		Course cou = null;            // 课程信息
		List<ChapSec> csList = null;  // 章节信息
		Teacher teacher = null;       // 老师信息
		StuCourse stuCourse = null;   // 选课信息
		try {
			conn = DBSource.getConnection();
			hcs = new HomeCourseService(conn);
			
			cou = hcs.queryCouByCouId(couIdInt);
			
			Student stu = null;
			if (role.equals(Role.student)) {
				stu = (Student) request.getSession().getAttribute("s");
				System.out.println("stuId = " + stu.getStuId());
				System.out.println("couId = " + couId);
				// 查询该课程是否已经被选
				int i = hcs.insertStuCourse(stu.getStuId(), couIdInt);
				System.out.println(i);
				if (i >= 0) {
					stuCourse = new StuCourse();
					request.setAttribute("stuCourse", stuCourse);
				}
			} 
			// 章节信息查看 
			csList = hcs.queryChapSecByCouId(couIdInt);
			request.setAttribute("csList", csList);
			
			// 查询教师信息
			teacher = hcs.queryTea(cou.getTeacherId());
			request.setAttribute("teacher", teacher);    // 教师信息
			request.setAttribute("cou", cou);            // 课程信息
			request.setAttribute("type", type);          // 类型信息 1-章节; 2-评论; 3-提问
			request.setAttribute("couId", couId);
			request.getRequestDispatcher("select_courses.jsp").forward(request, response);
			
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			try {
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
