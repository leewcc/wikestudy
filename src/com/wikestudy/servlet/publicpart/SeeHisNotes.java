package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wikestudy.model.pojo.NoteView;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;
import com.wikestudy.service.publicpart.TopicService;
import com.wikestudy.service.student.NoteManagerService;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/common/user_center/his_notes_see")
public class SeeHisNotes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SeeHisNotes() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid = Integer.parseInt(request.getParameter("id"));
		boolean mt = false;
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		//第二步：	获取个人信息,如果看的是自己，则跳转自己的主页
		HttpSession session = request.getSession();
		
		if (session.getAttribute("s") != null && ((Student) session.getAttribute("s")).getStuId() == uid) {
			response.sendRedirect("../../student/course/course_note_f");
			return;
		}
		
		Connection conn = null;
		NoteManagerService nms = null;
		TeacherService teas = null;
		StudentService ss = null;
		PageElem<NoteView> pe = null;
		try {
			conn = DBSource.getConnection();
			nms = new NoteManagerService(conn);
			ss = new StudentService(conn);
			teas = new TeacherService(conn);
			
			if(mt){
				request.setAttribute("user", teas.queryOneTeacher(uid));
			}else {
				request.setAttribute("user", ss.getStudent(uid));
			}
						
			pe = nms.queryNoteAll(uid, cp);
			
			request.setAttribute("pe", pe);
					
			request.getRequestDispatcher("his_notes.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
