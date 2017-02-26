package com.wikestudy.servlet.manager.topic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.TopicService;


@WebServlet("/dist/jsp/manager/topic/topic_delete")
public class DeleteTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		//获取用户权限
		int tid = 0;
		try{		
			tid = Integer.parseInt(request.getParameter("topId"));
		}catch(Exception e){
			request.setAttribute("message", "选择话题出错");
			jump(request, response);
			return;
		}
			
		Connection conn = null;
		TopicService ts = null;
		
		try{
			conn = DBSource.getConnection();
			ts = new TopicService(conn);
			
			//调用删除的方法  如果删除成功,则跳转回公共话题页面
			//删除失败  则继续返回上一个页面
			if(ts.delete(tid) <= 0){
				request.setAttribute("message", "删除话题失败");
			}else {
				request.setAttribute("message", "删除话题成功");
			}
			
			jump(request, response);
			
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
	
	private void jump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.getRequestDispatcher("topic_manage").forward(request, response);
	}

}
