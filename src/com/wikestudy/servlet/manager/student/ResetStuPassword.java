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


@WebServlet("/dist/jsp/manager/super/student_manage/stu_password_reset")
public class ResetStuPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ResetStuPassword() {
        super();
      
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sid = Integer.parseInt(request.getParameter("stuId"));				
		
		Connection conn = null;
		StudentManagerService sms = null;
		Log4JLogger log = new Log4JLogger("log4j.properties");
		try{
			conn = DBSource.getConnection();
			sms = new StudentManagerService(conn);
			if(sms.resetPassword(sid) <= 0){
				request.setAttribute("message","密码重置失败" );
			}else{
				request.setAttribute("message","密码重置成功" );
			}
			
			String url = request.getHeader("Referer");
			
			response.sendRedirect(url.substring(url.lastIndexOf("/") + 1));
		}catch(Exception e){
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
