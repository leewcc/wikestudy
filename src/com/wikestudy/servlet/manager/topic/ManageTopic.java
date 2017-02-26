package com.wikestudy.servlet.manager.topic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Topic;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.LabelService;
import com.wikestudy.service.publicpart.TopicService;


@WebServlet("/dist/jsp/manager/topic/topic_manage")
public class ManageTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
      


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		String labIdS = request.getParameter("labId");
		int cp = 1;
		
		String cpS = request.getParameter("currentPage");
		if(cpS != null){
			cp = Integer.parseInt(cpS);
		}
		
		Connection conn = null;
		LabelService ls = null;
		TopicService ts = null;
		
		try{
			conn =DBSource.getConnection();
			ts = new TopicService(conn);
			ls = new LabelService(conn);
			
			//获取所有类型
			List<Label> labels = ls.query();
			int labId = labIdS == null ? 0 : Integer.parseInt(labIdS) ;
			
			//获取话题列表
			PageElem<Topic> topics = ts.queryByTypeM(labId, cp);
			
			request.setAttribute("labels", labels);
			request.setAttribute("topics", topics);
			request.getRequestDispatcher("topic_manage.jsp").forward(request, response);
			
			
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
