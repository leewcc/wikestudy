package com.wikestudy.servlet.manager.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/manager/super/student_manage/student_base_update")
public class UpdateStudentBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Log4JLogger log = new Log4JLogger("log4j.properties");
    public UpdateStudentBase() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取学生id
		// 获取学生学号
		// 获取学生姓名
		// 获取学生年级
		// 获取学生班级
		// 获取学生性别
		// 获取学生个性签名
		int id = Integer.parseInt(request.getParameter("stuId"));
		String name = request.getParameter("stuName");
		String num = request.getParameter("stuNumber");
		String grade = request.getParameter("stuGrade");
		String sclass = request.getParameter("stuClass");
		String gender = request.getParameter("stuGender");
		String sign = request.getParameter("stuSignature");
		
		//创建学生对象,将属性值set进去
		Student s = new Student();
		s.setStuId(id);
		s.setStuName(name);
		s.setStuNumber(num);
		s.setStuGrade(grade);
		s.setStuGender(gender);
		s.setStuClass(sclass);
		s.setStuSignature(sign);
		
		if(check(s, request)){
			request.getRequestDispatcher("student_detail_see").forward(request, response);
			return;
		}
		
		
		Connection conn = null;
		StudentService ss = null;
		
		try{
			conn = DBSource.getConnection();
			ss = new StudentService(conn);
			Student stu1 = ss.getStudent(id);
			Student stu2 = ss.getStudent(num);
			if(stu2 != null && !stu1.getStuNumber().equals(stu2.getStuNumber()) ){
				request.setAttribute("message", "该学号已存在");
			}else{
				
				
				if(ss.updateBasic(s) <= 0)
					request.setAttribute("message", "修改信息失败");
				else
					request.setAttribute("message", "修改信息成功");
			}
			
			response.sendRedirect("student_detail_see?stuId=" + id );
			
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new ServletException();

		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean check(Student s, HttpServletRequest request) {
		String name = s.getStuName();
		String num = s.getStuNumber();
		String cla = s.getStuClass();
		String grade = s.getStuGrade();
		String signature = s.getStuSignature();
		String gender = s.getStuGender();
		boolean hasError = false;
		
		if(name == null || "".equals(name) || "".equals(name.trim())){
			request.setAttribute("name", "名字不能为空");
			hasError = true;
		}else {
			if(name.length() > 50){
				request.setAttribute("name", "姓名长度最大为50个字符");
				hasError = true;
			}
		}
		
		if(num == null || "".equals(num) || "".equals(num.trim())){
			request.setAttribute("num", "学号不能为空");
			hasError = true;
		}else{
			if(num.length() > 20) {
				request.setAttribute("num", "学号长度最大为20个字符");
				hasError = true;
			}
		}
		
		if(cla == null || "".equals(cla))
			s.setStuClass("1");
		
		if(grade == null || "".equals(grade) || "".equals(grade.trim())){
			request.setAttribute("grade", "年级出错");
			hasError = true;
		}
		
		if(gender == null || "".equals(gender)){
			s.setStuGender("male");
		}
		
		if(signature != null && signature.length() > 100){
			request.setAttribute("signature", "个人简介长度最大为100个字符");
			hasError = true;
		}
		
		return hasError;
	}

}
