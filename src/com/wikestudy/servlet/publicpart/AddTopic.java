package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.LabelService;


@WebServlet("/dist/jsp/common/user/user_topic/topic_add")
public class AddTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddTopic() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");

		//第一步：初始化数据库连接、服务
		Connection conn = null;
		LabelService ls = null;
		try{
			conn = DBSource.getConnection();
			ls = new LabelService(conn);
			
		
		//第二步：	获取所有标签类型
			List<Label> labels = ls.getList();
			
			
		//第三步：	将标签set进请求，请求转发到添加话题页面
			request.setAttribute("labels", labels);
			request.getRequestDispatcher("topic_add.jsp").forward(request, response);
			
			
		//最后：关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new ServletException();
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
