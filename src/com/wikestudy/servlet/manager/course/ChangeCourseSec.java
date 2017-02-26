package com.wikestudy.servlet.manager.course;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.CouSection;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.CourseInfor;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;

/**
 * Servlet implementation class ChangeCourseServletSec
 * 课程信息修改第二步, 根据课程id查询课程内容 
 * 查询信息：课程Course基本信息;
 *         课程所有章节信息
 *         课程所有课时信息
 */
@WebServlet("/dist/jsp/manager/course/course_sec_change")
public class ChangeCourseSec extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Log4JLogger log = new Log4JLogger("log4j.properties");
		Integer couId = null;
        try {
        	String couIdS = request.getParameter("couId");
        	couId = Integer.parseInt(couIdS);
        	String message=(String)request.getAttribute("message");
        	request.setAttribute("message", message);
        }  catch (NumberFormatException e) {
        	log.debug(e,e.fillInStackTrace());
        	e.printStackTrace();
        	// TODO 错误提示
        }
		
		Connection conn = null;
		CourseManageService cms = null;
		CourseInfor couInfor = null;
		List<Label> labelList = null;
		try {
			conn = DBSource.getConnection();
			cms = new CourseManageService(conn);
			
			couInfor = cms.queryCouByCouId(couId);
			
			// couInfor 为空, 课程信息查询失败 or 课程未创建
			if (couInfor == null) {
				System.out.println("ChangeCourseServletSec couInfor is null");
				request.setAttribute("URL", "course_fir_change");
				request.setAttribute("message", "该课程未创建");
				request.getRequestDispatcher("../../common/topic/error.jsp").forward(request, response);
			}
			
			Course cou = couInfor.getCou();
			
			labelList = cms.queryLabel();
			
			List<CouChapter> chaList = couInfor.getCouChapterList();
			List<CouSection> secList = couInfor.getCouSectionList();
			
			Map<CouChapter, List<CouSection>> map = new LinkedHashMap<CouChapter, List<CouSection>>();
			
			List<CouSection> secListF = null;
			for (CouChapter cc : chaList) {
				secListF = new ArrayList<CouSection>();
				for (CouSection cs : secList) {
					if (cc.getChaId() == cs.getChaId()) {
						secListF.add(cs);
					}
				}
				map.put(cc, secListF);
			}
			
			request.setAttribute("cou", cou);
			request.setAttribute("map", map);
			request.setAttribute("lab", labelList);
			
			String url = null;
			// m- manager; t-teacher
			String urlType = request.getParameter("uT");
			if (urlType != null && urlType.equals("m")) {
				url = "CheckCourseS.jsp";
			} else {
				url = "../../Teacher/Course/course.jsp";
			}
			
			request.getRequestDispatcher(url).forward(request, response);
			
		} catch (Exception e) {
			System.out.println("课程信息查询异常");
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				else 
					System.out.println("ChangeCoursetServletSec conn is null");
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
