package com.wikestudy.servlet.manager.course;

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


@WebServlet("/dist/jsp/manager/course/course_query_by_laybel_type")

public class QueryCourseByLabelType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public QueryCourseByLabelType() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		String type = null;
		int lid ;
		int cp;
		//第一步：	获取课程类型、标签id、页数
		try {
			type = request.getParameter("type") == null ? "0" : request.getParameter("type");
		} catch (Exception e) {
			type = "0";
		}
		try{
			lid = Integer.parseInt(request.getParameter("label"));
		} catch (Exception e) {
			lid = 0;
		}
		try {
			cp = Integer.parseInt(request.getParameter("currentPage"));
		} catch (Exception e) {
			cp = 1;
		}
		
		//第二步：	初始化数据库连接、服务
		Connection conn = null;
		CourseService cs = null;
		LabelService ls = null;
		try{
			conn = DBSource.getConnection();
			cs = new CourseService(conn);
			ls = new LabelService(conn);
			
	
		//第三步：	获取课程
 			PageElem<Course> pe = cs.query(lid, type, false, cp, false);
			
			
		//第四步：	获取所有标签
			List<Label> labels = ls.getList();
			
			
		//第四步：	将课程、标签set进请求，转发到课程管理页面
			request.setAttribute("labels", labels);
			request.setAttribute("courses", pe);
//			request.setAttribute("labelId", lid);
//			request.setAttribute("type", type);
			request.getRequestDispatcher("course_manage.jsp").forward(request, response);
			
		
		//最后：		关闭数据库连接	
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

}
