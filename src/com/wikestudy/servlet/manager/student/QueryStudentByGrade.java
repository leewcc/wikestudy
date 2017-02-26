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

import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.StudentManagerService;


@WebServlet("/dist/jsp/manager/super/student_manage/student_query_by_grade")
public class QueryStudentByGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//获取选中的年级和页数
		String g = request.getParameter("stuGrade");
		String c = request.getParameter("stuClass");
		c = c == null? "0" : c;
		String cpS = request.getParameter("currentPage");
		int cp = (cpS == null || cpS.equals("")) ? 1 :Integer.parseInt(cpS);
		
		if(!"".equals(g) && !"1".equals(g) && !"2".equals(g) && !"3".equals(g)){
			//年级非法参数，将年级设为1
			g = "1";
		}
		
		Connection conn = null;
		StudentManagerService sms = null;
		
		//根据年级去获取填充了数据的分页对象
		//将对象set进请求
		//跳转到展示页面展示
		try{
			conn = DBSource.getConnection();
			sms = new StudentManagerService(conn);
			PageElem<Student> pe = sms.queryByGradeAndClass(g, c, cp);
			request.setAttribute("pageElem", pe);
			
			request.getRequestDispatcher("student_manage.jsp").forward(request, response);;
			
		}catch(Exception e){
			System.out.println("根据年级获取信息出现异常");
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
