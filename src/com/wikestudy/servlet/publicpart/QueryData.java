package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.DataView;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.DataService;


@WebServlet("/dist/jsp/common/data/data_query")
public class QueryData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public QueryData() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	
		//第一步：	获取参数
		int id = Integer.parseInt(request.getParameter("id"));
		boolean type = Boolean.parseBoolean(request.getParameter("type"));
		
		//第二步：	获取资料对象
		DataView dv = query(id, type);
	
		if(dv == null) 
			jump404(request, response);
		
		else{
			request.setAttribute("datas", dv);
			jump200(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	private DataView query(int id, Boolean type){
		Connection conn = null;
		Log4JLogger log = new Log4JLogger("log4j.properties");
		try{
			conn = DBSource.getConnection();
			DataService ds = new DataService(conn);
			
			//获取资料目录
			DataView dv = ds.query(id, type);
			
			return dv;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}
	
	private void jump404(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("");
	}
	
	private void jump200(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("datas_page.jsp").forward(request, response);
	}

}
