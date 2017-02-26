<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>
	<title>修改密码</title>
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
		<%-- 侧边有头像和侧边栏 --%>
		<jsp:include page="../common/student_sidebar.jsp"></jsp:include>
			<%-- 中间的是主要内容区 --%>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="/wikestudy/dist/jsp/student/course/course_center#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="../common/my_topic_get#stu_sidebar">我的话题</a></li>	
			<li id="no_note_head"><a href="../course/course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="no_mess_head"><a href="../common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
	<%-- 中间的是主要内容区 --%>
	<div id="main_area">
				<h1>修改密码</h1>
				<form id="updatePassword" action="password_update" method="post">
					<p class="illegal">${error }</p>
					<label for="oldPassword">原密码&nbsp; &nbsp;&nbsp;:</label>
					<input id="oldPassword" type="password" name="oldPassword" value="${param.oldPassword }"/>
					<br/>
					<label for="newPassword">新密码&nbsp; &nbsp;&nbsp;:</label>
					<input id="newPassword" type="password" name="newPassword"  value="${param.newPassword }"/>
					<br/>
					<label for="newPassAgain">再次输入:</label>
					<input id="newPassAgain" type="password" name="newPassAgain" value="${param.newPassAgain }"/>
					<br/>	
					<button class="button" type="button" onclick="window.location.href='my_detail_show'">取消</button>
					<button class="button" type="submit" >修改</button>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/dist/jsp/common/footer.jsp"/>
	<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>