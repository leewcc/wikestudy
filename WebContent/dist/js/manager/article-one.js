// /**
//  * 显示一篇具体文章的JS
//  */
// function change(){
// 	var input = document.getElementsByTagName("textarea");    
// 	for (var i = 0;i < input.length; i++) {
// 				input[i].removeAttribute("id");
// 				input[i].removeAttribute("name");
// 				input[i].nextSibling.removeAttribute("id");
// 	}
// 	input[i-1].id="anAll";
// 	input = document.getElementById("myForm").getElementsByTagName("input");
// 	for (var i = 2;i < input.length; i++) {
// 		input[i].removeAttribute("id");
// 		input[i].removeAttribute("name");
//     }
// 	var aa = document.getElementsByTagName("input")[2];
// 	var t = document.getElementsByTagName("textarea");
// 	t[t.length-1].name= "artComment";
// 	aa = t[t.length-1].parentNode.getElementsByTagName("input")[0];
// 	aa.id="sh";
// }
// function putAllData(){
// 	change();
// 	putData();
// }
// var putData = function() {//提交数据
// 	$.ajax({
// 		type : "POST",
// 		url : "/wikestudy/dist/jsp/Manager/ManagerArticle/AddComment",
// 		data : $('#myForm').serialize(),// 你的formid
// 		async : false,
// 		error : function(request) {
// 		},
// 		success : function(data) {
// 		}
// 	});
// 	$("#show").empty();
// 	getData();
// }

// var getData = function() {
// 	var b = document.getElementById("page");
// 	var p = b.getElementsByTagName("p");
// 	var i = 0;
// 	var cp = 1;
// 	var artId=document.getElementById("artId").value;
// 	for( i=0 ; i<p.length ; i++)
// 	EventUtil.addHandler( p[i] , "click" , function(event){
// 		var event = EventUtil.getEvent(event);
// 		var target = EventUtil.getTarget(event);
// 	    cp= target.childNodes[0].nodeValue;	 
	  
// 	} );
// 	jQuery.ajax({
// 		url : "/wikestudy/dist/jsp/Manager/ManagerArticle/GetComment?cp="+cp+"&artId="+artId,
// 		type : 'GET',
// 		dataType : "json",
// 		data : {},
// 		success : function(data) {
// 			conData(data);
// 		}
// 	});
// }
// var conData = function(data) {//连接方法
// 	var cp=data['cp'];
// 	var tp=data['tp'];
// 	$('#show').empty();
// 	$('#page').empty();
// 	var sc ='';//showComment
// 	var j = 0;
// 	var a = null;
// 	a=data['ac'].pageElem;
// 	for (var i = 0; i < a.length; i++) {
// 		if (a[i].AComBinding == 0) {
// 			if (i != 0) {
// 				sc += '<div class="answer"><span class="answerLZ">回复楼主</span><br>';
// 				sc += '<textarea class="answerCon"></textarea>';
// 				sc += '<input type="button" class="button answerCon"  onclick="putData()" value="发表">'
// 					+'</div></div></div></div>';
// 			}
// 			j++;
// 			sc += '<div class="oneComment">' 
// 				    +'<div class="headPortrait" >'
// 					+'<img src="/wikestudy/dist/images/portrait/'+a[i].AComSenderPhoto+'" alt="头像"/>'   
// 				    + a[i].AComSender
// 					+'</div>'
// 					+'<div class="commentArea">'
// 						+'<div class="commentMain">'
// 							+'<div class="commentCon">'
// 								+ a[i].AComContent
// 							+'</div>'
// 							//+'<input type="hidden" value="+a[i].AComId+" name="acomId">'
// 							+'<input type="hidden" value='+a[i].AComId+'>'
// 							//诗婷请在这里做个点击后除了他以外的name="acomId"的都没了
// 							+'<div class="other" >'
// 								+((cp-1)*10+ j) + "楼   &nbsp;&nbsp;&nbsp;"
// 								+ "发布于："+a[i].AComTime
// 								+'<span class="showReply">显示回复</span>'
// 								+'<span class="hideReply">收起回复</span>'
// 							+'</div>'
// 					    +'</div>'
// 		    	+'<div class="reply">';//这是评论
// 		} else {
// 			sc += '<div class="oneReply">'
// 					+'<div class="replyCon">'
// 					 + '<a herf="#">'+a[i].AComSender+'</a>&nbsp;&nbsp;&nbsp;:'
// 					 + a[i].AComContent
// 					+'</div>'
					
// 					+'<div class="other">'
// 					  + a[i].AComTime
// 					  + '&nbsp;&nbsp;&nbsp;<span class="answerLZ">回复</span>'
// 					+'</div>'
// 				 +'</div>';
// 		}
// 	}
// 	if(a!="") {	
// 	sc += '<br><div class="answer"><span class="answerLZ">回复楼主</span><br>'
// 		sc += '<textarea class="answerCon" type="text"></textarea>';
// 		sc += '<input type="button" class="button answerCon"  onclick="putData()" value="发表">'
// 			+'</div></div></div></div></div>';
// 	}
// 	//上面的是评论区的评论框，下面的是回复整个文章的评论
// 	sc += '<div><textarea id="anAll" class="answerCon" onclick="change()" type="text" name="artComment"></textarea>';
// 	sc += '<input type="button" class="button answerCon"  onclick="putAllData()" value="发表">'
// 		+'</div>';
	
