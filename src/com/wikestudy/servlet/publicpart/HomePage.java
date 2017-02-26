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
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Star;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.StarService;
import com.wikestudy.service.publicpart.CourseService;


@WebServlet("/dist/jsp/common/home_page")
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HomePage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	初始化数据库连接、服务
		Connection conn = null;
		CourseService cs = null;
		StarService ss = null;
		try{
			conn = DBSource.getConnection();
			cs = new CourseService(conn);
			ss = new StarService(conn);
			
			
		//第二步：	获取热门课程
			PageElem<Course> courses = cs.query(0, "0", false, 1,true);
			
			
		//第三步：	获取一周之星
			List<Star> stars = ss.queryStars();
			
			
		//第四步：	将数据set进请求，请求转发到主页
			request.setAttribute("courses", courses);
			request.setAttribute("stars", stars);
			request.getRequestDispatcher("home_page.jsp").forward(request, response);
			
		
		//最后：		关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
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
