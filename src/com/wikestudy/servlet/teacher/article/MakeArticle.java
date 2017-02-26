package com.wikestudy.servlet.teacher.article;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dist/jsp/teacher/article/article_make")
public class MakeArticle extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("atypeId", req.getParameter("atypeId"));
		req.getRequestDispatcher("article_make.jsp").forward(req, resp);
	}
}
