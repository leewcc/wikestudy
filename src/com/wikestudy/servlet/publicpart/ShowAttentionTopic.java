package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.ColTopic;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.ColTopicService;


@WebServlet("/dist/jsp/common/user/user_topic/attention_topic_show")
public class ShowAttentionTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
      


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取话题id
		ColTopic col = new ColTopic();
		int topId = request.getParameter("topId") == null ? 0 : Integer.parseInt(request.getParameter("topId"));
		
		
		//第二步：	获取用户类型
		boolean ut = (boolean)request.getSession().getAttribute("userType");
		if(ut){
			col.setColUserId(((Teacher)request.getSession().getAttribute("t")).getTeaId());
		}else{
			col.setColUserId(((Student)request.getSession().getAttribute("s")).getStuId());
		}
		
		
		//第三步：	初始化收藏话题对象	
		col.setColTopicId(topId);
		col.setColUserEnum(ut);
		
		
		//第四步：	初始化数据库连接和服务
		Connection conn = null;
		ColTopicService cts = null;		
		try{
			conn = DBSource.getConnection();
			cts = new ColTopicService(conn);
			
		
		//第五步：	调用关注或取消关注话题的方法
			cts.SetCol(col);
	
			
		//第六步：	重定向到上一个请求的页面
			String url = request.getHeader("Referer");
			response.sendRedirect(url.substring(url.indexOf("/wikestudy")));
			
		
		//最后：		关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new ServletException();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.debug(e,e.fillInStackTrace());
			}
		}
	}

}
