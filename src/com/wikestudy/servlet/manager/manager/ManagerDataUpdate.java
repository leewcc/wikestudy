package com.wikestudy.servlet.manager.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;


@WebServlet("/dist/jsp/manager/page/manager_data_update")
public class ManagerDataUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("information_update.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		HttpSession session=request.getSession();
		Teacher t=(Teacher)session.getAttribute("t");
		int teaId=t.getTeaId();//获得Id
		String n = request.getParameter("name");//获得姓名
		String g = request.getParameter("gender");//获得性别
		String i = request.getParameter("introduction");
		
		if(check(n, g, i,request)) {
			jump(request, response);
			return;
		}
		
		//把信息存进去
		t.setTeaName(n);
		t.setTeaGender(g);
		t.setTeaIntroduction(i);
		
		Connection conn = null;
		try {
			conn=DBSource.getConnection();
			TeacherService ts=new TeacherService(conn);
			ts.updateOneTeacher(t);
			t = ts.queryOneTeacher(t.getTeaId());
			session.setAttribute("t", t);
			jump(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
		

	}
	
	private boolean check(String n, String g,String signature, HttpServletRequest request) {
		boolean hasError = false;
		
		if(n == null || "".equals(n)){
			request.setAttribute("name", "请输入姓名");
			hasError = true;
		}else{
			if(n.length() > 50){
				request.setAttribute("name", "姓名长度最大为50个字符");
				hasError = true;
			}
		}
		
		if(g == null || "".equals(g)){
			g = "male";
		}
		
		if(signature != null && signature.length() > 100){
			request.setAttribute("signature", "个人简介长度最大为100个字符");
			hasError = true;
		}
		
		return hasError;
	}

	private void jump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("information_update.jsp").forward(request, response);
	}

}
