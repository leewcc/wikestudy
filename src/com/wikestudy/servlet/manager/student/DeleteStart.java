package com.wikestudy.servlet.manager.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.StarService;


@WebServlet("/dist/jsp/manager/super/star_manage/star_delete")
public class DeleteStart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String[] idS = request.getParameterValues("starId");
		
		if(idS == null || idS.length <= 0){
			response.sendRedirect(request.getHeader("Referer").substring(16));
			return;
		}
		
		List<Integer> ids = new ArrayList<Integer>();
		for(int i = 0; i < idS.length; i++)
			ids.add(Integer.parseInt(idS[i]));
		
		Connection conn = null;
		StarService ss = null;
		
		try{
			conn = DBSource.getConnection();
			ss = new StarService(conn);
			ss.delete(ids);
			request.getRequestDispatcher("star_see").forward(request, response);
			
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
