<%@page import="com.wikestudy.model.pojo.Data"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>上传资源</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link href="/wikestudy/dist/css/uploadfile.css" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
<div id="wrapper">
	<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_cou">课程管理</span>
			<span id="sec_head">上传资源</span>
		</div>
		<div id="upd_data">
			<!-- <label>资料绑定名字</label> -->
			<%--三个按钮  上传 删除 下载按钮 --%>
			<div id="file_updata_area">
				<div id="fileuploader">上传</div>
		    	<p id="eventsmessage"></p>
				<button type="button" onclick="window.location.href='#'">删除</button>
				<button type="button" onclick="window.location.href='#'">下载</button>
			</div>
	<%--遍历资料输出资料列表 --%>
<%
		List<Data> datas = (List<Data>)request.getAttribute("datas");
		for(Data d : datas) {	
%>
			<%--输出文件的图标和名字 --%>
			<div class="one_data">
				<img src="/wikestudy/dist/system_icon_get?filename=file<%=d.getDataRoot().substring(d.getDataRoot().lastIndexOf(".")) %>" alt="图标" />
				<input type="text" name="dataName" value="<%=d.getDataName() %>"/>
			</div>
<%
		}
%>
		</div>
	</div>
</div>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery.uploadfile.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $("#fileuploader").uploadFile({
    	//修改这个路径为Java的。
        url: "/wikestudy/data_upload",
        
        fileName: "myfile",
        multiple: true,
        dragDrop: true,
        // returnType: 'json',
        showDone: true,
        onLoad:function(obj){
			$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Widget Loaded:");
		},
		onSubmit:function(files){
			$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Submitting:"+JSON.stringify(files));
			//return false;
		},
		onSuccess:function(files,data,xhr,pd){
			$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Success for: "+JSON.stringify(data));
		},
		afterUploadAll:function(obj){
			$("#eventsmessage").html($("#eventsmessage").html()+"<br/>All files are uploaded");
		},
		onError: function(files,status,errMsg,pd){
			$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Error for: "+JSON.stringify(files));
		},
		onCancel:function(files,pd){
			$("#eventsmessage").html($("#eventsmessage").html()+"<br/>Canceled  files: "+JSON.stringify(files));
		}
	}); 
});
</script>
</body>
</html>