package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.dao.impl.LabelDaoImpl;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.util.DBSource;

/**
 * Servlet implementation class QueryCourseByKey
 */
@WebServlet("/dist/jsp/common/course_query_by_key")
public class QueryCourseByKey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryCourseByKey() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		Integer cp = null;
		
		try {
			cp = Integer.parseInt(request.getParameter("cp"));
		} catch (Exception e) {
			cp = 1;
		}
		
		if (cp <= 0) 
			cp = 1;
		
		Connection conn = null;
		PageElem<Course> pe = null;
		CourseDao couDao = null;
		List<Label> list = null;
		try {
			conn = DBSource.getConnection();
			couDao = new CourseDaoImpl(conn);
			pe = new PageElem<Course>();
			pe.setCurrentPage(cp);
			pe.setPageShow(16);
			
			pe = couDao.queryCourseByKey(key, pe);
			
			
			list = new LabelDaoImpl(conn).queryLabelAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		
		request.setAttribute("peCourse", pe);
		request.setAttribute("grade", "0");
		request.setAttribute("type", "2");
		request.setAttribute("labelList", list);
		request.setAttribute("labelId", "0");
		request.setAttribute("cp", pe.getCurrentPage());
		request.setAttribute("tp", pe.getTotalPage());
		request.getRequestDispatcher("select_courseff.jsp").forward(request, response);
	}

}
