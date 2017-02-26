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

import com.wikestudy.model.pojo.Comment;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.CommentService;


@WebServlet("/dist/jsp/common/user/user_topic/comment_delete")
public class DeleteComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteComment() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取要删除的评论id
		int cid = Integer.parseInt(request.getParameter("id"));
		
		
		//第二步：	初始化数据库连接、服务
		Connection conn = null;
		CommentService cs = null;
		try{
			conn = DBSource.getConnection();
			cs = new CommentService(conn);
			
			
		//第三步：	根据id获取该评论
			Comment c = cs.comment_get(cid);
			
		
		//第四步：	获取用户信息
			HttpSession session = request.getSession();
			int uid = 0;
			boolean ut = (boolean)session.getAttribute("userType");
			if(ut){
				uid = ((Teacher)session.getAttribute("t")).getTeaId();
			}else {
				uid = ((Student)session.getAttribute("s")).getStuId();
			}
			
			
		//第五步：	判断操作的是否为主人,是的话执行删除操作
			if(c != null && c.isComUserEnum() == ut && uid == c.getComUserId()){
				cs.deleteComment(cid);
				String url = request.getHeader("Referer");
				response.sendRedirect(url.substring(url.indexOf("/wikestudy")));
			}else{
				
				
		//第六步：	跳转提示页面
				request.setAttribute("message", "操作失败");
				String url = request.getHeader("Referer");
				request.setAttribute("URL", url.substring(url.indexOf("/wikestudy")));
				request.getRequestDispatcher("/dist/jsp/common/Error.jsp").forward(request, response);			
			}
			
		//最后：	关闭数据库连接
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
