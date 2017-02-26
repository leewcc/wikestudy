package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;

/**
 * Servlet implementation class OneArticle
 */
@WebServlet("/dist/jsp/common/article/article_one")
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		try {
			Connection conn= DBSource.getConnection();
			ArticleService as=new ArticleService(conn);
			Integer artid=Integer.parseInt(request.getParameter("artid"));
			Article article=as.queryById(artid);
			article.getArtTime();
//			ArticleTypeService ats=new ArticleTypeService(conn);
//			a.setArtType(ats.queryById(a.getArtTypeId()).getATypeName());
//			TeacherService ts=new TeacherService(DBSource.getConnection());
//			PageElem<Teacher> pt=ts.queryAllTeacher(a.getArtAuthorId());
//			if(pt==null) {
//				//错误页面
//			}
//			a.setAuthor(pt.getPageElem().get(0).getTeaName());	
			conn.close();
			request.setAttribute("a", article);
			System.out.println(article.getArtTime());
			request.getRequestDispatcher("article_one.jsp").forward(request, response);
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		
	}

}
