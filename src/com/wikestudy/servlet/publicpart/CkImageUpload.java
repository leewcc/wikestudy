package com.wikestudy.servlet.publicpart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.impl.Log4JLogger;
/**
 * Servlet implementation class CkImageUpload
 * 
 * 该文件用来上传ckeditor的图片
 */
@WebServlet("/dist/jsp/manager/article/ckImage_upload")
public class CkImageUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
    public CkImageUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload fileUpload=new ServletFileUpload(factory);
		try {
			List<FileItem> list=fileUpload.parseRequest(request);
			for (FileItem item: list) {
				if (item.isFormField()) {
					
			} else {	
        response.setCharacterEncoding("utf-8");  
        PrintWriter out = response.getWriter();  
        // CKEditor提交的很重要的一个参数  
        String callback = request.getParameter("CKEditorFuncNum");  
        String expandedName =""; // 文件扩展名  
        String filename=item.getName();
        String ext=filename.substring(filename.lastIndexOf(".")+1);
        System.out.println(ext);
        if(!ext.contains("jpg")&&!ext.contains("png")&&!ext.contains("gif")&&!ext.contains("jpeg")&&!ext.contains("bmp")) {
        	 out.print("<font color=\"red\" size=\"2\">*文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）</font>");  
        	 return;
        }
        InputStream is = item.getInputStream();  
        //图片上传路径  
        String uploadPath = request.getServletContext().getRealPath("/images/article");  
        String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名  
        fileName += expandedName;  
        File file = new File(uploadPath);  
        if (!file.exists()) { // 如果路径不存在，创建  
            file.mkdirs();  
        }  
        File toFile = new File(uploadPath, fileName);  
        OutputStream os = new FileOutputStream(toFile);  
        byte[] buffer = new byte[1024];  
        int length = 0;  
        while ((length = is.read(buffer)) > 0) {  
            os.write(buffer, 0, length);  
        }  
        is.close();  
        os.close();  
        System.out.println("文章图片");
  
        // 返回"图像"选项卡并显示图片  request.getContextPath()为web项目名   
        out.println("<script type=\"text/javascript\">");  
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback  
                + ",'" + request.getContextPath() + "/images/article/" + fileName + "','')");  
        out.println("</script>");  
			}
			}
		} catch (FileUploadException e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		
	}
	

}
