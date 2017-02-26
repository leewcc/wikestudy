package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.CouCommentService;

/**
 * Servlet implementation class PutCouComment
 */
@WebServlet("/dist/jsp/student/course/cou_omment_put")
public class PutCouComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PutCouComment() {
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
		int userId=0;//评论者id
		Student s=null;
		HttpSession session=request.getSession();
		
		if(session.getAttribute("t")==null) {
			try {
				s=(Student)session.getAttribute("s");
				userId=s.getStuId();
			} catch (Exception e) {
				request.setAttribute("message", "请登录");
				request.setAttribute("URL", "/wikestudy/dist/jsp/student/account/student_login.jsp");
				request.getRequestDispatcher("/dist/jsp/common/Error.jsp").forward(request, response);
			}
		}
		
		NoteDis noteDis=new NoteDis();
		noteDis.setNDContent((String)request.getParameter("content"));
		System.out.println(request.getParameter("content"));
		noteDis.setNDMark(true); //  0-笔记; 1-评论
		noteDis.setNDReleTime(new Timestamp(System.currentTimeMillis()));
		System.out.println(noteDis.getNDReleTime());
		noteDis.setSecId(Integer.parseInt(request.getParameter("secId")));
		noteDis.setStuId(userId);
		
		//存到数据库中
		try {
			Connection conn=DBSource.getConnection();
			CouCommentService ccs=new  CouCommentService(conn);
			
			ccs.saveCouComment(noteDis);
			conn.close();
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
	}

}
