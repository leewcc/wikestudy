package com.wikestudy.servlet.manager.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.StarService;


@WebServlet("/dist/jsp/manager/super/star_manage/star_set")
public class SetStarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SetStarServlet() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		//第一步：	获取要设置为一周之星的学生的学生id
		String[] stuIdS = request.getParameterValues("stuId");
		
		
		//第二步：	判断是否有学生，没有则重定向回上一个页面
		if(stuIdS == null || stuIdS.length <= 0){
			response.sendRedirect(request.getHeader("Referer").substring(16));
			return;
		}
		
		
		//第三步：	初始化数据库连接、服务
		Connection conn = null;
		StarService ss = null;		
		try{
			conn = DBSource.getConnection();
			ss = new StarService(conn);
			
			
		//第四步：	获取当前还可设置一周之星的个数
			int count = 10 - ss.getRows();
			
		
		//第五步：	判断是否还可设置一周之星，不可以则跳转提示页面
			if(count <= 0){
				request.setAttribute("message", "人数已满");
				request.getRequestDispatcher("star_see").forward(request, response);
				return;
			}
			
		
		//第六步：	封装一周之星的学生id
			List<Integer> stuId = new ArrayList<Integer>();
			for(int i = 0; i < stuIdS.length && i < count; i++){
				stuId.add(Integer.parseInt(stuIdS[i]));
			}
			
			
		//第七步：	调用设置一周之星的方法
			ss.set(stuId);
			
			
		//第八步：	重定向到一周之星管理页面
			response.sendRedirect("star_see");
		
			
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
