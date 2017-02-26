package com.wikestudy.servlet.student;

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

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/student/common/sign_update")
public class UpdateSign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String data = request.getParameter("json");
		JSONObject jData = JSONObject.fromObject(data);
		
		//获取个性签名
		//获取学生id
		
		String s = jData.getString("signature");
		Student stu = (Student)request.getSession().getAttribute("student");
		int id = stu.getStuId();
		
		Connection conn = null;
		StudentService ss = null;
		
		try{
			conn = DBSource.getConnection();
			ss = new StudentService(conn);
			
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			
			
			//调用底层修改个性签名的方法
			//如果返回的数字等于0,则修改不成功
			//如果返回的数字大于0,则修改成功
			if(ss.updateSign(id, s) <= 0){
				json.put("message", "error");
			}else{
				stu.setStuSignature(s);
				json.put("message", "success");
			}
			out.print(json);
			
		}catch(Exception e){
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
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
