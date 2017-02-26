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


@WebServlet("/dist/jsp/manager/topic/label_update")
public class UpdateLabel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UpdateLabel() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取标签id，和标签数据
		int lid = Integer.parseInt(request.getParameter("labId"));
		String des = request.getParameter("labDes");
		if(des != null && des.length() > 150)
			des = des.substring(0,149);
		else if(des == null)
			des = "";
		
		//第三步：	组装标签对象
		Label l = new Label();
		l.setLabCib(des);
		l.setLabId(lid);
		
		
		// 第四步： 初始化数据库连接、服务
		Connection conn = null;
		LabelService ls = null;
		try {
			conn = DBSource.getConnection();
			ls = new LabelService(conn);

		//第五步：	调用更新标签的方法
			ls.update(l);
			
			
		//第六步：	返回上一个页面
			String url = request.getHeader("Referer");
			response.sendRedirect(url.substring(url.indexOf("/wikestudy")));
			
			
		// 最后： 	关闭数据库连接
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
			throw new ServletException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
				
	}

}
