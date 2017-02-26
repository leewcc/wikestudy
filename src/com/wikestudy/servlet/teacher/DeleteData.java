package com.wikestudy.servlet.teacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Data;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.DataService;


@WebServlet("/dist/jsp/teacher/course/data_delete")
public class DeleteData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteData() {
        super();
        
    }

    Log4JLogger log = new Log4JLogger("log4j.properties");

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		String data = request.getParameter("json");
//		JSONObject j = JSONObject.fromObject(data);
//		int did = Integer.parseInt(j.getString("id"));

//		String data = request.getParameter("json");
//		JSONObject j = JSONObject.fromObject(data);
//		int did = Integer.parseInt(j.getString("id"));
//		
//		PrintWriter out = response.getWriter();
//		JSONObject json = new JSONObject();
//		
//		Connection conn = null;
//		DataService ds = null;
//		
//		try{
//			conn = DBSource.getConnection();
//			ds = new DataService(conn);
//			Data d = ds.queryById(did);
//			
//			if(d != null){
//				if(ds.delete(d) <= 0){
//					json.put("message", "error");
//					
//				}else{
//					json.put("message", "success");
//				}
//			}else{
//				json.put("message", "error");
//			}
//			
//			out.println(json);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new ServletException();
//		}finally{
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}

		
		int id = Integer.parseInt(request.getParameter("id"));
		int binding = Integer.parseInt(request.getParameter("binding"));
		boolean type = Boolean.parseBoolean(request.getParameter("type"));
		delete(id, binding, type, request);
		response.sendRedirect(request.getHeader("Referer").substring(request.getHeader("Referer").indexOf("/wikestudy")));
			

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

	private boolean delete(int did, int binding, boolean type, HttpServletRequest request) {
		Connection conn = null;
		DataService ds = null;
		
		try{
			conn = DBSource.getConnection();
			ds = new DataService(conn);
			Data d = ds.queryById(did);
			
			if(d != null){
				if(!(d.getDataBinding() == binding && d.isDataMark() == type)){
					request.setAttribute("message", "非法删除");
					return false;
				}
				
				if(ds.delete(d) <= 0){
					request.setAttribute("message", "删除失败");
					return false;
					
				}else{
					request.setAttribute("message", "删除成功");
					return true;
				}
			}else{
				request.setAttribute("message", "资料不存在");
				return false;
			}
			
		}catch(Exception e){
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			return false;
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
