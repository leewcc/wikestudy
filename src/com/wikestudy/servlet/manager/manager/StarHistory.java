package com.wikestudy.servlet.manager.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.StarView;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.StarService;


@WebServlet("/history_star")
public class StarHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public StarHistory() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//	前台获取页数
		//前台获取一周之星已展示的个数
		int cp = Integer.parseInt(request.getParameter("currentPage"));
		int star = Integer.parseInt(request.getParameter("hasNum"));
		
		//调用业务查询一周之星的方法
		Connection conn = null;
		StarService ss = null;
		
		try{
			conn = DBSource.getConnection();
			ss = new StarService(conn);
			
			PageElem<StarView> sv = ss.query(cp, star);
			request.setAttribute("stars", sv);
			//返回给前端
			
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
