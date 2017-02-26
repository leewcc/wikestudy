package com.wikestudy.servlet.student;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.dao.StuCourseDao;
import com.wikestudy.model.dao.impl.StuCourseDaoImpl;
import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.CouSection;
import com.wikestudy.model.pojo.CourseInfor;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;


@WebServlet("/dist/jsp/student/course/media_show")
public class MediaShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MediaShow() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Connection conn;
		//没有选
	
		
		
		//获得课程的id
		int couId=Integer.parseInt(request.getParameter("couId"));
		
		Teacher t = ((Teacher)request.getSession().getAttribute("t"));
	    
		Student s = (Student)request.getSession().getAttribute("s");
		
		boolean isStu = true;
		if (s == null && t == null) {
			response.sendRedirect("/wikestudy/dist/jsp/common/course_select?couId=" + couId);	
			return ;
		} else if (s == null) {
			// 是老师
			isStu = false;
		} else if (t == null) {
			// 是学生
			
		}
		
		
	
		//获得要播放的课时id
		int secId=Integer.parseInt(request.getParameter("secId"));
		//获得要播放的章节
		int chaId=Integer.parseInt(request.getParameter("chaId"));
		request.getAttribute("c");
		try {
			conn = DBSource.getConnection();
			StuCourseDao scd = new StuCourseDaoImpl(conn);
			if (isStu) {
				int stuId = s.getStuId();
				if(scd.isSelected(stuId, couId) <= 0) {
					response.sendRedirect("/wikestudy/dist/jsp/common/course_select?couId=" + couId);	
					return ;
				}
			}
			
			CourseManageService cms=new CourseManageService(conn);
			
			CourseInfor c=cms.queryCouByCouId(couId);
			/*		获得课程所有的对象
			获得一个章节的对象
			CouChapter cc=cms.queryCouChapterById(chaId);
			获得一个课时对象
			CouSection cs=cms.queryCouSectionById(secId);*/
			
			CouChapter cc=new CouChapter();
			CouSection cs=new CouSection();
			
			cms.queryCouMedia(chaId,secId,cc,cs);
			
			
			//在前台对章节课时信息进行合并显示
			conn.close();
			/*把三者以前传输到前台*/
			if("".equals(cs.getSecVideoUrl())||null==cs.getSecVideoUrl()) {
						
				request.setAttribute("message", "当前没有视频");
				cs.setSecVideoUrl("");
			}
			request.setAttribute("c", c);
			request.setAttribute("cc", cc);
			request.setAttribute("cs", cs);
			
			request.getRequestDispatcher("media_show.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
