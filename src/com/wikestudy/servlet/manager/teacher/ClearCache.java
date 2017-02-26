package com.wikestudy.servlet.manager.teacher;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.util.ClearFile;

/**
 * Servlet implementation class ClearCache
 */
@WebServlet("/dist/jsp/manager/super/star_manage/cache_clear")
public class ClearCache extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearCache() {
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
	
		String path=request.getServletContext().getRealPath("/images/portrait/temp");
		if(ClearFile.delAllFile(path))
		{
			System.out.println("删除文件缓存成功");
		}
		path=request.getServletContext().getRealPath("/temp");
		if(ClearFile.delAllFile(path))
		{
			System.out.println("删除文件缓存成功");
		}
		path=request.getServletContext().getRealPath("/videos/temp");
		if(ClearFile.delAllFile(path))
		{
			System.out.println("删除文件缓存成功");
		}
	}
	
}
