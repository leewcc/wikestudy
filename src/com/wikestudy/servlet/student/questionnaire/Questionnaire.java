package com.wikestudy.servlet.student.questionnaire;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Answer;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.SaltValue;
import com.wikestudy.service.StuScheduleService;
import com.wikestudy.service.student.AnswerService;
import com.wikestudy.service.student.QuestionService;

@WebServlet("/dist/jsp/student/course/questionnaire/questionnaire")
public class Questionnaire extends HttpServlet {

	/**
	 * 获得试卷
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		if(req.getSession().getAttribute("t")!=null) {
			//假如是老师
			req.getRequestDispatcher("/dist/jsp/teacher/course/answer_situation").forward(req, resp);
			return ;
		}
		
		int secId = 0;
		try {
			secId = Integer.parseInt(req.getParameter("secId"));
		} catch (Exception e) {
		}
		Connection conn = null;
		StuScheduleService sss=null;
		// 判断该生是否已经考过试了
		try {
			conn=DBSource.getConnection();
			sss=new StuScheduleService(conn);
			if (sss.checkAnswerStatus(
					((Student) req.getSession().getAttribute("s")).getStuId(),secId)) {
				// 已经回答页面
				resp.sendRedirect("answer_situation?secId=" +req.getParameter("secId"));
				return ;
			}
		} catch (Exception e1) {
			e1.printStackTrace();

			resp.sendRedirect("/wikestudy/dist/jsp/student/account/student_login.jsp");
			return;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				resp.sendRedirect("/wikestudy/dist/jsp/student/account/student_login.jsp");
				return ;
			}
		}

		String url=new StringBuffer("/dist/html/questionnaire/"
						+ SaltValue.getSaltValue("question" + secId)
						+ "/questionnaire_" + secId + ".html").toString();
		File file=new File(url);
		if(!file.exists()) {
			req.getRequestDispatcher("/dist/html/questionnaire/no_questionaire.html").forward(req,resp);
			return;
		}
		req.getRequestDispatcher(url).forward(req, resp);

	}

	/**
	 * 提交试卷
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		AnswerService as=null;
		StuScheduleService sss=null;
		Connection conn=null;
		int secId=0;
		try {
			secId=Integer.parseInt(req.getParameter("secId"));
		}catch(Exception e){
			return ;
		}
		
		conn= DBSource.getConnection();
		as=new AnswerService(conn);
		sss=new StuScheduleService(conn);
		
		// 判断该生是否已经考过试了
		try {
			if (sss.checkAnswerStatus(
					((Student) req.getSession().getAttribute("s")).getStuId(),
				secId)) {
				// 已经回答页面
				resp.sendRedirect("answer_situation?secId=" +req.getParameter("secId"));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//获取学生
		HttpSession session = req.getSession();
		Student s = (Student)session.getAttribute("s");
		int stuId = s.getStuId();
		
		
		List<Answer> pager = new ArrayList<>();
		
		//获取选择题回答
		String option = req.getParameter("optionNum");
		int optionNum = 0;
		try{
			optionNum = Integer.parseInt(option);
		}catch(NumberFormatException e){
			optionNum = 0;
		}
		
		Answer a = null;
		for(int i = 0; i < optionNum; i++) {
			a = installAnswer(req, i + 1, "option");
			a.setStudentId(stuId);
			pager.add(a);
		}
		
		
		//获取判断题回答
		String judge = req.getParameter("judgeNum");
		int judgeNum = 0;
		try{
			judgeNum = Integer.parseInt(judge);
		}catch(NumberFormatException e){
			judgeNum = 0;
		}

		for(int i = 0; i < judgeNum; i++) {
			a = installAnswer(req, i + 1, "judge");
			a.setStudentId(stuId);
			pager.add(a);
		}
		
		
		//获取填空题回答
		String fill = req.getParameter("fillNum");
		int fillNum = 0;
		try{
			fillNum = Integer.parseInt(fill);
		}catch(NumberFormatException e){
			fillNum = 0;
		}

		for(int i = 0; i < fillNum; i++) {
			a = installAnswer(req, i + 1, "fillKey");
			a.setStudentId(stuId);
			pager.add(a);
		}
		
		
		/*整理入数据库*/	
		try {
			if(as.addAnswer(pager)) {
				QuestionService qs=new QuestionService(conn);
	
				if(sss.updateQuestionStatus(stuId,
						Integer.parseInt(req.getParameter("secId")))) {//插入成功
				}
				
				
				
				for(Answer answer : pager) {			
					qs.updateQuesSitu( answer.getQuestionId(), answer.getAnswer());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}
		try {
			conn.close();
		} catch (SQLException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		resp.sendRedirect("answer_situation?secId=" +req.getParameter("secId"));

	}
	
	/**
	 * 答案检查
	 * @param request
	 * @param num
	 * @param type
	 * @return
	 */
	private Answer installAnswer(HttpServletRequest request, int num, String type) {
		int qid = Integer.parseInt(request.getParameter(num + type + "id"));
		String choose = request.getParameter(num + type);
		
		Answer answer = new Answer();
		answer.setQuestionId(qid);
		answer.setAnswer(choose == null ? "" : choose);
		return answer;
	}

}
