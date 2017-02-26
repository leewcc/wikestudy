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

import com.wikestudy.model.pojo.Message;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.MessageService;


@WebServlet("/dist/jsp/student/common/message_delete")
public class DeleteMessageServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteMessageServelt() {
        super();
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取留言id
		int messId = Integer.parseInt(request.getParameter("messId"));
		
		
		//第二步：	获取用户类型
		boolean  ut = (Boolean)request.getSession().getAttribute("userType");
		int uid = 0;
		if (ut) {
			Teacher t = (Teacher) request.getSession().getAttribute("t");
			uid = t.getTeaId();
		} else {
			Student s = (Student) request.getSession().getAttribute("s");
			uid = s.getStuId();
		}
		
		
		//第三步：	初始化数据库连接、服务
		Connection conn = null;
		MessageService ms = null;		
		try{
			conn = DBSource.getConnection();
			ms = new MessageService(conn);
		
		
		//第四步：	获取留言	
		Message m = ms.queryById(messId);
			
		
		//第五步：	判断用户是否是留言版的主人
		if(m.isMessMasterMark() == ut && m.getMessMasterId() == uid){
			
			
		//第六步：	是主人操作的话，则执行删除操作，重定向到上一个页面	
			ms.delete(messId);
			request.setAttribute("message", "删除成功");
			response.sendRedirect(request.getHeader("Referer").substring(16));
		}else{
			
			
		//第六步：	不是主人则提醒不能操作
			request.setAttribute("message", "对不起，你不能删除该留言");
			request.setAttribute("URL", request.getHeader("Referer"));
			request.getRequestDispatcher("").forward(request, response);
			request.getRequestDispatcher("/dist/jsp/common/Error.jsp").forward(request, response);
		} 
			
						
		//最后：		关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
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
