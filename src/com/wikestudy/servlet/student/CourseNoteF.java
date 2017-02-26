package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.NoteView;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.NoteManagerService;

/**
 * 分页查询个人全部笔记
 */
@WebServlet("/dist/jsp/student/course/course_note_f")
public class CourseNoteF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CourseNoteF() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		Student user = (Student)request.getSession().getAttribute("s");
		if (user == null ) {
			request.setAttribute("URL", "/wikestudy/dist/jsp/student/account/student_login.jsp");
			request.setAttribute("msg", "用户未登录");
			request.getRequestDispatcher("../../common/error.jsp").forward(request, response);
		}
		
		Integer page = null;
		Integer flag = null;
		try {		
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		try {
			flag = Integer.parseInt(request.getParameter("flag"));
		} catch (Exception e) {
			flag = 1;
		}
		
		
		Connection conn = null;
		NoteManagerService nms = null;
		PageElem<NoteView> pe = null;
		try {
			conn = DBSource.getConnection();
			nms = new NoteManagerService(conn);
			
			pe = nms.queryNoteAll(user.getStuId(), page);
			
			request.setAttribute("pe", pe);
			
			request.setAttribute("flag", flag);
			
			request.getRequestDispatcher("all_note_check.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.debug(e,e.fillInStackTrace());
			}
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
