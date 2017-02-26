<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>回答问题</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
</head>
<body>
<div id="nav" class="outside">
	<div class="wrapper">
		<a id="nav_logo" href="/wikestudy/dist/jsp/common/HomePage">
			<img src="/wikestudy/dist/images/system/small_logo.png" alt="微课网logo" >
		</a>
		<ul>
			<li><a href="/wikestudy/dist/jsp/common/home_page">首页</a></li>
			<li><a href="/wikestudy/dist/jsp/common/course_show?grade=0&labelId=0&type=3">选课</a></li>
			<li><a href="/wikestudy/dist/jsp/common/topic/topic_list_get?labId=0&currentPage=1&type=1">在线交流</a></li>
			<li><a href="/wikestudy/dist/jsp/common/data/data_center?labId=0&grade=0&sort=false&currentPage=1">资料下载</a></li>
			<li><a href="/wikestudy/dist/jsp/common/article/article_show?cp=1&aTypeId=1">家校互动</a></li>
		</ul>

		<div id="user">
			<a id='user_name' href='/wikestudy/dist/jsp/student/common/student_home.jsp'></a>
	
			<form id="searchForm" action="/wikestudy/dist/jsp/common/course_query_by_key" method="post">
				<input id="navSearch" type="text" name="key" placeholder="请输入关键字搜索课程"/>
				<input id="search" type="submit" value=""/>
			</form>
		</div>
	</div>
</div>
<div class="outside">
	<div class="wrapper" id=nav_head" >
		<div id="center_headA">
	      <div id="center_headD">
	        <div id="center_headC"></div>
	        <h1 id="center_headB"> 个人中心</h1>
	      </div>
	    </div>


<div id="sidebar">
	<div id="intro_img">
		<img class="touxiang" id="user_photo" alt="头像" src=""  width="200px" height="200px"/>
		
	</div>
	<div id="intro_main">
		<h2 class="name"><a id='user_name' href='/wikestudy/dist/jsp/student/common/student_home.jsp'></a><button id="change" type="button" onclick="window.location.href='/wikestudy/dist/jsp/student/account/my_detail_show.jsp'">修改资料</button> </h2>
			<div id="intro_gender"><span id="user_gender"></span></div>
			<div id="intro_grade"><span id="user_grade"></span><span id="user_class"></span></div>
			<div id="intro_introduction">
				<span id="user_signature"></span>
			</div>
			<div id="intro_time">累计在线学习<span id="study_time"></span></div>
	</div>
</div>

<div id="main_area">
	<h1 id="answer_heading">测试题</h1>
<form action="/wikestudy/dist/jsp/student/course/questionnaire/questionnaire" method="post" id="update_question">
	<input type="hidden" id="secId" name="secId" value="${secId}">
	<div id="question_div">
		<#--问题的位置-->
		${question}
	</div>
	<div class="button_qun">	
		<input class="button" type="submit" value="提交考卷" name="submit">
	</div>
	

	</form>
	</div>
</div>
</div>
<div id="footer" class="outside">
	<div class="wrapper out_pad ">
		<div id="foot_href">
			<img src="/wikestudy/dist/images/system/school_name.png" alt="学校图片"/>		
			<div id="foot_fri">
				<h3>友情链接</h3>
				<p><a href="http://www.educity.cn/">希赛教育</a></p>
				<p><a href="http://www.emall.edu.cn/4401/">易学网</a></p>
				<p><a href="http://pd.ok365.com/?dm=lhhuanqiu.com&acc=0D6A16DB-693A-44CE-B7C6-58C6F175A177&poprequest=1&ref=https://www.baidu.com/link?url=HgPzcwP60VA2_DeDyUWMZgRIgeGQMV-regTqJODuNHujy9DMI76ymnro1IrEBVAs&wd=&eqid=a2272a490000fbd6000000065680213c">环球英语</a></p>
			</div>
			<div id="foot_call">
				<h3>联系我们</h3>
				<p>电话：0769-83915190</p>
				<p>地址：东莞市常平镇板石村教育路1号</p>
			</div>
		</div>
		<div id="foot_intro">
			<a href="/wikestudy/dist/jsp/common/home_page">网站首页</a>
		</div>
		<div id="foot_copy">Copyright©2015 QG Studio All Rights Reserved</div>
	</div>
</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script>
$(document).ready(function(){
	$.ajax({
		url: '/wikestudy/dist/user_data',
		type: 'get',
		dataType: 'json',
		success:function (data){
			putData(data)				
		}
	});
});
<#--拼接所有的动态内容-->
function putData(data) {
	$('#user_photo').attr('src',data['user_photo']);
	$('#user_name').html(data["user_name"]);
	$('#user_gender').html(data["user_gender"]);
	$('#user_grade').html(data["user_grade"]);
	$('#user_class').html(data["user_class"]);
	$('#user_signature').html(data["user_signature"]);
	$('#study_time').html(data["study_time"]);
}

	
</script>


</body>
</html>