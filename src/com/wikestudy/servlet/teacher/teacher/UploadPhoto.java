package com.wikestudy.servlet.teacher.teacher;

import java.io.File;
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

import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.UploadUtil;


@WebServlet("/dist/jsp/teacher/person_data/photo_upload")
public class UploadPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UploadPhoto() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		HttpSession session=request.getSession();
		Map<String, String> map=new HashMap<String,String>();
		Teacher t=(Teacher)session.getAttribute("t");
		StringBuilder sb=new StringBuilder();
		String uuid="t"+t.getTeaId();//伪造一个防止覆盖文件
		sb.append(request.getServletContext().getRealPath("/dist/images/portrait")+File.separator+"temp"+File.separator+uuid);
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
			session.setAttribute("photoUrl", "/wikestudy/dist/images/portrait/temp"
			+File.separator+uuid+File.separator+map.get("fileName"));
			request.getRequestDispatcher("photo_update.jsp").forward(request, response);
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
	}

}
