package com.wikestudy.servlet.manager.article;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.ArticleType;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;
import com.wikestudy.service.manager.ArticleTypeService;
import com.wikestudy.service.manager.TeacherService;

/**
 * Servlet implementation class OneArticle
 */
@WebServlet("/dist/jsp/manager/article/article_one")
public class OneArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OneArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		try {
			Integer artId=Integer.parseInt(request.getParameter("artid"));
			Integer typeId=Integer.parseInt(request.getParameter("typeid"));
			//获得文章id之后可以拼接
			String url=new StringBuffer("/dist/html/article/"+typeId+"/"+"article_"+artId+".html").toString();
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
	}


}
