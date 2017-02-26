package com.wikestudy.servlet.teacher.teacher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.mysql.jdbc.Field;
import com.wikestudy.model.pojo.Photo;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.ImageCut;
import com.wikestudy.model.util.MyFile;
import com.wikestudy.service.manager.TeacherService;


@WebServlet("/dist/jsp/teacher/person_data/photo_update")
public class UpdatePhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UpdatePhoto() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("photo_update.jsp").forward(request, response);;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String uploadPhoto=request.getParameter("UploadPhoto");
		HttpSession session=request.getSession();
		Teacher t=(Teacher)session.getAttribute("t");
		/*没有坐标的时候*/
		if(null==request.getParameter("x"))
		{
			request.setAttribute("error", "头像错误,请重新上传");
			jumpFalse(request, response);
			return ;
		}
		// 生成伪 UUID
		String alongUrl = "t" + t.getTeaId();
		String filename = uploadPhoto.substring(uploadPhoto.lastIndexOf(File.separator)+1);
		// 设置存放的路径
		String savePath = Paths.get(request.getServletContext().getRealPath("/dist/images/portrait"),alongUrl).toString();
		File fileP=new File(savePath);
		if (!fileP.exists()) {
			fileP.mkdir();
		} else {
			MyFile.delAllFile(savePath);
		}
		savePath=savePath+File.separator+filename;
		// 缓存路径
		String root = Paths.get(request.getServletContext().getRealPath("/dist/images/portrait") , "temp", alongUrl).toString();
		// 缓存文件的路径
		String imageUrl = Paths.get(root , uploadPhoto.substring(uploadPhoto.lastIndexOf("\\") + 1)).toString();
		int x,y,w,h;
		try {
			x = Integer.valueOf(request.getParameter("x"));
			y = Integer.valueOf(request.getParameter("y"));
			w = Integer.valueOf(request.getParameter("w"));
			h = Integer.valueOf(request.getParameter("h"));
		}catch (Exception e) {
			x=0;y=0;w=0;h=0;
		}
		
		//封装成Photo
		Photo p=new Photo(x, y, w, h, 300, 300, imageUrl, savePath);
		ImageCut ic = new ImageCut();
		ic.cutImage(p);
		Connection conn = null;
		try {
			TeacherService ts = null;
			conn = DBSource.getConnection();
			ts = new TeacherService(conn);

			if (ts.updatePhotourl(t.getTeaId(), filename) != 0) {
				System.out.println("上传成功");
				session.removeAttribute("photoUrl");
				t = ts.queryOneTeacher(t.getTeaId());
				t.setTeaPhotoUrl(filename);
				session.setAttribute("t", t);
								
				//清空缓存
				MyFile.delFolder(root);
				
				jumpTrue(request, response);
				return;

			} else {
				request.setAttribute("error", "修改头像失败");
				jumpFalse(request, response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

	private void jumpFalse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("photo_update.jsp").forward(request, response);;
	}
	
	private void jumpTrue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("teacher_update.jsp");
		return ;
	}
}
