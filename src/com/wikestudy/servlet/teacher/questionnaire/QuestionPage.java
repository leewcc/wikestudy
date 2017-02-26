package com.wikestudy.servlet.teacher.questionnaire;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Question;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.QuestionService;

/**
 * Servlet implementation class QuestionPage
 */
@WebServlet("/dist/jsp/teacher/course/question_page")
public class QuestionPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionPage() {
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

		/*从数据库读所有的数据*/
		QuestionService qs;
		List<Question>  qList = null;
		Connection conn=null;
		int secId=0;
		try {
			conn= DBSource.getConnection();
			qs = new QuestionService(conn);
			try {
			 secId=Integer.parseInt(request.getParameter("secId"));
			}catch(Exception e) {
				log.debug(e,e.fillInStackTrace());
				request.setAttribute("message", "操作失败");
				request.setAttribute("URL", "/dist/jsp/teacher/course/question_page?secId="+secId);
				request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
				return;
			}
			qList=qs.queryQuestion("节",secId);			
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		/*将问题进行分类*/

		/*String str[] = list.toArray(new String[]{}); */
		Iterator<Question> qi= qList.iterator();
		List<Question> oList=new ArrayList<Question>();
		List<Question> jList=new ArrayList<Question>();
		List<Question> fList=new ArrayList<Question>();		
		Question q;
		while(qi.hasNext()) {
			q=qi.next();
			if("option".equals(q.getQueType())) oList.add(q);
			else if("judge".equals(q.getQueType())) jList.add(q);
			else if("fill".equals(q.getQueType())) fList.add(q);			
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}
		request.setAttribute("secId", secId);
		request.setAttribute("options", oList);
		request.setAttribute("judges", jList);
		request.setAttribute("fills", fList);
		request.getRequestDispatcher("question_page.jsp").forward(request, response);
		return ;
	}

}
