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
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
	<link rel="stylesheet" type="text/css" href="/wikestudy/dist/css/wangEditor.min.css">
	<style>
	.wangEditor-container .clearfix:after{
		display: none;
	}</style>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
	<%--分为两部分 显示已设置的一周之星 未设置的学生列表 --%>
<%
	String atypeId=(String)request.getAttribute("atypeId");
%>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
		    <div id="center_headD">
		        <div id="center_headC"></div>
		        <h1 id="center_headB"> 个人中心</h1>
		    </div>
	    </div>
    	<%--第一步：	include进老师简介 --%>
		<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
			<li id="note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
			<li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
		<div id="main_area">
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
				<div id="add_art">
	    			<button class="button" type="submit" id="submit_art_botton" >提交</button>
	    		</div>
			</form>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/wangEditor.min.js"></script>
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