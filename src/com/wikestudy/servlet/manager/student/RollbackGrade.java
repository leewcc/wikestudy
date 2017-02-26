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
import com.wikestudy.service.manager.StudentManagerService;


@WebServlet("/dist/jsp/manager/super/student_manage/grade_rollback")
public class RollbackGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public RollbackGrade() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		StudentManagerService sms = null;
		Log4JLogger log = new Log4JLogger("log4j.properties");
		try{
			//一键升级功能
			conn = DBSource.getConnection();
			sms = new StudentManagerService(conn);
			System.out.println(sms.rollbackGrade());
			request.setAttribute("message", "退级成功");
			response.sendRedirect("student_query_by_grade?stuGrade=1&stuClass=0");
			
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
