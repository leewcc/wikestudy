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

import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.LabelService;


@WebServlet("/dist/jsp/manager/topic/label_delete")
public class DeleteLabel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		int id = Integer.parseInt(request.getParameter("labId"));
		
		Connection conn = null;
		LabelService ls = null;
		
		try{
			conn = DBSource.getConnection();
			ls = new LabelService(conn);
			
			//如果标签下面存在话题,则跳入对应标签的话题目录下,因为删除标签需先删除话题
			Label l = ls.getLabel(id);
			if(l.getTopicCount() > 0){
				request.setAttribute("message", "请先删除该标签下的话题");
				request.getRequestDispatcher("topic_manage").forward(request, response);
				return;
			}else if(l.getCourseCount() > 0){
				request.setAttribute("message", "请先删除该标签下的课程");
				request.getRequestDispatcher("../course/QueryCourse").forward(request, response);
				return;
			}else {
				ls.delete(id);
			}
			
			response.sendRedirect("label_query");
			
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
