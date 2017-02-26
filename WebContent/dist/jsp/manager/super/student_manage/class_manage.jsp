<%@page import="com.wikestudy.model.pojo.GraClass"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.wikestudy.model.pojo.Teacher" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>班级管理</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
	<div id="wrapper">
		<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
		<div id="column">
		<%
			//获取初一 初二 初三年级对象
			GraClass one = (GraClass)application.getAttribute("one");
			GraClass two = (GraClass)application.getAttribute("two");
			GraClass three = (GraClass)application.getAttribute("three");
		%>
		
			<div id="col_head">
				<span id="fir_head_per">人员管理</span>
				<span id="sec_head">班级管理</span>
			</div>
			<form  id="up_class" action="class_update" method="post">
				<p class="illegal">${message}</p>
				<table>
					<tbody>
						<tr>
							<td><label for="one">初一</label></td>
							<td><input id="one" type="number" name="one" value="<%=one.getClaClassNum() %>"/><span>班级个数</span>
							</td>
						<!-- <input type="text" name="one" value=""/>-->
						</tr>
					<tr>
						<td><label for="two">初二</label></td>
						<!-- <input type="text" name="two" value=""/> -->
						<td><input id="two" type="number" name="two" value="<%=two.getClaClassNum() %>"/><span>班级个数</span></td>
					</tr>
					<tr>
						<td><label for="three">初三</label></td>
						<td><input id="three" type="number" name="three" value="<%=three.getClaClassNum() %>"/><span>班级个数</span></td>
					</tr>
					</tbody>
				</table>
				<button id="update_data" type="submit">修&nbsp;改</button> 
			</form>
			</div>
			</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
</body>
</html>