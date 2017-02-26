package com.wikestudy.servlet.student;

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

import com.wikestudy.model.filter.EncodingFilter;
import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.ArticleType;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;
import com.wikestudy.service.manager.ArticleTypeService;

/**
 * Servlet implementation class article_show
 */
@WebServlet("/dist/jsp/common/article/article_show")
public class ShowArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowArticle() {
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
		String cps=(String) request.getParameter("cp");//获的当前页数

		int cp;
		if(cps!=null) {
			if(!EncodingFilter.isNumber(cps)) {
				cp=1;
			}
			else 
				cp=Integer.parseInt(cps);
		} else {
			cp=1;
		}
		if(cp<=0) cp=1;
		System.out.println(cp);
		Connection conn=null;
		try {
			conn= DBSource.getConnection();
			ArticleService as=new ArticleService(conn);
			ArticleTypeService ats=new ArticleTypeService(conn);
			
			List<ArticleType> at=ats.queryType();//展示所有的文章标题
			/*找到想要的类型文章标题和id*/	
			int aTypeId=Integer.parseInt(request.getParameter("aTypeId"));//获得想查找的类型
			PageElem<Article> a=new PageElem<Article>();
			
			a=as.queryAriById(aTypeId, cp);
			int  tp=a.getTotalPage();
			request.setAttribute("aTypeId", aTypeId);
			request.setAttribute("at",at);
			request.setAttribute("cp", cp);//当前页
			request.setAttribute("tp", tp);//总页数
			request.setAttribute("a",a);//文章

			
			

		} catch (Exception e) {
	
			log.debug(e,e.fillInStackTrace());			
		}
		try {
			conn.close();
		} catch (SQLException e) {
			log.debug(e,e.fillInStackTrace());			
			e.printStackTrace();
		}
		request.getRequestDispatcher("article_show.jsp").forward(request, response);
		
	}

}
