<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>管理员登陆页面</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/login.css"/>
</head>
<body>
<div class="outside login">
	<div class="wrapper">
		<div id="loginArea">
			<h1>登 &#160; &#160; &#160;录</h1>
				<div id="loginJiao">
				<div id="left_jiao"></div>
				<div id="right_jiao"></div>
			</div>
			<form id="loginForm" action="/wikestudy/dist/jsp/manager/page/manager_login" method="post" autocomplete="off">
				<p class="illegal">${error}</p>
				<div id="login_number">
					<input id="teaNumber" type="text" name="number" placeholder="账号" value="${param.number }" checked="checked"/>
				</div>
				<div id="login_password">
					<input id="password" type="password" name="password" placeholder="密码" value="123456"/>
				</div>
				
				<div id="code_check">
					<p class="illegal">${codeE}</p>
					<input type="text" name="code" id="code" placeholder="验证码"/>
						<img id="vimg"  title="点击更换" src="/wikestudy/dist/security_code"/>
					</div>
					
				<button class="login_button" type="submit">LOGIN</button> 
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/login.js"></script>
</body>
</html>