package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.MessageView;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;
import com.wikestudy.service.publicpart.MessageService;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/common/user_center/message_query")
public class QueryMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取留言板主人数据、页数
		int mid = Integer.parseInt(request.getParameter("id"));
		boolean mt = Boolean.parseBoolean(request.getParameter("type"));
		int cp = request.getParameter("currentPage") == null? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		
		//第二步：	获取个人信息,如果看的是自己，则跳转自己的主页
		HttpSession session = request.getSession();
		if (mt) {
			if (session.getAttribute("t") != null && ((Teacher) session.getAttribute("t")).getTeaId() == mid) {
				response.sendRedirect("../../student/common/my_message_query");
				return;
			}
		} else {
			if (session.getAttribute("s") != null && ((Student) session.getAttribute("s")).getStuId() == mid) {
				response.sendRedirect("../../student/common/my_message_query");
				return;
			}
		}
		
		
		//第二步：	初始化数据库连接和服务
		Connection conn = null;
		MessageService ms = null;
		StudentService ss = null;
		TeacherService ts = null;
		try{
			conn = DBSource.getConnection();
			ss = new StudentService(conn);
			ts = new TeacherService(conn);
			ms = new MessageService(conn);
			
			
		//第三步：	获取用户信息
			if(mt){
				request.setAttribute("user", ts.queryOneTeacher(mid));
			}else {
				request.setAttribute("user", ss.getStudent(mid));
			}
				
			
		//第四步：	获取留言数据
			PageElem<MessageView> mv = ms.query(mid, mt, cp);
				
			
		//第五步：	将留言数据set进请求，将请求转发到留言板页面
			request.setAttribute("messages", mv);
			request.getRequestDispatcher("his_message_borde.jsp").forward(request, response);
		
		
		//最后：		关闭数据库连接
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
