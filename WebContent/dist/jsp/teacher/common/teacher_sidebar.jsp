<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page import="com.wikestudy.model.pojo.Student"%>
<%
	Teacher t = ((Teacher)session.getAttribute("t"));
System.out.println(t);
%>
<%-- 学生个人中心侧边栏 --%>
<div id="sidebar">
	<div id="intro_img">
		<img class="touxiang person_img" alt="头像" src="<%=t.getTeaPortraitUrl() %>"  width="200px" height="200px"/>
		
	</div>
	<div id="intro_main">
		<h2 class="name"><%=InputUtil.input(t.getTeaName()) %> <button id="change" type="button" onclick="window.location.href='/wikestudy/dist/jsp/teacher/person_data/teacher_update'">修改资料</button> </h2>
			<div id="intro_grade"><%="male".equals(t.getTeaGender())? "男" : "女" %></div>
		<div id="intro_introduction"><p>简介：<%=InputUtil.input(t.getTeaIntroduction()) %></p></div>
	</div>
</div>