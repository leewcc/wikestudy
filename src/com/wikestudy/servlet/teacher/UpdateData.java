package com.wikestudy.servlet.teacher;

import java.io.IOException;
import java.io.PrintWriter;
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


@WebServlet("/dist/jsp/teacher/course/data_update")
public class UpdateData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public UpdateData() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}


	Log4JLogger log = new Log4JLogger("log4j.properties");
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String data = request.getParameter("json");
//		JSONObject j = JSONObject.fromObject(data);
//		
//		int id = Integer.parseInt(j.getString("id"));
//		String name = j.getString("name");
//		
//		Data d = new Data();
//		d.setDataId(id);
//		d.setDataName(name);
//		
//		PrintWriter out = response.getWriter();
//		JSONObject json = new JSONObject();
//		
//		if(update(d) > 0) {
//			json.put("message", "success");
//		}else{
//			json.put("message", "error");
//		}
//		
//		out.println(json);
		
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		
		if(check(name, request)){
			request.getRequestDispatcher("datas_manage").forward(request, response);
			return;
		}
		
		Data d = new Data();
		d.setDataId(id);
		d.setDataName(name);
		
		if(update(d) > 0) {
			request.setAttribute("message", "修改成功");
		}else{
			request.setAttribute("message", "修改失败");
		}
		
		response.sendRedirect(request.getHeader("Referer").substring(request.getHeader("Referer").indexOf("/wikestudy")));
		
	}
	
	private boolean check(String name, HttpServletRequest request){
		boolean hasError = false;
		if(name == null || "".equals(name) || "".equals(name.trim())){
			request.setAttribute("message", "请输入资料名");
			return true;
		}else {
			if(name.length() > 50){
				request.setAttribute("message", "资料名长度最大为50个字符");
				return true;
			}
		}
		
		return false;
			
	}
	
	private int update(Data d) {
		Connection conn = null;
		try{
			conn = DBSource.getConnection();
			DataService ds = new DataService(conn);
			return ds.update(d);
			
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			return 0;
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.debug(e,e.fillInStackTrace());
			}
		}
		
	}

}
