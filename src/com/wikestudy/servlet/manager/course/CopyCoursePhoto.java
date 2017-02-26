package com.wikestudy.servlet.manager.course;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;
import org.lxh.smart.SmartUpload;

import com.wikestudy.model.util.UploadUtil;

/**
 * Servlet implementation class CopyCPhoto
 */
@WebServlet("/dist/jsp/teacher/course/course_photo_copy")
public class CopyCoursePhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		String couId;
		try {
			 couId=request.getParameter("couId");
		}catch (Exception e) {
			couId="";
		}
	
		HttpSession session=request.getSession();
		Map<String, String> map=new HashMap<String,String>();
		String s=request.getServletContext().getRealPath("/dist/images/lesson/temp");
		
		map.put("savePath",s);
		
		UploadUtil u=new UploadUtil(request, session);
		try {
			if(u.uploadFile(map)) {
				System.out.println("后台图图片上传成功");
			} else {
				request.setAttribute("message", "图片上传失败");
			}
			session.setAttribute("photoUrl","/wikestudy/dist/images/lesson/temp/"+map.get("fileName"));
			request.setAttribute("couId", couId);
			request.getRequestDispatcher("course_photo_update.jsp").forward(request, response);
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
	}

}
