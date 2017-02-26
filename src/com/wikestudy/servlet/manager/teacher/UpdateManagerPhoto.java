package com.wikestudy.servlet.manager.teacher;

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


import com.wikestudy.model.pojo.Photo;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.ImageCut;
import com.wikestudy.model.util.MyFile;
import com.wikestudy.service.manager.TeacherService;

/**
 * Servlet implementation class PhotoUpdata
 */
@WebServlet("/dist/jsp/manager/page/photo_update")
public class UpdateManagerPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String imagePath;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("photo_update.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		imagePath=request.getParameter("uploadPhoto");
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String UploadPhoto=request.getParameter("UploadPhoto");
		HttpSession session=request.getSession();
		Teacher t=(Teacher)session.getAttribute("t");

		/*没有坐标的时候*/
		if(null==request.getParameter("x"))
		{
			request.setAttribute("message", "头像错误，请重新上传");
			jumpFalse(request, response);
			return ;
		}
			//生成伪	UUID
			String alongUrl="t"+t.getTeaId();
			String filename=UploadPhoto.substring(UploadPhoto.lastIndexOf(File.separator)+1);
			//设置存放的路径
			String savePath=Paths.get(request.getServletContext().getRealPath("/dist/images/portrait"),alongUrl).toString();
			File f=new File(savePath) ;
			if(!f.exists()) {
				f.mkdir();
			} else {
				MyFile.delAllFile(savePath);
			}
			savePath=savePath+File.separator+filename;
			//缓存路径
			String root=Paths.get(request.getServletContext().getRealPath("/dist/images/portrait"),"temp",alongUrl).toString();
			//缓存文件的路径
			String imageUrl=Paths.get(root,filename).toString();
			Photo p=null;
			int x,y,w,h;
			try {
				x=Integer.valueOf(request.getParameter("x"));
				y=Integer.valueOf(request.getParameter("y"));
				w=Integer.valueOf(request.getParameter("w"));
				h=Integer.valueOf(request.getParameter("h"));
			} catch (Exception e) {
				x=0;y=0;w=300;h=300;
			}
			//包装成Photo
			 p=new Photo(x, y, w, h, 300, 300, imageUrl, savePath);
			

			ImageCut ic=new ImageCut();
			ic.cutImage(p);
			Connection conn=null;
			try {
				TeacherService ts=null;
				conn=DBSource.getConnection();
				ts=new TeacherService(conn);
				if(ts.updatePhotourl(t.getTeaId(), filename)!=0) {
					session.removeAttribute("photoUrl");	
					t = ts.queryOneTeacher(t.getTeaId());
					//清空缓存
					MyFile.delFolder(root);
					session.setAttribute("t", t);
					jumpTrue(request, response);
				}else{
					request.setAttribute("error", "修改头像失败");
					jumpFalse(request, response);
					return;
				}
			
			} catch (Exception e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}finally {
				try {
					conn.close();
				} catch (SQLException e) {
					log.debug(e,e.fillInStackTrace());
					e.printStackTrace();
				}
			}

	}

	
	private void jumpFalse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("photo_update").forward(request, response);
	}
	
	private void jumpTrue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("manager_information");
	}
}
