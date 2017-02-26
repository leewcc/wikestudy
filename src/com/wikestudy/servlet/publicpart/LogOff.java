package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.RecordService;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/common/log_off")
public class LogOff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//第一步：	移除session内的属性 
		HttpSession session = request.getSession();
		Student s = (Student)session.getAttribute("s");
		if(s != null) {
			long start = (long)session.getAttribute("start");
       	 	session.removeAttribute("start");
	       	 long end = System.currentTimeMillis();
	       	 
	       	 long time = end - start;
	       	 
	       	 Connection conn = null;
	       	 try{
	       		 conn = DBSource.getConnection();
	       		 RecordService rs = new RecordService(conn);
	       		 StudentService ss = new StudentService(conn);
	       		 
	       		 rs.updateStudy(s.getStuId(), time);
	       		 ss.updateStudy(s.getStuId(), time);
	       		 
	       	 }catch(Exception e) {
	       		 e.printStackTrace();
	       	 }finally {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		}
		session.removeAttribute("userType");
		session.removeAttribute("s");
		session.removeAttribute("t");
		session.removeAttribute("role");
		 
		 //第二步：	重定向到主页
		 response.sendRedirect("home_page");
	}

}
