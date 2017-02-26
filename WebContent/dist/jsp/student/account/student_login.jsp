<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>请登录</title>
	<link rel="Shortcut Icon" href="/wikestudy/favicon.ico" />
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/login.css"/>
	<!--[if lte IE 5.5]>
		show('low', '该浏览器版本太低，请下载最新的浏览器。', 'low');
		
	<![endif]-->
</head>
<body>

<jsp:include page="/dist/jsp/common/nav.jsp"/>
<div class="outside login">
	<div class="wrapper">
		<div id="loginArea">
			<h1>登 &#160; &#160; &#160;录</h1>
			<div id="loginJiao"><div id="left_jiao"></div><div id="right_jiao"></div></div>
			<form id="loginForm" action="/wikestudy/dist/jsp/student/account/login" method="post" >
				<p class="illegal">${error}</p>
				<div id="login_number">
					<input id="number" type="text" name="number" placeholder="学号" value="${param.number }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
				</div>
				<div id="login_password">
					<input id="password" type="password" name="password" value="123456" onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
				</div>
				<div id="code_check">
					<p class="illegal">${codeE}</p>
					<input type="text" name="code" id="code" placeholder="验证码"/>
					<img id="vimg"  title="点击更换" src="/wikestudy/dist/security_code">
				</div>	
				<div id="login_role">
					<label class="check"><input type="radio" name="role" value="stu" checked /> 学生</label>
					<label><input type="radio" name="role" value="tea"/> 老师</label>
				</div>
				<button class="login_button" type="button">LOGIN</button> 
			</form>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/login.js"></script>
</body>
</html>