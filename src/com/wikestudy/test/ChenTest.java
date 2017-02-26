package com.wikestudy.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;


@WebServlet("/chen/test")
public class ChenTest extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if("teacher".equals(req.getParameter("user"))) {
			Teacher jia=new Teacher();
			jia.setTeaId(1);
			jia.setTeaName("陈帅铭");
			jia.setTeaNumber("1111");
			jia.setTeaPassword("123456");
			jia.setTeaPhotoUrl("t11111.jpeg");
			session.setAttribute("t", jia);
			System.out.println("插入老师");
		} else {
			Student s=new Student();
			s.setStuClass("1");
			s.setStuGender("初一");
			s.setStuGrade("1");
			s.setStuId(1);
			s.setStuName("李伟淙");
			s.setStuNumber("1");
			s.setStuPassword("123456");
			s.setStuPhotoUrl("t11111.jpeg");
			session.setAttribute("s", s);
			System.out.println("插入学生");
		}
	}


}
