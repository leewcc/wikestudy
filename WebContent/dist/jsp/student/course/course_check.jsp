<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.wikestudy.model.pojo.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>个人中心</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"/>  
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
	      <div id="center_headD">
	        <div id="center_headC"></div>
	        <h1 id="center_headB"> 个人中心</h1>
	      </div>
	    </div>
	    <jsp:include page="../common/student_sidebar.jsp"></jsp:include>
		<%-- 中间的是主要内容区 --%>
		<ul id="stu_sidebar">
			<li id="course_head"><a href="/wikestudy/dist/jsp/student/course/course_center#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="../common/my_topic_get#stu_sidebar">我的话题</a></li>	
			<li id="no_note_head"><a href="course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="no_mess_head"><a href="../common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
		<div id="main_area">
<%
	PageElem<Course> pe = (PageElem<Course>)request.getAttribute("pe"); 
	Integer flag = (Integer)request.getAttribute("flag");
	
	int cp = pe.getCurrentPage();
	int tp = pe.getTotalPage();
	int beforePage = cp == 1? 1 : cp-1;
	int nextPage = cp == tp ? tp : cp+1;
	
	if (pe != null) {
%>

		<%-- 第一楼 --%>
			 <%-- 第二楼 --%>
		  	<div id="choice">
				<div id="choiceOne">
					 <a href="course_center?flag=1&currentPage=1#stu_sidebar" <%=flag.toString().equals("1")?"class=\"selected\"":"" %> >推荐微课</a>
			         <a href="course_center?flag=2&currentPage=1#stu_sidebar" <%=flag.toString().equals("2")?"class=\"selected\"":"" %> >正在学习</a>
			    </div>
				<div id="choiceTwo">
					<span>共<%=pe.getRows() %>个课程</span>
					<span>
						<b><%=pe.getCurrentPage() %></b>/<b><%=pe.getTotalPage() %></b>	
					</span>
					<a href="course_center?flag=<%=flag %>&currentPage=<%=beforePage %>">上一页</a>
					<a href="course_center?flag=<%=flag %>&currentPage=<%=nextPage %>">下一页</a>
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
						<%-- 前端注意: a需要变成块级元素 --%>
						<a	class="hasImg" href="../../common/course_select?couId=<%=cou.getCouId()%>"><img class="course_img" alt="课程头像" src="<%=cou.getCouAllUrl()%>"></a>
						<%-- 右侧课程信息 --%>
						<div>
							<h3><%=cou.getCouName() %></h3><%--  课程标题 --%>
							<div>
								<a href="../../common/course_select?couId=<%=cou.getCouId()%>" class="cou_class">课程查看</a>
								<a href="stu_course_delete?couId=<%=cou.getCouId() %>&flag=<%=flag%>&page=<%=pe.getCurrentPage()%>" class="cou_delect">删除</a>
							</div>
						</div>
					</li>
			<%--}} } else {out.println("<h1>程序代码错误</h1>");}--%>
			<%}} } else {out.println("<h1>对不起，我们遇到了未知的错误，小猿正在维修中</h1>");}%>
				</ul>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/script" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>