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
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.LabelService;


@WebServlet("/dist/jsp/manager/topic/label_query")
public class QueryLabel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public QueryLabel() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Connection conn = null;
		LabelService ls = null;
		
		try{
			conn = DBSource.getConnection();
			ls = new LabelService(conn);
			List<Label> list = ls.query();
			request.setAttribute("labels", list);
			request.getRequestDispatcher("type_manage.jsp").forward(request, response);
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
