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

import com.wikestudy.model.filter.EncodingFilter;
import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;

/**
 * Servlet implementation class SearchArticle
 */
@WebServlet("/dist/jsp/common/article/article_search")
public class SearchArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Log4JLogger log = new Log4JLogger("log4j.properties");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String artSearch=request.getParameter("artSearch");
		String cpString=request.getParameter("cp");
		int cp=1;
		if(cpString==null||cpString==""||!EncodingFilter.isNumber(cpString)) {
			cp=1;
		} else {
			cp=Integer.parseInt(cpString);
		}
		if(cp<=0) {cp=1;}
		Connection conn=null;
		PageElem<Article> search=new PageElem<Article>();
		try {
			conn = DBSource.getConnection();
			ArticleService as=new ArticleService(conn);
			search=as.queryByTitle(artSearch, cp);
			request.setAttribute("search", search);
			request.setAttribute("artSearch",artSearch );
			search.getTotalPage();
			request.getRequestDispatcher("article_search.jsp").forward(request,response);
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
			
		}
	}

}
