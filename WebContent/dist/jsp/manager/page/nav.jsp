<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%
	Teacher t = (Teacher)session.getAttribute("t");
	if(t.getTeaPortraitUrl()!=null&&t.getTeaPortraitUrl()!="") {
%>
	<div id="nav">
		<a id="nav_cancale" href="/wikestudy/dist/jsp/manager/page/logout">注销</a>
		<a id="nav_big_logo" href="/wikestudy/dist/jsp/common/home_page"><img src="/wikestudy/dist/images/system/logo.png" alt="微课网logo" ></a>
		<span id="user">你好，<%=t.getTeaName() %></span>
		<a id="user_img" href="#">
			<img alt="头像"  src="<%=t.getTeaPortraitUrl() %>" style="background:url(<%=t.getTeaPortraitUrl() %>') no-repeat center center;"/>
		</a>
		
	</div>
<%
	}
%>