package com.wikestudy.servlet.manager.teacher;

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
import com.wikestudy.service.manager.TeacherService;

/**
 * Servlet implementation class TeacherOut
 */
@WebServlet("/dist/jsp/manager/page/teacher_out")
public class OutTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Connection conn=null;
		try {
			String[] ids=request.getParameterValues("teaId");
			List<Integer> id=new ArrayList<Integer>();
			for(int i=0;i<ids.length;i++) {
				id.add(Integer.parseInt(ids[i]));
			}
			conn = DBSource.getConnection();
			TeacherService ts=new TeacherService(conn);
			ts.deleteTeacher(id);
			response.sendRedirect("teacher_manage?cp=1");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

}
