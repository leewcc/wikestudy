<%@page import="com.wikestudy.model.pojo.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>一篇文章 </title>
	<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/article-one.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
	<%
	  Article a = (Article) request.getAttribute("a");
	%><jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>	
<div class="outside">
	<div id="articleMain" class="wrapper">
		<div id="article">
			<h1>
			<%=a.getArtTitle()%>
			</h1>
			<div id="articleMes">
				<span>
				<%=a.getArtType()%></span>
				<span>发布于：
				<%=a.getArtTime()%></span>
				<span>作者：
				<%=a.getAuthor()%></span>
				<span>点击量：
				<%=a.getArtClick()%></span>
			</div>
			<div id="articleCon">
			  <%=a.getArtContent()%>
			</div>
		</div>
		<form action="" id="myForm" method="post">
			<input type="button" class="button" id="comment_get" value="评论" onclick="getData()" />
			<input type="hidden" value=<%=a.getArtId()%> id="artId" name="artId"/> 评论区
			<p id="show"></p>		
			<div id="page"></div>				
		</form>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/article-one.js"></script>
</body>
</html>