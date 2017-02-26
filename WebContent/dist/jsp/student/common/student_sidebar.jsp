<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page import="com.wikestudy.model.pojo.Student"%>
<%
	Student s = ((Student)request.getSession().getAttribute("s"));
%>
<div id="sidebar">
	<div id="intro_img">
		<img class="touxiang person_img" alt="头像" src="<%=s.getStuPortraitUrl() %>"  width="200px" height="200px"/>
		
	</div>
	<div id="intro_main">
		<h2 class="name"><%=InputUtil.input(s.getStuName()) %><button id="change" type="button" onclick="window.location.href='/wikestudy/dist/jsp/student/account/my_detail_show'">修改资料</button> </h2>
			<div id="intro_gender">性别：<%=s.getStuGender().equals("male")?"男生":"女生" %></div>
			<div id="intro_grade"><span>年级：<%=InputUtil.input(s.getGrade()) %></span><span>班别：<%=InputUtil.input(s.getStuClass() ) %></span></div>
			<div id="intro_introduction">
				<p>简介：<%=InputUtil.input(s.getStuSignature()) %></p>
			</div>
			<div id="intro_time">累计在线学习<span><%=InputUtil.inputTime(s.getStuStudyHour()) %></span></div>
	</div>
</div>
