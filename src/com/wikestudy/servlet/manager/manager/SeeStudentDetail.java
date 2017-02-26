package com.wikestudy.servlet.manager.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.AttenTopic;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.StudentManagerService;
import com.wikestudy.service.manager.TopicManagerService;


@WebServlet("/dist/jsp/manager/super/student_manage/student_detail_see")
public class SeeStudentDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SeeStudentDetail() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		//获取学生id
		int id = Integer.parseInt(request.getParameter("stuId"));
		
		//获取要展示何种数据 1-课程 2-话题 
		String type = request.getParameter("type");
		type = type == null? "1" : type;
		//调用底层获取学生的个人信息,关注的课程,发布的话题等等
		Connection conn = null;
		StudentManagerService sms = null;
		TopicManagerService tms = null;
		
		try{
			//根据id获取学生视图对象
			//若对象不存在,则跳转到提醒用户不存在页面
			//若存在,则跳转到学生信息展示页面
			conn = DBSource.getConnection();
			sms = new StudentManagerService(conn);
			tms = new TopicManagerService(conn);
			Student s = sms.queryById(id);
			
			if(s == null){
				request.setAttribute("message", "用户不存在");
				String url = request.getHeader("Referer");
				request.setAttribute("URL", url.substring(url.indexOf("/wikestudy")));
				request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
				return;
			}
			
			PageElem<Course> courses = null;
			PageElem<AttenTopic> topics = null;
			
//			if(type.equals("2")){
//				topics  = tms.queryTopics(id);
//				request.setAttribute("datas", topics);
//			}else{
//				//获取课程数据
//			}
//			
			request.setAttribute("student", s);
			request.getRequestDispatcher("student_detail_see.jsp").forward(request, response);
			
			
		}catch(Exception e) {
			System.out.println("获取学生信息出错");
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

}
