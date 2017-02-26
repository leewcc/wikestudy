<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${art_title?html}</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
</head>
<body>
	<#--<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>-->
	
	<div id="nav" class="outside">
	<div class="wrapper">
		<a id="nav_logo" href="/wikestudy/dist/jsp/common/home_page">
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

	</div>

		<form id="searchForm" action="/wikestudy/dist/jsp/common/course_query_by_key" method="post">
			<input id="navSearch" type="text" name="key" placeholder="请输入关键字搜索课程"/>
			<input id="search" type="submit" value=""/>
		</form>
	</div>
</div>



	<div class="outside">
		<div id="articleMain" class="wrapper">
		<div id="article">
			<h1>
			<#--<%=a.getArtTitle()%>-->
			${art_title?html}
			</h1>
			<div id="articleMes">
			<span id="art_type">文章类别：
		
			</span>
			<span>发布于：
			<#--<%=a.getTime() %>-->
			${art_time}
			</span>
			<span>作者：
			<#--<%=a.getAuthor()%>-->
			${art_author}
			</span>
			<span id="click_num">点击量：
			<#--<%=a.getArtClick()%> json-->
			
			</span>
			</div>
			<div id="articleCon">
			  <#--<%=a.getArtContent()%>-->
			  ${art_content}
			</div>
		</div>
		<form action="" id="myForm" method="post">
			<input type="hidden" value="${art_id}" name="artId" id="artId"/> 
			<input type="hidden" value="0" name="acomId" id="acomId"/>
			<div id="show"></div>		
			<div id="page"></div>				
		</form>
		</div>
	</div>
<#--<jsp:include page="/dist/jsp/common/footer.jsp"/>-->
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

<script type="text/javascript" src="/wikestudy/dist/js/manager/article-one.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script>
$(document).ready(function(){
	$.ajax({
		url: '/wikestudy/dist/article_data?artId=${art_id}',
		type: 'get',
		dataType: 'json',
		success:function (data){
			putData(data)				
		}
	});
});

function putData(data) {
	$('#user').html(data['userData']);
	$('#click_num').html(data['clickNum']);
	$('#art_type').html(data['artType']);

}
</script>
</body>
</html>