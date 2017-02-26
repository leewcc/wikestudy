
function saveNote(ndid, cp) {
	
	var messNode = document.getElementById("messId");
	if (messNode != null)
		messNode.style.display = "none"
	
	var node = document.getElementById("saveContent");
    var content = node.value;
    
    var div = document.createElement("div");
    var span = document.createElement("span");
    div.appendChild(span);
    div.id = "messId";
    
    var flag = true;
    if(content.replace(/(^\s*)|(\s*$)/g, "")=="") {
    	
    	span.innerHTML = "笔记内容不能为空";
    	flag = false;
    }
    if (content.length > 600) {
    	span.innerHTML = "笔记内容不能超过600字";
    	false;
    }
    
    node.parentNode.insertBefore(div,node);
    
    if (!flag) return ;
    
    var request = $.ajax({
        type : "POST",
        url : "/wikestudy/dist/jsp/student/course/note_save",
        data : {content : content, id : ndid, flag : "1"},
        dataType : 'html',
        success:function(data) {
        	window.location.href="course_note_f?page"+cp;
        },
        error : function (){
        	alert("修改失败");
        }
    });
}

function deleteNote(ndid, cp) {
	alert(ndid);
    var request = $.ajax({
        type : "POST",
        url : "/wikestudy/dist/jsp/student/course/note_save",
        data : {id : ndid, flag : "2"},
        dataType : 'html',
        success:function(data) {
        	window.location.href="course_note_f?page"+cp;
        	
        },
        error : function (){
        	alert("删除失败");
        }
    });
}


$(function (){
	$('.pContent').click(function(event){
		var $target = $(event.target);
		var $next = $target.next();
		var $find = $('#hide_note_p');
		if($find.length > 0){ //如果有输入框了
			$find.next().removeClass('show_note_div').addClass('hide_note_div');
			$find.next().find('textarea').removeAttr('id');
			$find.removeAttr('id');
		}
		$target.attr('id','hide_note_p');
		$next.removeClass('hide_note_div').addClass('show_note_div');
		$next.find('textarea').attr('id','saveContent');
	});
}); 
