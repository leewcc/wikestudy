<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>个人资料修改</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
</head>
<body>
<%
	Teacher tea=(Teacher)session.getAttribute("t");
%>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
	      <div id="center_headD">
	        <div id="center_headC"></div>
	        <h1 id="center_headB"> 个人中心</h1>
	      </div>
	    </div>
<%--第一步：	include进老师简介 --%>
		<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="course_head"><a href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
				<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
				<li id="no_note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
				<li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
		<div id="main_area">
			<form id="mana_upda" action="teacher_update" method="post">
				<ul>
					<li>
						<p class="illegal">${message}</p>
					</li>
					<li>
						<p class="guide">头像</p>
						<a id="picture" href="photo_update"><img class="person_img" src="<%=tea.getTeaPortraitUrl()%>"/></a>
					</li>
					<li>
						<label for="name">姓名</label>
						<p class="illegal">${name}
							<input type="text" id="name" name="name" value="<%=InputUtil.input(tea.getTeaName())%>">
						</p>
						
					</li>
					<li>
						<label for="gender">性别</label>
						<p class="illegal">${gender}
							<select name="gender">
								<option value="male">男</option>
								<option value="female"  <%="female".equals(tea.getTeaGender())? "selected" : "" %>>女</option>
							</select>
						</p>	
					</li>						
					<li>
					<label for="number">工号</label>
						<span><%=InputUtil.input(tea.getTeaNumber())%></span><input	type="hidden" id="tnm" name="tnm" value="<%=tea.getTeaNumber()%>" disabled>
					</li>
					<li>
						<p class="guide">密码</p>
						<p>
							<span>密码已隐藏</span>
							<a href="password_update.jsp">修改密码</a>
						</p>
					</li>
					<li>
						<label for="introduction">简介</label>
						<textarea id="introduction" name="introduction"><%=InputUtil.input(tea.getTeaIntroduction())%></textarea>
					</li>
					<li>
						<button type="submit" >提交修改</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>	
