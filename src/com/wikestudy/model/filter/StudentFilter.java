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

@WebFilter("/dist/jsp/student/*")
public class StudentFilter implements Filter {

	public StudentFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();

		String url = ((HttpServletRequest) request).getRequestURI();
		if (url.indexOf("login") >= 0) {
			chain.doFilter(request, response);
			return;
		}

		if (session.getAttribute("s") == null
				&& session.getAttribute("t") == null) {
			request.setAttribute("message", "您好,您还未登录");
			request.setAttribute("URL",
					"/wikestudy/dist/jsp/student/account/student_login.jsp");
			request.getRequestDispatcher("/dist/jsp/common/error.jsp")
					.forward(request, response);
		} else {

			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
