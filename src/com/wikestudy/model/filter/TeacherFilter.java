package com.wikestudy.model.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wikestudy.model.enums.Role;
import com.wikestudy.model.pojo.Teacher;


@WebFilter("/dist/jsp/teacher/*")
public class TeacherFilter implements Filter {

  
    public TeacherFilter() {
        
    }


	public void destroy() {
		
	}
	
	public static void  requestDispathcer(String url, String message,
			ServletRequest request,ServletResponse response) throws ServletException, IOException{
		
		request.setAttribute("url", url);
		request.setAttribute("message", message);
		
		request.getRequestDispatcher("").forward(request, response);
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		
		Teacher t = (Teacher)session.getAttribute("t");
		if(t == null) {
			((HttpServletResponse)response).sendRedirect("/wikestudy/dist/jsp/student/account/student_login.jsp");
		}else
			chain.doFilter(request, response);
	}
		
//		HttpSession session = ((HttpServletRequest)request).getSession();
//		
//		Integer userId = (Integer) session.getAttribute("userId");
//		
//		Role role = (Role)session.getAttribute("role");
//		
//		String url = null;
//		String message = null;
//		// 未登陆状态
//		if (userId == null || userId <= 0 || role == null) {
//			url = "";
//			
//			message = "";
//			
//			TeacherFilter.requestDispathcer(url, message, request, response);
//			
//			return ;
//		} 
//		// 不是老师登陆
//		if (!role.equals(Role.teacher) && !role.equals(Role.supmanager)) {
//			url = "";
//			
//			message = "";
//			
//			TeacherFilter.requestDispathcer(url, message, request, response);
//			
//			return ;
//		}
//		// 根据网址url 和 Servlet 再继续进行权限匹配 
//		
//			
//			
//		chain.doFilter(request, response);
		
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
