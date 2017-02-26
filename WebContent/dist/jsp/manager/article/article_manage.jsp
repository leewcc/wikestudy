<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>管理文章</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
<div id="wrapper">
	<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_art">文章管理</span>
			<span id="sec_head">管理文章</span>
		</div>
		<div id="col_area">
			<div id="mana_art_label">
				<input type="hidden" id="typeId"/><%--获得页码的时候用到 --%>
				<%--在这里拼接所有的  --%>
				<form action="" method="post" id="typeManager">
					<h2>标签管理</h2>
					<div id="atype"></div>
					<fieldset>
						<legend>新建标签</legend>
						<label for="aaTypeName">名字：</label>
						<input type="text" name="aaTypeName" id="aaTypeName"/>
						<label for="aaTypeDes">简介：</label>
						<textarea name="aaTypeDes" id="aaTypeDes"></textarea>
						<button type="button"  name="option" onclick="putData();">增加</button>
					</fieldset>
					<button type="button" name="option" onclick="putData();" style="margin: 10px auto;display: block;">确定修改</button>
				</form>
			</div>
			<div id="hehe">
				<div id=articleDiv>
				<p class="no_p">当前没有选中文章类型</p></div>
				<div id="page"></div>
			</div>
		</div>
	</div>
</div>   
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
<script type="text/javascript">
	var putData =function() {//用来修改或者增加文章标签的
		var str = $('#typeManager').serialize();
		str += "&option="+encodeURIComponent(event.target.innerHTML);
		var id=document.getElementById("typeId").value;
		$.ajax({
			type : "POST",
			url : "/wikestudy/dist/jsp/manager/article/art_type_manage",
			data : str,
			async : true,
			error : function(request) {
				getData();
			},
			success : function(data) {
				getData();
				document.getElementById('aaTypeName').value='';
				document.getElementById('aaTypeDes').value='';
			}
		});
/* 			$('#atype').empty();
		getData(); */
	}
	var getData = function() {//用来获得文章标签的
		$.ajax({
					url : "/wikestudy/dist/jsp/manager/article/article_before",
					type : 'POST',
					dataType : "json",
					data : {},
					success : function(data) {
						conData(data);
						var id=document.getElementById("typeId").value;
						manage(id);
						updateButton();
					}
				});
	};
	var deleData= function (one) {//用来删除文章的
		var type=event.target.parentNode.childNodes[1].value;
		$.ajax({
			type:"get",
			url:"/wikestudy/dist/jsp/manager/article/"+one,
			async:true,
			success: function () {
				var id=document.getElementById("typeId").value;
				manage(id);
				},
				error:function () {
					var id=document.getElementById("typeId").value;
					manage(id);
				}
			});
		}
 	function deleteType(one) {//删除文章标签
 		jQuery.ajax({
			url : '/wikestudy/dist/jsp/manager/article/article_delete?typeId='+one,
			type:"get",
			async:true,
			success:function () {
				getData();
				},
			error: function () {
				getData();
				}
			});
	}
	function pageData() {
		var typeId=document.getElementById("typeId").value;
		var cp=event.target.childNodes[0].nodeValue;
		
		jQuery.ajax({
			url:'/wikestudy/dist/jsp/manager/article/aype_art_manage?typeId='+typeId+'&cp='+cp,
			type:"get",
			dataType:"json",
			data:{},
			success:function (data) {
					manageData(data);
				}
			});
	}
	var manage=function(one) {//获得某种类型文章
		//在此拼接上该文章的种类
		document.getElementById("typeId").value=one;
		jQuery.ajax({
			url : '/wikestudy/dist/jsp/manager/article/aype_art_manage?typeId='+one,
			type:"get",
			dataType:"json",
			data:{},
			success:function (data) {
					manageData(data);
				}
			});
		$('#typeId').value=one;
	}
	function manageData(data) {//用来获得该类型的文章的
		$("#articleDiv").empty();
		var con=data["pea"].pageElem;
		//拼接文章
		var content='';
		if(data["pea"].pageElem.length != 0){
			for(var i=0;i<con.length;i++) {
				content+='<div class="one_art">'
					+'<h3><a href="article_one?artid='+con[i].artId+'&typeid='+con[i].artTypeId+'">'+con[i].artTitle+'</a></h3>'//文章标题
					+'<input type="hidden" value='+con[i].artTypeId+'>'
					+'<span>'+con[i].author+'</span>'//文章作者
					+'<span>'+con[i].artTime+'</span>'//文章时间
					+'<a onclick=deleData("article_delete?artid='+con[i].artId+'&typeId='+con[i].artTypeId+'")>删除</a>'
					+'</div>';
			}
		}else{
			content = "<p class=\"no_p\">当前类型没有文章</p>";
		}
			$("#articleDiv").append(content);

			$("#page").empty();

		//拼接文章页码
		var page='';
		var tp=data["pea"].totalPage;
		var cp=data["pea"].currentPage;
		var selected="";
		if(cp>1) {
			page+='<a onclick="pageData()" id="previousPage" >'+(cp-1)+'</a>';
		}
		if(tp<=10) {
			for(var i=1;i<=tp;i++) {
				selected=(i==cp)? 'class=="selected"':'';
				page+='<a onclick="pageData()" '+selected+'>'+i+'</a>';
			}
		} else {
			var selected1 =(1==cp)?'class=="selected"':'';
			var selected2 =(2==cp)?'class=="selected"':'';
			page+='<a onclick="pageData()" '+selected1+'>1</a>';
			page+='<a onclick="pageData()" '+selected2+'>2</a>';
			i=cp-2;
			if(i>3) {
				page+='<span>...</span>';
			}else {
				i=3;
			}
			for(;i<=cp+2 &&i<tp-1;i++) {
				selected=(i==cp)?'class="selected"':'';
				page+='<a onclick="pageData()" '+selected+'>'+i+'</a>';
			} 
			if(i<tp-1) {
				page+='<span>...</span>';
			}
			selected1 =(cp==tp-1)?'class="selected"':'';
			selected2 = (cp==tp)?'class="selected"':'';
			page+='<a onclick="pageData()" '+selected+'>'+(tp-1)+'</a>';
			page+='<a onclick="pageData()" '+selected2+'>'+tp+'</a>';
		}
		if(cp<tp) {
			page+='<a onclick="pageData()" id="nextPage">'+(cp+1)+'</a>'
		}
		
		$('#page').append(page);
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
		$('#atype').empty();
		if(data[0]!=null) {
			typeId.value = data[0].ATypeId;
		var type = "";
		for (var i = 0; i < data.length; i++) {
			type += '<div onclick="manage('+data[i].ATypeId+')">'
					+ '类别：<input type="text" disabled="true" value="'+data[i].ATypeName+'"/>'
					+'<input type="hidden" name="aTypeId" value="'+data[i].ATypeId
					+'"/>简介：<textarea  disabled="true" >'+data[i].ATypeDes+'</textarea>'
					+'<button class="update_type" type="button">修改</button>'
					+'<button onclick="clickme()" type="button">新增文章</button>'
					+'<button onclick="deleteType('+data[i].ATypeId+')" type="button">删除</button>'
					+'</div>';			
		}
		
		$('#atype').append(type);
		}
	}
	
	function clickme() {//跳到做文章的页面的
		var par = event.target.parentNode;
		var td = par.getElementsByTagName("input");
		for (var i = 0; i < td.length; i++) {
			if (td[i].type=="hidden") {
				window.location.href='article_make?atypeId='+td[i].value;
			}
		}
	}
	util.addLoadEvent(getData);
</script>
</body>
</html>