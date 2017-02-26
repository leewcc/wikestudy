package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.NoteManagerService;

/**
 * Servlet implementation class SaveNoteServlet
 */
@WebServlet("/dist/jsp/student/course/note_save")
public class SaveNote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("get");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		System.out.println("post");
		
		String content = request.getParameter("content");
		
		Integer nDId = null;
		// 笔记操作标志 1-保存更改; 2-删除
		String flag = request.getParameter("flag");
		
		
		if (content == null && flag.equals("1")) {
			response.getWriter().write("content is null");
			throw new NullPointerException();
		}
		
		try {
			String id = request.getParameter("id");
			if (id == null)
				throw new NullPointerException();
			
			nDId = Integer.parseInt(id);
		} catch (ClassCastException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			return ;
		} catch (NullPointerException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			return ;
		}
		
		
		Connection conn = null;
		NoteManagerService nms = null;
		
		try {
			conn = DBSource.getConnection();
			nms = new NoteManagerService(conn);
			
			Timestamp d = new Timestamp(System.currentTimeMillis());
			System.out.println(d);
			if (flag.equals("1")) {
				nms.saveNote(nDId, content, d
						);
			} 
			else {
				nms.deleteNote(nDId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		} finally {
			try {
				if (conn != null)
					conn.close();
				else 
					System.out.println("course_note_f conn is null");
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

}
