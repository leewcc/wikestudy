package com.wikestudy.model.dto;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.InputUtil;

@WebServlet("/dist/user_data")
public class SessionData extends HttpServlet {

	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		//这是答题页面，用户必须登陆才可以使用
		if(req.getSession().getAttribute("s")!=null){
			Student user = ((Student)req.getSession().getAttribute("s"));
			int user_id = user.getStuId();
			//用户信息
			String user_name = user.getStuName();
	
			String photo_url=user.getStuPortraitUrl();;
			String user_gender=user.getStuGender().equals("male")? "男生":"女生";
			String user_grade="年级："+user.getGrade();
			String user_class="班级"+user.getStuClass();
			String user_signature=user.getSign();
			String study_time=InputUtil.inputTime(user.getStuStudyHour());
			
			JSONObject data=new JSONObject();
			data.put("user_name", user_name);
			data.put("user_photo", photo_url);
			data.put("user_gender", user_gender);
			data.put("user_grade", user_grade);
			data.put("user_class", user_class);
			data.put("user_signature", user_signature);
			data.put("study_time", study_time);
			
			PrintWriter out=resp.getWriter();
			out.print(data);
			out.flush();
			out.close();
			
		}else{
			resp.sendRedirect("/wikestudy/dist/jsp/student/account/student_login.jsp");	
			return ;
		}
	}

}


