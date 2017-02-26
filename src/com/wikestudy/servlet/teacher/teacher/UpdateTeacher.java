package com.wikestudy.servlet.teacher.teacher;

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

import com.sun.swing.internal.plaf.metal.resources.metal_es;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;


@WebServlet("/dist/jsp/teacher/person_data/teacher_update")
public class UpdateTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public UpdateTeacher() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("teacher_update.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取老师
		HttpSession session=request.getSession();
		Teacher t=(Teacher)session.getAttribute("t");
		
	 
		
		//第二步：	获取姓名、性别、老师简介
		String n = request.getParameter("name");//获得姓名
		String g = request.getParameter("gender");//获得性别
		String i = request.getParameter("introduction");
		
		if(check(n, g, i, request)) {
			jump(request, response);
			return;
		}
		
		//第三步更新Session
		t.setTeaName(n);
		t.setTeaGender(g);
		t.setTeaIntroduction(i);
		
		
		//第三步：	初始化数据库连接、服务
		Connection conn = null;
		TeacherService ts = null;
		try {
			conn=DBSource.getConnection();
			ts=new TeacherService(conn);
			
			
		//第四步：	调用更新的方法,如果更新成功，则更新session域的对象
			if(ts.updateOneTeacher(t) > 0){
				request.setAttribute("message", "修改成功");
			}
			
			t = ts.queryOneTeacher(t.getTeaId());
			
		//第五步：	跳转回资料页面
			jump(request, response);
			
			
		//最后：		关闭数据库连接
		} catch (Exception e) {
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
	
	private boolean check(String n, String g, String signature, HttpServletRequest request) {
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
			return true;
		}
		
		return hasError;
	}

	private void jump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("teacher_update.jsp").forward(request, response);
	}
}
