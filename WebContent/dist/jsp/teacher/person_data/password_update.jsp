<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
<title>修改密码</title>
	<link href="/wikestudy/dist/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
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
	<%--第一步：	include进老师简介 --%>
		<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
		<ul id="stu_sidebar">
					<li id="course_head"><a href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
				<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
				<li id="no_note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
				<li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
		<div id="main_area">
			<form id="up_password" action="password_update" method="post">
			<p class="illegal">${error }</p>
			<table>
				<tbody>
					<tr>
				<th><label for="oldPassword">原密码&nbsp; &nbsp;&nbsp;:</label></th>
				<td><input id="oldPassword" type="password" name="oldPassword" value="${param.oldPassword }"/></td>
				</tr>
				<tr>
				<th><label for="newPassword">新密码&nbsp; &nbsp;&nbsp;:</label></th>
				<td><input id="newPassword" type="password" name="newPassword" value="${param.newPassword }"/></td>
				</tr>
				<tr>
				<th><label for="newPassAgain">再次输入:</label></th>
				<td><input id="newPassAgain" type="password" name="newPassAgain" value="${param.newPassAgain }"/></td>
				</tr>
					<tr>
				<td><button type="button" onclick="window.location.href='teacher_update.jsp'">取消</button></td>
				<td><button type="submit">修&nbsp;改</button></td>
				</tr>
			</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>