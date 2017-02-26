package com.wikestudy.servlet.manager.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.MD5;
import com.wikestudy.service.manager.StudentManagerService;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;


@WebServlet("/dist/jsp/manager/super/student_manage/student_add")
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取学生数据信息
		String[] stuNumbers = request.getParameterValues("stuNumber");
		String[] stuNames = request.getParameterValues("stuName");
		String[] stuGrades = request.getParameterValues("stuGrade");
		String[] stuClas = request.getParameterValues("stuClass");
		int count = stuNumbers.length;
		
		
		//第二步：	初始化学生集合与存放学生信息的变量
		List<Student> students = new ArrayList<Student>();
		String number = "";
		String name = "" ;
		String grade = "" ;
		String cla = ""; 	
		Student stu = null;
		
		
		//第三步：	迭代前台传来的信息，将数据封装到数据变量中
		for(int i = 0; i < count; i++) {
			number = stuNumbers[i];
			name = stuNames[i];
			grade = stuGrades[i];
			cla = stuClas[i];
			
			
		//第五步：	判断当前学生数据是否合理，不合理则跳过，合理则组装学生对象
			if("".equals(number) || "".equals(name) || "".equals(grade)){
				continue;
			}		
			stu = new Student();
			stu.setStuNumber(number);
			stu.setStuName(name);
			stu.setStuGrade(grade);
			stu.setStuClass(cla);
			stu.setStuSignature("");
			stu.setStuPassword(MD5.getMD5Code("123456"));
			students.add(stu);
		}
		
		
		//第六步：	判断学生集合是否有新学生，无则返回原页面，有则继续执行添加
		if(students.isEmpty()){
			request.setAttribute("message", "请添加学生");
			request.getRequestDispatcher("student_see.jsp").forward(request, response);
			return;
		}
		
		
		//第七步：	初始化数据库连接、服务
		StudentManagerService sms = null;
		Connection conn = null;			
			try{				
				conn = DBSource.getConnection();
				sms = new StudentManagerService(conn);
				
		//第七步：	调用导入学生的方法
				List<Student> es = sms.addStudent(students);
				
				
		//第八步：	判断是否有未添加成功的学生，有则返回添加页面，无则跳转到学生管理页面
				if(es.size() > 0) {
					request.setAttribute("message", "学生已存在");
					request.setAttribute("students", es);
					request.getRequestDispatcher("student_see.jsp").forward(request, response);
				}else{
					response.sendRedirect("student_query_by_grade");
				}
					
				
		//最后：		关闭数据库连接
			}catch(Exception e){
				e.printStackTrace();
				log.debug(e,e.fillInStackTrace());
				
				request.setAttribute("message", "添加学生出错，请重新导入");
				request.getRequestDispatcher("student_see.jsp").forward(request, response);
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
