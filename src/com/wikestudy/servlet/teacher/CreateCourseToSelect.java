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

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;


@WebServlet("/dist/jsp/teacher/course/course_to_select_create")
public class CreateCourseToSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public CreateCourseToSelect() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	初始化数据库服务、连接
		Connection conn = null;
		CourseManageService cms = null;
		List<Label> labelList = null;	
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
			
			
		//第二步：	查询所有标签
			labelList = cms.queryLabel();
		
			
		//第四步：	标签set进请求，转发到创建课程页面
			request.setAttribute("labels", labelList);
			request.getRequestDispatcher("course_create.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		
		} finally {
			try {
				if (conn != null)
					conn.close();
				else 
					System.out.println("course_create conn is null");
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

}
