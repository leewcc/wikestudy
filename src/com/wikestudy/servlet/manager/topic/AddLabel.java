package com.wikestudy.servlet.manager.topic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.LabelService;

@WebServlet("/dist/jsp/manager/topic/label_add")
public class AddLabel extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		//第一步：	获取标签数据
		String name = request.getParameter("labName");
		String cib = request.getParameter("labCib");
		
		
		//第二步：	判断标签数据是否合理，不合理则跳转提示页面
		if(check(name, cib, request)){
			request.getRequestDispatcher("type_create.jsp").forward(request, response);
			return;
		}
		
		
		//第三步：	组装标签对象
		Label l = new Label();
		l.setLabName(name);
		l.setLabCib(cib);
		
		
		//第四步：	初始化数据库连接、服务
		Connection conn = null;
		LabelService ls = null;
		try {
			conn = DBSource.getConnection();
			ls = new LabelService(conn);

			Label label = ls.getLabel(name);
			
			if(label != null) {
				request.setAttribute("name", "该标签已存在");
				request.getRequestDispatcher("type_create.jsp").forward(request, response);
				return;
			}
			
			// 第五步： 调用添加标签的方法
			ls.add(l);

			// 第六步： 重定向到标签管理
			response.sendRedirect("label_query");

			// 最后： 关闭数据库连接
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e, e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}
		
	private boolean check(String name, String des, HttpServletRequest request) {
		boolean hasError = false;
		if(name == null || "".equals(name) || "".equals(name.trim())){
			request.setAttribute("name", "请输入标签名");
			hasError = true;
		}else {
			if(name.length() > 30){
				request.setAttribute("name", "标签名最大长度为30个字符");
				hasError = true;
			}
		}
		
		if(des != null && des.length() > 150) {
			request.setAttribute("des", "标签描述最大长度为150个字符");
			hasError = true;
		}
		
		return hasError;
		
	}
	

}
