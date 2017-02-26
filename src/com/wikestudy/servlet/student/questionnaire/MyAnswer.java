package com.wikestudy.servlet.student.questionnaire;

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

import com.wikestudy.model.pojo.Answer;
import com.wikestudy.model.pojo.Question;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.AnswerService;
import com.wikestudy.service.student.QuestionService;

/**
 * Servlet implementation class MyAnswer
 */
@WebServlet("/dist/jsp/student/answer/my_answer")
public class MyAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyAnswer() {
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
		QuestionService qs=null;
		AnswerService as=null;
		List<Question>  qList = null;
		Connection conn=null;
		try {
			 conn= DBSource.getConnection();
			qs = new QuestionService(conn);
			as= new AnswerService(conn);
			qList=qs.queryQuestion("节",Integer.parseInt(request.getParameter("secId")));			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}
		
		/*获得学生id*/
		int stuId=((Student)request.getSession().getAttribute("s")).getStuId();
		
		/*将问题进行分类*/
		Iterator<Question> qi= qList.iterator();
		List<Question> oList=new ArrayList<Question>();
		List<Question> jList=new ArrayList<Question>();
		List<Question> fList=new ArrayList<Question>();		
		Question q;
		while(qi.hasNext()) {
			q=qi.next();
			/*把学生的答案添加进去*/
			int id=q.getQueId();
			try {
				Answer a=as.queryAnswer(stuId, id);//1是学生id
				q.setStuAnswer(a.getAnswer());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if("option".equals(q.getQueType())) oList.add(q);
			else if("judge".equals(q.getQueType())) jList.add(q);
			else if("fill".equals(q.getQueType())) fList.add(q);			
		}
		request.setAttribute("option", oList);
		request.setAttribute("judge", jList);
		request.setAttribute("fill", fList);
		try {
			conn.close();
		} catch (SQLException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		request.getRequestDispatcher("my_answer.jsp").forward(request, response);
	}

}
