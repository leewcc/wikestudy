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

import com.wikestudy.model.pojo.ColTopic;
import com.wikestudy.model.pojo.CommentView;
import com.wikestudy.model.pojo.Label;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.pojo.Topic;
import com.wikestudy.model.pojo.TopicView;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.ColTopicService;
import com.wikestudy.service.publicpart.CommentService;
import com.wikestudy.service.publicpart.LabelService;
import com.wikestudy.service.publicpart.TopicService;


@WebServlet("/dist/jsp/common/topic/topic_detail_get")
public class GetTopicDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
      


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取话题id、页数
		int tid = Integer.parseInt(request.getParameter("topId"));
		int cp = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
			
		
		//第二步：	初始化数据库连接和服务
		Connection conn = null;
		TopicView tv = new TopicView();
		ColTopicService cts = null;
		CommentService cs = null;
		LabelService ls = null;
		try{
			conn = DBSource.getConnection();
			TopicService ts = new TopicService(conn);
			cs = new CommentService(conn);
			cts = new ColTopicService(conn);
			ls = new LabelService(conn);
			
			
		//第三步：	根据话题id获取话题	
			Topic t = ts.getTopic(tid);
			tv.setTopic(t);
			List<Label> labels = ls.getListV();
		
		
		//第四步：	初始化用户类型、关注标志
			int uid = 0;
			boolean ut = false;
			boolean hasAtten = false;
			
			Object object = request.getSession().getAttribute("userType");
		//第五步：	判断是否存在用户
			if(request.getSession().getAttribute("userType") != null){
				
				
		//第六步：	存在用户则获取用户信息
				ut = (Boolean)request.getSession().getAttribute("userType");
				if(ut)
					uid = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
				else
					uid = ((Student)request.getSession().getAttribute("s")).getStuId();
			
				
		//第七步：	组装话题收藏对象				
				ColTopic col = new ColTopic();
				col.setColTopicId(tid);
				col.setColUserEnum(ut);
				col.setColUserId(uid);
				
				
		//第八步：	获取用户对应话题的关注标志
				hasAtten = cts.hasAtten(col);
			}
			request.setAttribute("hasAtten", hasAtten);		
		
			
		//第九步：	根据当前页数获取话题的评论回复集合
			PageElem<CommentView> cv = cs.comment_gets(tid, cp);
			tv.setComments(cv);

			
		//第十步：	将话题set进请求，转发到话题具体页面
			request.setAttribute("labels", labels);
			request.setAttribute("topic", tv);
			request.getRequestDispatcher("detail_see.jsp").forward(request, response);
			
			
		//最后：		关闭数据库连接
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
			throw new ServletException();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
