package com.wikestudy.servlet.manager.article;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.filter.EncodingFilter;
import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;

/**
 * Servlet implementation class ManageAypeArt
 */
@WebServlet("/dist/jsp/manager/article/aype_art_manage")
public class ManageAypeArt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageAypeArt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Connection conn;
		try {
			int typeId=1;
			int cp=1;
			String cpString =request.getParameter("cp");
			if(cpString ==null||!EncodingFilter.isNumber(cpString)) {
				cp=1;
			} else {
				cp=Integer.parseInt(cpString);
			}
			String typeIdString =request.getParameter("typeId");
			if(typeIdString==null||!EncodingFilter.isNumber(typeIdString)) {
				typeId=1;
			} else {
				typeId=Integer.parseInt(typeIdString);
			}
			conn = (Connection)DBSource.getConnection();
			ArticleService as=new ArticleService(conn);
			
			PageElem<Article> pea=new PageElem<Article>();
			pea.setCurrentPage(cp);
			PrintWriter out =response.getWriter();
			pea=as.queryByType(typeId, pea);
			JSONObject j=new JSONObject();
			j.put("pea", pea);
			out.print(j);
			conn.close();
			out.close();
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
	}

}
