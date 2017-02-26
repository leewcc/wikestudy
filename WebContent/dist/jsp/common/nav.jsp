<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wikestudy.model.pojo.Student"%>
<%@ page import="com.wikestudy.model.pojo.Teacher"%>
<div id="nav" class="outside">
	<div class="wrapper">
		<a id="nav_logo" href="/wikestudy/dist/jsp/common/home_page">
			<img src="/wikestudy/dist/images/system/small_logo.png" alt="微课网logo">
		</a>
		<ul>
			<li><a href="/wikestudy/dist/jsp/common/home_page">首页</a></li>
			<li><a href="/wikestudy/dist/jsp/common/course_show?grade=0&labelId=0&type=3">选课</a></li>
			<li><a href="/wikestudy/dist/jsp/common/topic/topic_list_get?labId=0&currentPage=1&type=1">在线交流</a></li>
			<li><a href="/wikestudy/dist/jsp/common/data/data_center?labId=0&grade=0&sort=false&currentPage=1">资料下载</a></li>
			<li><a href="/wikestudy/dist/jsp/common/article/article_show?cp=1&aTypeId=1">家校互动</a></li>
		</ul>
		<div id="user">
<% if(request.getSession().getAttribute("s")!=null || request.getSession().getAttribute("t")!=null){%>
			<a style="margin-left: 7px;" href="/wikestudy/dist/jsp/common/log_off">注销</a>
<%}
	if(request.getSession().getAttribute("s")!=null){
		Student user = ((Student)request.getSession().getAttribute("s"));
		int id = user.getStuId();
		String name = user.getStuName();
%>
			<a id="user_name" href="/wikestudy/dist/jsp/student/common/student_home"><%= name%></a>
<%
	}else if(request.getSession().getAttribute("t")!=null){
		Teacher user = ((Teacher)request.getSession().getAttribute("t"));
		if(Teacher.class==user.getClass());
		int id = user.getTeaId();
		String name = user.getTeaName();
%>
			<a href="/wikestudy/dist/jsp/teacher/common/teacher_home"><%=name %></a>
<% }else{ %>                         
			<a href="/wikestudy/dist/jsp/student/account/login">登录</a>	
<% } %>
		</div>
		<form id="search_form" action="/wikestudy/dist/jsp/common/course_query_by_key" method="post">
			<input id="nav_search" type="text" name="key" placeholder="请输入关键字搜索课程"/>
			<input id="search" type="submit" value=""/>
		</form>
	</div>
</div>