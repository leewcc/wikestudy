package com.wikestudy.servlet.manager.questionnaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dist/jsp/teacher/course/questionnaire/questionnaire_make")
public class PreQuestionnaire extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("secId", req.getParameter("secId"));
		req.getRequestDispatcher("questionnaire_make.jsp").forward(req, resp);
	}
}
