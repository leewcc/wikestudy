package com.wikestudy.servlet.manager.student;

import java.io.IOException;
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

import com.wikestudy.model.pojo.Student;
import com.wikestudy.service.manager.StudentManagerService;
;


@WebServlet("/dist/jsp/manager/super/student_manage/stu_excel_upload")
public class UploadStudentExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		// 获取文件工厂
		// 通过工厂创建解析器
		// 通过解析request,得到FileItem集合
		request.setCharacterEncoding("utf-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		
		try{
			List<FileItem>  fi = sfu.parseRequest(request);
			
			//判断文件项目集合是否为空
			//不为空，获取第一个文件项目
			//判断是否为文件项，是的话获取它的MIME类型
			//判断是否为Excel文件
			//是，则执行Excel文件解析
			//不是则提醒前台上传文件格式不正确
			if(!fi.isEmpty()){
				FileItem f = fi.get(0);
				
				if(!f.isFormField()){
					System.out.println(f.getContentType());
					String filename = f.getName();
					List<Student> stu = null;
					
					if(filename.endsWith(".xls")){
						//调用业务逻辑执行Excel文件的解析
						stu = StudentManagerService.parseExcel(true, f.getInputStream());
						
					}else if(filename.endsWith(".xlsx")){
						 stu = StudentManagerService.parseExcel(false, f.getInputStream());
												
					}else{
						request.setAttribute("message", "文件上传格式不正确");
						request.setAttribute("URL", "/wikestudy/dist/jsp/manager/super/student_manage/student_see.jsp");
						request.getRequestDispatcher("/dist/jsp/common/Error.jsp").forward(request, response);
						return;
					}
					
					request.setAttribute("students", stu);
					request.getRequestDispatcher("student_see.jsp").forward(request, response);
				}
			}
				
		}catch(Exception e){
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			throw new ServletException();
		}
	}

}
