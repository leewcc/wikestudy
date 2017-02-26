package com.wikestudy.servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.filter.EncodingFilter;
import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.student.NoteManagerService;

import net.sf.json.JSONObject;


@WebServlet("/dist/jsp/student/course/notes_get")
public class getNotes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public getNotes() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		
		//获得课时id
		String secIdString=request.getParameter("secId");
		int secId;
		if(secIdString==null||!EncodingFilter.isNumber(secIdString)) {
			secId=0;
		} else {
			secId=Integer.parseInt(secIdString);
		}
		
		int uid = 0;
		HttpSession session = request.getSession();
		Student s = (Student)session.getAttribute("s");
		if(s != null)
			uid = s.getStuId();
		
		PageElem<NoteDis> nd = null;
		Connection conn = null;
		try {
			
			conn = DBSource.getConnection();
			NoteManagerService ns = new NoteManagerService(conn);
			nd = ns.queryBySec(secId);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		PrintWriter out =response.getWriter();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("notes",nd);
		out.print(jsonObject);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
