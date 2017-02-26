<%@page import="java.util.ArrayList"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.ArticleType"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>制作文章</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
	<link rel="stylesheet" type="text/css" href="/wikestudy/dist/css/wangEditor.min.css">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
	<%--分为两部分 显示已设置的一周之星 未设置的学生列表 --%>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
<%
	String atypeId=(String)request.getAttribute("atypeId");
%>
<div id="wrapper">
	<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_art">文章管理</span>
			<span id="sec_head">制作文章</span>
		</div>
		<form id="col_main" class="make_art" action="article_publish" method="post">
			<ul>
				<li>
					<label for="artTitle">文章标题：</label>
					<input type="hidden" id="artType" name="artType" value="<%=atypeId %>"/>
					<input type="text" id="artTitle" name="artTitle"/>
				</li>
				<li>
					<label for="content">文章内容：</label>
					<textarea id="artContent" name="artContent" style="display:none"></textarea>
					<div id="content">
					</div>
				</li>
			</ul>
			<div class="overflow">
				<button class="button" type="button" id="submit_art_botton">提交</button>
			</div>
		</form>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>	
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/wangEditor.min.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
<script type="text/javascript">
   util.addLoadEvent(function () {
        var editor = new wangEditor('content');
        // 图片路径
        editor.config.uploadImgUrl = '/wikestudy/upload_editior_image';
        // 隐藏掉插入网络图片功能。该配置，只有在你正确配置了图片上传功能之后才可用。
        editor.config.hideLinkImg = true;
        editor.create();
        EventUtil.addHandler(document.getElementById('submit_art_botton'),'click',function(event){
        	var form = document.getElementById('col_main');
        	var html = editor.$txt.html();
        	form.getElementsByTagName('textarea')[0].innerHTML = html;
			form.submit();
        });
    });
</script>
</body>
</html>