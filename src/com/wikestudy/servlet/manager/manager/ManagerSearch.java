package com.wikestudy.servlet.manager.manager;

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

@WebServlet("/dist/jsp/manager/super/student_manage/manager_search")
public class ManagerSearch extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManagerSearch() {
		super();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String key=req.getParameter("search");
		Connection conn=null;
		try {
			conn=DBSource.getConnection();
			TeacherService ts=new TeacherService(conn);
			PageElem<Teacher> pe=new PageElem<Teacher>();
			String cp=(String) req.getParameter("cp");
			int cpInteger;
			try {
				 cpInteger=Integer.parseInt(cp);
			} catch (Exception e) {
				cpInteger=1;
			}
			pe.setCurrentPage(cpInteger);
			pe.setPageShow(10);
			ts.queryTeacherByName(key, pe);
			req.setAttribute("pe",pe);
			conn.close();
			req.getRequestDispatcher("manager_reset.jsp").forward(req, resp);
			} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
	}
	
}
