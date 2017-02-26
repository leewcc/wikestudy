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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.ColTopic;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.ColTopicService;
import com.wikestudy.service.publicpart.LabelService;


@WebServlet("/dist/jsp/common/user/user_topic/my_col_topics_get")
public class GetMyColTopics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetMyColTopics() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：获取页数数据
		int cp = request.getParameter("currentPage") == null? 1 : Integer.parseInt(request.getParameter("currentPage"));
		
		
		// 第二步：获取Session的用户信息
		HttpSession session = request.getSession();
		boolean ut = (boolean)session.getAttribute("userType");
		int uid = 0;
		if(ut)
			uid = ((Teacher)session.getAttribute("t")).getTeaId();
		else
			uid = ((Student)session.getAttribute("s")).getStuId();
		
		
		//第三步：初始化链接、服务
		Connection conn = null;
		ColTopicService cts = null;
		LabelService ls = null;
		try{
			conn = DBSource.getConnection();
			cts = new ColTopicService(conn);
			ls = new LabelService(conn);
		
		//第四步：根据用户信息获取关注的话题信息	
			PageElem<ColTopic> cols = cts.queryMyCols(cp, uid, ut);
			List<Label> labels = ls.getList();
		
		//第五步：将话题关注信息Set进请求，将请求转发到我的关注页面
			request.setAttribute("labels", labels);
			request.setAttribute("cols", cols);
			request.getRequestDispatcher("my_col_topics.jsp").forward(request, response);
		
			
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
