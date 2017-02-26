<%@page import="com.wikestudy.model.pojo.Article"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
<%
  Article a = (Article) request.getAttribute("a");
%>
	<title><%=a.getArtTitle() %></title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div id="article_main" class="wrapper">
		<div id="article">
			<h1><%=a.getArtTitle()%></h1>
			<div id="articleMes">
				<span>文章类别：<%=a.getArtType()%></span>
				<span>发布于：<%=a.getTime() %></span>
				<span>作者：<%=a.getAuthor()%></span>
				<span>点击量：<%=a.getArtClick()%></span>
			</div>
			<div id="article_con">
			  <%=a.getArtContent()%>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>