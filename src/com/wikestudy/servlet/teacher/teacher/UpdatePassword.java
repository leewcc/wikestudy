package com.wikestudy.servlet.teacher.teacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.MD5;
import com.wikestudy.service.manager.TeacherService;


@WebServlet("/dist/jsp/teacher/person_data/password_update")
public class UpdatePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdatePassword() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取旧密码、新密码
		String op = request.getParameter("oldPassword");
		String np = request.getParameter("newPassword");
		String npa = request.getParameter("newPassAgain");	
		
		//第二步：	判断输入的密码是否为空，若为空则赋值错误信息
		//如果密码为空,则添加密码为空的错误信息
		if (check(op, np, npa, request)) {
			jumpFalse(request, response);
			return;
		}	
		
		//第四步：	初始化数据库连接、服务
		Connection conn = null;
		TeacherService ts = null;		
		try{			
			conn = DBSource.getConnection();
			ts = new TeacherService(conn);
			
			
		//第五步：	调用更新密码的方法
			int id = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
			if(ts.updatePassword(id, MD5.getMD5Code(op), MD5.getMD5Code(np)) <= 0){
				request.setAttribute("error", "原密码不正确");
				jumpFalse(request, response);
				return;
			}else{
				request.setAttribute("message", "修改密码成功");
				jumpTrue(request, response);
				return;
			}
			
			
		//最后：		关闭数据库连接
		}catch(Exception e){
			System.out.println("修改密码失败");
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

	private boolean check(String op, String np, String npa, HttpServletRequest request) {
		String opE = Student.passwordCheck(op);
		String npE = Student.passwordCheck(np);
		String npaE = Student.passwordCheck(npa);
		
		if(!"".equals(opE)){
			request.setAttribute("error", opE);
			return true;
		}
		
		if(!"".equals(npE)){
			request.setAttribute("error", npE);
			return true;
		}
			
		
		if(!"".equals(npaE)){
			request.setAttribute("error", npaE);
			return true;
		}
			
		if(!npa.equals(np)){	
			request.setAttribute("error", "两次输入不一致");
			return true;
		}
			
		return false;
	}
	
	private void jumpFalse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("password_update.jsp").forward(request, response);
	}
	
	private void jumpTrue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("teacher_update.jsp").forward(request, response);
	}
}
