package com.wikestudy.servlet.manager.manager;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.filter.EncodingFilter;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;

/**
 * Servlet implementation class manager_reset
 */

@WebServlet("/dist/jsp/manager/super/student_manage/manager_reset")
public class ResetManager extends HttpServlet {
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
		String option=(String)request.getParameter("option");
		Connection conn = null;
		TeacherService ms=null;
		try {
			conn = DBSource.getConnection();
			 ms =new TeacherService (conn);
		} catch (Exception e1) {

			log.debug(e1,e1.fillInStackTrace());
			e1.printStackTrace();
		}
		if("reset".equals(option))
		{
			String teaId=request.getParameter("teaId");
			try {
				ms.ResetPassword(Integer.parseInt(teaId));
			} catch (NumberFormatException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			} catch (Exception e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		} else if("delete".equals(option)) 
		{
			String teaId=request.getParameter("teaId");
			List<Integer> list=new ArrayList<Integer>();
			list.add(Integer.parseInt(teaId));
			try {
				ms.deleteTeacher(list);
			} catch (Exception e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
		String cps=(String)request.getParameter("cp");
		int cp;
		if(cps!=null||EncodingFilter.isNumber(cps))
		{
			cp=Integer.parseInt(cps);
		} else {
			cp=1;
		}
		if(cp<=0) cp=1;
		try {
	
			PageElem<Teacher> pe=ms.queryAllTeacher(cp);
			int tp=pe.getTotalPage();
			request.setAttribute("cp", cp);
			request.setAttribute("tp", tp);
			request.setAttribute("pe", pe);
			conn.close();
			request.getRequestDispatcher("manager_reset.jsp").forward(request,response);
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
	}

}
