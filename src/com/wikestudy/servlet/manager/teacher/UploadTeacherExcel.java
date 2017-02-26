package com.wikestudy.servlet.manager.teacher;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;

/**
 * Servlet implementation class TeacherIn
 */
@WebServlet("/dist/jsp/manager/super/student_manage/tea_excel_upload")
public class UploadTeacherExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		DiskFileItemFactory factory =new DiskFileItemFactory();
		ServletFileUpload sfu=new ServletFileUpload(factory);
		List<Teacher> teachers=null;
		Connection conn=null;
		try {
			conn=DBSource.getConnection();
			TeacherService ts=new TeacherService(conn);
			List<FileItem> fi=sfu.parseRequest(request);
			if(!fi.isEmpty()) {
				FileItem f=fi.get(0);
				if(!f.isFormField()) {
					String filename=f.getName();
					if(filename.endsWith(".xls")) {
						//写一个存放excel的业务					
						teachers=ts.parseExcel(".xls",f.getInputStream());			
						request.setAttribute("teacher", teachers);

					}
					else if(filename.endsWith(".xlsx")){
						teachers=ts.parseExcel(".xlsx",f.getInputStream());
						request.setAttribute("teacher", teachers);

					} 
					else {
						request.setAttribute("message", "文件上传格式不正确");
						request.setAttribute("URL", "/wikestudy/dist/jsp/manager/super/student_manage/teacher_show.jsp");
						request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
					}					
				}
				conn.close();
				request.getRequestDispatcher("teacher_show.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}
		
		
		
	}

}
