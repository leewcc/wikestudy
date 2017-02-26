package com.wikestudy.servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.NoteManagerService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@WebServlet("/DeleteNote")
public class DeleteNote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteNote() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		String sec = request.getParameter("sec");
		int sid = 0;
		if(sec.matches("/^[1-9]+\\d*$")){
			sid = Integer.parseInt(sec);
		}
		
		int uid = 0;
		HttpSession session = request.getSession();
		Student s = (Student)session.getAttribute("s");
		
		
		if(s == null){
			json.put("message", "error");
			print(out, json);
			return;
		}
		
		Connection conn = null;
		try{
			conn = DBSource.getConnection();
			NoteManagerService ns = new NoteManagerService(conn);
			NoteDis n = ns.queryById(sid);
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

	private void print(PrintWriter out, JSONObject json) {
		out.println(json);
	}
	
	
}
