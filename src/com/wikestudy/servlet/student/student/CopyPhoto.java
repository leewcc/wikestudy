package com.wikestudy.servlet.student.student;

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

import com.wikestudy.model.pojo.Photo;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.UploadUtil;


@WebServlet("/dist/jsp/student/account/photo_copy")
public class CopyPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CopyPhoto() {
        super();
     
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		HttpSession session=request.getSession();
		Map<String, String> map=new HashMap<String,String>();
		Student s=(Student)session.getAttribute("s");
		StringBuilder sb=new StringBuilder();
		String uuid="s"+s.getStuId();//伪造一个防止覆盖文件
		String ss=null;
			ss=Paths.get(request.getServletContext().getRealPath("/dist/images/portrait"),"temp",uuid).toString();
		log.info("图片存放的路径"+ss);
		File fileP=new File(ss);
		if (!fileP.exists()) {
			fileP.mkdir();
		}
		map.put("savePath",ss);
		UploadUtil u=new UploadUtil(request, session);
		try {
			if(u.uploadFile(map)) {
				System.out.println("后台图图片上传成功");
			}

//			p.setSaveUrl(Paths.get("/wikestudy/dist/images/portrait/temp",uuid,map.get("fileName")).toString());
			String url = File.separator + "wikestudy" + File.separator + "images" + File.separator + "portrait" + File.separator
					+ "temp" + File.separator + uuid + File.separator + map.get("fileName");
			session.setAttribute("photoUrl", "/wikestudy/dist/images/portrait/temp"
					+File.separator+uuid+File.separator+map.get("fileName"));
			request.getRequestDispatcher("photo_update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}
		
		
	}

}
