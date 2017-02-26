<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<meta name="viewport" content="width=device-width, minimum-scale=0.1">
	<title>学生照片上传</title>
	<link href="/wikestudy/dist/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>
</head>
<body>
	<%--获得更新头像的用户类型 --%>
	<%
	Student s = (Student)session.getAttribute("s");
	String photoUrl=(String)session.getAttribute("photoUrl");
	if(photoUrl==null) {
		photoUrl="/wikestudy/dist/images/portrait/defualt.jpg";
	}
	
	%>
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
			<li id="no_note_head"><a href="../course/course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="no_mess_head"><a href="../common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
	<%-- 中间的是主要内容区 --%>
	<div id="main_area">
		<h2 class="guide_message">第一步：点击下面的按钮上传文件</h2>
			<form action="photo_copy" id="photoForm" method="post" enctype="multipart/form-data">
				<input type="file"  id="PhotoUrl" name="PhotoUrl" onchange="upload(this)">
				<div id="update_photo">上传文件</div>
			</form>
			<div id="show_pho">
				<%--只有上传了图片才显示确定按钮,输入文件框不可编辑 --%>
				<h2 class="guide_message">原头像</h2>
				<img src=<%=s.getStuPortraitUrl()%> alt="原头像" style="height:300px">
				<h2 class="guide_message">预览头像</h2>
				<div id="imgdiv">
					<img src="<%=photoUrl %>" alt="预览头像" id="cropbox" style="height:300px"/>
			</div>
				<p class="illegal">${error}</p>
				<form action="photo_update" method="post" id="myform">
					<input type="hidden" value="0" name="ready">
					<input type="hidden" id="x" name="x" /> 
					<input type="hidden"  id="y" name="y" /> 	
					<input type="hidden" id="w" name="w" /> 
					<input type="hidden" id="h" name="h" />
					<input type="hidden" id="UploadPhoto" name ="UploadPhoto" value="<%=photoUrl %>"/>
					<input	type="submit" value="确认">
				</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script src="/wikestudy/dist/js/jquery.min.js" type="text/javascript"></script>
<script src="/wikestudy/dist/js/photodeal.js" type="text/javascript"></script>
<script src="/wikestudy/dist/js/jquery.Jcrop.js" type="text/javascript"></script>
</body>
</html>