package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.dao.StuCourseDao;
import com.wikestudy.model.dao.impl.StuCourseDaoImpl;
import com.wikestudy.model.dao.impl.StuScheduleDaoImpl;
import com.wikestudy.model.pojo.StuSchedule;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;

/**
 * Servlet implementation class MediaCheckServlet
 */
@WebServlet("/dist/jsp/student/course/media_check")
public class MediaCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    // 检查学生是否选修该课;
    // 更新学生课程进度表
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer couId = null;
		Integer chaId = null;
		Integer secId = null;
		Student student = (Student) request.getSession().getAttribute("s");
		
		boolean flag = true;
		String message = null;
		
		try {
			couId = Integer.parseInt(request.getParameter("couId"));
			chaId = Integer.parseInt(request.getParameter("chaId"));
			secId = Integer.parseInt(request.getParameter("secId"));
		} catch (Exception e) {
			flag = false;
			message = "请求参数错误";
		}
		
		if (student != null) {
			
			Integer stuId = student.getStuId();

			
			
			
			Connection conn = null;
			StuCourseDao scDao = null;
			
			try {
				conn = DBSource.getConnection();
					
				scDao = new StuCourseDaoImpl(conn);
				
				if ( scDao.isSelected(stuId, couId) == 0) {
					message = "选修该课程后才能观看视频";
					flag = false;
				}
				
				if (!flag) {
					request.setAttribute("URL", "/wikestudy/dist/jsp/common/course_select?couId="+couId);
					request.setAttribute("message", message);
					request.getRequestDispatcher("../../common/Error.jsp").forward(request, response);
					return ;
				}
				
				// 更新学生进度表
				StuSchedule schedule = new StuSchedule();
				
				schedule.setStuId(stuId);
				schedule.setChaId(chaId);
				schedule.setSecId(secId);
				schedule.setSecExam(false);
				schedule.setSecCondition("2");
				schedule.setSecTime("");
				
				new StuScheduleDaoImpl(conn).insertStuSchedule(schedule);
				
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		response.sendRedirect("media_show?secId="+ secId + "&chaId="+chaId+"&couId=" + couId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
