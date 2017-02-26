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


@WebServlet("/dist/jsp/common/topic/topic_list_get")
public class GetTopicList extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取标签id、搜索类型、页数
		int lid = request.getParameter("labId") == null ? 0 : Integer.parseInt(request.getParameter("labId"));
		int cp = request.getParameter("currentPage") == null ? 1 :Integer.parseInt(request.getParameter("currentPage"));
		int t = request.getParameter("type") == null ? 1 :Integer.parseInt(request.getParameter("type"));	
		
		
		//第二步：	初始化数据库连接和服务
		Connection conn = null;
		TopicService ts = null;
		LabelService ls = null;
		try{
			conn = DBSource.getConnection();
			ts = new TopicService(conn);
			ls = new LabelService(conn);
			
			
		//第三步：	根据话题id获取话题
			Label l = null;
				
			
		//第四步：	获取所有标签
			List<Label> labels = ls.getListV();
			
			if(lid > 0) {
				l = new Label();
				l.setLabId(lid);
				int index = labels.indexOf(l);
				if(index != -1)  
					l = labels.get(index);
				else 
					l = ls.getLabel(lid); 
			}
			
		//第五步：	获取话题
			PageElem<Topic> pe = ts.queryTopicByType(t,lid, cp);
			
			
		//第六步：	将查找回来的内容set进请求，转发到话题列表页面
			request.setAttribute("label", l);
			request.setAttribute("labels", labels);
			request.setAttribute("topics", pe);
			request.getRequestDispatcher("topic_see.jsp").forward(request, response);
		
			
		//最后：		关闭数据库连接
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
