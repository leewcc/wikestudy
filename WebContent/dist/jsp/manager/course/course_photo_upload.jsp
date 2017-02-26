<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>上传课程封面</title>
	<link href="/wikestudy/dist/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
	<script src="/wikestudy/dist/js/jquery.min.js" type="text/javascript"></script>
	<script src="/wikestudy/dist/js/photodeal.js" type="text/javascript"></script>
	<script src="/wikestudy/dist/js/jquery.Jcrop.js" type="text/javascript"></script>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
<div id="wrapper">
	<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_cou">课程管理</span>
			<span id="sec_head">更新课程</span>
		</div>
		<div id="col_main">
<%
	//新增还是更新图片


	String Copyphoto = (String)session.getAttribute("photoUrl");//预览图
	if(Copyphoto == null) {
		Copyphoto = "/wikestudy/dist/images/lesson/default.jpg";
	}
	String imageUrl = (String)request.getAttribute("imageUrl");
	if(imageUrl == null) {
		imageUrl = "/wikestudy/dist/images/lesson/default.jpg";//原图
	}
%>
			<h2 class="guide_message">第一步：点击下面的按钮上传文件</h2>
			<form action="CopyCPhoto" id="photo_form" method="post" enctype="multipart/form-data">
				<input type="hidden" id="couId" name="couId" value="<%=request.getAttribute("couId")%>"/>
				<input type="file"  id="Photo_url" name="PhotoUrl" onchange="upload(this)">
				<div id="update_photo">上传文件</div>
			</form>
			<div id="show_pho">  
				<h2 class="guide_message">预览图片</h2>
				<div id="imgdiv">
					<img src="<%=Copyphoto %>" alt="预览课程封面" id="coursephoto" style="height:300px"/>
				</div>
			</div>t
			<form action="course_photo_update" method="post" id="myform">
				<input type="hidden" id="couId" name="couId" value="<%=request.getAttribute("couId")%>">
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
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>	
</body>
</html>