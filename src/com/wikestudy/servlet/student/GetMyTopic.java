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

import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.pojo.Topic;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.TopicService;


@WebServlet("/dist/jsp/student/common/my_topic_get")
public class GetMyTopic  extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取页数
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		
		//第二步：	获取用户类型
		Boolean ut = (Boolean)request.getSession().getAttribute("userType");
		int uid = 0;
		if(ut != null && ut)
			uid = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
		else
			uid = ((Student)request.getSession().getAttribute("s")).getStuId();
		
		
		//第三步：	初始化数据库连接和服务
		Connection conn = null;
		TopicService ts = null;		
		try{
			conn = DBSource.getConnection();
			ts = new TopicService(conn);
			
			
		//第四步：	获取我的话题
			PageElem<Topic> myTopics = ts.getMyTopics(uid, ut, cp);
			
		
		//第五步：	将我的话题set进请求里，转发到我的话题页面
			request.setAttribute("myTopics", myTopics);
			request.getRequestDispatcher("my_topics.jsp").forward(request, response);
			
			
		//最后：		关闭数据库连接
		}catch(Exception e){
			
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
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
