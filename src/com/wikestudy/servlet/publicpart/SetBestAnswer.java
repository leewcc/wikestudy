package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.CommentService;
import com.wikestudy.service.publicpart.TopicService;


@WebServlet("/best_answer_set")
public class SetBestAnswer extends  HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String data = request.getParameter("json");
		JSONObject jData = JSONObject.fromObject(data);
		
		//获取话题id
		//获取评论id
		//获取是否置为最佳回复
		//获取用户类型
		//根据用户类型获取用户id
		int tid = Integer.parseInt(jData.getString("topicId"));
		int cid = Integer.parseInt(jData.getString("comId"));
		boolean isUp = Boolean.parseBoolean(jData.getString("comBest"));
		boolean ut = (boolean)request.getSession().getAttribute("userType");
		
		int uid = 0;
		if(ut){
			uid = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
		}else{
			uid = ((Student)request.getSession().getAttribute("s")).getStuId();
		}
		
		Connection conn = null;
		TopicService ts = null;
		
		try{
			conn = DBSource.getConnection();
			ts = new TopicService(conn);
			
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			
			//调用判断是否话题创建者的主人方法
			//若是创建者,则可以调用设置最佳回复方法
			//若不是,则不可以操作
			if(ts.isCreater(tid, uid, ut)){
				CommentService cs = new CommentService(conn);
				
				//调用设置方法,如果设置失败，则设置错误信息，进行页面跳转
				if(cs.setBestAnswer(tid, cid, isUp) <= 0){
					json.put("message", "error");
				}else{
					json.put("message", "success");
				}
				
			}else{
				json.put("message", "no power");
			}
			out.print(json);
			
		}catch(Exception e){
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
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
