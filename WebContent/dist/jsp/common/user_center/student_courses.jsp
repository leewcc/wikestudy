<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>学生课程页面</title>
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
			<li id="course_head"><a href="#stu_sidebar">他的课程</a></li>
			<li id="no_topic_head"><a href="user_topics_show?id=${param.id }&type=false#stu_sidebar">他的话题</a></li>
			<li id="no_note_head"><a href="his_notes_see?id=${param.id }#stu_sidebar">他的笔记</a></li>
			<li id="no_mess_head"><a href="message_query?id=${param.id }&type=false#stu_sidebar">留言板</a></li>
		</ul>
		
		<div id="main_area">
<%
	PageElem<Course> pe = (PageElem<Course>)request.getAttribute("courses"); 
	int cp = pe.getCurrentPage();
	int tp = pe.getTotalPage();
	int beforePage = cp == 1? 1 : cp-1;
	int nextPage = cp == tp ? tp : cp+1;
	String flag = (String)request.getAttribute("flag");
	if (pe != null) {
%>
		<%-- 第一楼 --%>
			 <%-- 第二楼 --%>
		  	<div id="choice">
		  		<div id="choiceOne">
		  			<a href="stu_courses_see?id=${param.id }&flag=2">正在学习</a>
		  		</div>
				<div id="choiceTwo">
					<span>共<%=pe.getRows() %>个课程</span>
					<span>
						<b><%=pe.getCurrentPage() %></b>/<b><%=pe.getTotalPage() %></b>	
					</span>
					<a href="stu_courses_see?id=${param.id }&flag=2&currentPage=<%=beforePage %>">上一页</a>
					<a href="stu_courses_see?id=${param.id }&flag=2&currentPage=<%=nextPage %>">下一页</a>
				</div>
			</div>
			<div id="mana_cou">
	<%-- 课程展示 --%> 
		<%if (pe.getPageElem().size() == 0) { // 判断是否有课程%>
				<p class="guide_message">当前无选课程</p>
		<%} else {%>
				<ul>
		<% for(Course cou:pe.getPageElem()){ %>
					<li class="one_cou">
						<%-- 左侧可点击图片 --%>
						<a	class="hasImg" href="/wikestudy/dist/jsp/common/course_select?couId=<%=cou.getCouId() %>">
						<img alt="课程头像" class="course_img" src="<%=cou.getCouAllUrl()%>"></a>
						<%-- 右侧课程信息 --%>
						<div>
							<h3><%=cou.getCouName() %></h3><%--  课程标题 --%>
							<div></div>
						</div>
					</li>
			<%--}} } else {out.println("<h1>程序代码错误</h1>");}--%>
			<%}} } else {out.println("<h1>对不起，我们遇到了未知的错误。</h1>");}%>
				</ul>
			</div>
		</div>
	</div>
	<jsp:include page="/dist/jsp/common/footer.jsp"/>
	<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</div>
</body>
</html>