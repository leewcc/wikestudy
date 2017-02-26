<%@page import="com.wikestudy.model.util.InputUtil"%>
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
	<title>管理员照片上传</title>
	<link href="/wikestudy/dist/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>

<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
<%
Teacher tea=(Teacher)session.getAttribute("t");
%>
<div id="wrapper">
	<jsp:include page="sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_per">个人资料</span>
			<span id="sec_head">修改资料</span>
		</div>
		<div id="col_main">
	<%--获得更新头像的用户类型 --%>
	<%
	String photoUrl=(String)session.getAttribute("photoUrl");
	if(photoUrl==null) {
		if((photoUrl=tea.getTeaPortraitUrl())==null)
			photoUrl="/wikestudy/dist/images/portrait/defualt.jpg";
	}
	%>
			<form action="photo_copy" id="photo_form" method="post" enctype="multipart/form-data">
				<div><input type="file"  id="PhotoUrl" name="PhotoUrl" onchange="upload(this)"/></div>
			</form>
			<div id="show_pho">
				<%--只有上传了图片才显示确定按钮,输入文件框不可编辑 --%>
				<h3>原头像</h3>
				<img src=<%=tea.getTeaPortraitUrl() %> alt="原头像" style="height:300px">
				<h3>预览头像</h3>
				<div id="imgdiv">
					<img src="<%=photoUrl %>" alt="预览头像" id="cropbox" style="height:300px"/>
			</div>
				<form action="photo_update" method="post" id="myform">
					<input type="hidden" value="0" name="ready">
					<input type="hidden" id="x" name="x" /> 
					<input type="hidden"  id="y" name="y" /> 	
					<input type="hidden" id="w" name="w" /> 
					<input type="hidden" id="h" name="h" />
					<input type="hidden" id="UploadPhoto" name ="UploadPhoto" value="<%=photoUrl %>"/>
					<input	type="submit" value="确认"/>
				</form>
			</div>
		</div>
	</div>
</div>
<script src="/wikestudy/dist/js/jquery.min.js" type="text/javascript"></script>
<script src="/wikestudy/dist/js/photodeal.js" type="text/javascript"></script>
<script src="/wikestudy/dist/js/jquery.Jcrop.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>			
</body>
</html>