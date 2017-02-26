package com.wikestudy.servlet.teacher;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.dao.CouSectionDao;
import com.wikestudy.model.dao.impl.CouSectionDaoImpl;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.QiNiuUtil;


@WebServlet("/dist/jsp/teacher/course/video_upload")
public class UploadVideo extends HttpServlet {
	private static Log4JLogger log =new Log4JLogger("log4j.properties");
	private static final long serialVersionUID = 1L;
       

    public UploadVideo() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024, new File(""));
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(500 * 1024 * 1024);
		int secId = 0;
		int couId = 0;
		String status = "error";
		try {
			List<FileItem> fileItemList = sfu.parseRequest(request);
			Iterator<FileItem> it = fileItemList.iterator();		
			FileItem fi = null;
			
			while(it.hasNext()) {
				fi = it.next();
				// 判断FileItem对象封装的是普通表单项,还是文件表单项
				// 是普通表单项,
				if (fi.isFormField()) {
					
					// 如果是普通表单项，获取课时id,课程id
					String name = fi.getFieldName();
					String value = fi.getString("utf-8");
					
					if("secId".equals(name)) {
						try{
							secId = Integer.parseInt(value);
						}catch(NumberFormatException e) {
							secId = 0;
						}
					}else {
						try{
							couId = Integer.parseInt(value);
						}catch(NumberFormatException e) {
							couId = 0;
						}
					}
					
				} else {
					// 获取文件名，并进行路径截取
					String name = fi.getName();
					if((name == null || "".equals(name))) {
						break;
					}
					
					int index = (name.lastIndexOf("\\"));

					if (index != -1) {
						name = name.substring(index + 1);
					}
				
					// 进行文件名验证，验证数据格式，不通过验证直接不上传
					if(!checkVideo(name)) {
						request.setAttribute("status", "文件格式不正确，请选择正确的视频文件");
						break;
					}
					
					// 生成保存的文件名
					String uuid = secId + UUID.randomUUID().toString();
					
					//获取上传的文件的数据
					byte[] data =  fi.get();
					
					System.out.println(String.format("课程id为%d,课时id为%d,正在开始上传文件，文件名为%s,key为%s\n",couId ,secId, name, uuid));
					log.info(String.format("课程id为%d,课时id为%d,正在开始上传文件，文件名为%s,key为%s\n",couId ,secId, name, uuid));
					
					// 开始上传
					status = QiNiuUtil.upload(data,uuid);
					
					log.info(String.format("key为%s的文件上传状态%s\n",uuid, status));
					System.out.println(String.format("key为%s的文件上传状态%s\n",uuid, status));
					
					// 如果上传成功，则保存信息到数据库
					if(!"error".equals(status)) {
						request.setAttribute("status", "上传视频成功");
						String url = QiNiuUtil.domian + "/" + uuid + ".m3u8";
						updateDB(secId, url);
					}else {
						// 什么都不操作
					} 
					
					
				}
				
			}
		}catch (FileSizeLimitExceededException se) {
			request.setAttribute("status", "上传文件太大，请上传小于500M的视频文件");
		}catch (FileUploadException fe) {
			request.setAttribute("status", "上传出错,请重新上传");
		}catch (Exception e) {
			request.setAttribute("status", "上传出错,请重新上传");
			
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
	
			e.printStackTrace();
		}
		response.sendRedirect("media_upload?secId=" + secId + "&couId=" + couId + "&id=" + status);
	}

	
	private void updateDB(int secId, String url) {
		Connection connection = null;
		try{
			connection = DBSource.getConnection();
			CouSectionDao csd = new CouSectionDaoImpl(connection);
			csd.updateSecVideo(secId, url);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean checkVideo(String name) {
		return name != null && (name.toLowerCase().contains("mp4") || 
				name.toLowerCase().contains("flv") || name.toLowerCase().contains("mp3") || name.toLowerCase().contains("avi"));
	}
}
