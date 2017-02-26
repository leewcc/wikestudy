package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.CourseService;
import com.wikestudy.service.publicpart.LabelService;


@WebServlet("/dist/jsp/common/data/data_center")
public class QueryDataCenter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：获取标签id、课程类型、排序分类、当前页数
		int lid = request.getParameter("labId") == null ? 0 :Integer.parseInt(request.getParameter("labId"));
		String grade = request.getParameter("grade") == null ? "0" : request.getParameter("grade");
		boolean sort = request.getParameter("sort") == null? false : Boolean.parseBoolean(request.getParameter("sort"));
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		
		//第二步：初始化数据库连接、服务
		Connection conn = null;
		CourseService cs = null;
		LabelService ls = null;		
		try{
			conn = DBSource.getConnection();
			cs = new CourseService(conn);
			ls = new LabelService(conn);
			
			
		//第三步：获取所有标签
			List<Label> labels = ls.getListV();
			
			
		//第四步：获取对应的课程
			PageElem<Course> courses = cs.query(lid, grade, sort, cp,true);
			
			
		//第五步：将标签、课程set进请求内，并将请求转发到资料中心页面
			request.setAttribute("labels", labels);
			request.setAttribute("courses", courses);
			request.getRequestDispatcher("data_center.jsp").forward(request, response);
			
		
		//最后：关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new ServletException();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
