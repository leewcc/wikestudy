package com.wikestudy.service.publicpart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.util.DBSource;

import net.sf.json.JSONObject;


@WebServlet("/DeleteColServlet")
public class DeleteColServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteColServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonStr = request.getParameter("json"); 
		
		//JSON的字符串转化成JSON对象
        JSONObject json_data = JSONObject.fromObject(jsonStr);
		
		//获取关注id
		int colId = Integer.parseInt(json_data.getString("colId"));
		
		Connection conn = null;
		ColTopicService cts = null;
		
		try{
			conn = DBSource.getConnection();
			cts = new ColTopicService(conn);
			PrintWriter out =response.getWriter();
			JSONObject json = new JSONObject();
			
			if(cts.delete(colId) <= 0){
				json.put("message", "error");
			}else{
				json.put("message", "success");
			}
			out.print(json);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
