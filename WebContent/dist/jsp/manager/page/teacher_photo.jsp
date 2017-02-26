<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>老师的头像</title>
	<link href="/wikestudy/dist/css/jquery.Jcrop.css" rel="stylesheet"	type="text/css"/>
	<style>
		#breDiv img {
			display: inline-block;
			width: 240px;
			height: 60px;
		}
	</style>
</head>
<body>
	<script src="/wikestudy/dist/js/jquery.min.js" type="text/javascript"></script>
	<script src="/wikestudy/dist/js/jquery.Jcrop.js" type="text/javascript"></script>
	<script type="text/javascript">
	function uploading(one) {
		var allowExt = ".jpg,.jpeg,.png,.bmp,.gif"; // 支持的扩展名
		var fileExt = one.value.substr(one.value.lastIndexOf('.')).toLowerCase();
		if (allowExt.indexOf(fileExt) == -1) {
			alert("上传图片的类型错误！仅支持jpg,gif,png,bmp,png格式图片");
			return;
		} else {
			var ff = document.getElementById('TeacherPhoto');
			if(ff!=null){
				ff.submit();
			}
		}

	}
	
	$(function() {
		var jcrop_api, boundx, boundy;
		//使原图具有裁剪功能
		$('#target').Jcrop({
			onChange : updatePreview,
			onSelect : updatePreview,
			aspectRatio : 240/60		//比例4：1
		}, function() {
			// Use the API to get the real image size
			var bounds = this.getBounds();
			boundx = bounds[0];
			boundy = bounds[1];
			// Store the API in the jcrop_api variable
			jcrop_api = this;
		});
		//裁剪过程中，每改变裁剪大小执行该函数
		function updatePreview(c) {
			if (parseInt(c.w) > 0) {
				$('#preview').css({
					width : Math.round(240 / c.w * boundx) + 'px',		//240*60分辨率的缩略图
					height : Math.round(60 / c.h * boundy) + 'px',
					marginLeft : '-' + Math.round(240 / c.w * c.x) + 'px',
					marginTop : '-' + Math.round(60 / c.h * c.y) + 'px'
				});
				$('#width').val(c.w); //c.w 裁剪区域的宽
				$('#height').val(c.h); //c.h 裁剪区域的高
				$('#x').val(c.x); //c.x 裁剪区域左上角顶点相对于图片左上角顶点的x坐标
				$('#y').val(c.y); //c.y 裁剪区域顶点的y坐标

			}
		}
		;
	});
	</script>

		<form action="photo_copy" id="TeacherPhoto" method="post" enctype="multipart/form-data" >
				<input  type="file" name="Picturefile"/>
				<input type="submit" >
			<input id="upload2" type="submit" value="更换" />
		</form>
		<form action="DealPhoto" method="post">
		</form>
		
		<div id="main">
	<%
	if(request.getParameter("imgPath")!=null) {
		//获取裁减的图片参数
		String imgPath = "";
		int srcWidth = 0;
		int srcHeight = 0;
		int tarWidth = 0;
		int tarHeight = 0;
		int srcImgWidth = 350; //原图展示边长
		int viewImgWidth = 240; //缩略图展示宽		//比例4：1
		int viewImgHeight = 60;//缩略图展示的长
		//存在原图地址属性则展示此模块
			

			imgPath = request.getParameter("imgPath").toString();
			srcWidth = Integer.valueOf(request.getParameter("Width")
					.toString());
			srcHeight = Integer.valueOf(request.getParameter("Height")
					.toString());
		

		if (srcWidth > srcImgWidth || srcHeight > srcImgWidth) {

			if (srcWidth >= srcHeight) {

				tarWidth = srcImgWidth;
				tarHeight = (int) Math.round((((double) srcImgWidth)
						* ((double) srcHeight) / ((double) srcWidth)));
			} else {

				tarHeight = srcImgWidth;
				tarWidth = (int) Math.round((((double) srcImgWidth)
						* ((double) srcWidth) / ((double) srcHeight)));
			}
		} else {

			tarWidth = srcWidth;
			tarHeight = srcHeight;
		}
	%>
	<div class="secondDiv">
		<h2>LOGO图片剪裁：<span>（如大小不合适可先用图像处理软件处理，最佳尺寸为240像素*60像素）</span></h2>
	<div class="bigPic">
		<span>原图：</span>
		<div style="width: <%=srcImgWidth%>px; height: <%=srcImgWidth%>px;">
			<img src="<%=imgPath%>" id="target" alt="" width="<%=tarWidth%>px" height="<%=tarHeight%>px" />
		</div>
	</div>
	<div class="smartPic3">
		<span>预览：</span>
		<div
			style="width: <%=viewImgWidth%>px; height: <%=viewImgHeight%>px; overflow: hidden;">
			<img id="preview" src="<%=imgPath%>" width="<%=viewImgWidth%>px"
				height="<%=viewImgHeight%>px" />
		</div>
	</div>
	<form action="PhotoCopy" method="get">
		<input type="hidden" name="image.x" id="x" />
		<input type="hidden" name="image.y" id="y" />
		<input type="hidden" name="image.width" id="width" /> 
		<input type="hidden" name="image.height" id="height" />
		<input type="hidden" name="image.Path" id="Path" value="<%=imgPath%>">
		<input type="hidden" name="tarWidth" id="tarWidth" value="<%=tarWidth%>"> 
		<input type="hidden" name="tarHeight" id="tarHeight" value="<%=tarHeight%>"> 
		<input class="sureAndSave" type="submit" value="确定并保存" />
	</form>
	</div>
	<%} %>
	</body>
</html>