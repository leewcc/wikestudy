<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page import="com.wikestudy.model.pojo.Student"%>
<%
String name = "";
int id = 0;
String photo = "";
String introduction = "";
String gender = "";
Teacher user=new Teacher();
		if(request.getAttribute("user")!=null){
			user = ((Teacher)request.getAttribute("user"));
			id = user.getTeaId();
			name = user.getTeaName();
			introduction = user.getTeaIntroduction();
			gender = "male".equals(user.getTeaGender())? "男" : "女";
			photo = user.getTeaPortraitUrl();
		}
%>
<%-- 学生个人中心侧边栏 --%>
<div id="sidebar">
	<div id="intro_img">
		<img class="touxiang person_img" alt="头像" src="<%=user.getTeaPortraitUrl() %>"  width="200px" height="200px"/>
	</div>
	<div id="intro_main">
		<h2 class="name"><%=name %></h2>
		<div id="intro_grade"><%=gender %></div>
		<div id="intro_introduction"><p><%=introduction %></p></div>
	</div>
</div>