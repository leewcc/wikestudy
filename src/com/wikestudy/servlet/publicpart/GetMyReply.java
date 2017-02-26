package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Comment;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.CommentService;
import com.wikestudy.service.publicpart.LabelService;


@WebServlet("/dist/jsp/common/user/user_topic/my_replys_get")
public class GetMyReply extends HttpServlet {
	private static final long serialVersionUID = 1L;
      


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取页数
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		
		//第二步：	获取用户类型
		boolean ut = (boolean) request.getSession().getAttribute("userType");
		
		int uid = 0;
		try {
			if (ut) {
				uid = ((Teacher) request.getSession().getAttribute("t")).getTeaId();
			}
			else {
				uid = ((Student) request.getSession().getAttribute("s")).getStuId();
			}
		}catch (Exception e) {
			
		}

		
		//第三步：	初始化数据库连接和服务
		Connection conn = null;
		CommentService cs = null;
		LabelService ls = null;
		try {
			conn = DBSource.getConnection();
			cs = new CommentService(conn);
			ls = new LabelService(conn);
			
		//第四步：	获取我的回复
			PageElem<Comment> myReplys = cs.getMyReplys(uid, ut, cp);
			List<Label> labels = ls.getList();
			
		//第五步：	将我的回复set进请求里，请求转发到我的回复页面	
			request.setAttribute("labels", labels);
			request.setAttribute("myReplys", myReplys);
			request.getRequestDispatcher("my_replys.jsp").forward(request, response);

			
		//最后：		关闭数据库连接
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
