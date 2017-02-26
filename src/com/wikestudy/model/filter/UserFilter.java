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
import javax.servlet.http.HttpSession;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;


@WebFilter("/dist/jsp/common/user/*")
public class UserFilter implements Filter {

  
    public UserFilter() {
        
    }

	
	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		Teacher t = (Teacher)session.getAttribute("t");
		Student s = (Student)session.getAttribute("s");
		if(t == null && s == null){
			request.setAttribute("message", "对不起,您还未登录");
			request.setAttribute("URL", "/wikestudy/dist/jsp/student/account/student_login.jsp");
			request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
			return;
		}
			
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
