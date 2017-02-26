package com.wikestudy.servlet.manager.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.GraClass;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ClassService;


@WebServlet("/dist/jsp/manager/super/student_manage/class_update")
public class UpdateClass extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取Context里面的初一,初二,初三年级班级对象
		ServletContext context = request.getServletContext();
		GraClass one = (GraClass)context.getAttribute("one");
		GraClass two = (GraClass)context.getAttribute("two");
		GraClass three = (GraClass)context.getAttribute("three");
		
		
		//第二步：	获取前台各年级的班级数
		int oneN = request.getParameter("one").equals("")? one.getClaClassNum() : Integer.parseInt(request.getParameter("one"));
		int twoN = request.getParameter("two").equals("")? two.getClaClassNum() : Integer.parseInt(request.getParameter("two"));
		int threeN =request.getParameter("three").equals("")? three.getClaClassNum() : Integer.parseInt(request.getParameter("three"));
		
		
		//第三步：	初始化数据库连接、服务
		Connection conn = null;
		ClassService cs = null;
		try{
			conn = DBSource.getConnection();
			cs = new ClassService(conn);
			
			
		//第四步：	判断初一班级个数是否有改，则调用更新
			if(one.getClaClassNum() != oneN){
				one.setClaClassNum(oneN);
				cs.update(one);
			}
			
			
		//第五步：	判断初二班级个数是否有改，则调用更新
			if(two.getClaClassNum() != twoN){
				two.setClaClassNum(twoN);
				cs.update(two);
			}
			
			
		//第六步：	判断初三班级个数是否有改，则调用更新
			if(three.getClaClassNum() != threeN){
				three.setClaClassNum(threeN);
				cs.update(three);
			}
			
			
		//	第七步：	重定向到班级管理页面
			request.setAttribute("message", "修改成功");
			request.getRequestDispatcher("class_manage.jsp").forward(request, response);
			
		
		//最后：		关闭数据库连接
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new ServletException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
		
	}

}
