package com.wikestudy.servlet.student;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wikestudy.model.enums.Role;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.MD5;
import com.wikestudy.service.manager.TeacherService;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/student/account/login")
public class LoginStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("student_login.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//第一步：	获取账户密码
		String n = request.getParameter("number");
		String p = request.getParameter("password");
		String c = request.getParameter("code");		
		
		//第二步：	判断账号与密码是否非空,非空执行页面跳转，跳转回登陆页面
		if(check(n, p, c, request)){
			jump(request, response);
			return;
		}
		
		p = MD5.getMD5Code(p);
		
		//第三步：	初始化数据库连接与服务
		Connection conn = null;
		StudentService ss = null;
		TeacherService ts = null;
		try{
			conn = DBSource.getConnection();
			ss = new StudentService( conn);		
			ts = new TeacherService(conn);
			
			
		//第四步：	获取登陆的用户类型
			String role = request.getParameter("role");
			
			
		//第五步：	根据用户类型进行登陆
			if("tea".equals(role)){
		
				
		//第六步：	用账号和密码进行老师登陆
				Teacher t = ts.queryTeacherByNum(n, p);
				
		//第七步：	判断用户是否登陆成功，如果登陆失败，则返回原页面提示错误信息
				if(t == null || !t.isTeaDelete()){
					request.setAttribute("error", "账号或密码错误");
					jump(request, response);
					return;
					
		//第八步：	登陆成功，则设置Session属性，cookie属性，返回到老师个人中心主页			
				}else{

					
					HttpSession session = request.getSession();
					session.setAttribute("t", t);
					session.setAttribute("userType", true);
					session.setAttribute("role", Role.teacher);
					session.removeAttribute("s");
					Cookie c1 = new Cookie("mid",t.getTeaId()+"");
					response.addCookie(c1);
					Cookie c2 = new Cookie("type","0");
					response.addCookie(c2);
					response.sendRedirect("/wikestudy/dist/jsp/teacher/common/teacher_home");
				}
				
			
			}else{
				
				
		//第六步：	用账号和密码进行学生登陆
				Student s = ss.Login(n, p);
				
		
		//第七步：	判断用户是否登陆成功，如果登陆失败，则返回原页面提示错误信息
				if(s == null){
					request.setAttribute("error", "账号或密码错误");
					jump(request, response);
			
					
		//第八步：	登陆成功，则设置Session属性，cookie属性，返回到学生个人中心主页
				}else{
	
					
					HttpSession session = request.getSession();
					session.setAttribute("start", System.currentTimeMillis());
					session.setAttribute("s", s);
					session.setAttribute("userType", false);
					session.setAttribute("role", Role.student);
					session.removeAttribute("t");
					Cookie c1 = new Cookie("mid",s.getStuId()+"");
					response.addCookie(c1);
					Cookie c2 = new Cookie("type","1");
					response.addCookie(c2);
					response.sendRedirect("/wikestudy/dist/jsp/student/common/student_home");
				}
			}
			
			
		//最后：		关闭数据库连接
		}catch(Exception e){
			System.out.println("学生登陆失败");
			e.printStackTrace();
			throw new ServletException();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//检查账号吗密码是否为空
	private boolean check(String n, String p, String c, HttpServletRequest req) {
		if("".equals(n) || "".equals(p)){
			req.setAttribute("error", "请输入账号或密码");
			return true;
		}
		
		String pE = Student.passwordCheck(p);
		if(!"".equals(pE)){
			req.setAttribute("error", pE);
			return true;
		}
		
		if(c == null || "".equals(c)){
			req.setAttribute("codeE", "请输入验证码");
			return true;
		}else{
			HttpSession ses = req.getSession();
			String rand = (String)ses.getAttribute("rand");
			c = c.toUpperCase();
			if(!c.equals(rand)){
				req.setAttribute("codeE", "验证码不正确");
				return true;
			}
		}
		
		return false;
				
	}
	
	//执行跳转
	private void jump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("student_login.jsp").forward(request, response);
	}
	
}
