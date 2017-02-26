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
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Topic;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.LabelService;
import com.wikestudy.service.publicpart.TopicService;


@WebServlet("/dist/jsp/common/topic/topic_query_by_key")
public class QueryTopicByKey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public QueryTopicByKey() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取关键字、页数
		String key = request.getParameter("content");
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		
		//第二步：	判断搜索内容是否为空，为空则不执行搜索，跳转会上一个页面
		if(null == key || "".equals(key)){
			String url = request.getHeader("Referer");
			response.sendRedirect(url.substring(url.indexOf("/wikestudy")));
			return;
		}
		
		
		//第三步：	初始化数据库连接和服务
		Connection conn = null;
		TopicService ts = null;
		LabelService ls = null;
		try{
			conn = DBSource.getConnection();
			ts = new TopicService(conn);
			ls = new LabelService(conn);
			
			
		//第四步：	获取所有标签
			List<Label> labels = ls.getListV();
			
			
		//第五步：	获取满足关键字的话题
			PageElem<Topic> topics = ts.queryByKey(cp, key);
			
		
		//第六步：	将内容set进请求，转发到搜索话题页面
			request.setAttribute("labels", labels);
			request.setAttribute("topics", topics);
			request.getRequestDispatcher("topic_search.jsp").forward(request, response);
			
			
		//最后：		关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
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
