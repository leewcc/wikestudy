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

import com.sun.net.httpserver.HttpServer;
import com.wikestudy.model.pojo.Teacher;


@WebFilter("/dist/jsp/manager/*")
public class SuperManagerFilter implements Filter {

   
    public SuperManagerFilter() {
        
    }
    
	
	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String url = ((HttpServletRequest) request).getRequestURI();
		if(url.contains("login.jsp") || url.contains("login")){
			chain.doFilter(request, response);
			return;
		}
		
		Teacher t = (Teacher)((HttpServletRequest)request).getSession().getAttribute("t");
		
		if(t == null){
			request.setAttribute("message", "你还未登陆");
			request.setAttribute("URL", "/wikestudy/dist/jsp/manager/page/login.jsp");
		}else if(!t.isTeaType()){
			request.setAttribute("message", "你不能进行此操作");
			request.setAttribute("URL", "/wikestudy/dist/jsp/student/account/student_login.jsp");
		}else{
			chain.doFilter(request, response);
			return;
		}
		request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
