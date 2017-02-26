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

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.dev.EFBiffViewer;



@WebFilter("/*")
public class ExceptionTrun implements Filter {


    public ExceptionTrun() {
        
    }


	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Log4JLogger l = new Log4JLogger("log4j.properties");
		try{
			chain.doFilter(request, response);
		}catch(Exception e){
			e.printStackTrace();
			l.debug(e,e.fillInStackTrace());
			request.setAttribute("message", "很抱歉,您的操作出现了错误,我们将会尽快修复该问题");
			String url = ((HttpServletRequest)request).getHeader("Referer");
			request.setAttribute("URL", url.substring(url.indexOf("/wikestudy")));
			request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
