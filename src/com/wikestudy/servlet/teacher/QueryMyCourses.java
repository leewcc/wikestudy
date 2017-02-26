package com.wikestudy.servlet.teacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.StaticBucketMap;
import org.omg.CORBA.PRIVATE_MEMBER;

import com.sun.istack.internal.logging.Logger;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;
import com.wikestudy.service.publicpart.LabelService;


@WebServlet("/dist/jsp/teacher/common/my_courses_query")
public class QueryMyCourses extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public QueryMyCourses() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger logger=Logger.getLogger(QueryMyCourses.class); 
		
		int type = 1;
		int cp = 1;
		//第一步：	获取搜索类型、页数
		try{
			type  = request.getParameter("type") == null ? 1 : Integer.parseInt(request.getParameter("type"));
			cp = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		}catch(NumberFormatException e) {
			type = 1;
			cp = 1;
		}
	
	
		//第二步：	获取老师
		Teacher tea = (Teacher) request.getSession().getAttribute("t");

		
		//第三步：	初始化数据库连接、服务
		Connection conn = null;
		CourseManageService cms = null;
		LabelService ls = null;
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
			ls = new LabelService(conn);
			
			
		//第四步：	获取标签
			List<Label> labels = ls.getListV();
			
			
		//第五步：	获取我的课程
			PageElem<Course> pe = cms.queryCouListByTea(tea.getTeaId(), type, cp);
			
			
		//第五步：	将课程set进去，转发到我的课程页面
			request.setAttribute("labels", labels);
			request.setAttribute("courses", pe);
			request.getRequestDispatcher("my_course.jsp").forward(request, response);
		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		} finally {
			try {
				if (conn != null) 
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
