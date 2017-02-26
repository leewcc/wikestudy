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

import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.RelCourseManageService;
import com.wikestudy.service.publicpart.CourseService;


@WebServlet("/dist/jsp/manager/course/course_delete")
// 课程删除, 学生笔记, 课程进度, 学生课表，课程所有评论区，
//关于课程的讨论都被清除。课程资料 课程评论，
//课程视频，学生关于该课程的答题情况，课程章节，课程课时
public class DeleteCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteCourse() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*	Log4JLogger log = new Log4JLogger("log4j.properties");
		Integer couId = null;
		
		couId = Integer.parseInt(request.getParameter("couId"));

		Connection conn = null;
		RelCourseManageService rcms = null;
		
		try {
			conn = DBSource.getConnection();
			conn.setAutoCommit(false);
			rcms = new RelCourseManageService(conn);
			
			if (rcms.delReleCou(couId) != -1) {
				conn.commit();
				request.getRequestDispatcher("course_query_by_laybel_type").forward(request, response);
				return ;
			}
			else {
				conn.rollback();
				request.setAttribute("URL", "/wikestudy/dist/jsp/manager/course/course_query_by_laybel_type?currentPage=");
				request.setAttribute("message", "删除失败");
				request.getRequestDispatcher("../../common/error.jsp").forward(request, response);
				return ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
		}*/
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取课程id
		int cid = Integer.parseInt(request.getParameter("course"));
		
		
		//第二步：	初始化数据库连接、服务
		Connection conn = null;
		CourseService cs = null;
		RelCourseManageService rcms = null;
		
		try{
			conn = DBSource.getConnection();
			conn.setAutoCommit(false);
			
			rcms = new RelCourseManageService(conn);
			
			
		//第四步：	删除课程

			if (rcms.delReleCou(cid) != -1) {
				conn.commit();
				request.getRequestDispatcher("course_query_by_laybel_type").forward(request, response);
				return ;
			}
			else {
				conn.rollback();
				request.setAttribute("URL", "/wikestudy/dist/jsp/manager/course/course_query_by_laybel_type");
				request.setAttribute("message", "删除失败");
				request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
				return ;
			}
				
		//最后：		关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.debug(e,e.fillInStackTrace());
			}
		}
	}

}
