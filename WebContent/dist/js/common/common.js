var EventUtil = {
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
var util = {
	addLoadEvent: function(func){
		var oldonload = window.onload;
			if(typeof oldonload != 'function'){
				window.onload = func;
			}else{
				window.onload = function(){
					oldonload();
					func();
				}
			}
	},
	hasClass: function(elem, name){
		return elem.className.match(new RegExp('(\\s|^)' + name + '(\\s|$)'));
	},
	addClass: function(elem, name){
		if(!elem.className){
			elem.className = name;
		}else {
			elem.className = elem.className+" "+name;
		}
		return elem;
	},
	removeClass: function(elem,name){
		if(this.hasClass(elem,name)){
			elem.className = elem.className.replace(name," ");
		}
		return elem;
	},
	parentElement: function(elem, str){
		var temp = str.toLowerCase();
		if(elem !=null){
			do{
				elem = elem.parentNode;
			}while(elem !== null && elem.tagName.toLowerCase() !== temp);	//elem==null是预防传入的elem为空的时候找不到下一个div
		}
		return elem;
	},
	nextElement: function(elem,str){
		var temp = str.toLowerCase();
		if(elem !=null){
			do{
				elem = elem.nextElementSibling;
			}while(elem !== null && elem.tagName.toLowerCase() !== temp);	//elem==null是预防传入的elem为空的时候找不到下一个div
		}
		return elem;
	},
	lastElement: function(elem,str){
		var temp = str.toLowerCase();
		if(elem !=null){
			do{
				elem = elem.previousElementSibling;
			}while(elem !== null && elem.tagName.toLowerCase() !== temp);	//elem==null是预防传入的elem为空的时候找不到下一个div
		}
		return elem;
	},
	getElementsByClassName: function(node, className){
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
	},
	//导航栏的占位符
	testSearchInput: function(input, message){
		var input = document.getElementById(input);
		if(input !== null){
			EventUtil.addHandler(input, "focus", function(event){
				var event = EventUtil.getEvent(event);
				var target = EventUtil.getTarget(event);
		    if(target.value === message){
					target.value = "";
				}
			});
			EventUtil.addHandler(input, "blur", function(event){
				var event = EventUtil.getEvent(event);
				var target = EventUtil.getTarget(event);
				var str = target.value.replace(/^\s|\s+$/g,'');
				if( str === ""){
					target.value = message;
				}
			});
		}
	},
	get: function(config){
		// 适用于IE7及之后版本
		var xhr = new XMLHttpRequest();
		
	},
	// 检查头像
	checkImg: function(){
		var ps_imgs = util.getElementsByClassName(document, 'person_img'),
			cou_imgs = util.getElementsByClassName(document, 'course_img');
		if(ps_imgs.length > 0){
			ps_imgs.forEach(function(currentValue, index, array){
				EventUtil.addHandler(currentValue, 'error', function(event){
					currentValue.src = '/wikestudy/dist/images/common/default.png';
				});
			});
		}
		if(cou_imgs.length > 0){
			cou_imgs.forEach(function(currentValue, index, array){
				EventUtil.addHandler(currentValue, 'error', function(event){
					currentValue.src = '/wikestudy/dist/images/common/img_17.png';
				});
			});
		}

	},
	checkSideBar: function(){
		var ul = document.getElementById("sidebar");
		if(ul){
			var li = ul.getElementsByTagName("li");
			for(var i = 0; i < li.length; i++){
				var span = li[i].getElementsByTagName("span")[0];
				if(span != undefined){                    
				EventUtil.addHandler(li[i],"click",function(event){
					var event = EventUtil.getEvent(event);
					var target = EventUtil.getTarget(event);
				    if(target.tagName.toLowerCase() == "span") target = target.parentNode;
					//先把有的selected移除掉
				    if(!util.hasClass(target,"selected")){
						var ul = document.getElementById("sidebar");
						var li = ul.getElementsByTagName("li");
						for(var j = 0 ; j < li.length; j++){
							util.removeClass(li[j],"selected");
						}
						//select the target to add the class name.
						util.addClass(target,"selected");
				    }else{
				    	util.removeClass(target,"selected");
				    }
				});
				}
			}
		}
	}
};

function Ajax(config){
	var cfg = null,
		obj = this,
		xhr = new XMLHttpRequest();

	if(!(this instanceof Ajax)){
		obj = new Ajax(config);
	}

	if(false === (cfg = obj.checkConfig(config)) ){
		return false;
	}

	if(cfg.type === 'GET'){
		// 拼接字符串
		xhr.open('GET', obj.addDataInUrl(), true);
		cfg.data = null;
	}else{
		xhr.open('POST', obj.url, true);
		if(typeof cfg.data === 'object'){
			cfg.data = 'data' + JSON.stringify(cfg.data);
		}
	}
	xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            if(xhr.status == 200){
                cfg.success(xhr.responseText);
            } else if(cfg.error){
                cfg.error(xhr.status);
            }
        }
    }
    xhr.send(cfg.data);
}
Ajax.prototype.checkConfig = function(config){
	var cfg = {};
	if(!config.url || typeof config.url !== 'string'){
		console.log('[system error]:不能没有路径');
		return false;
	}
	cfg.url = config.url;

	if(!config.type){
		cfg.type = 'GET';
	}else{
		cfg.type = config.type.toUpperCase();
	}

	if(!config.success || Object.prototype.toString.call(config.success) !== '[object Function]'){
			console.log('[system error]:success不是函数');
			cfg.success = function(){};
	}else{
		cfg.success = config.success;
	}
	if(!config.error || Object.prototype.toString.call(config.error) !== '[object Function]'){
			console.log('[system error]:error不是函数');
			cfg.error = function(){};
	}else{
		cfg.error = config.error;
	}
	return cfg;
};
Ajax.prototype.addDataInUrl = function(config){
    var str = '',
    	data = config.data;
	if(typeof data === 'object'){
        for(var key in data){
            str += key +'='+data[key]+'&';
        }
        data = str.replace(/&$/, '');
    }
    return config.url + '?' + str;
};

/*** init ***/
(function(){
	util.checkImg();
	util.checkSideBar();
	
})();
