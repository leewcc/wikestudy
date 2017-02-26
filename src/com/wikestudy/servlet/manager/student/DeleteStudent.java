package com.wikestudy.servlet.manager.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/manager/super/student_manage/student_delete")
public class DeleteStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取要删除的学生id数组;
		int  id = Integer.parseInt(request.getParameter("stuId"));
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		Connection conn = null;
		StudentService ss = null;
		
		try{
			conn = DBSource.getConnection();
			ss = new StudentService(conn);
			//调用删除学生方法
			//如果返回的<= 0,则删除失败
			//否则删除成功
			if(ss.delete(id) <= 0){
				request.setAttribute("message", "删除失败");
			}else{
				request.setAttribute("message", "删除成功");
			}
			response.sendRedirect("student_query_by_grade");
		}catch(Exception e){
			log.debug(e,e.fillInStackTrace());
			System.out.println("删除学生失败");
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
