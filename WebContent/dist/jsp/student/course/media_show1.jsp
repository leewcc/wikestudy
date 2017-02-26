<%@page import="com.wikestudy.model.pojo.CouChapter"%>
<%@page import="com.wikestudy.model.pojo.CourseInfor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.CouSection"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>视频播放页面</title>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/media.css"/>
</head>
<body>
<%
	//获得课程的信息进行拼接显示在课程的列表中
	CourseInfor c= (CourseInfor) request.getAttribute("c");
	String courseName = c.getCou().getCouName();
	ArrayList<CouChapter> lcc = (ArrayList<CouChapter>)c.getCouChapterList();
	ArrayList<CouSection> lcs = (ArrayList<CouSection>)c.getCouSectionList();
	//获得章节的信息
	CouChapter cc = (CouChapter) request.getAttribute("cc");
	//获得课时信息
	CouSection cs = (CouSection) request.getAttribute("cs");
	String mediaPath = cs.getSecVideoUrl();//"lesson/1449497148882.flv";地址是相对与播放器的地址
		
%>
<%--用来获得评论用到的secId --%>
<div id="media_header">
	<button onclick="window.history.go(-1)" ></button>
	<input type="hidden" value="<%=cs.getSecId() %>" name="secId" id="secId">
	<h4 id="chaName"><%=cc.getChaName() %></h4> 
	<h4 id="secName"><%=cs.getSecName() %></h4>
	<h4 id="couName">[<%=courseName %>]</h4>
</div>
<%--播放器 --%>
<div id="wrapper">
	<div id="list_section">
		<div id="list">
			<%--下面这个是章节列表 --%>
			<div id="cha_list">
				<h1><%=courseName %></h1>
<%//需找两者中chaId一样的，显示过的cousection用一个Collection包起来一起扔掉
	for(CouChapter ci:lcc) {
%> 
				<div class="cou_chapter">
					<h4><%=ci.getChaName() %></h4><%--这是章节名 --%>
<%			
		for(CouSection si:lcs) {
			List<CouSection> temp=new ArrayList<CouSection>();
			if(si.getChaId()==ci.getChaId()) {
		//显示该章节的的课时
%>
				<div class="cou_section"><%--这个div可用来包课时的名字 --%>
					<input type="hidden" value="<%=si.getSecName() %>" name="secName"/>
					<input type="hidden" value="<%=si.getSecId() %>" name="secId"/>
					<input type="hidden" value="<%=ci.getChaName()%>" name="chaName">
					<input type="hidden" value="<%=si.getSecVideoUrl() %>" name="mediaPath"/>
					<a onclick="getMedia()"><%=si.getSecName() %></a>
				</div>
<%
				temp.add(si);				
		}
		lcs.remove(temp);
	}
		%>
				</div>
<% }%>
			</div>
	<%--下面这个是评论列表 --%>
			<div id="reply_list">
			 <form action="" id="myForm" method="post"> 
					<textarea id="content" name="content"></textarea>
					<button type="button" onclick="putData()">提交</button>
				</form> 
					<%--<div id="page"></div> --%>
					<p id="show"></p>		
					<input type="hidden" id="page" value="1"/>
					<input type="button" class="button" onclick="getData()" value="获得更多"/>
			</div>
			<%--下面这个是笔记列表 --%>
			<div id="note_list">
				<%-- 提交成功后显示 --%>
				<div><span id="succRes">对不起，老师没有笔记功能</span>
				<textarea id="noteCont" name="noteCont"></textarea>
				<button type="button" onclick="putNote()">提交</button>
				</div>
				<div id="show_note"></div>
			</div>
			<%--下面这个是话题列表 --%>
			<div id="topic_list">
			</div>
		</div>
		<ul id="video_list">
			<li class="selected"><button type="button" id="show_cha"></button></li>
			<li><button type="button" id="comment_get" onclick="getData()"></button></li>
			<li><button type="button" id="doNote" onclick="notes_get()"></button></li> <%--做笔记的弹窗 --%>
			<li><a id="getTest" href="/wikestudy/dist/jsp/student/course/questionnaire/questionnaire?secId=<%=cs.getSecId() %>"  target="_blank">
			</a></li><%--测试题 --%>
		</ul>
	</div>
 	<div id="video">
 		<img id="no_video" src="/wikestudy/dist/images/system/no_video.png" alt="当前没有视频">
		<%if(!(cs.getSecVideoUrl()==""||cs.getSecVideoUrl()==null))
		{
			%>
		<object type="application/x-shockwave-flash">
			<param name="movie" id="movie" value="/wikestudy/dist/videos/flvplayer.swf?autostart=false&file=<%=cs.getSecVideoUrl() %>">
			<%-- &image=/wikestudy/temp/temp.jpg"/>  --%>
			<param name="wmode" value="transparent" />窗口设置透明
			<param name="quality" value="high" />质量设置高
			<param name="allowfullscreen" value="true" />
		</object>
					<%
		}
		%>
	</div>
		<input type="hidden" id="secId" name="secId" value="<%=cs.getSecId() %>"/>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>

