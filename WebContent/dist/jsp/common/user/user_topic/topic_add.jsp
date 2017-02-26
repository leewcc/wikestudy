<%@page import="com.wikestudy.model.pojo.Label"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page import="sun.nio.cs.ext.ISCII91"%>
<%@ page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
	<meta charset="UTF-8">
	<title>添加话题</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<link rel="stylesheet" type="text/css" href="/wikestudy/dist/css/wangEditor.min.css">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"/>
<div class="outside">
	<div id="wrapper">
	   <%--  侧边栏  --%>
	    <jsp:include page="personal_topic.jsp">
	    	<jsp:param name="sidebar" value="none"></jsp:param>
	    </jsp:include>
		<div id="add_topic">
		<h1>添加话题</h1>
			<form action="topic_release" method="post" id="form_topic">
<%
		int lid = 0;
		String label = request.getParameter("label");
		try{
			lid = Integer.parseInt(label);
		}catch(NumberFormatException e){
			lid = 0;
		}
		List<Label> labels = (List<Label>)request.getAttribute("labels");
%>
				<ul>				
					<li>	
						<label for="label">标签：</label>
						<p class="illegal">${label }</p>
						<select id="label" name="label">
<%
				for(Label l : labels){
%>
						<option value="<%=l.getLabId() %>" <%=l.getLabId() == lid ? "selected" : "" %>><%=l.getLabName() %></option>
<%
				}
%>
						</select>
					</li>
					<li>
						<label for="title">标题：</label>
						<p class="illegal">${title }</p>
						<input type="text" name="title" id="title" value="${param.title }" />
					</li>				
					<li>
						<label for="content">内容：</label>
						<p class="illegal">${content }</p>
						<textarea name="content" style="display:none" cols="30" rows="10"></textarea>
						<div id="content">
						</div>
					</li>
					<li>	
						<button type="button" id="submit_topic">发布</button>
						<button type="button" onclick="window.location.href='/wikestudy/dist/jsp/common/topic/topic_list_get?labId=0&currentPage=1&type=1'">取消</button>
				</li>
			</ul>
		</form>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/wangEditor.min.js"></script>
<script type="text/javascript">
   util.addLoadEvent(function () {
        var editor = new wangEditor('content');
        // 图片路径
        editor.config.uploadImgUrl = '/wikestudy/upload_editior_image';
        // 隐藏掉插入网络图片功能。该配置，只有在你正确配置了图片上传功能之后才可用。
        editor.config.hideLinkImg = true;
        editor.create();
        EventUtil.addHandler(document.getElementById('submit_topic'),'click',function(event){
        	var form = document.getElementById('form_topic');
        	var html = editor.$txt.html();
        	form.getElementsByTagName('textarea')[0].innerHTML = html;
			form.submit();
        });
    });
</script>
</body>
</html>