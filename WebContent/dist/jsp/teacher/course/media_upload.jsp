<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>上传视频</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/Huploadify.css"/>
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
</head>
<body>
<script type="text/javascript">
	function checkEmpty(form) {
		var src = document.getElementById("src").value;
		if(null === src || "" === src) {
			alert("请选择要上传的文件");
			return false;	
		} else {
			alert("上传视频由ffmpeg.exe进行转码");
		}
	}
</script>
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
		<li id="course_head"><a href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
		<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
		<li id="no_note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
		<li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
	</ul>

	<div id="main_area">

		<div id="add_art" style="margin: 10px 20px;">
				<button type="button" onclick="window.location.href='course_manage?couId=${param.couId}&type=${param.type }&page=${param.page}'">返回</button>
		</div>

		<p class="guide_message">请选择要上传的视频</p>
		<form id="give_file" action="video_upload" method="post" enctype="multipart/form-data">
			<p class="illegal">${status}</p>
			<input type="hidden" name="secId" value="<%=request.getAttribute("secId") %>">
			<input type="hidden" name="couId" value="<%=request.getAttribute("couId") %>">
			<div>
			<input type ="file" name="file">
			</div>
			<button type="submit">上传</button>
		</form>
		<div id="video" style="margin-bottom: 50px;    min-height: 600px;">
		<%String mediaUrl=(String)request.getAttribute("mediaUrl"); %>
		<%if(!("no video".equals(mediaUrl)||"".equals(mediaUrl)||null==mediaUrl))
		{
			%>
		<%-- <object type="application/x-shockwave-flash" id="flash" style="width: 600px; height: 400px;">
			<param name="movie" id="movie" value="/wikestudy/dist/videos/flvplayer.swf?autostart=false&file=<%=mediaUrl %>"/>
			<param name="wmode" value="transparent" />
			<param name="quality" value="high" />
			<param name="allowfullscreen" value="true" />
		</object> --%>
		<div id="media">
			<div class="video" id="HLSPlayer" style="width:100%;height:300px;">
				<div id="my">	
		        <script>
		            var vID = "";
		            var vWidth = "100%";
		            var vHeight = "600";
		            var vPlayer = "/wikestudy/dist/tools/HLSPlayer.swf?v=1.5";
		            var vHLSset = "/wikestudy/dist/tools/HLS.swf";
		            var vPic = "/wikestudy/dist/images/vico.jpg";
		            var vCssurl = "/wikestudy/dist/images/mini.css"; 
		            var vHLSurl = "http://<%=mediaUrl %>";
		            <%-- 		            var vHLSurl = "http://"+<%=cs.getSecVideoUrl() %>; --%>
		        </script>
		        <script type="text/javascript" src="/wikestudy/dist/js/hls.min.js" ></script>
		        </div>
		        </div>
   		 </div>
		<%}
		%>

<!-- 		<div id="upload"></div> -->
<%-- 		<input type="hidden" id="secId" value="<%=request.getAttribute("secId") %>"> --%>
<!-- 		<button id="btn1" class="uploadify-button">暂停</button> -->
<!-- 		<button id="btn2"  class="uploadify-button">上传</button> -->
<!-- 		<button id="btn3"  class="uploadify-button">取消</button> -->

	</div>
	</div>
	</div>
	</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script src="/wikestudy/dist/js/jquery.Huploadify.js"></script>
<script type="text/script" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript">
	$(function(){
		
		var up = $('#upload').Huploadify({
			auto:true,
			fileTypeDesc:'All Files',
			fileTypeExts:'*.*',
			multi:false,
			fileSizeLimit:99999999,
			breakPoints:true,
			saveInfoLocal:true,
			showUploadedPercent:true,//是否实时显示上传的百分比，如20%
			showUploadedSize:true,
			removeTimeout:9999999,
			uploader:'media_add',
			onUploadStart:function(){	
				var filename=$('.up_filename').eq(0).text();
				up.Huploadify('settings','formData', {'filename':filename});
			},
			onUploadComplete:function(){
				var filename=$('.up_filename').eq(0).text();
				var secId=$('#secId').attr("value");
				//启动转码
				$.ajax({
					url:"/wikestudy/dist/jsp/teacher/course/media_convert",
					type:"post",
					data:{"fileName":filename,"secId":secId},
					dataType:"json",
					success:function(data) {
						
						$('#movie').remove();
						//拼接视频
						$('#flash').append('<param name="movie" id="movie" value="/wikestudy/dist/videos/flvplayer.swf?autostart=true&file='+data['filename']+'"/>')
				
					},
					error: function() {
						alert("上传失败");
					}
				});
				
			},
		});

		$('#btn1').click(function(){
			up.stop();
		});
		$('#btn2').click(function(){
			up.upload('*');
		});
		$('#btn3').click(function(){
			up.cancel('*');
		});

		var search = window.location.search;

		if(search.indexOf('id=') !== -1){
			// js获取id
			var id = window.location.search.slice(window.location.search.indexOf('id=')+3);
			if(id !== 'error' && id !== null && id !== undefined){
				$.ajax({
					type: 'get',
					url: 'http://api.qiniu.com/status/get/prefop',
					data: {
						id: id
					},
					ContentType: 'json',
					dataType: 'jsonp',
					success: function(data){
						if(data.error){
							alert('文件上传失败');
						}else{
							// 上传成功
						}
					},
					error: function(data){
						console.log('[system ajax]上传后视频请求失败');
					}
				});
			}
			
		}

	});
</script>
</body>
</html>