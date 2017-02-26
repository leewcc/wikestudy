package com.wikestudy.servlet.manager.data;

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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Data;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.DataManagerService;


@WebServlet("/dist/jsp/manager/data/data_upload")
public class UploadData extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//获取文件工厂
		//通过工厂创建解析器
		//通过解析request,得到FileItem集合
		DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024, new File(""));
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(300 * 1024 * 1024);
		
		Data data = null;
		
		try{
			List<FileItem> fileItemList = sfu.parseRequest(request);
			Iterator<FileItem> it = fileItemList.iterator();
			String url = "1\\2\\";
			data = new Data();
			FileItem fi = null;
			
			while(it.hasNext()){
				fi = it.next();
				//判断FileItem对象封装的是普通表单项,还是文件表单项
				//是普通表单项,
				if(fi.isFormField()){
					//获取普通表单项名字
					//根据普通表单项名字,讲属性值set进data内
					String name = fi.getFieldName();
					String value = fi.getString("utf-8");
					switch (name) {
					
					case "binding":
						data.setDataBinding(Integer.parseInt(value));
						break;
						
					case "mark":
						data.setDataMark(Boolean.parseBoolean(value));
						break;
						
					case "name":
						data.setDataName(value);
						break;
						
					case "des":
						data.setDataDes(value);
						break;

					default:
						url += "/" + value;
						break;
					}
				}else{
					//获取上传文件的大小
					//获取文件上传的文件名
					//如果文件名是绝对路径,则进行路径的切割,保留文件名
					//将文件保存到 类型id/课程id的文件夹下
					data.setDataSize(fi.getSize());
					String name = fi.getName();
					int index = (name.lastIndexOf("\\"));
					
					if(index != -1){
						name = name.substring(index+1);
					}
					
					String root = this.getServletContext().getRealPath("/") +url + UUID.randomUUID() + name;
					File f = new File(root);
					fi.write(f);
					data.setDataRoot(root);
					
				}
			}
		}
		catch(FileUploadException e){
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}catch(Exception e){
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
	
	
		Connection conn = null;
		DataManagerService dms = null;
		
		try{
			conn = DBSource.getConnection();
			dms = new DataManagerService(conn);
			
			//调用保存资料到数据库
			if(dms.add(data) <= 0){
				request.setAttribute("error", "上传资料失败");
				//页面跳转
			}else{
				//页面跳转
			}
			
		}catch(Exception e){
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());				
				e.printStackTrace();
			}
		}
		
	}

}
