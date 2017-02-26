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

import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;
import com.wikestudy.service.manager.TeacherService;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/common/user_center/teacher_articles_see")
public class SeeTeacherArticles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SeeTeacherArticles() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//第一步：	获取用户id、页数
		int uid = Integer.parseInt(request.getParameter("id"));
		boolean mt = true;    //Boolean.parseBoolean(request.getParameter("type"));
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));

		
		//第二步：	获取个人信息,如果看的是自己，则跳转自己的主页
		HttpSession session = request.getSession();
		if(mt){
			if(session.getAttribute("t") != null && ((Teacher)session.getAttribute("t")).getTeaId() == uid){
				response.sendRedirect("../../teacher/common/my_article_querys");
				return;
			}
		}	
		
		
		// 第二步： 初始化数据库连接、服务
		Connection conn = null;
		ArticleService as = null;
		TeacherService ts = null;
		StudentService ss = null;
		try {
			conn = DBSource.getConnection();
			as = new ArticleService(conn);
			ts = new TeacherService(conn);
			ss = new StudentService(conn);
			
		//第三步：	获取用户信息
			if(mt){
				request.setAttribute("user", ts.queryOneTeacher(uid));
			}else {
				request.setAttribute("user", ss.getStudent(uid));
			}
			
			
		// 第四步： 获取他的文章
			PageElem<Article> articles = as.queryByAuthor(uid, cp);

			
		// 第五步： 将数据set进请求，转发到我的文章处
			request.setAttribute("articles", articles);
			request.getRequestDispatcher("his_articles.jsp").forward(request,
					response);

			
		// 最后： 关闭数据库连接
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
