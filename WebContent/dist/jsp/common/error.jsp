<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	String url = (String)request.getAttribute("URL"); 
	String message=(String)request.getAttribute("message");
%>
	<meta charset="UTF-8"/>
	<title>操作提示</title>
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/error.css"/>
</head>
<body>
<jsp:include page="nav.jsp"></jsp:include>
<div id="wrapper">
	<div id="err_sub">
		<h3><%=message %></h3>
		<p>页面将于<input type="text" disabled="true" id="txt" class="duan">秒后自动跳转，您也可以点击<a id="jump" href="<%=url%>">这里</a>到达相应页面。</p>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript">
	var c = 5,
		t;
	function timedCount(){
		document.getElementById('txt').value = c;
		c = c - 1;
		if(c <= 0){
			window.location.href="<%=url%>";
		}
		t = setTimeout("timedCount()",1000);
	}
	window.onload = timedCount();
</script>
</body>
</html>