// 	//进行拼接
// 	  $('#show').append(sc);
// 	var p = document.getElementsByTagName("span");
// 	for (var j = 0 ;j<p.length;j++){
// 		if(p[j].className == "answerLZ"){
// 			EventUtil.addHandler(p[j],"click",function(){
// 				var event = EventUtil.getEvent(event);
// 				var target = EventUtil.getTarget(event);
// 				var input = document.getElementsByTagName("textarea");    
// 				for (var i = 0;i < input.length; i++) {
// 							input[i].removeAttribute("id");
// 							input[i].removeAttribute("name");
// 							input[i].nextSibling.removeAttribute("id");
// 				}
// 				input = document.getElementById("myForm").getElementsByTagName("input");
// 				for (var i = 2;i < input.length; i++) {
// 					input[i].removeAttribute("id");
// 					input[i].removeAttribute("name");
// 					//input[i].nextSibling.removeAttribute("id");
// 		        }
				 
// 				if(target.childNodes[0].nodeValue=="回复"){
// 					var parent = target.parentNode.parentNode;
// 					var a = parent.getElementsByTagName("div")[0].getElementsByTagName("a")[0];
// 					input = parent.parentNode.getElementsByTagName("textarea")[0];
// 					input.id = "uuu";
// 					input.name= "artComment";
// 					var temp=a.childNodes[0].nodeValue;
// 					if(temp)
// 					input.value="回复 "+a.childNodes[0].nodeValue	+ ":";
// 					else 
// 					input.value="回复 "+""	+ ":";
// 					input.nextSibling.id = "sh";
// 					input = input.parentNode.parentNode.parentNode;
// 					input = input.getElementsByTagName("input")[0];
// 					input.name = "acomId";
// 					input.id = "acomId";
// 				}else {
// 					input = target.parentNode.getElementsByTagName("textarea")[0];
// 					input.value="";
// 					input.id = "uuu";
// 					input.name= "artComment";
// 					input.nextSibling.id = "sh";
// 					input = input.parentNode.parentNode.parentNode;
// 					input = input.getElementsByTagName("input")[0];
// 					input.name = "acomId";
// 					input.id = "acomId";
// 				}
// 			});
// 		}else if(p[j].className == "showReply"){
// 			EventUtil.addHandler(p[j],"click",function(){
// 				var event = EventUtil.getEvent(event);
// 				var target = EventUtil.getTarget(event);
// 				var temp = target.parentNode.parentNode;
// 				var input = null;
// 				var t = null;
// 				var hidden = lastElement(target.parentNode,"INPUT");
// 				temp = nextDiv(temp);
// 				if(temp.style.display=="none"||temp.style.display==""){
// 					temp.style.display = "block";
// 				}
// 				target.style.display = "none";
// 				target.nextSibling.style.display = "inline";
				
// 				t = temp.parentNode;
// 				input = t.getElementsByTagName("textarea")[0];
// 				input.name="artComment";
// 				hidden.name = "acomId";
				
// 			});
// 		}else if(p[j].className == "hideReply"){
// 			EventUtil.addHandler(p[j],"click",function(){
// 				var event = EventUtil.getEvent(event);
// 				var target = EventUtil.getTarget(event);
// 				var temp = target.parentNode.parentNode;
// 				var hidden = lastElement(target.parentNode,"INPUT");
// 				temp = nextDiv(temp);
// 				if(temp.style.display=="block"||temp.style.display==""){
// 					temp.style.display = "none";
// 				}
// 				target.style.display = "none";
// 				target.previousSibling.style.display = "inline";
// 				var input = null;
// 				var t = null;
// 				t = temp.parentNode;
// 				input = t.getElementsByTagName("textarea")[0];
// 				input.removeAttribute(name);
// 				hidden.removeAttribute(name);
// 			});
// 		}
// 	}
// 		//拼接页码
// 	var page ='';
// 	var selected="";
// 	if(cp>1) {
// 		page+='<a onclick="pageData()" id="previousPage" >'+(cp-1)+'</a>';
// 	}
// 	if(tp<=10) {
// 		for(var i=1;i<=tp;i++) {
// 			selected=(i==cp)? 'class="selected"':'';
// 			page+='<a onclick="pageData()" '+selected+'>'+i+'</a>';
// 		}
// 	} else {
// 		var selected1 =(1==cp)?'class="selected"':'';
// 		var selected2 =(2==cp)?'class="selected"':'';
// 		page+='<a onclick="pageData()" '+selected1+'>1</a>';
// 		page+='<a onclick="pageData()" '+selected2+'>2</a>';
// 		i=cp-2;
// 		if(i>3) {
// 			page+='<span>...</span>';
// 		}else {
// 			i=3;
// 		}
// 		for(;i<=cp+2 &&i<tp-1;i++) {
// 			selected=(i==cp)?'class="selected"':'';
// 			page+='<a onclick="pageData()"'+selected+'>'+i+'</a>';
// 		} 
// 		if(i<tp-1) {
// 			page+='<span>...</span>';
// 		}
// 		selected1 =(cp==tp-1)?'class="selected"':'';
// 		selected2 = (cp==tp)?'class="selected"':'';
// 		page+='<a onclick="pageData()"'+selected1+'>'+(tp-1)+'</a>';
// 		page+='<a onclick="pageData()" '+selected2+'>'+tp+'</a>';
// 	}
// 	if(cp<tp) {
// 		page+='<a onclick="pageData()" id="nextPage">'+(cp+1)+'</a>';
// 	}
// 	$('#page').append(page);
// }
// function pageData() {
// 	var artId=document.getElementById("artId").value;
// 	var cp=event.target.childNodes[0].nodeValue;
// 	if(cp == null) 
// 		cp = 1;
// 	jQuery.ajax({
// 		url : '/wikestudy/dist/jsp/Manager/ManagerArticle/GetComment?cp='+cp+'&artId='+artId,
// 		type : 'GET',
// 		dataType : "json",
// 		data : {},
// 		success : function(data) {
// 			conData(data);
// 		}
// 	});
// }
// addLoadEvent(getData);