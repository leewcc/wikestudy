package com.wikestudy.servlet.manager.article;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.ArticleComment;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleCommentService;

/**
 * Servlet implementation class GetComment
 */
@WebServlet("/dist/jsp/manager/article/comment_get")
public class GetComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String artIdString=request.getParameter("artId");	
		int artid=Integer.parseInt(artIdString);
		int cp=Integer.parseInt((String)request.getParameter("cp"));
		Connection conn= null;
		try {
			conn= DBSource.getConnection();
			ArticleCommentService acs=new ArticleCommentService(conn);
			PageElem<ArticleComment> ac=acs.queryAll(artid, cp);			
			int tp=ac.getTotalPage();//总页数
			
			PrintWriter out =response.getWriter();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("p",artid);
			jsonObject.put("tp", tp);
			jsonObject.put("ac", ac);
			jsonObject.put("cp", cp);
			out.print(jsonObject);
			conn.close();
			out.close();
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		int cp=1;//当前页数，只能从前台获得
		doGet(request, response);
	
		
		
	}

}
