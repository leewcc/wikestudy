<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="com.wikestudy.model.pojo.*,  java.util.List" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>我的笔记</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"/>  
	<%-- 学生主页,共三部分 --%>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
	      <div id="center_headD">
	        <div id="center_headC"></div>
	        <h1 id="center_headB"> 个人中心</h1>
	      </div>
	    </div>
		<%-- 侧边有头像和侧边栏 --%>
		<jsp:include page="../common/student_sidebar.jsp"></jsp:include>
			<%-- 中间的是主要内容区 --%>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="/wikestudy/dist/jsp/student/course/course_center#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="../common/my_topic_get#stu_sidebar">我的话题</a></li>	
			<li id="note_head"><a href="course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="no_mess_head"><a href="../common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
	<%-- 中间的是主要内容区 --%>
		<div id="main_area">
		<%--笔记查询——课程列表展示 --%>
		<%
			List<NoteCourse> ncList = (List<NoteCourse>)request.getAttribute("ncList"); 
			if (ncList != null && ncList.size() > 0) {
		%>
 		<%-- 第二楼 --%>
		<div id="choice">
			<div id="choiceOne">
			 	<a href="course_note_f?page=1">全部</a>
			 	<a href="course_note_s"  class="selected">课程笔记</a>
			</div>
			<div id="choiceTwo">
				<span>共<%=ncList.size() %>个课程</span>
			</div>
		</div>

			<%-- 第三楼 笔记目录展示,如果没有笔记呢？== --%>
		<div id="mana_cou">
			<ul>
			<%for(NoteCourse nc:ncList) {%>
			  	<li class="one_cou">
	  				<a href="course_note_t?couId=<%=nc.getCouId() %>&page=1&sort=1">
		  				<img  src="<%= nc.getCouPricUrl() %>">
	  				</a>
		  			<h5><%=nc.getCouName() %></h5>
		  			<span><%=nc.getNoteNumber() %>条笔记</span>
				</li>
			<%}
			%>
			</ul>
		</div>
			<%
			}else{%>
			<p class="guide_message">当前未选课程</p>
			<%} %>
		</div>
		</div>
	</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/script" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>