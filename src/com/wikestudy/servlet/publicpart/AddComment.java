package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.wikestudy.model.pojo.Comment;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.CommentService;

import jdk.nashorn.internal.ir.RuntimeNode.Request;


@WebServlet("/dist/jsp/common/user/user_topic/comment_add")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//第0步：	获取回复内容，判断是否为空，为空则返回上一页面
		//第0步：	获取回复内容，判断是否为空，为空则返回上一页面
				Connection conn = null;
				CommentService cs = null;	
							
				String content = request.getParameter("content");
					
				if(check(content, request)){
					jumpFasle(request, response);
					return;
				}
						 
				try{
					//第一步：	获取前台数据组装评论
					Comment c = installCommentFromView(request, content);
				
				
				//第四步：	初始化数据库连接和服务	
				
					conn = DBSource.getConnection();
					cs = new CommentService(conn);
					
					
				//第四步：	调用插入评论的方法
					if(cs.add(c) <= 0){
						request.setAttribute("message", "评论失败");
						jumpFasle(request, response);
					}else{
						jumpTrue(request, response);
					}
					

					
					
				//最后：		关闭数据库连接
				}catch(Exception e){
					e.printStackTrace();
					throw new ServletException();
				}finally{
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}		
			}
			
			
			private boolean check(String content, HttpServletRequest request){
				if(content == null || "".equals(content) || "".equals(content.trim())) {
					request.setAttribute("message", "请输入你要回复的信息");
					return true;
				}else if(content.length() > 200){
					request.setAttribute("message", "信息长度只能在200字符以内");
					return true;
				}
				
				return false;
			}
			
			private void jumpFasle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				String url = request.getHeader("Referer");
				request.setAttribute("URL", url.substring(url.indexOf("/wikestudy")));
				request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
			}
			
			private void jumpTrue(HttpServletRequest request, HttpServletResponse response) throws IOException {
				String url = request.getHeader("Referer");
				response.sendRedirect(url.substring(url.indexOf("/wikestudy")));
			}
			
//			private void jump(PrintWriter out, JSONObject json) {
//				out.println(json);
//			}
			
			//获取前台的数据组装评论对象
			private Comment installCommentFromView(HttpServletRequest request,  String content) throws NumberFormatException, JSONException {
				boolean ut = (boolean)request.getSession().getAttribute("userType");
				int uid = 0;	
				if(ut){
					uid = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
				}else {
					uid = ((Student)request.getSession().getAttribute("s")).getStuId();
				}
				
				
				//	第二步：	获取回复的数据
				int tid = Integer.parseInt(request.getParameter("topic"));
				int binding = request.getParameter("binding") == null? 0 : Integer.parseInt(request.getParameter("binding"));
				int rid = Integer.parseInt(request.getParameter("receiver"));
				boolean rt = (Boolean.parseBoolean(request.getParameter("type")));
				
				
				//第三步：	根据内容创建回复对象
				Comment c = new Comment();
				c.setComTopicId(tid);
				c.setComUserId(uid);
				c.setComUserEnum(ut);
				c.setComBinding(binding);
				c.setComCon(content);
				c.setComTime(new Timestamp(new java.util.Date().getTime()));
				c.setComReceiverId(rid);
				c.setComReceiverType(rt);
				
				return c;
			}

		

}
