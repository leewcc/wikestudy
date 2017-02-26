/**
 * 该文件用于上传头像、剪接头像
 */
 function showCoords(obj) {  
        $("#x").val(obj.x);  
        $("#y").val(obj.y);  
        $("#w").val(obj.w);  
        $("#h").val(obj.h);  
//        if (parseInt(obj.w) > 0) {  
//            //计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到  
//            var rx = $("#preview_box").width() / obj.w;  
//            var ry = $("#preview_box").height() / obj.h;  
//            //通过比例值控制图片的样式与显示  
//            $("#previewImg").css( {  
//                width : Math.round(rx * $("#srcImg").width()) + "px", //预览图片宽度为计算比例值与原图片宽度的乘积  
//                width : Math.round(rx * $("#srcImg").width()) + "px", //预览图片宽度为计算比例值与原图片宽度的乘积  
//                height : Math.round(rx * $("#srcImg").height()) + "px", //预览图片高度为计算比例值与原图片高度的乘积  
//                marginLeft : "-" + Math.round(rx * obj.x) + "px",  
//                marginTop : "-" + Math.round(ry * obj.y) + "px"  
//            });  
//        }
}
$(function() {
	jQuery('#cropbox').Jcrop({//头像
		setSelect : [ 0, 0, 1, 1 ],
		onChange : showCoords,
		aspectRatio: 1//设置截图的 比例
	});
});

$(function() {
	jQuery('#coursephoto').Jcrop({//课程
		setSelect : [ 0, 0, 1, 1 ],//初始显示框
		onChange : showCoords,
		aspectRatio: 2//设置截图的 比例 长比高
	});
});


function upload(one) {
	//document.getElementById("UploadPhoto").value=document.getElementById('PhotoUrl').value;;
	var allowExt = ".jpg,.jpeg,.png,.bmp,.gif"; // 支持的扩展名
    var fileExt = one.value.substr(one.value.lastIndexOf('.')).toLowerCase();
	if (allowExt.indexOf(fileExt) == -1) {
		alert("上传图片的类型错误！仅支持jpg,gif,png,bmp,png格式图片");
		return;
	} else {
		var ff = document.getElementById('photoForm') || document.getElementById('photo_form');
		var input = document.getElementById("Photo_url") ||document.getElementById("PhotoUrl") ;
		if(input.files[0]){
			if(input.files[0].size >= 1024*1024){
				alert("文件过大！请选择1MB以下的文件");
				return;
			}
		}
		if(ff != null){
			ff.submit();
		}
	}	
}