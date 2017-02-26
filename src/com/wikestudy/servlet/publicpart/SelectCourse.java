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
import com.wikestudy.model.pojo.DiscussView;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.StuCourse;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.pojo.Topic;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.HomeCourseService;

/**
 * 获得课程详细信息
 */
@WebServlet("/dist/jsp/common/course_select")
public class SelectCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String couId = request.getParameter("couId");
		Student user = (Student)request.getSession().getAttribute("s");
		Role role =(Role) request.getSession().getAttribute("role");

		
		String flag = request.getParameter("flag");
		
		if (couId == null) {
			System.out.println("couId is null");
		}
		// 1-章节; 2-点评; 3-问答
		String type = request.getParameter("type");
		if (type == null) {
			type = "1";
		}
		
		
		Integer couIdInt = null;
		
		try {
			couIdInt = Integer.parseInt(couId);
		} catch (Exception e) {
			e.printStackTrace();
			couIdInt = new Integer(1);
		}
		
		Student stu = null;
		StuCourse stuCourse = null;
		
		Connection conn = null;
		HomeCourseService hcs = null;
		Course cou = null;
		List<ChapSec> csList = null;
		PageElem<DiscussView> peDisView = null;
		PageElem<Topic> peTopic = null;
		Teacher teacher = null;
		try {
			conn = DBSource.getConnection();
			hcs = new HomeCourseService(conn);
			cou = hcs.queryCouByCouId(couIdInt);
			
			if (flag != null && flag.equals("1")) {
				hcs.insertStuCourse(((Student)user).getStuId(), couIdInt);
			}
			
			
			if (Role.student.equals(role) ) {
				stu = (Student) user;
				// 查询该课程是否已经被选
				stuCourse = hcs.queryStuCourseByStu(stu.getStuId(), couIdInt);
				request.setAttribute("stuCourse", stuCourse);
			}
			
			if (type.equals("1"))  { 
				// 章节信息查看 
				csList = hcs.queryChapSecByCouId(couIdInt);
				request.setAttribute("csList", csList);
			}
			else if (type.equals("2")) {
				// 点评信息查看
				Integer page = null;
				
				try {
					String pS =  request.getParameter("page");
					if (pS == null)
						pS = "1";
					page = Integer.valueOf(pS);
				} catch (Exception e) {
					e.printStackTrace();
					page = 1;
				}
				
				peDisView = hcs.queryDissView(couIdInt, page);
				request.setAttribute("peDisView", peDisView);
			}
			else if (type.equals("3")) {
				// 查看讨论区
				Integer page = null;
				try {
					page = Integer.valueOf(request.getParameter("page"));
				} catch (Exception e) {
					page = 1;
				}
				peTopic = hcs.queryTopicByCouId(couIdInt, page);
				request.setAttribute("peTopic", peTopic);
			}
			
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
		
	}

}
