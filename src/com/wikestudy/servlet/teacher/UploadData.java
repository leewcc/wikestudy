package com.wikestudy.servlet.teacher;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.impl.Log4JLogger;

import com.sun.glass.ui.Size;
import com.wikestudy.model.pojo.Data;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.DataService;

import net.sf.json.JSONObject;

@WebServlet("/dist/jsp/teacher/course/data_upload")
public class UploadData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UploadData() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024, new File(""));
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(500 * 1024 * 1024);
		int cid = 0;
		Data d = new Data();
		try {
			List<FileItem> fileItemList = sfu.parseRequest(request);
			Iterator<FileItem> it = fileItemList.iterator();
			String couPath = this.getServletContext().getRealPath("/");
			System.out.println(couPath);
			String url = couPath  + "WEB-INF" + File.separator + "course" ;		
			FileItem fi = null;
			while(it.hasNext()) {
				fi = it.next();
				// 判断FileItem对象封装的是普通表单项,还是文件表单项
				// 是普通表单项,
				if (fi.isFormField()) {
					
//					// 获取普通表单项名字
//					// 根据普通表单项名字,讲属性值set进data内
					String name = fi.getFieldName();
					String value = fi.getString("utf-8");
					switch (name) {
//
					case "binding":
						d.setDataBinding(Integer.parseInt(value));
						break;
//
					case "type":
						d.setDataMark(Boolean.parseBoolean(value));
						break;
//
//					case "name":
//						data.setDataName(value);
//						break;
//
					case "course":
						cid = Integer.parseInt(value);
						break;
//
//					default:
//						url += "/" + value;
//						break;
					}
				} else {
					// 获取上传文件的大小
					// 获取文件上传的文件名
					// 如果文件名是绝对路径,则进行路径的切割,保留文件名
					// 将文件保存到 类型id/课程id的文件夹下
					String name = fi.getName();
					long size = fi.getSize();
					if((name == null || "".equals(name)) && size <= 0) {
						break;
					}
					
					int index = (name.lastIndexOf("\\"));

					if (index != -1) {
						name = name.substring(index + 1);
					}
					HttpSession session = request.getSession();
					String course = (String)session.getAttribute("course");
					if(course != null){
						try{
							cid = Integer.parseInt(course);
						}catch(NumberFormatException e){
							cid = 0;
						}
					}
					
					String catalog = url + File.separator + cid;
					File c = new File(catalog);
					if(!c.exists())
						c.mkdirs();
					
					String suffix = name.lastIndexOf(".") != -1 ? name.substring(name.lastIndexOf(".")) : "";
					String root = url + File.separator + cid + File.separator + UUID.randomUUID() + suffix;
//					String root = url  + "123.sql";
					File f = new File(root);
					
					
					fi.write(f);
//					root = root.substring(root.indexOf("WEB-INF"));
					d.setDataRoot(root);
					d.setDataName(name);
					d.setDataSize(size);
					d.setDataDes("");
					inputList(request, d, json);
					json.put("message", "success");
//					if(insert(d) > 0)
//						json.put("message", "success");
//					else
//						json.put("message", "error");
				}
			}

			
			
		}catch (FileSizeLimitExceededException se) {
			se.printStackTrace();
			json.put("message", "overflow");
		}catch (FileUploadException fe) {
			fe.printStackTrace();
			json.put("message", "error");
		}catch (Exception e) {
			e.printStackTrace();	
			json.put("message", "error");
			
			log.debug(e,e.fillInStackTrace());
			
		}	
		
		out.println(json);
//		response.sendRedirect("datas_manage?couId=" + cid + "&binding=" + d.getDataBinding() + "&type=" + d.isDataMark());
	}
	
	private void inputList(HttpServletRequest request, Data d, JSONObject json) {
		HttpSession session = request.getSession();
		List<Data> uploaded = (List<Data>)session.getAttribute("uploaded");
		if(uploaded == null) {
			uploaded = new ArrayList<>();
			session.setAttribute("uploaded", uploaded);
		}
		
		
		uploaded.add(d);
		json.put("mark", uploaded.size());
		return;
	}
	
	private int insert(Data d) {
		Connection conn = null;
		try{
			conn = DBSource.getConnection();
			DataService ds = new DataService(conn);
			return ds.insert(d);
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

}
