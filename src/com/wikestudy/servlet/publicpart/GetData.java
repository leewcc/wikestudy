package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.pojo.CourseView;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.DataService;


@WebServlet("/dist/jsp/common/data/data_get")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//第一步：获取课程id
		int cid = Integer.parseInt(request.getParameter("courseId"));
		
		
		//第二步：初始化数据库连接和服务
		Connection conn = null;
		DataService ds = null;		
		try{
			conn = DBSource.getConnection();
			ds = new DataService(conn);
			
				
			//第三步：根据课程id去获取对应资源下的资料结构
			CourseView cv = ds.getCourseCata(cid);
			
			
			//第四步： 如何获取不到课程，则提示课程不存在；
			//				   若获取成功，则将课程资料set进请求，转发到资料目录页面
			if(cv == null){
				request.setAttribute("message", "很抱歉,该课程不存在");
				String url = request.getHeader("Referer");
				request.setAttribute("URL", url.substring(url.indexOf("/wikestudy")));
				request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
			}else{
				request.setAttribute("courseData", cv);
				request.getRequestDispatcher("data_catalog.jsp").forward(request, response);
			}
			
			
		//最后：关闭数据库连接	
		}catch(Exception e){
			e.printStackTrace();
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
