<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="com.wikestudy.model.pojo.*,com.wikestudy.model.enums.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>笔记测试</title>
</head>
<body>
	<%
	session.setAttribute("role", Role.student);
	
	Student s = new Student();
	
	s.setStuId(1);
	session.setAttribute("s", s);
	%>
	<a href="course_note_f?page=1">查询全部笔记</a>
</body>
</html>