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

import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Record;
import com.wikestudy.model.pojo.Star;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.RecordService;
import com.wikestudy.service.manager.StarService;
import com.wikestudy.service.manager.StudentManagerService;


@WebServlet("/dist/jsp/manager/super/star_manage/star_see")
public class SeeStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpS = request.getParameter("currentPage");
		int cp = cpS == null? 1 : Integer.parseInt(cpS);
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Connection conn = null;
		StarService ss = null;
		StudentManagerService sms= null;
		
		try{
			conn = DBSource.getConnection();
			ss = new StarService(conn);
			sms = new StudentManagerService(conn);
			RecordService rs = new RecordService(conn);
			
			//获取已经设置了的一周之星
			List<Star> stars = ss.query();
			
			//将设置了的一周之星的学生id存放到一个学生集合中
			List<Integer> ids = new ArrayList<Integer>();
			for(Star star : stars){
				ids.add(star.getStarStuId());
			}
			
			PageElem<Record> records = rs.selectRecord(cp, ids);
			
			request.setAttribute("stars", stars);
			request.setAttribute("students", records);
			request.getRequestDispatcher("star_set.jsp").forward(request, response);
			
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
