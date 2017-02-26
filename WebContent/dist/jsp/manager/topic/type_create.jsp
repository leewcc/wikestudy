<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.wikestudy.model.pojo.Teacher" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>创建标签</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
	<div id="wrapper">
		<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
		<div id="column">
			<div id="col_head">
				<span id="fir_head_top">话题管理</span>
				<span id="sec_head">创建标签</span>
			</div>
			<div id="add_label">
				<form action="label_add" method="post">
					<ul>
						<li>
							<label for="labName">标签名：</label>
							<input type="text" name="labName" id="labName" value="${param.labName }"/>
							<p class=illegal">${name }</p>	
						</li>
						<li>
							<label for="labCib">标签描述：</label>
							<textarea name="labCib" id="labCib">${param.labCib }</textarea>
							<p class="illegal">${des }</p>	
						</li>
					</ul>
					<button type="submit">创建</button>
				</form>
			</div>
		</div>
	</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
</body>
</html>