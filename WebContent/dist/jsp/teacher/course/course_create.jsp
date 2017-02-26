<%@page import="com.wikestudy.model.pojo.Label"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>创建课程</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
	      	<div id="center_headD">
	        	<div id="center_headC"></div>
	        	<h1 id="center_headB">个人中心</h1>
	      	</div>
	    </div>
	<%--第一步：	include进老师简介 --%>
		<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="course_head"><a href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
			<li id="no_note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
			<li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
	<%
		List<Label> labelList = (List) request.getAttribute("labels");
	%>
		
		<div id="main_area" class="create_course">
			<h1>创建课程</h1>
		<hr>
		
		
		<form action="course_add" method="post" onsubmit="return checkData()">
		<ul>
			<li><label for="photo"><span>课程图片：</span></label>
			<%
				String imageUrl=(String)request.getAttribute("imageUrl");
				if(imageUrl==null) imageUrl="/wikestudy/dist/images/lesson/default.png";//默认图片
			%>
			<a href="photo_f_upload?imageUrl=<%=imageUrl %> &couId=0">	
				<img src="<%=imageUrl %>" style="height:80px">
			</a>
			<input type="hidden" name="imageUrl" id="imageUrl" value="<%=imageUrl %>">
			<li><label for="couName">课程名称：
				<input type="text" id="couName" name="couName"/>
				<span  id="error" style="display:none">课程名称不能为空</span>
				<span  id="error2" style="display:none">课程名称不能超过40个字符</span></label></li>
			<li><%--课程简介 --%>
				<label for="couBrief"><span>课程简介：</span>
				<textarea id="couBrief" name="couBrief"></textarea></label>
			</li>
			<li><%--选择课程类型 1-初一;2-初二;3-初三;4-公众--%>
			<label for="couType">课程年级：
				<select id="couType" name="couType">
					<option value="1">初一</option>
					<option value="2">初二</option>
					<option value="3">初三</option>
				</select></label>
			</li>
			<li><%--选择课程标签, 考虑标签是否有创建的情况 --%>
			
				<% if (labelList.size() == 0) {%>
					<p>当前未创建任何标签</p>
				<%} else { %>
				<label for="labelId">课程标签：
				<select id="labelId" name="labelId">
				<% for (Label label : labelList) {%>
					<option value="<%=label.getLabId()%>"><%=label.getLabName() %></option>
				<% }%>
				</select></label>
				<% }%>
			</li>
			</ul>
			<div class="button_qun">
				<button type="button" id="cancel" onclick="window.history.go(-1)">取&nbsp;消</button>
				<button type="submit" id="create">创&nbsp;建</button>
			</div>
		</form>
	</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<%
String message=(String)request.getAttribute("message");
if(message!=null) {
	out.print("<script>alert('"+message+"')</script>");
}
%>
<!--[if lt IE 9]>
		<script>
			function show(condition, message, time){
			if(condition){
				var target = document.body;
				if(!target){
					target = document.createElement('body');
					document.appendChild(target);
				}
				target.innerHTML = '<div id="popup" style="width: 100%;height: 2000px;position: fixed;top: 0;left: 0;background: rgba(0,0,0,0.4);background: black;"><div id="popup_area"style="position: absolute; width: 500px;padding: 40px;left: 50%;top: 50px;border-radius: 200px;border: 9px solid #EEE;margin-left: -290px;background: white;"><p>'+message+'</p></div></div>';

			}
		};

		show(true, '请下载最新版本的现代浏览器：譬如<a href="http://www.firefox.com.cn/download/">火狐浏览器</a>或<a href="http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html">谷歌浏览器</a>', 24*60*60*60*1000*60);
	</script>
	<![endif]-->
<!-- 	<script>
	  function checkData(){
	    if( document.getElementById("couName").value.replace(/(^\s*)|(\s*$)/g, "")==""){
	      document.getElementById("error").style.display="inline";
	      return false;
	    }
	    
	    if( document.getElementById("couName").value.length > 40){
	        document.getElementById("error2").style.display="inline";
	        return false;
	    }
	    
	    return true;
	  }
	</script> -->
</body>


</html>