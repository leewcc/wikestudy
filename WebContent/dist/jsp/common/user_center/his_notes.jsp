<%@page import="com.wikestudy.model.pojo.NoteView"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ta的笔记</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
		    <div id="center_headD">
		        <div id="center_headC"></div>
		        <h1 id="center_headB"> 个人中心</h1>
		    </div>
		</div>
		
		<jsp:include page="student_home.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="stu_courses_see?id=${param.id }&flag=2#stu_sidebar">他的课程</a></li>
			<li id="no_topic_head"><a href="user_topics_show?id=${param.id }&type=false#stu_sidebar">他的话题</a></li>
			<li id="note_head"><a href="#stu_sidebar">他的笔记</a></li>
			<li id="no_mess_head"><a href="message_query?id=${param.id }&type=false#stu_sidebar">留言板</a></li>
		</ul>
		
		<div id="main_area">
<%
			PageElem<NoteView> pe = (PageElem<NoteView>)request.getAttribute("pe"); 
		  	if (pe != null) {
		%>
		 <%-- 第二楼 --%>
			<div id="choice">
				<div id="choiceTwo">
					<span>共<%=pe.getRows() %>条笔记</span>
					<a href="his_notes_see?page=<%=pe.getCurrentPage()-1%>&id=${param.id}" id="previousPage"></a>
					<span>
						<b><%=pe.getCurrentPage() %></b>/<b><%=pe.getTotalPage() %></b>	
					</span>
					<a href="his_notes_see?page<%=pe.getCurrentPage()+1%>&id=${param.id}" id="nextPage"></a>
				</div>
			</div>
			<div id="noteArea">
<%if (pe.getPageElem().size() == 0) {%>
				<p>当前课程未做笔记</p>					
<%}else for(NoteView nd: pe.getPageElem()) { %>
				<div class="oneNote">
					<div class="heading">
						<span class="play"></span>
						<span><%=nd.getSecNumber()%></span>
						<span><%=nd.getSecName() %></span>
					</div>
					<p class="pContent" ><%=nd.getNDContent()%></p>
					<%-- 隐藏区 --%>
					<div class="hide_note_div">
						<textarea><%=nd.getNDContent()%></textarea>
					<%-- 隐藏区 --%>
					</div>
					<div class="time"><span><%=nd.getNDReleTime() %></span></div>
				</div>
			<%}
			} %>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</div>
</body>
</html>