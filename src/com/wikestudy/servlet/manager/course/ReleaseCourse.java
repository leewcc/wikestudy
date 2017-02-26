package com.wikestudy.servlet.manager.course;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.CourseService;


@WebServlet("/dist/jsp/manager/course/course_release")
public class ReleaseCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ReleaseCourse() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		//第一步：	获取课程id
		
		int cid = Integer.parseInt(request.getParameter("course"));


		
		//第二步：	初始化数据库连接、服务
		Connection conn = null;
		CourseService cs = null;
		try{
			conn = DBSource.getConnection();
			cs = new CourseService(conn);
			
			
		//第三步：	根据课程id获取课程
			Course c = cs.queryById(cid);
			
		
		//第四步：	根据发布情况更新发布状况
			if(c != null){
				cs.updateRelease(c);
			}
			

		//第五步：	跳转回上一个页面
			request.getRequestDispatcher("course_query_by_laybel_type").forward(request, response);
			
			
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
