package com.wikestudy.servlet.manager.teacher;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;

/**
 * Servlet implementation class teacher_manage
 */
@WebServlet("/dist/jsp/manager/page/teacher_manage")
public class TeacherManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherManager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		TeacherService ts=null;
		Connection conn=null;
		int cp=Integer.parseInt((String)request.getParameter("cp"));
		try {
			conn = DBSource.getConnection();
			ts=new TeacherService(conn);
			PageElem <Teacher> t=ts.queryAllTeacher(cp,0);//0代表的是管理员
			conn.close();
			request.setAttribute("tea", t);
			request.getRequestDispatcher("teacher_manage.jsp").forward(request, response);;
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}	
	}
}
