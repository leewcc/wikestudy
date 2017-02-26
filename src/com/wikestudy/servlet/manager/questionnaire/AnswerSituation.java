package com.wikestudy.servlet.manager.questionnaire;

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


@WebServlet("/dist/jsp/teacher/course/answer_situation")
public class AnswerSituation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public AnswerSituation() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Connection conn=null;
		/*从数据库读所有的数据*/
		QuestionService qs;
		List<Question>  qList = null;
		try {
			int secId=Integer.parseInt(request.getParameter("secId"));
			conn= DBSource.getConnection();
			qs = new QuestionService(conn);
			qList=qs.queryQuestion("节",secId);			
		} catch (Exception e) {
			
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		
		request.setAttribute("option", oList);
		request.setAttribute("judge", jList);
		request.setAttribute("fill", fList);
		request.getRequestDispatcher("answer_situation.jsp").forward(request, response);
	}



}
