<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>管理文章</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
	<style>
	.wangEditor-container .clearfix:after{
		display: none;
	}</style>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside" style="overflow:hidden;">
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
			<div id="col_area">
				<p class="guide_message" style="text-align: left;">先选择你创建的文章类型</p>
				<div id="mana_art_label">
					<input type="hidden" id="typeId"/><%--获得页码的时候用到 --%>
					<%--在这里拼接所有的  --%>
					<form action="" method="post" id="typeManager">
						<div id="atype"></div>
						<fieldset>
							<legend>新建标签</legend>
							<label for="aaTypeName">名字：</label>
							<input type="text" name="aaTypeName" id="aaTypeName"/>
							<label for="aaTypeDes">简介：</label>
							<textarea name="aaTypeDes" id="aaTypeDes"></textarea>
							<button type="button"  name="option" onclick="putData();">增加</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>   
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<!-- <script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script> -->
<script type="text/javascript">
	var putData =function() {//用来修改或者增加文章标签的
		var str = $('#typeManager').serialize();
		str += "&option="+encodeURIComponent(event.target.innerHTML);
		var id = document.getElementById("typeId").value;
		$.ajax({
			type : "post",
			url : "/wikestudy/dist/jsp/manager/article/art_type_manage",
			data : str,
			async : true,
			error : function(request) {
				getData();
			},
			success : function(data) {
				getData();
			}
		});
	}
	var getData = function() {//用来获得文章标签的
		jQuery.ajax({
			url : "/wikestudy/dist/jsp/manager/article/article_before",
			type : 'POST',
			dataType : "json",
			data : {},
			success : function(data) {
				conData(data);
				var id = document.getElementById("typeId").value;
				//manage(id);
				updateButton();
			}
		});
	};
	function pageData() {
		var typeId=document.getElementById("typeId").value;
		var cp=event.target.childNodes[0].nodeValue;
		
		jQuery.ajax({
			url:'/wikestudy/dist/jsp/manager/article/aype_art_manage?typeId='+typeId+'&cp='+cp,
			type:"get",
			dataType:"json",
			data:{},
			success:function (data) {
					//manageData(data);
				}
			});
	}
	function updateButton() {//委托事件给按键的
		$(".update_type").click(function() {
			var par = event.target.parentNode.parentNode;
			td = par.getElementsByTagName("input");
			for (var i = 0; i < td.length; i++) {
				if (td[i].disabled) {
					td[i].disabled = false;
					td[i].name="aTypeName";
				}
				if(td[i].type=="hidden") {
					td[i].name="aTypeId";
				}
			}
			td = par.getElementsByTagName("textarea");
			for (var i = 0; i < td.length; i++) {
				if (td[i].disabled) {
					td[i].disabled = false;
					td[i].name="aTypeDes";
				}
			}
		});
	}
	var conData = function (data) {//拼接文章种类的
		var typeId = document.getElementById("typeId");
		typeId.value = data[0].ATypeId;
		$('#atype').empty();
		var type = "";
		for (var i = 0; i < data.length; i++) {
			type += '<div class="one_art_type">'
				+ '<p>类别：' + data[i].ATypeName + '</p>'
				+ '<p>简介：' + data[i].ATypeDes + '</p>'
				+ '<button onclick="clickme(' + data[i].ATypeId + ')" type="button">新增文章</button>'
				+ '</div>';			
		}
		$('#atype').append(type);
	}
	function clickme(num) {//跳到做文章的页面的
		var par = event.target.parentNode;
		window.location.href='article_make?atypeId='+num;
	}
	util.addLoadEvent(getData);
</script>
</body>
</html>