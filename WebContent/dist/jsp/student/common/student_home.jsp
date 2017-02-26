<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
<title>学生主页</title>
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
			<li id="no_topic_head"><a href="my_topic_get#stu_sidebar">我的话题</a></li>
			<li id="no_note_head"><a href="../course/course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="no_mess_head"><a href="my_message_query#stu_sidebar">留言板</a></li>
		</ul>
		<div id="main_area">
			<p class="guide_message">欢迎回到个人中心主页。</p>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>