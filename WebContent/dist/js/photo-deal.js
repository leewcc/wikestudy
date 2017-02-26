	/**
	 * 该文件用于上传头像、剪接头像
	 */
	 jQuery(window).load(function(){  
			var jcrop_api, boundx, boundy,x;
			var imgBoxSize=$('#cropbox').attr("height");
			var viewImgWidth=$('#preview').attr("width");
			var viewImgHeight=$('#preview').attr("height");
			//使原图具有裁剪功能
			$('#cropbox').Jcrop({
				onChange : updatePreview,
				onSelect : updatePreview,
				aspectRatio : viewImgWidth /viewImgHeight//截图的长宽比
			}, function() {
				x =1;
				var bounds = this.getBounds();
				boundx = bounds[0];
				boundy = bounds[1];
				jcrop_api = this;	
			});
			//裁剪过程中，每改变裁剪大小执行该函数
			function updatePreview(c) {	
				    $("#x").val(c.x);  
			        $("#y").val(c.y);  
			        $("#w").val(c.w);  
			        $("#h").val(c.h);  
					$('#preview').css({
						width : Math.round(viewImgWidth/c.w*boundx*imgBoxSize/boundy) + 'px',
						height : Math.round(viewImgHeight/c.h*imgBoxSize) + 'px',
						marginLeft : '-' + Math.round(viewImgWidth/c.w*c.x) + 'px',//100为要求的宽度
						marginTop : '-' + Math.round(viewImgHeight/c.h*c.y) + 'px'
					});

				}
		
		});
	function uploadPhoto(one) {
		var allowExt = ".jpg,.jpeg,.png,.bmp,.gif"; // 支持的扩展名
	    var fileExt = one.value.substr(one.value.lastIndexOf('.')).toLowerCase();
		if (allowExt.indexOf(fileExt) == -1) {
			alert("上传图片的类型错误！仅支持jpg,gif,png,bmp,png格式图片");
			return;
		} else {
			var ff = document.getElementById('photoForm');
			if(ff!=null){
				ff.submit();
			}
		}	
	}
	
	function checkSubmit() {
		var img=$('#cropbox');
		if(img.attr('src')=="imgPath") {
			return false;
		}
		
	}
	