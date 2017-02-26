<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.wikestudy.model.pojo.*" %>
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
		<span id="here"></span>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="/wikestudy/dist/jsp/student/course/course_center">我的课程</a></li>
			<li id="no_topic_head"><a href="../common/my_topic_get#here">我的话题</a></li>	
			<li id="note_head"><a href="course_note_f">我的笔记</a></li>
			<li id="no_mess_head"><a href="../common/my_message_query#here">留言板</a></li>
		</ul>
		<div id="main_area">
		<%--查询全部笔记 --%>
		<%--注意只要是该页面, "全部课程查询"按钮请凸显出 --%>
		<%
			PageElem<NoteView> pe = (PageElem<NoteView>)request.getAttribute("pe"); 
		  	if (pe != null) {
		%>
		 <%-- 第二楼 --%>
		<div id="choice">
			<div id="choiceOne">
			 	<a href="course_note_f?page=1" class="selected">全部</a>
			 	<a href="course_note_s">课程笔记</a>
			</div>
			<div id="choiceTwo">
				<span>共<%=pe.getRows() %>条笔记</span>
				
				<a href="course_note_f?page=<%=pe.getCurrentPage()-1%>" id="previousPage"></a>
				<span>
					<b><%=pe.getCurrentPage() %></b>/<b><%=pe.getTotalPage() %></b>	
				</span>
				<a href="course_note_f?page<%=pe.getCurrentPage()+1%>" id="nextPage"></a>
			</div>
		</div>
		
		<%-- 第三层 笔记展示区 ，这个需要分页吗？@天信 By婷20160123--%>
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
						<button onclick="deleteNote('<%=nd.getNDId()%>','<%=pe.getCurrentPage()%>')">删除</button>
						<button class="save_note" onclick="saveNote('<%=nd.getNDId()%>','<%=pe.getCurrentPage()%>')">保存</button>
					<%-- 隐藏区 --%>

					</div>
<%-- 					<div class="time"><span><%=nd.getNDReleTime() %></span></div> --%>
				</div>
			<%}
			} %>
		</div>
	</div>
</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/script" src="/wikestudy/dist/js/common/common.js"></script>

<script type="text/javascript" src ="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src ="/wikestudy/dist/js/jquery-form.js"></script>
<script type="text/javascript" src ="note-save.js"></script>
</body>
</html>