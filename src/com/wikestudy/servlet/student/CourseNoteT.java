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
 * Servlet implementation class course_note_t
 */
@WebServlet("/dist/jsp/student/course/course_note_t")
// 以课程为单位查询笔记(查询具体课程笔记，根据时间排序)
public class CourseNoteT extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseNoteT() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Student user = (Student)request.getSession().getAttribute("s");
		
		// 1-章节顺序; 2-时间顺序
		String sortType = request.getParameter("sort");	
		Integer couId = null;
		Integer page = null;
		
		if (sortType == null) {
			sortType = "1";
		}
		try {
			page = Integer.parseInt(request.getParameter("page"));
			if (page <= 0)
				page = 1;
		} catch (Exception e) {
			page = 1;
		}
		try {
			couId = Integer.parseInt(request.getParameter("couId"));
		} catch (Exception e) {
			// 跳到请求参数错误页面
			request.setAttribute("msg", "请求参数错误");
			request.setAttribute("URL", "");
			request.getRequestDispatcher("../../common/Error.jsp").forward(request, response);
		}
		
		
		Connection conn = null;
		NoteManagerService nms = null;
		PageElem<NoteView> pe = null;
		try {
			conn = DBSource.getConnection();
			
			nms = new NoteManagerService(conn);
			
			pe = nms.queryNoteByCou(user.getStuId(), couId, page, sortType);
			
			request.setAttribute("pe", pe);
			request.setAttribute("sort", sortType);
			request.setAttribute("couId", couId);
			
			request.getRequestDispatcher("note_c_check.jsp").forward(request, response);
		} catch (Exception e) {
			//log.debug(e,e.fillInStackTrace());
			System.out.println("course_note_t servlet 异常");
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				else 
					System.out.println("course_note_t conn is null");
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
