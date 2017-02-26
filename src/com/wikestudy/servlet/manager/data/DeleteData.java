package com.wikestudy.servlet.manager.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Data;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.DataService;


@WebServlet("/dist/jsp/manager/data/data_delete")
public class DeleteData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int did = Integer.parseInt(request.getParameter("dataId"));
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Connection conn = null;
		DataService ds = null;
		
		try{
			conn = DBSource.getConnection();
			ds = new DataService(conn);
			Data d = ds.queryById(did);
			
			if(d != null){
				if(ds.delete(d) <= 0){
					request.setAttribute("message", "删除文件失败");
					
				}else{
					request.setAttribute("message", "删除文件成功");
				}
			}else{
				request.setAttribute("message", "该文件不存在");
			}
			
			request.setAttribute("URL", request.getHeader("Referer"));
			request.getRequestDispatcher("/dist/jsp/common/Error.jsp").forward(request, response);
			
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
