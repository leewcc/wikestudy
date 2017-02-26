package com.wikestudy.servlet.teacher.article;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;


@WebServlet("/dist/jsp/teacher/article/my_article_query")
public class QueryMyArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public QueryMyArticle() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取老师id、页数
		int uid = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		
		//第二步：	初始化数据库连接、服务
		Connection conn = null;
		ArticleService as = null;
		try{
			conn = DBSource.getConnection();
			as = new ArticleService(conn);
			
			
		//第三步：	获取我的文章
			PageElem<Article> articles = as.queryByAuthor(uid, cp);
	
			
		//第四步：	将数据set进请求，转发到我的文章处
			request.setAttribute("articles", articles);
			request.getRequestDispatcher("my_articles.jsp").forward(request, response);
			
			
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
