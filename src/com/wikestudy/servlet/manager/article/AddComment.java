package com.wikestudy.servlet.manager.article;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.ArticleComment;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleCommentService;

/**
 * Servlet implementation class ArticleComment
 */
@WebServlet("/dist/jsp/manager/article/comment_add")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		response.setContentType("text/html");
		String []c=request.getParameterValues("artComment");
		String cs=null;
		for(String s:c){
			if((!("".equals(s)))&&null!=s) {
				cs=s;
				System.out.println(cs);
				break;			
				}
		}
		String artid=request.getParameter("artId");
		String artcomid=request.getParameter("acomId");
		if(artcomid==null) {
			artcomid="0";
		}
		
		HttpSession session = request.getSession();
		Teacher t=(Teacher)session.getAttribute("t");
		int sendermark=2;//在session中取
		int senderid=0;//在session中取
		if(t==null) {
			Student s=(Student)session.getAttribute("s");
			if(s==null) {
				
			} else {
				sendermark=0;
				senderid=s.getStuId();
			}
		} else {
			sendermark=1;
			senderid=t.getTeaId();
		}
	
		ArticleComment ac=new ArticleComment();
		ac.setAComArtId(Integer.parseInt(artid));//文章id 
		ac.setAComBinding(Integer.parseInt(artcomid));
		ac.setAComContent(cs);//文章的评论
		ac.setAComSenderId(senderid);
		ac.setAComSenderMark(sendermark);
		ac.setAComTime(new Timestamp(System.currentTimeMillis()));
		System.out.println(ac.toString());
		Connection conn= null;
		try {
			conn= DBSource.getConnection();
			ArticleCommentService acs=new ArticleCommentService(conn);
			acs.addComment(ac);
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
