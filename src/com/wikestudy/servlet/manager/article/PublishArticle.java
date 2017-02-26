package com.wikestudy.servlet.manager.article;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.HtmlCreater;
import com.wikestudy.service.manager.ArticleService;
import com.wikestudy.service.manager.ArticleTypeService;

/**
 * Servlet implementation class PublishArticle
 * 发布文章
 */
@WebServlet("/dist/jsp/manager/article/article_publish")
public class PublishArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//获得作者信息
		HttpSession session=request.getSession();
		Teacher t=(Teacher)session.getAttribute("t");
		int teaId=t.getTeaId();
		//获得文章内容
		String artTitle=request.getParameter("artTitle");
		String artContent=request.getParameter("artContent");
		String artType=request.getParameter("artType");
		String message=null;
		if(artTitle==null||artContent==null||artType==null) {
			message="您好,您的操作失败";
		}
		//进行文章封装
		Article a=new Article();
		try {
			a.setArtTypeId(Integer.parseInt(artType));
		} catch (Exception e) {
			//TODO 
		}
		a.setAuthor(t.getTeaName());
		a.setArtAuthorId(teaId);
		a.setArtClick(0);
		a.setArtContent(artContent);
		a.setArtTime(new Timestamp(System.currentTimeMillis()));
		a.setArtTitle(artTitle);
		//存进数据库
		Connection conn=null;
		try {
			conn=DBSource.getConnection();
			ArticleService as=new ArticleService(conn);
			int articleId=0;
			if((articleId=as.insertArticle(a))!=0) {
				message="您好，您的操作成功";
				//查询type_name
				ArticleTypeService ats=new ArticleTypeService(conn);
				String typeName=ats.queryTypeByTypeId(a.getArtTypeId());
				a.setArtType(typeName);
				a.setArtId(articleId);
				//生成静态页面
				String rootUrl=new StringBuffer(request.getServletContext().getRealPath("/dist/html/article")+"/"+artType+"/").toString();
				File file=new File(rootUrl);
				if(!file.exists()) {
					file.mkdir();
				}
				new ArticleCreaterThread(rootUrl, a).start();
			} else {
				message="您好，您的操作失败";
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}
		response.sendRedirect("article_manage");
	}
	
	private class ArticleCreaterThread extends Thread {
		private String rootUrl;
		private Article article;
		
		public ArticleCreaterThread(String rootUrl, Article article) {
			super();
			this.rootUrl = rootUrl;
			this.article = article;
		}

		@Override
		public void run() {
			String fileName=new StringBuffer("article_"+article.getArtId()+".html").toString();
			File file=new File(new StringBuffer(rootUrl+fileName).toString());
			if(file.exists()) {
				file.delete();
			}else {
				//文件夹不存在 创建文件夹
				File rootFile=new File(rootUrl);
				if(!file.exists()) {
					rootFile.mkdir();
				}
			}
			//文件不存在
			//从数据库拿数据
			Log4JLogger log = new Log4JLogger("log4j.properties");
			String url;
			String articleStr="";//拼接文章
			//拼接文章
			Map<String, String> map=new HashMap<String, String>();
			String ftlName="article_one.ftl";
			Map<String,String> root=new HashMap<String,String>();
			root.put("art_id", ""+article.getArtId());
			root.put("art_title",article.getArtTitle());
			root.put("art_time", article.getArtTime());
			root.put("art_author", article.getAuthor());
			
			root.put("art_content", article.getArtContent());
			HtmlCreater.create(rootUrl,fileName,ftlName,root);
				
		}
		
	}
	
}
