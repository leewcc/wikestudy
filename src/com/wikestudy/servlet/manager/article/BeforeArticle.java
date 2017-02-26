package com.wikestudy.servlet.manager.article;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

import com.wikestudy.model.pojo.ArticleType;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleTypeService;

/**
 * Servlet implementation class BeforeArticle
 */
@WebServlet("/dist/jsp/manager/article/article_before")
public class BeforeArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger=Logger.getLogger(AddComment.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeforeArticle() {
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

		response.setContentType("text/html");
		ArticleTypeService ats=null;
		System.out.println("返回文章种类");
		Connection conn= null;
		try {
			JSONArray jsonArray=new JSONArray();
			PrintWriter out =response.getWriter();
			conn= DBSource.getConnection();
			ats=new ArticleTypeService(conn);
			List<ArticleType>atList=ats.queryType();
			jsonArray.addAll(atList);
			out.print(jsonArray);
			
//			request.setAttribute("atList", atList);
//			request.getRequestDispatcher("MakeArticle.jsp").forward(request, response);
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			logger.info(e);
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