<%--课程章节课时列表 --%>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script><script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script>

<script>
	function showImg(){
		var image = document.getElementById("no_video");
		if(image){
			image.style.display = "block";
		}
	}
<%
	String message=(String)request.getAttribute("message");
	if(message != null) {
		out.print("showImg();");
	}
%>
	$(document).ready(function(){
		//章
		$('#show_cha').click(function(){
			$('#show_cha').parent().addClass("selected").siblings().removeClass("selected");
			if($('#cha_list').css("display")==="none"){
				$('#reply_list').hide();
				$('#note_list').hide();
				$('#topic_list').hide();
				$('#data_list').hide();
				$('#cha_list').show();
			}
		});
		//论
		$('#comment_get').click(function(){
			$('#comment_get').parent().addClass("selected").siblings().removeClass("selected");
			if($('#reply_list').css("display")==="none"){
					$('#cha_list').hide();
					$('#note_list').hide();
					$('#topic_list').hide();
					$('#data_list').hide();
					$('#reply_list').show();
			}
		});
		//记
		$('#doNote').click(function(){
			$('#doNote').parent().addClass("selected").siblings().removeClass("selected");
			if($('#note_list').css("display")==="none"){
				$('#cha_list').hide();
				$('#reply_list').hide();
				$('#topic_list').hide();
				$('#data_list').hide();
				$('#note_list').show();
			}
		});
	});
 	var getData=function() {
 		//得到章节的内容
 		var cp= document.getElementById("page").value;
 	 	var secId=document.getElementById("secId").value;
 		jQuery.ajax({
 			url:'/wikestudy/dist/jsp/student/course/cou_comment_get?cp='+cp+'&secId='+secId,
			type:'GET',
			dataType:"json",
			data:{},
			success: function (data) {
 				//这是返回数据的处理
 				conData(data);
 				//alert(data['nd'].pageElem[0]['NDReleTime']);
			}
 		});
 	}
 	
 	var conData=function(data) {
 		$('#show').empty();
 		/*评论*/
 		var con = data['nd'].pageElem;
 		var show = "";//用来拼接那些字符串
 		for(var i = 0; i < con.length; i++) {
	 		show += '<div><p>' +
	 		con[i]['stuName']+"："+
			con[i]['NDContent'] +
	 		'</p><span class="time">'+ '时间：' +
	 		con[i]['time'] +
	 		'</span>' +
	 		'</div>';
 		}
 		$('#show').append(show);//评论
 		
 		/*查看更多*/
 		var page=data['nd']['currentPage']+1;
 		document.getElementById('page').value=page;
 	}
 	var putData = function () {
 		$("#messFId").remove();
 		
 		//document.getElementById("page").value=1;
 		var secId=document.getElementById("secId").value;
 		
 		var content = document.getElementById("content").value;
 	    var div = document.createElement("div");
 	    var span = document.createElement("span");
 	    div.appendChild(span);
 	    div.id = "messFId";
 	    var flag = true;
 		if (content.replace(/(^\s*)|(\s*$)/g, "")=="") {
 			span.innerHTML = "评论内容不能为空";
 			flag = false;
 		}
 		
 	    if (content.length > 600) {
 	    	span.innerHTML = "评论内容不能超过600字";
 	    	flag = false;
 	    }
 	    
 	   if (!flag) {
 		   document.getElementById("myForm").insertBefore(div,
 	    		document.getElementById("content"));
 		   return ;
 	   }
	 	   
 		$.ajax({
			type:"POST",
			url:"/wikestudy/dist/jsp/student/course/PutCouComment?secId="+secId,
			data:$('#myForm').serialize(),
			asyns:true,
			error : function(request) {
			},
			success : function(data) {
				getData();
				
			}
 		});
 	}
 	var notes_get=function() {
 		//得到章节的内容
 	 	var secId=document.getElementById("secId").value;
 		jQuery.ajax({
 			url:'/wikestudy/dist/jsp/student/course/notes_get?secId='+secId,
			type:'GET',
			dataType:"json",
			data:{},
			success: function (notes) {
 				//这是返回数据的处理
 				conNotes(notes);
 				//alert(data['nd'].pageElem[0]['NDReleTime']);
			}
 		});
 	}
 	var conNotes=function(data) {
 		$('#show_note').empty();
 		/*评论*/
 		var con = data['notes'].pageElem;
 		var show = "";//用来拼接那些字符串
 		for(var i = 0; i < con.length; i++) {
	 		show += '<div><p>'+
			con[i]['NDContent'] +
	 		'</p><span class="time">'+ '时间：' +
	 		con[i]['time'] +
	 		'</span>'+
	 		'</div>';
 		}
 		$('#show_note').append(show);//评论
 		
 		/*查看更多*/
 		var page=data['notes']['currentPage']+1;
 	/* 	var moreLink='<a href="/wikestudy/dist/jsp/student/course/cou_comment_get?cp='
 				+ cp + '&secId=' + document.getElementById("secId").value+'">查看更多</a>';
 				console.log(moreLink); */
 		document.getElementById('page').value=page;

 	}
 	
 	var putNote = function() {
 			$("#messId").remove();
 			
	 		var secId=document.getElementById("secId").value;
	 		var content = document.getElementById("noteCont").value;
	 		
	 	    var div = document.createElement("div");
	 	    var span = document.createElement("span");
	 	    div.appendChild(span);
	 	    div.id = "messId";
	 	    
	 	   
	 	   
	 	    var flag = true;
	 		if (content.replace(/(^\s*)|(\s*$)/g, "")=="") {
	 			span.innerHTML = "笔记内容不能为空";
	 			flag = false;
	 		}
	 		
	 	    if (content.length > 600) {
	 	    	span.innerHTML = "笔记内容不能超过600字";
	 	    	flag = false;
	 	    }
	 	   
	 	    
	 	    
	 	   if (!flag) {
	 		   document.getElementById("note_list").insertBefore(div,
	 	    		document.getElementById("noteCont"));
	 		   return ;
	 	   }
	 	   
	 		$.ajax({
	 			type : "POST",
	 			url  : "/wikestudy/dist/jsp/student/course/section_note_save",
	 			data : { "secId" : secId, 
	 				 	"content" : content
	 				 },
	 				
	 			
	 			asyns:false,
				error : function(request) {
					
				},
				success : function(data) {
					var json = JSON.parse(data);
					if(json["message"] == "error"){
						document.getElementById("noteCont").value = "";
						$("#succRes").show();
						
						setTimeout(function() {
							$("#succRes").hide();
						}, 5000)
					}
					
					notes_get();
					
				}
	 		});
	 	}
 	
 	var getMedia = function () {//刷新页面展示新的章节 修改视频信息和测试题信息
		var td = event.target.parentNode.getElementsByTagName("input");
		for(var i = 0; i < td.length; i++) {
			if(td[i].name == "secName") {
				var value = document.createTextNode(td[i].value);
				document.getElementById("secName").innerHTML = "";
				document.getElementById("secName").appendChild(value);
			} else if(td[i].name == "chaName") {
				var value = document.createTextNode(td[i].value);
				document.getElementById("chaName").innerHTML = "";
				document.getElementById("chaName").appendChild(value);
			}else if(td[i].name == "secId") {
				document.getElementById("secId").value = td[i].value;//进行完这一步之后就要重新拼接评论字符串
			} else if(td[i].name = "mediaPath") {
				$("#video").empty();
				var v ='';
					
				if(!(td[i].value==""||td[i].value==null)) {
					v=v+'<object  type="application/x-shockwave-flash" width="400" height="400">'
					+'<param name="movie" id="movie" value="../../../../videos/flvplayer.swf?autostart=true&file='+td[i].value+'"/>'
					+'<param name="wmode" value="transparent"/>'
					+'<param name="quality" value="high" />'
					+'<param name="allowfullscreen" value="true" />'
					v+='</object>'
				}
					$("#video").append(v);
			}
		} 
		//修改测试题信息
		document.getElementById("getTest").href="/wikestudy/dist/jsp/student/course/questionnaire/questionnaire?secId="
				+$('#secId').prop('value');
 	}
	 </script>
</body>
</html>