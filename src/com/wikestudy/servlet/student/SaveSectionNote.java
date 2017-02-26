package com.wikestudy.servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.dao.NoteDisDao;
import com.wikestudy.model.dao.impl.NoteDisDaoImpl;
import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;

import net.sf.json.JSONObject;

/**
 * 看视频记笔记
 */
@WebServlet("/dist/jsp/student/course/section_note_save")
public class SaveSectionNote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveSectionNote() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String content = request.getParameter("content");
		Integer secId = null; 
		
		if (content == null || content.length() > 600 || content.trim().equals("")){
			
		}
		
		try {
			secId = Integer.parseInt(request.getParameter("secId"));
		} catch (Exception e) {
			request.setAttribute("", "参数错误");
			
			return ;
		}
		
		Teacher t = (Teacher) request.getSession().getAttribute("t");
		Student s = (Student) request.getSession().getAttribute("s");
		if (t != null) {
			jsonObject.put("message", "error");
		}else{
		
			Connection conn = null;
			NoteDisDao ndDao = null;
			try {
				NoteDis nDis = new NoteDis();
				nDis.setNDContent(content);
				nDis.setNDReleTime(new Timestamp(System.currentTimeMillis()));
				nDis.setSecId(secId);
				
				nDis.setStuId(s == null ? 0 : s.getStuId());
				
				ndDao = new NoteDisDaoImpl((conn = DBSource.getConnection()));
				
				conn.setAutoCommit(false);
				
				ndDao.insertNoteDis(nDis);
				
				conn.commit();
				jsonObject.put("message", "success");
				
			} catch (Exception e) {
				e.printStackTrace();		
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
		
		
		out.println(jsonObject);
		
	}

}
