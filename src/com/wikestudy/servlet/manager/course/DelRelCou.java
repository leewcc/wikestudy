package com.wikestudy.servlet.manager.course;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

/**
 * Servlet implementation class DelRelCouSecServlet
 */
@WebServlet("/rel_cou_del")
public class DelRelCou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Integer couId = null;
		
		try {
			String s = request.getParameter("couId");
			
			if (s == null)
				throw new NullPointerException();
			
			couId = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			System.exit(1);
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
