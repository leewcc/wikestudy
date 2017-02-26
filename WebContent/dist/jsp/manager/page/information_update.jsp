<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>修改资料</title>
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
		<form id="mana_upda" action="manager_data_update" method="post">
			
			<table>
				<tr>
					<th>头像 </th>
					<td><img class="person_img" src="<%=tea.getTeaPortraitUrl()%>"/><a href="photo_update">修改头像</a> </td>
				</tr>
				<tr>
					<th><label for="name">姓名</label></th>
					<td><input type="text" id="name" name="name" value="<%=InputUtil.input(tea.getTeaName())%>"></td>
					<p class="illegal">${name}</p>
				</tr>
				<tr>
					<th><label for="tgen">性别</label></th>
					<td>
						<select name="gender">
							<option value="male">男</option>
							<option value="female"  <%="female".equals(tea.getTeaGender())? "selected" : "" %>>女</option>
						</select>
						<p class="illegal">${gender}</p>
					</td>
				</tr>
				<tr>
					<th><label for="tnm">工号</label></th>
					<td><span><%=InputUtil.input(tea.getTeaNumber())%></span><input	type="hidden" id="tnm" name="tnm" value="<%=tea.getTeaNumber()%>" disabled></td>
				</tr>
				<tr>
					<th><label for="tp">密码</label></th>
					<td>
						<span>密码已隐藏</span>
						<a href="password_update">修改密码</a>
					</td>
				</tr>
				<tr>
					<th><label for="introduction">简介</label></th>
					<td>
						<textarea id="introduction" name="introduction"><%=InputUtil.input(tea.getTeaIntroduction())%></textarea>
						<p class="illegal">${signature}</p>
					</td>
				</tr>
				<tr>
					<th></th>
					<td><button type="submit" >提交修改</button></td>
				</tr>
					<a href="/wikestudy/dist/jsp/Manager/page/ManagerHome.jsp">.</a>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
</body>
</html>