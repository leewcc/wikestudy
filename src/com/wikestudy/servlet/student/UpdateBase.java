package com.wikestudy.servlet.student;

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


import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/student/account/base_update")
public class UpdateBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		HttpSession session = request.getSession();
		
		Student s = (Student)session.getAttribute("s");
		
		//获取学生班级
		//获取学生性别
		//获取学生个性签名
		String sclass = request.getParameter("cla");
		String gender = request.getParameter("gender");
		String sign = request.getParameter("signature");
		
		if(check(sclass, gender, sign, request)){
			jump(request, response);
			return;
		}
		
		//创建学生对象,将属性值set进去
		s.setStuGender(gender);
		s.setStuClass(sclass);
		s.setStuSignature(sign);
		
		Connection conn = null;
		StudentService ss = null;
		
		try{
			conn = DBSource.getConnection();
			ss = new StudentService(conn);
			
			//调用修改学生信息的方法
			//如果返回数大于0,则修改成功,将用户存进session替换掉
			//否则修改不成功,则返回修改页面
			if(ss.updateBasic(s) > 0){
				request.setAttribute("message", "修改成功");
			}else{
				request.setAttribute("message", "修改失败");
			}
			
			Student stu = ss.getStudent(s.getStuId());
			session.setAttribute("s", stu);
			jump(request, response);
				
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
	
	private boolean check(String cla, String gender, String signature, HttpServletRequest request) {
		if(cla == null || "".equals(cla))
			cla = "1";
		
		if(gender == null || "".equals(gender))
			gender = "male";
		
		if(signature != null && signature.length() > 100){
			request.setAttribute("signature", "个性签名长度最大为100个字符");
			return true;
		}
		
		return false;
			
	}

	private void jump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("my_detail_show.jsp").forward(request, response);
	}
	
}
