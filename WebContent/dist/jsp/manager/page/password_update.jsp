<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>更新密码</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
<%
		Teacher t = (Teacher)session.getAttribute("t");
		if(t.getTeaPortraitUrl()!=null&&t.getTeaPortraitUrl()!="") {
	%>
		<div id="nav">
			<span id="user">你好，<%=t.getTeaName() %></span>
			<a href="#">
				<img alt="<%=t.getTeaName() %>"  src="/wikestudy/dist/images/manager/circle.png" style="background:url(<%=t.getTeaPortraitUrl() %>') no-repeat center center;"/>
			</a>
		</div>
	<%
		} 
	%>
	<div id="wrapper">
		<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
		<div id="column">
				<div id="col_head">
					<span id="fir_head_per">个人资料</span>
					<span id="sec_head">修改资料</span>
				</div>
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
					<td><button type="button" onclick="window.location.href='ManagerUpdate.jsp'">取消</button></td>
					<td><button type="submit">修&nbsp;改</button></td>
					</tr>
				</tbody>
					</table>
				</form>
			</div>
		</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
	<%--密码保存情况 --%>
	<%String  error=(String)request.getAttribute("error");
		if(error!=null) {
			%>
			<p><%=error %></p>
			<%
		}
	%>
</body>
</html>