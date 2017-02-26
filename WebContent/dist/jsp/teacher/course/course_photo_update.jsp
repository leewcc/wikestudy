<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>上传课程封面</title>
	<link href="/wikestudy/dist/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
	<script src="/wikestudy/dist/js/jquery.min.js" type="text/javascript"></script>
	<script src="/wikestudy/dist/js/photodeal.js" type="text/javascript"></script>
	<script src="/wikestudy/dist/js/jquery.Jcrop.js" type="text/javascript"></script>
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
<%
	String imageUrl = (String)request.getAttribute("imageUrl");
	if(imageUrl == null) {
		imageUrl = "/wikestudy/dist/images/lesson/defualt.jpg";//原图
	}
	String Copyphoto = (String)session.getAttribute("photoUrl");//预览图
	if(Copyphoto == null) {
		Copyphoto = imageUrl;
	}
	
%>
			<div id="add_art" style="margin: 10px 20px;">
				<button type="button" onclick="window.location.href='course_manage?couId=${param.couId}&type=${param.type }&page=${param.page}'">返回</button>
			</div>
			<h2 class="guide_message">第一步：点击下面的按钮上传文件</h2>
			<form action="course_photo_copy?couId=<%=request.getAttribute("couId")%>" id="photo_form" method="post" enctype="multipart/form-data">
				<input type="file"  id="Photo_url" name="PhotoUrl" onchange="upload(this)">
				<div id="update_photo">上传文件</div>
			</form>
			<div id="show_pho">  
				<h2 class="guide_message">预览图片</h2>
				<div id="imgdiv">
					<img src="<%=Copyphoto %>" alt="预览课程封面" id="coursephoto" style="height:300px"/>
				</div>
			</div>
			<form action="course_photo_update" method="post" id="myform">
				<input type="hidden"  name="couId" value="<%=request.getAttribute("couId")%>"/>
				<input type="hidden" value="0" name="ready">
				<input type="hidden" id="x" name="x" /> 
				<input type="hidden"  id="y" name="y" /> 	
				<input type="hidden" id="w" name="w" /> 
				<input type="hidden" id="h" name="h" />
				<input type="hidden" id="uploadPhoto" name ="uploadPhoto" value="<%=Copyphoto %>"/>
				<input	type="submit" value="确认">
			</form>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>