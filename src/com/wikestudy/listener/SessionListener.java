package com.wikestudy.listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.service.manager.RecordService;
import com.wikestudy.service.student.StudentService;


@WebListener
public class SessionListener implements HttpSessionListener {


    public SessionListener() {
       
    }

    public void sessionCreated(HttpSessionEvent se)  { 
         System.out.println("Session 开启了");
    }


    public void sessionDestroyed(HttpSessionEvent se)  { 
    	System.out.println("Session关闭了");
//        HttpSession session = se.getSession();
//        Student s = (Student)session.getAttribute("s");
//        if(s != null) {
//        	long start = (long)session.getAttribute("start");
//	       	 session.removeAttribute("start");
//	       	 long end = System.currentTimeMillis();
//	       	 
//	       	 long time = end - start;
//	       	 
//	       	 Connection conn = null;
//	       	 try{
//	       		 conn = DBSource.getConnection();
//	       		 RecordService rs = new RecordService(conn);
//	       		 StudentService ss = new StudentService(conn);
//	       		 
//	       		 rs.updateStudy(s.getStuId(), time);
//	       		 ss.updateStudy(s.getStuId(), time);
//	       		 
//	       	 }catch(Exception e) {
//	       		 e.printStackTrace();
//	       	 }finally {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//						e.printStackTrace();
//				}
//			}
//        }
    }
	
}
