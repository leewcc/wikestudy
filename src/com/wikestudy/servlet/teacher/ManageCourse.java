package com.wikestudy.servlet.teacher;

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


@WebServlet("/dist/jsp/teacher/course/course_manage")
public class ManageCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ManageCourse() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Integer couId = null;
        try {
        	String couIdS = request.getParameter("couId");
        	couId = Integer.parseInt(couIdS);
        }  catch (NumberFormatException e) {
        	e.printStackTrace();
        	log.debug(e,e.fillInStackTrace());
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
			
		//第五步：	跳转到课程管理
	
			request.getRequestDispatcher("course_manage.jsp").forward(request, response);
			
			
		//最后：		关闭数据库
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new 	ServletException();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

}
