package com.wikestudy.servlet.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.net.httpserver.HttpsConfigurator;
import com.wikestudy.model.pojo.Data;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.InputUtil;
import com.wikestudy.service.publicpart.DataService;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;




@WebServlet("/dist/jsp/teacher/course/data_confirm")
public class ConfirmData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ConfirmData() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Connection conn = null;
//		try{
//			String data = request.getParameter("json");
//			JSONObject j = JSONObject.fromObject(data);
//			
//			//获取数据
//			Data d = install(j, request);
//						
//			PrintWriter out = response.getWriter();
//			JSONObject json = new JSONObject();
//			
//			if(insert(d) > 0){
//				json = JSONObject.fromObject(d);
//			}
//			
//			out.println(json);
//		
//		
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new ServletException("确认资料出错");
//		}finally {
//			
//		}
			//获取资料
//			Data d = getData(request);
//			
//			if(d == null) {
//				request.setAttribute("confirm", "确认出错");
//				request.getRequestDispatcher("datas_manage").forward(request, response);
//				return;
//			}
//			
//			//获取数据
//			install(request,d);
//			
//			if(check(d.getDataName(), request)) {
//				request.getRequestDispatcher("datas_manage").forward(request, response);
//				return;
//			}
//			
//			if(insert(d) <= 0){
//				request.setAttribute("confrim", "上传出错");
//			}else {
//				request.setAttribute("confirm", "上传成功");
//			}
//			
//			request.getRequestDispatcher("datas_manage").forward(request, response);
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		Data d = getData(request);
		
		if(d == null) {
			json.put("message", "error");
		}
		
		//获取数据
		install(request,d);
		String name = d.getDataName();
		if(name == null)
			d.setDataName("");
		else if(name.length() > 50)
			d.setDataName(name.substring(0, 50));
//		if(check(d.getDataName(), request)) {
//			request.getRequestDispatcher("datas_manage").forward(request, response);
//			return;
//		}
		
		if(insert(d) <= 0){
			json.put("message", "error");
		}else {
			json.put("message", "success");
		}
		
		out.println(json);
		
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
	
	private void install(HttpServletRequest request, Data d) throws NumberFormatException{
		
		d.setDataBinding(Integer.parseInt(request.getParameter("binding")));
		d.setDataMark(Boolean.parseBoolean(request.getParameter("type")));
		String url = request.getServletContext().getRealPath("/");
		d.setDataDes("");		
	}
	
	private Data getData(HttpServletRequest request) {
		int index = Integer.parseInt(request.getParameter("index"));
		HttpSession session = request.getSession();
		List<Data> datas = (List<Data>)session.getAttribute("uploaded");
		if(datas != null)
			if(datas.size() < index)
				return null;
			else{	
				return datas.remove(index - 1);
			}
		return null;
	}
	
	private int insert(Data d) {
		Connection conn = null;
		try{
			conn = DBSource.getConnection();
			DataService ds = new DataService(conn);
			return ds.insert(d);
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void jump(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("datas_manage?couId=" + request.getParameter("couId") + "&binding=" + 
	request.getParameter("binding") + "&type=" + request.getParameter("type"));
	}

}
