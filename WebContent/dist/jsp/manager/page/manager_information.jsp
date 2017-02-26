<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>展示管理员资料</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
	<form action="manager_data_update" method="post"  enctype="multipart/form-data">
		<%
			Teacher t = (Teacher) session.getAttribute("t");
		%>
		头像 <img src=<%=t.getTeaPortraitUrl() %>> <br/>
		 姓名<%=t.getTeaName()%><br/>
		工号<%=t.getTeaNumber()%><br/>
		密码<%=t.getTeaPassword()%><br/>
		简介<%=t.getTeaIntroduction()%><br/>
		<input
			type="submit" value="修改" class="button">
	</form>
</body>
</html>