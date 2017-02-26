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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.pojo.Topic;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;
import com.wikestudy.service.publicpart.LabelService;
import com.wikestudy.service.publicpart.TopicService;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/common/user_center/user_topics_show")
public class ShowUserTopics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取用户id、页数
		int uid = Integer.parseInt(request.getParameter("id"));
		boolean mt = Boolean.parseBoolean(request.getParameter("type"));
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		
		//第二步：	获取个人信息,如果看的是自己，则跳转自己的主页
		HttpSession session = request.getSession();
		if (mt) {
			if (session.getAttribute("t") != null && ((Teacher) session.getAttribute("t")).getTeaId() == uid) {
				response.sendRedirect("../../student/common/my_topic_get");
				return;
			}
		}else{
			if (session.getAttribute("s") != null && ((Student) session.getAttribute("s")).getStuId() == uid) {
				response.sendRedirect("../../student/common/my_topic_get");
				return;
			}
		}
		
		
		
		//第二步：	初始化数据库服务、连接
		Connection conn = null;
		TeacherService teas = null;
		StudentService ss = null;
		TopicService ts = null;
		LabelService ls = null;
		try{
			conn = DBSource.getConnection();
			teas = new TeacherService(conn);
			ss = new StudentService(conn);
			ts = new TopicService(conn);
			ls = new LabelService(conn);
		
			
		//第三步：	获取用户信息
			if(mt){
				request.setAttribute("user", teas.queryOneTeacher(uid));
			}else {
				request.setAttribute("user", ss.getStudent(uid));
			}
			
			
		//第四步：	获取他的话题
			PageElem<Topic> hisTopics = ts.getMyTopics(uid, mt, cp);
			List<Label> labels = ls.getList();
			
		
		//第五步：	将他的话题set进请求里，转发到他的话题页面
			request.setAttribute("labels", labels);
			request.setAttribute("hisTopics", hisTopics);
			request.getRequestDispatcher("his_topics.jsp").forward(request, response);
			
			
		//最后：		关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
			throw new ServletException();
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
