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
import com.wikestudy.service.manager.TopicManagerService;


@WebServlet("/dist/jsp/manager/topic/topic_setup")
public class SetUpTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Log4JLogger log = new Log4JLogger("log4j.properties");
			
			//获取话题id
			//获取是否置顶标志
			int tid = Integer.parseInt(request.getParameter("topId"));
			boolean isUp = Boolean.parseBoolean(request.getParameter("topIsUp"));
			
			Connection conn = null;
			TopicManagerService tms = null;
			
			try{
				conn = DBSource.getConnection();
				tms = new TopicManagerService(conn);
				
				
				tms.setUp(tid, isUp);
				request.getRequestDispatcher("topic_manage").forward(request, response);
				
			}catch(Exception e){
				log.debug(e,e.fillInStackTrace());
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
