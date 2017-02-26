package com.wikestudy.servlet.student.student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Photo;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.ImageCut;
import com.wikestudy.model.util.MyFile;
import com.wikestudy.service.student.StudentService;


@WebServlet("/dist/jsp/student/account/photo_update")
public class UpdatePhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("photo_update.jsp").forward(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String UploadPhoto=request.getParameter("UploadPhoto");
		HttpSession session=request.getSession();
		Student s=(Student)session.getAttribute("s");

		/*没有坐标的时候*/
		if(null==request.getParameter("x"))
		{
			request.setAttribute("message", "头像错误");
			jumpFalse(request, response);
			return ;
		}
		
		StringBuilder filename=new StringBuilder(UUID.randomUUID().toString());
		filename.append(".jpeg");
		StringBuilder alongUrl=new StringBuilder("s");
		alongUrl.append(s.getStuId());
		// 设置存放的路径
		String savePath =(Paths.get(request.getServletContext().getRealPath("/dist/images/portrait"),alongUrl.toString())).toString();
		File fileP=new File(savePath);
		if (!fileP.exists()) {
			fileP.mkdir();
		} else {
			MyFile.delAllFile(savePath);
		}
		savePath=savePath+File.separator+filename;
		// 缓存文件路径
		String root = Paths.get(request.getServletContext().getRealPath("/dist/images/portrait"), "temp" , alongUrl.toString() ).toString();
		// 缓存文件
		String imageUrl =Paths.get( root,UploadPhoto.substring(UploadPhoto.lastIndexOf(File.separator) + 1)).toString();
		int x,y,h,w;
		try {
		x= Integer.valueOf(request.getParameter("x"));
		y = Integer.valueOf(request.getParameter("y"));
		w = Integer.valueOf(request.getParameter("w"));
		h = Integer.valueOf(request.getParameter("h"));
		
		} catch (Exception e) {
			x=0;y=0;w=0;h=0;
		}
		//封装成photo
		Photo p=new Photo(x, y, w, h, 300, 300, imageUrl, savePath);

		ImageCut ic = new ImageCut();
		ic.cutImage(p);
		
		Connection conn = null;
		try {
			conn = DBSource.getConnection();
			StudentService ss = new StudentService(conn);

			if (ss.updatePhotourl(s.getStuId(), filename.toString()) != 0) {
				session.removeAttribute("photoUrl");
				session.removeAttribute("s");
				s = ss.getStudent(s.getStuId());
				s.setStuPhotoUrl(filename.toString());
				session.setAttribute("s", s);
				//删除备份文件
				MyFile.delFolder(root);
				
				jumpTrue(request, response);
				return;
			}else{
				request.setAttribute("error", "修改头像失败");
				jumpFalse(request, response);
				return;
			}
			
			// 清空缓存
			// ClearFile.delAllFile(root);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}

	}
	
	private void jumpFalse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("photo_update.jsp").forward(request, response);
	}
	
	private void jumpTrue(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("my_detail_show.jsp");
	}

}
