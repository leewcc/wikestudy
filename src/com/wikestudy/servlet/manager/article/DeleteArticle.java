package com.wikestudy.servlet.manager.article;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.MyFile;
import com.wikestudy.service.manager.ArticleService;
import com.wikestudy.service.manager.ArticleTypeService;

/**
 * Servlet implementation class DeleteArticle
 */       
@WebServlet("/dist/jsp/manager/article/article_delete")
public class DeleteArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 删除文章或者文章id删除文章
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		Connection conn = null;
		ArticleService as;
		String artIdString=request.getParameter("artid");
		String typeIdString =request.getParameter("typeId");
		if(artIdString !=null) {//删除文章
			int artId=Integer.parseInt(artIdString);
			try {
				conn = DBSource.getConnection();
				as=new ArticleService(conn);
				if(as.deleteById(artId)>0) {
					//删除本地文章
					String fileUrl=request.getServletContext().getRealPath("/dist/html/article/"+typeIdString+"/article_"+artId+".html").toString();
					File file=new File(fileUrl);
					if(file.exists()) {
						file.delete();
					}
				}
				
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
		} else if(typeIdString!=null) {//删除文章id
			try {
				conn = DBSource.getConnection();
				as=new ArticleService(conn);
				int typeId=Integer.parseInt(typeIdString);
				as.deleteByTypeId(typeId);
				ArticleTypeService ats=new ArticleTypeService(conn);
				if(ats.deleteById(typeId)>0) {
					//删除文件
					MyFile.delFolder(request.getServletContext().getRealPath("/dist/html/article/"+typeIdString));
				}
				
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
