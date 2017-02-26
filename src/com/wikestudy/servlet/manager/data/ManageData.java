package com.wikestudy.servlet.manager.data;

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

import com.wikestudy.model.pojo.Data;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.DataService;


@WebServlet("/dist/jsp/manager/data/data_manage")
public class ManageData extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取资料绑定id、类型
		int did = Integer.parseInt(request.getParameter("dataBinding"));
		boolean dt = Boolean.parseBoolean(request.getParameter("dataMark"));
		
		
		//第二步：	初始化数据库连接、服务
		Connection conn = null;
		DataService ds = null;
		try{ 
			conn = DBSource.getConnection();
			ds = new DataService(conn);
			
			
		//第三步：	获取资料
			List<Data> datas = ds.queryByBinding(did, dt);
			
			
		//第四步：	将资料set进请求，转到资料管理页面
			request.setAttribute("datas", datas);
			request.getRequestDispatcher("ManageData.jsp").forward(request, response);
		
			
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
