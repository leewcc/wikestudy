package com.wikestudy.servlet.student;

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

import com.wikestudy.model.filter.EncodingFilter;
import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.CouCommentService;

/**
 * Servlet implementation class GetCouComment
 */
@WebServlet("/dist/jsp/student/course/cou_comment_get")
public class GetCouComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCouComment() {
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
		//获得课时id
		String secIdString=request.getParameter("secId");
		int secId;
		if(secIdString==null||!EncodingFilter.isNumber(secIdString)) {
			secId=1;
		} else {
			secId=Integer.parseInt(secIdString);
		}
		//获得页数
		String cpString =request.getParameter("cp");
		int cp;
		if(cpString==null||!EncodingFilter.isNumber(cpString)) {
			cp=1;
		} else {
			cp=Integer.parseInt(cpString);
		}
		PageElem<NoteDis> nd=null;
		Connection conn = null;
		try {
			conn = DBSource.getConnection();
			CouCommentService ccs=new CouCommentService(conn);
			nd=ccs.queryCouComment(secId, cp);
			
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
		
		PrintWriter out =response.getWriter();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("nd",nd);
		out.print(jsonObject);
		}


}
