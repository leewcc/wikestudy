package com.wikestudy.servlet.manager.course;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.util.SaltValue;

/**
 * Servlet implementation class media_add
 */
@WebServlet("/dist/jsp/teacher/course/media_add")
public class AddMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Log4JLogger log=new Log4JLogger("log4j.properties");
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

			//上传文件
		   	DiskFileItemFactory factory=new DiskFileItemFactory();
	        factory.setSizeThreshold(10*1024*1024);
	        factory.setRepository(new File(request.getServletContext().getRealPath("/dist/videos/temp/temp")));

	        ServletFileUpload upload=new ServletFileUpload(factory);
	        upload.setHeaderEncoding("utf-8");

	        List<FileItem> list= null;
	        try {
	            list = upload.parseRequest(new ServletRequestContext(request));
	        } catch (FileUploadException e) {
	            e.printStackTrace();
	        }
	        if(list.size()<0) {

	        }else {
	            String filename="";
	            //获得名字
/*	            if((filename=(String) request.getSession().getAttribute("filename"))!=null) {
	            	//没事
	            }else {*/
	                for(FileItem item:list) {
		                if("".equals(filename)&&"filename".equals(item.getFieldName())) {
		                    filename=new String(item.getString().getBytes("iso-8859-1"),"utf-8");
		                } 
		          //  }
	                //filename=UUID.randomUUID()+filename.split(".")[1];
	         /*       request.getSession().setAttribute("filename",filename);    */
	            }
	            
	            //缓存文件
	            StringBuffer buffer=new StringBuffer(request.getServletContext().getRealPath("/dist/videos/temp"));
	            buffer.append("/");
	            buffer.append(filename);;

	           
	    		String upFilePath = buffer.toString() ;
	            
	            for(FileItem item:list) {

	                if(!item.isFormField()) {
	                    File file=new File(upFilePath);
	                    OutputStream out;
	                    InputStream in = item.getInputStream() ;
	                    if(file.exists()) {
	                        out=new FileOutputStream(file,true);
	                    }else {
	                        out = new FileOutputStream(file);
	                    }
	                    int length = 0 ;
	                    byte [] buf = new byte[1024] ;
	                    while( (length = in.read(buf) ) != -1){
	                        out.write(buf, 0, length);
	                    }
	                    in.close();
	                    out.close();

	                    item.delete();
	                    break;
	                }
	            }
	        }

		
		
	}

	/**
	 * @功能：验证是否选择了要上传的文件，以及文件大小
	 * @返回：String型值
	 */
/*	private String validateUpLoad(File upfile) {
		String message = "";
		long maxLen = 300 * 1024 * 1024; // 设置允许上传的文件的最大长度为30MB
		if (upfile.isMissing()) { // 没有选择文件
			message += "<li>请选择要上传的视频！</li>";
		} else {
			int len = upfile.getSize();
			if (len == 0)
				message = "<li>不允许上传大小为0的空文件！</li>";
			else if (len > maxLen)
				message = "<li>上传的视频最大应为300MB！</li>";
		}
		return message;
	}
*/


}
