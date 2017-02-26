package com.wikestudy.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.RecordService;
import com.wikestudy.service.student.StudentService;


//@WebListener
public class StudentListener implements HttpSessionAttributeListener {

   
    public StudentListener() {
            }

	
    public void attributeAdded(HttpSessionBindingEvent se)  { 
         if(se.getName().equals("s")) {
        	 se.getSession().setAttribute("start", System.currentTimeMillis());
         }
    }

	
    public void attributeRemoved(HttpSessionBindingEvent se)  { 
         if(se.getName().equals("s")) {
        	 Student s = (Student)se.getValue();
        	 long start = (long)se.getSession().getAttribute("start");
        	 se.getSession().removeAttribute("start");
        	 long end = System.currentTimeMillis();
        	 
        	 long time = end - start;
        	 
        	 Connection conn = null;
        	 try{
        		 conn = DBSource.getConnection();
        		 RecordService rs = new RecordService(conn);
        		 StudentService ss = new StudentService(conn);
        		 
        		 rs.updateStudy(s.getStuId(), time);
        		 ss.updateStudy(s.getStuId(), time);
        		 
        	 }catch(Exception e) {
        		 e.printStackTrace();
        	 }finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
         }
    }

	
    public void attributeReplaced(HttpSessionBindingEvent se)  { 
         
    }
	
}
