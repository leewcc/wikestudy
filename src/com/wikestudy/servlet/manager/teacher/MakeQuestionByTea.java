package com.wikestudy.servlet.manager.teacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.dao.QuestionDao;
import com.wikestudy.model.dao.impl.QuestionDaoImpl;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.QuestionService;

/**
 * Servlet implementation class MakeQuestion
 */
@WebServlet("/dist/jsp/teacher/course/question_make_by_tea")
public class MakeQuestionByTea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeQuestionByTea() {
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
		int secId =Integer.parseInt(request.getParameter("secId"));
		Connection conn=null;
		
		try {
			conn = DBSource.getConnection();
			QuestionService qs=new QuestionService(conn);
			
			if(qs.searchSecId(secId))//检查课时是否已经存在 
			{
				request.getRequestDispatcher("question_page?secId="+secId).forward(request, response);;
			} else {
				request.setAttribute("secId",secId);
				request.getRequestDispatcher("question_make.jsp").forward(request, response);
			}
		} catch (Exception e) {
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
