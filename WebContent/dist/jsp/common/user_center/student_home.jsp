<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page import="com.wikestudy.model.pojo.Student"%>
<%
String name = "";
int id = 0;
String introduction = "";
long learningTime = 0;
String grade="";
String photo = "";
Student user=null;
		if(request.getAttribute("user")!=null){
			user = ((Student)request.getAttribute("user"));
			id = user.getStuId();
			name = user.getStuName();
			introduction = user.getStuSignature();
			grade = user.getGrade();
			learningTime = user.getStuStudyHour();
			photo = user.getStuPhotoUrl();
		}
%>
<%-- 学生个人中心侧边栏 --%>
<div id="sidebar">
	<div id="intro_img">
		<img class="touxiang person_img" alt="头像" src="<%=user.getStuPortraitUrl()%>"  width="200px" height="200px"/>
	</div>
	<div id="intro_main">
		<h2 class="name"><%=name %></h2>
		<div id="intro_grade"><%=grade %></div>
		<div id="intro_introduction"><p><%=introduction %></p></div>
		<div id="intro_time">累计在线学习<span><%=InputUtil.inputTime(learningTime) %></span></div>
	</div>
</div>