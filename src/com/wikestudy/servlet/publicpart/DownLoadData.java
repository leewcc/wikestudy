package com.wikestudy.servlet.publicpart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Data;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.DataService;


@WebServlet("/dist/jsp/common/data/data_download")
public class DownLoadData extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//获取资料id
		int did = Integer.parseInt(request.getParameter("dataId"));
		
		Connection conn = null;
		DataService ds = null;
		FileInputStream input = null;
		
		try{
			conn = DBSource.getConnection();
			ds = new DataService(conn);
			
			//根据资料id获取资料
			Data d = ds.queryById(did);
			
			if(d == null) {
				request.setAttribute("message", "该资料不存在");
				request.getRequestDispatcher("QueryData").forward(request, response);
				return;
			}
			
			//获取文件名
			//根据文件名获取Mine类型
			//设置内容头
			String filename = d.getDataRoot();
			
			try{
				input = new FileInputStream(filename);
			}catch(FileNotFoundException ffe) {
				request.setAttribute("message", "该资料不存在");
				request.getRequestDispatcher("data_query").forward(request, response);
				return;
			}
		
					
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			
			String contentType = this.getServletContext().getMimeType(filename);
			
			String name = d.getDataName() + d.getSuffix();
			
			name = new String(name.getBytes("GBK"), "ISO-8859-1");
			
			String contentDisposition = "attachment; filename=" + name;
		
			
			
			//设置头
			response.setHeader("Content-Type", contentType);
			response.setHeader("Content-Disposition", contentDisposition);
			
			ServletOutputStream output = response.getOutputStream();
			
			IOUtils.copy(input, output);
			
			ds.updateDownload(did);
		}catch(Exception e){	
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new ServletException();
		}finally{
			try {
				input.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e,e.fillInStackTrace());
			}
		}
	}

}
