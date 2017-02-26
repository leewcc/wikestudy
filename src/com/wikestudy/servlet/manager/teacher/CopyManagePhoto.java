package com.wikestudy.servlet.manager.teacher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.UploadUtil;

/**
 * Servlet implementation class PhotoCopy
 */
@WebServlet("/dist/jsp/manager/page/photo_copy")
public class CopyManagePhoto extends HttpServlet {
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
		Log4JLogger log = new Log4JLogger("log4j.properties");// TODO Auto-generated method stub

		HttpSession session=request.getSession();
		Map<String, String> map=new HashMap<String,String>();
		Teacher t=(Teacher)session.getAttribute("t");
		StringBuilder sb=new StringBuilder();
		String uuid="t"+t.getTeaId();//伪造一个防止覆盖文件
		sb.append(Paths.get(request.getServletContext().getRealPath("/dist/images/portrait"),"temp",uuid).toString());
		String s=sb.toString();
		File fileP=new File(s);
		if (!fileP.exists()) {
			fileP.mkdir();
		}
		map.put("savePath",s);
		UploadUtil u=new UploadUtil(request, session);
		try {
			if(u.uploadFile(map)) {
				System.out.println("后台图图片上传成功");
			}
			session.setAttribute("photoUrl","/wikestudy/dist/images/portrait/temp"+File.separator+uuid+File.separator+map.get("fileName"));
			request.getRequestDispatcher("photo_update.jsp").forward(request, response);
		} catch (Exception e) {
		//	log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		
	}

}
