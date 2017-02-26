package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.MessageView;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.MessageService;


@WebServlet("/dist/jsp/student/common/my_message_query")
public class QueryMyMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public QueryMyMessage() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取页数
		int cp = request.getParameter("currentPage") == null? 1 : Integer.parseInt(request.getParameter("currentPage"));
	
	
		//第二步：	获取用户的数据
		int uid = 0;
		boolean ut = (boolean)request.getSession().getAttribute("userType");
		if(ut){
			uid = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
		}else {
			uid = ((Student)request.getSession().getAttribute("s")).getStuId();
		}
		
		
		//第三步：	初始化数据库连接、服务
		Connection conn = null;
		MessageService ms = null;
		try{
			conn = DBSource.getConnection();
			ms = new MessageService(conn);
			
			
		//第三步：	获取留言数据
			PageElem<MessageView> mv = ms.query(uid, ut, cp);
			
			
		//第四步：	将留言数据set进请求，将请求转发到留言板页面
			request.setAttribute("messages", mv);
			request.getRequestDispatcher("my_message_borde.jsp").forward(request, response);
			
		// 最后： 关闭数据库连接
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new ServletException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

}
