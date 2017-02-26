package com.wikestudy.servlet.publicpart;

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

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.pojo.Topic;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.TopicService;


@WebServlet("/dist/jsp/common/user/user_topic/topic_release")
public class ReleaseTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ReleaseTopic() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		//第一步：	获取话题内容
		int lid = 0;
		try{
			lid = Integer.parseInt(request.getParameter("label"));
		}catch(NumberFormatException ne) {
			request.setAttribute("label", "请选择标签");
			jumpError(request, response);
			return;
		}
			
		String t = request.getParameter("title");
		String c = request.getParameter("content");
		int sid =request.getParameter("topSecId") == null? 0 : Integer.parseInt(request.getParameter("topSecId"));
		
		
		//第二步：	判断输入内容是否为空
		if(check(lid, c, t, request)) {
			jumpError(request,response);
			return;
		}
			
		//第三步：	获取用户类型
		int uid = 0;
		boolean ut = (boolean)request.getSession().getAttribute("userType");
		if(ut){
			uid = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
		}else {
			uid = ((Student)request.getSession().getAttribute("s")).getStuId();
		}
		
		
		//第四步：	组装话题对象
		Topic topic = new Topic();
		topic.setLabId(lid);
		topic.setTopTit(t);
		topic.setTopCon(c);
		topic.setTopSecId(sid);
		topic.setTopUserId(uid);
		topic.setTopUserEnum(ut);
		topic.setTopTime(new Timestamp(new java.util.Date().getTime()));
		
		
		//第五步：	初始化数据库连接和服务	
		Connection conn = null;
		TopicService ts = null;	
		try{
			conn = DBSource.getConnection();
			ts = new TopicService(conn);
			
			
		//第六步：	调用插入话题方法
			int id = ts.add(topic);
			
			
		//第七步：	判断插入是否成功
			if(id <= 0){
				request.setAttribute("message", "创建话题失败，请重新创建");
				jumpError(request, response);
				return;
				
			}else{
						
		//第八步：	插入成功，重定向到查看具体话题业务		
				jumpOk(id, request, response);
			}
			
			
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

	private boolean check(int label, String content, String title, HttpServletRequest request) {
		boolean hasFalse = false;
		
		if(label <= 0){
			request.setAttribute("label", "请选择话题类型");
			hasFalse = true;
		}
		
		if(title == null || "".equals(title) || "".equals(title.trim())){
			request.setAttribute("title", "请输入话题标题");
			hasFalse = true;
		}else if(title.length() > 200){
			request.setAttribute("title", "标题长度只能在200个字符以内");
			hasFalse = true;
		}
		
		
		if(content == null || "".equals(content) || "".equals(title.trim())) {
			request.setAttribute("content", "请输入话题内容");
			hasFalse = true;
		}
		
		return hasFalse;
	}
	
	private void jumpError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("topic_add").forward(request, response);
	}
	
	private void jumpOk(int topic, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("../../topic/topic_detail_get?topId=" + topic + "&currentPage=1");
	}
	
}
