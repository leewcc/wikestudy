package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.NoteCourse;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.NoteManagerService;

/**
 * Servlet implementation class course_note_s
 */
@WebServlet("/dist/jsp/student/course/course_note_s")
// 查询笔记——根据课程
public class CourseNoteS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseNoteS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		Student user = (Student)request.getSession().getAttribute("s");
		
		Connection conn = null;
		NoteManagerService nms = null;
		
		List<NoteCourse> ncList = null;
		try {
			conn = DBSource.getConnection();
			nms = new NoteManagerService(conn);
			
			ncList = nms.querycourse_note_sort(user.getStuId());
			
			
			request.setAttribute("ncList", ncList);
			
			request.getRequestDispatcher("note_s_check.jsp").forward(request, response);
			
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					log.debug(e,e.fillInStackTrace());
				}
		}
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
