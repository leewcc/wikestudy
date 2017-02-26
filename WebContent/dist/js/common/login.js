function show(condition, message, time){
		if(condition){
			var target = document.getElementById("popup_area");
			var parent =  document.getElementById("popup");
			if(!target && !parent){
				target = document.createElement('div');
				target.setAttribute('id', 'popup_area');
				parent = document.createElement('div');
				parent.setAttribute('id', 'popup');
				parent.appendChild(target);
				document.body.appendChild(parent);
			}
			target.innerHTML = "<div id='temp_left'></div><div id='temp_right'></div>"+"<p>"+message+"</p>";
			parent.style.display = "block";
			EventUtil.addHandler(target, "click", function(event){
				event.stopPropagation();
			});
			EventUtil.addHandler(parent,"click", function(){
				if(time !== 'low'){
					disappear();
				}		
			});
			if(time !== 'low'){
				setTimeout("disappear()", time);
			}
		}
	}
(function(){
	function addLoadEvent(func){

	var oldonload = window.onload;
	if(typeof oldonload != 'function'){
		window.onload = func;
	}else{
		window.onload = function(){
			oldonload();
			func();
		}
	}
	}
	function hasClass(elem,name){
		return elem.className.match(new RegExp('(\\s|^)' + name + '(\\s|$)'));
	}
	function addClass(elem,name){
		if(!elem.className){
			elem.className = name;
		}else {
			elem.className = elem.className+" "+name;
		}
	}
	function removeClass(elem,name){
		if(hasClass(elem,name)){
			elem.className = elem.className.replace(name," ");
		}
	}
	function nextElement(elem,str){
		if(elem !=null){
			do{
				elem = elem.nextSibling;
			}while(elem != null && elem.tagName != str);	//elem==null是预防传入的elem为空的时候找不到下一个div
		}
		return elem;
	}
	function lastElement(elem,str){
		if(elem !=null){
			do{
				elem = elem.previousSibling;
			}while(elem != null && elem.tagName != str);	//elem==null是预防传入的elem为空的时候找不到下一个div
		}
		return elem;
	}
	EventUtil = {
		addHandler : function(elem, type, handler){
				if(elem.addEventListener){
					elem.addEventListener(type,handler,false);
				// Because the attachEvent has a bug , and JQuery don't use it now.
				}else if(elem.attachEvent){
				 	elem.attachEvent("on"+type,handler);
				}else {
					elem["on" + type] = handler;
				}
		},
		removeHandler: function(elem,type,handler){
			if(elem.removeEventListener){
				elem.removeEvenetListener(type,handler,false);
			// Because the attachEvent has a bug , and JQuery don't use it now.
			}else if(elem.detachEvent){
			 	elem.detachEvent("on"+type,handler);
			}else {
				elem["on"+type] = null;
			}
		},
		//获得事件的event对象的引用，因为在IE中，event参数是未定义的（undefined）
		getEvent: function(event){
			return event?event:window.event;
		},
		//返回 事件的目标
		getTarget : function(event){
			return event.target || event.srcElement;
		},
		//取消事件的默认行为
		preventDefault: function(event){
			if(event.preventDefault){
				event.preventDefault();
			}else{
				event.returnValue = false;
			}
		},
		stopPropagation: function(event){
			if(event.stopPropagation){
				event.stopPropagation();
			}else{
				event.cancelBubble = true;
			}
		}
	};
	function getElementsByClassName(node, className){
		var results = new Array(),
			elems = null,
			i = 0;
		if(node.getElementsByClassName){
			elems = node.getElementsByClassName(className);
			for(i = 0; i < elems.length; i++){
				results.push(elems[i]);
			}
		}else{
			elems = node.getElementsByTagName("*");
			for(var i = 0; i < elems.length; i++){
				if(elem[i].className.indexOf(className) !== -1){
					results[results.length] = elems[i];
				}
			}
		}
		return results;
	}
	//导航栏的占位符
	function testSearchInput(input, message){
		var input = document.getElementById(input);
		if(input != null){
			EventUtil.addHandler(input, "focus", function(event){
				var event = EventUtil.getEvent(event);
				var target = EventUtil.getTarget(event);
		    if(target.value == message){
					target.value = "";
				}
			});
			EventUtil.addHandler(input, "blur", function(event){
				var event = EventUtil.getEvent(event);
				var target = EventUtil.getTarget(event);
				//remove extra black space
				var str = target.value.replace(/^\s|\s+$/g,'');
				if( str== ""){
					target.value = message;
				}
			});
		}
	}
	function createXHR(){
		if(typeof XMLHttpRequest != "undefined"){
			return new XMLHttpRequest();
		}else if(typeof ActiveXObject != "undefined"){
			if(typeof arguments.callee.activeXString != "string"){
				var versions = ["MSXML2.XMLHttp.6.0","MSXML2.XMLHttp.3.0","MSXML2.XMLHttp"],
					i,
					len;
				for(i=0,len=versions.length ; i<len ;i++){
					try{
						new ActiveXObject(versions[i]);
						arguments.callee.activeXString = versions[i];
						break;
					}catch(ex){
						
					}
				}
			}
			return new ActiveXObject(arguments.callee.activeXString);
		}else{
			throw new Error("No XML object available.");
		}
	}
	
	
	function test1(){
		testSearchInput("number","学号");
		testSearchInput("teaNumber","账号");
		testSearchInput("password","密码");
		var button = document.getElementById("loginForm").getElementsByTagName("button")[0];
		var label = document.getElementById('login_role');
		if(label){
			EventUtil.addHandler(label,'click',function(){
				label = document.getElementById('login_role').getElementsByTagName('label');

				for(var i = 0; i < label.length; i++){
					if(label[i].getElementsByTagName('input')[0].checked){
						label[i].className = 'check';
					}else{
						label[i].className = '';
					}
				}
			});
		}
		if(button){
			EventUtil.addHandler(button,"click",function(){
				var input = document.getElementById("loginForm").getElementsByTagName("input");
				if(input[0].value.trim() == ""||input[0].value.trim() == "账号"||input[0].value.trim() == "学号"){
					show(true, "你的账号输入为空", 3000);
					input[0].value="";
					return false;
				}else if(input[1].value.trim() == ""||input[1].value.trim() == "密码"){
					show(true, "你的密码输入为空", 3000);
					input[1].value="";
					return false;
				}else if(input[2].value.trim() === ''){
					show(true, "请填写验证码", 3000);
				}else if(input[3]){
					if(input[3].checked||input[4].checked){
						document.getElementById("loginForm").submit();
					}else{
						show(true, "你未选择登陆类型", 3000);

						return false;
					}
				}else
					document.getElementById("loginForm").submit();
			});
		}
	}
	EventUtil.addHandler(document.getElementById('vimg'),'click', function(event){
		changeCode();
	});
	function changeCode() {    
		var imgNode = document.getElementById("vimg");                    
			imgNode.src = "/wikestudy/dist/security_code?t=" + Math.random();  // 防止浏览器缓存的问题       
	}
	addLoadEvent(test1);
	addLoadEvent(changeCode);
})();

function disappear(){
	var parent = document.getElementById("popup");
	if(parent){
		parent.style.display = "none";
	}
}
