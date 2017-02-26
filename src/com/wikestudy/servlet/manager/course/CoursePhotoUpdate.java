package com.wikestudy.servlet.manager.course;

import java.io.File;
import java.io.IOException;
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

import org.apache.poi.ss.usermodel.Picture;

import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.impl.CourseDaoImpl;
import com.wikestudy.model.pojo.Photo;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.ImageCut;
import com.wikestudy.model.util.PictureTool;

/**
 * Servlet implementation class UpdataCPhoto
 */           
@WebServlet("/dist/jsp/teacher/course/course_photo_update")
public class CoursePhotoUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoursePhotoUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

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

		String uploadPhoto=request.getParameter("uploadPhoto");
		uploadPhoto=Photo.returnUrl(uploadPhoto);
		String fileName=uploadPhoto.substring(uploadPhoto.lastIndexOf(File.separator)+1);
		/*没有坐标的时候*/
		if(null==request.getParameter("x"))
		{
			request.setAttribute("message", "图片错误");
			request.setAttribute("URL","/wikestudy/dist/jsp/manager/page/course_photo_upload");
			request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
			return ;
		}
		//设置存放的路径
		String oldUrl=request.getServletContext().getRealPath("/dist//images/lesson/temp")+File.separator+fileName;
		String imgUrl=request.getServletContext().getRealPath("/dist/images/lesson")+File.separator+fileName;
		int x=Integer.valueOf(request.getParameter("x"));
		int y=Integer.valueOf(request.getParameter("y"));
		int w=Integer.valueOf(request.getParameter("w"));
		int h=Integer.valueOf(request.getParameter("h"));

		Photo p=new Photo(x,y,w,h,300,300,oldUrl,imgUrl);
		
		PictureTool.uploadPicture(p);

		String imageUrl="/wikestudy/dist/images/lesson/"+oldUrl.substring(oldUrl.lastIndexOf(File.separator)+1);
		request.setAttribute("imageUrl",imageUrl);
		String couId=(String)request.getParameter("couId");
		if("".equals(couId)||null==couId||"0".equals(couId)) {
		
			request.getRequestDispatcher("create_course_f").forward(request, response);
			return ;
		} else {
			//修改数据库
			Connection conn=null;
			conn= DBSource.getConnection();
			CourseDao cd=new CourseDaoImpl(conn);
			try {
				if(cd.updateCoursePhoto(Integer.parseInt(couId),fileName)) {
					request.setAttribute("message", "更新图片成功");
				} else {
					request.setAttribute("message", "更新图片失败");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("course_query?couId="+couId);
		}
	}
}
