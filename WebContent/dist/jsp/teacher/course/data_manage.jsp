<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.Data"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
<title>上传资料</title>
<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
<link href="/wikestudy/dist/css/uploadfile.css" rel="stylesheet">
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
		<div id="main_area">
			<div id="add_art" style="margin: 10px 20px;">
				<button type="button" onclick="window.location.href='course_manage?couId=${param.couId}&type=${param.type }&page=${param.page}'">返回</button>
			</div>
		<%--三个按钮  上传 删除 下载按钮 --%>
			<div id="file_updata_area">
			<!--	<form action="data_upload" method="post" enctype="multipart/form-data" id="photoForm">
					<input type="hidden" name="course" value="${param.couId}" />
					<input type="hidden" name="binding" value="${param.binding }" />
					<input type="hidden" name="type" value="${param.type }"/> 
					<input type="file" id="PhotoUrl" name="file" onchange="submitForm()"/>
					<div id="update_photo">上传文件</div> 
				</form>-->
				<div id="fileuploader">上传</div>
    			<p id="eventsmessage"></p>
			</div>

		<%--遍历资料输出资料列表 --%>
		<p class="illegal">${message }</p>
<%
		List<Data> datas = (List<Data>)request.getAttribute("datas");
		for(Data d : datas) {	
			String root = d.getDataRoot();
			int index = root.lastIndexOf(".");
%>
			<%--输出文件的图标和名字 --%>
				<div class="one_data">
					<form action="data_update" method="post">
						<img src="/wikestudy/system_icon_get?filename=<%=index < 0 ? "blank" : root.substring(index + 1) %>" alt="图标" />
						<input type="text" name="name" value="<%=d.getDataName() %>"/>
						<input type="hidden" name="id" value="<%=d.getDataId() %>" />
						<input type="hidden" name=couId value="${param.couId}" />
						<input type="hidden" name="binding" value="${param.binding }" />
						<input type="hidden" name="type" value="${param.type }" />
						<button type="submit">修改</button>
						<button type="button" onclick="window.location.href='data_delete?id=<%=d.getDataId() %>&binding=<%=d.getDataBinding()%>&type=<%=d.isDataMark() %>&couId=${param.couId }'">
							删除
						</button>
					</form>
				</div>
<%}%>
	<%--正在上传文件展示区 --%>
			</div>
		</div>
	</div>

<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery.uploadfile.min.js"></script> 
<script type="text/javascript">
	function submitForm(){
		var form = document.getElementById("photoForm");
		var input = document.getElementById("PhotoUrl");
		if(input.files.length > 0){
			form.submit();
		}else{
			//还不处理先
		}
	}
	function getParam(str,text){
		var binding = "",
			type = 0,
			str = window.location.href,
			count = 0,
			temp;
		var a = JSON.parse(text);
		debugger;
		count = str.indexOf("=")+1;
		binding = str.slice(count,str.indexOf("&"));
		count = str.indexOf("=",count)+1;
		temp = str.indexOf("&",count);
		type = str.slice(count,temp);
		
		if(a.message == "success"){
			
// 			return "{\"binding\":\""+binding+"\",\"type\":\""+type+"\",\"index\":\""+ a.mark+"\"}";
			return "binding="+binding+"&type="+type+"&index="+ a.mark;
		}else{
			alert("你的文件上传失败");
		}
		
	};
	
    $(document).ready(function() {
         $("#fileuploader").uploadFile({
         	//改这个路径为Java的。
            url: "data_upload",
            fileName: "myfile",
            multiple: true,
            dragDrop: true,
            returnType: 'json',
            showDone: true,
		 	onSubmit:function(files){
		 		//$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Submitting:"+JSON.stringify(files));
		 	},
		 	onSuccess:function(files,data,xhr,pd){
				var a = getParam(window.location.href,xhr.responseText);
				console.log(a);
		 		$.ajax({
		 			type: "get",
		 			url: "data_confirm",
		 			data: a,
		 			async: true,
		 			success: function(){
		 				//alert("成功");
		 				window.history.go(0);
		 			},
		 			error: function(){
		 				//alert("失败");
		 			}
		 			
		 		});
		 	//	$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Success for: "+JSON.stringify(data));
		 	},
		 	afterUploadAll:function(obj){
		 		console.log('ddddd');
		 		//return false;
		 	},
		 	onError: function(files,status,errMsg,pd){
		 		//alert("onerror");
		 		//$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Error for: "+JSON.stringify(files));
		 	},
		 	onCancel:function(files,pd){
		 		//alert("oncancel");
		 		//$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Canceled  files: "+JSON.stringify(files));
		 	}
		 }); 
     });
    </script>
</body>
</html>