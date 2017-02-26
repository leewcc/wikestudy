package com.wikestudy.servlet.teacher;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

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


@WebServlet("/dist/jsp/teacher/course/course_add")
public class AddCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddCourse() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取老师
		Teacher tea = (Teacher) request.getSession().getAttribute("t");

		
		//第二步：	获取课程内容
		int lid = request.getParameter("labelId") == null ? -1 : Integer.parseInt(request.getParameter("labelId")); 
		String couName = request.getParameter("couName");
		String couBrief = request.getParameter("couBrief");
		String couGrade = request.getParameter("couType");
		String couPricUrl = request.getParameter("imageUrl");
		
		//第三步：	组装课程对象
		Course cou = new Course();
		cou.setCouName(couName);
		cou.setCouBrief(couBrief);
		cou.setCouGrade(couGrade);
		cou.setCouPricUrl(couPricUrl.substring(couPricUrl.lastIndexOf("/")+1));
		cou.setLabelId(lid);
		cou.setTeacherId(tea.getTeaId());
		cou.setCouReleTime(new Timestamp(new Date().getTime()));
		
		
		//第四步：	初始化数据库服务、连接
		Connection conn = null;
		CourseManageService cms = null;
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
			
			
		//第五步：	执行创建课程
			int couId = -1;
			if ((couId = cms.createCourse(cou)) != -1) {
				
				
		//第六步：	创建成功，创建课程必需的文件夹
				String couPath = this.getServletContext().getRealPath("/");
				File couFile = new File(couPath + File.separator + "WEB-INF" + File.separator + "course" + File.separator + couId);
				if (!couFile.exists()) {
					couFile.mkdir();
				}
				
		
		//第七步：	跳转到该创建课程进行管理
				response.sendRedirect("course_manage?couId=" + couId);
				return ;
			}else {
			
				
		//第六步：	创建失败，跳转到提示页面
				request.setAttribute("URL", request.getHeader("Referer"));
				request.setAttribute("message", "创建课程失败");
				request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
				return ;
			}
			
			
		//最后：		关闭数据库连接
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			System.out.println("课程创建数据库连接异常异常");
			e.printStackTrace();
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

}
