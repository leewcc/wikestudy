package com.wikestudy.servlet.manager.article;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;

/**
 * Servlet implementation class MakeArticle
 */
@WebServlet("/dist/jsp/manager/article/article_make")
public class MakeArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("atypeId", request.getParameter("atypeId"));
		request.getRequestDispatcher("article_make.jsp").forward(request, response);
	}


}
