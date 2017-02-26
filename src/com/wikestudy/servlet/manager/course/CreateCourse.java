package com.wikestudy.servlet.manager.course;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;

/**
 * 创建课程，将传递过来的课程信息存入到数据库中，并且要创建对应课程的文件夹
 */
@WebServlet("/dist/jsp/manager/course/course_create")
public class CreateCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger l = new Log4JLogger("log4j.properties");
		Teacher tea = (Teacher) request.getSession().getAttribute("t");
		
		Integer labelId = null;
		String couName = request.getParameter("couName");
		String couBrief = request.getParameter("couBrief");
		String couGrade = request.getParameter("couType");
		String couPricUrl = request.getParameter("imageUrl");
		
		try {
			String labelIdS = request.getParameter("labelId");
			if (labelIdS == null)
				labelIdS = "-1";
			labelId = Integer.parseInt(labelIdS);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			l.debug(e,e.fillInStackTrace());
			labelId = -1;
		}
		
		if (couName == null || couName.equals("")) {
			System.out.println("course_create couName is null");
			request.setAttribute("couNameError", "请填写课程名");
			request.getRequestDispatcher("CreateCourse.jsp").forward(request, response);
			return ;
		}
		
		if (couGrade == null || couGrade.equals("")) {
			System.out.println("course_create couGrade is null");
			
		}
		if (couPricUrl == null || couPricUrl.equals("")) {
			System.out.println("course_create couPricUrl is null");
		}
		
		Course cou = new Course();
		cou.setCouName(couName);
		cou.setCouBrief(couBrief);
		cou.setCouGrade(couGrade);
		cou.setCouPricUrl(couPricUrl);
		cou.setLabelId(labelId);
		cou.setTeacherId(tea.getTeaId());
		cou.setCouReleTime(new Timestamp(System.currentTimeMillis()));
		
		Connection conn = null;
		CourseManageService cms = null;
		
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
			int couId;
			// 创建成功
			if ((couId = cms.createCourse(cou)) != 1) {
				// 创建该课程相关文件夹
				//String couPath = this.getServletContext().getRealPath("course"+File.separator) + couId;
				String couPath = this.getServletContext().getRealPath("/");
				System.out.println("课程创建的路径:" + couPath + File.separator + "WEB-INF" + File.separator + "course" + File.separator + couId);
				File couFile = new File(couPath + File.separator + "WEB-INF" + File.separator + "course" + File.separator + couId);
				if (!couFile.exists()) {
					couFile.mkdir();
				}
				else {
					System.out.println("该课程文件之前已经创建，程序错误");
				}
				
				request.setAttribute("URL", "ChangeCourseServletSec?couId="+couId);
				request.setAttribute("message", "创建课程成功，完善课程信息");
				request.getRequestDispatcher("/dist/jsp/common/Error.jsp").forward(request, response);
				return ;
			}
			else {
				// 创建失败提示信息
				request.setAttribute("URL", "ChangeCourseServletSec?couId="+couId);
				request.setAttribute("message", "创建课程失败");
				request.getRequestDispatcher("../../common/Error.jsp").forward(request, response);
				return ;
			}
		} catch (Exception e) {
			System.out.println("课程创建数据库连接异常异常");
			l.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				else 
					System.out.println("course_create conn is null");
			} catch (SQLException e) {
				l.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

}
