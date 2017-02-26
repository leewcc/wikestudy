package com.wikestudy.servlet.publicpart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.pojo.Comment;
import com.wikestudy.model.pojo.Message;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.publicpart.MessageService;


@WebServlet("/dist/jsp/common/user/user_center/message_send")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//第一步：	获取回复内容
				String c = request.getParameter("content");
				
				
				//第二步：	判断留言内容是否为空，为空则跳转错误页面
				if(check(c, request)){
					String url = request.getHeader("Referer");
					request.setAttribute("URL", url.substring(url.indexOf("/wikestudy")));
					request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
					return;
				}
				
				
				//第三步：	获取留言数据
				int mId = request.getParameter("binding") == null ? 0 : Integer.parseInt(request.getParameter("binding"));
				int rId = Integer.parseInt(request.getParameter("marster"));
				boolean rt = Boolean.parseBoolean(request.getParameter("type"));
				
				
				//第四步：	获取用户类型
				boolean ut = (Boolean)request.getSession().getAttribute("userType");
				int uid = 0; 
				if(ut){
					Teacher t = (Teacher)request.getSession().getAttribute("t");
					uid = t.getTeaId();
				}else{
					Student s = (Student)request.getSession().getAttribute("s");
					uid = s.getStuId();
				}
				
				
				//第五步：	组装留言对象
				Message m = new Message();
				m.setMessBinding(mId);
				m.setMessContent(c);
				m.setMessMasterId(rId);
				m.setMessMasterMark(rt);
				m.setMessSenderId(uid);
				m.setMessSenderMark(ut);
				m.setMessTime(new Timestamp(new Date().getTime()));
				
				
				//第六步：	初始化数据库、服务
				Connection conn = null;
				MessageService ms = null;		
				try{
					conn = DBSource.getConnection();
					ms = new MessageService(conn);
					
					
				//第七步：	调用插入回复的方法
					int result = ms.addMessage(m);
					
					
				//第八步：	判断插入是否成功
					if(result <= 0){
						
						
				//第九步：	插入失败，则跳转到提示页面
						request.setAttribute("message", "对不起，留言失败");
						String url = request.getHeader("Referer");
						request.setAttribute("URL", url.substring(url.indexOf("/wikestudy")));
						request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
					}else{
						
						
				//第九步：	插入成功，重定向回留言板页面
						String url = request.getHeader("Referer");
						response.sendRedirect(url.substring(url.indexOf("/wikestudy")));
					}
					
				//最后：		关闭数据库连接	
				}catch(Exception e){
					System.out.println("发送留言失败");
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
	
	private boolean check(String content, HttpServletRequest request) {
		if(content == null || "".equals(content) || "".equals(content.trim())) {
			request.setAttribute("message", "请输入你要回复的信息");
			return true;
		}else if(content.length() > 200){
			request.setAttribute("message", "信息长度只能在200字符以内");
			return true;
		}
		
		return false;
	}
}
	
	
	
	

