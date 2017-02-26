package com.wikestudy.servlet.manager.course;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.CouSectionService;

import jdk.nashorn.internal.ir.RuntimeNode.Request;


@WebServlet("/dist/jsp/teacher/course/media_upload")
public class UploadMedia extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("couId", request.getParameter("couId"));
		request.setAttribute("secId", request.getParameter("secId"));
		
		//查询视频
		Connection conn=DBSource.getConnection();
		CouSectionService css=new CouSectionService(conn);
		String mediaUrl="no video";
		try {
			mediaUrl=css.getMediaUrl(Integer.parseInt(request.getParameter("secId")));
		} catch (Exception e) {
			mediaUrl="no video";
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("mediaUrl", mediaUrl);
		request.getRequestDispatcher("media_upload.jsp").forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	
}
