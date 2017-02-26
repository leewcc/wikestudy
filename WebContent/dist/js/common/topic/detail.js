var temp_array = [];
util.addLoadEvent(function () {
	var div = document.getElementById('content');
	var editor = new wangEditor(div);
    editor.config.menus = [
        'source',
        '|',     // '|' 是菜单组的分割线
        'bold',
        'underline',
        'italic',
        'strikethrough',
        'eraser',
        'forecolor',
        'bgcolor'
     ];
     temp_array.push({
    	editor: editor,
    	form: util.parentElement(div,'form')
    });
    editor.create();
	// 添加button点击事件
	var button_show_array = util.getElementsByClassName(document, 'topic_reply');
	button_show_array.forEach(function(currentValue, index, array){
		EventUtil.addHandler(currentValue, 'click', function(event){
			// 获取到目标
			var temp = EventUtil.getTarget(EventUtil.getEvent(event)),
				div = util.nextElement(temp.parentNode, 'div'),
				editor = null;
			if(util.hasClass(div, 'hidden_textarea')){
				// 显示信息
				util.addClass(div, 'show_textarea');
				util.removeClass(div, 'hidden_textarea');
				div = div.getElementsByTagName('div')[0];
				editor = new wangEditor(div);
		        editor.config.menus = [
			        'source',
			        '|',     // '|' 是菜单组的分割线
			        'bold',
			        'underline',
			        'italic',
			        'strikethrough',
			        'eraser',
			        'forecolor',
			        'bgcolor'
			     ];
			     temp_array.push({
		        	editor: editor,
		        	form: util.parentElement(temp, 'form')
		        });
		        editor.create();

			}else if(util.hasClass(div, "show_textarea")){
				// util.removeClass(div, 'show_textarea');
				// util.addClass(div, 'hidden_textarea');
			}

		});
	});
	var button_submit_array = util.getElementsByClassName(document, 'topic_reply_sub');
	button_submit_array.push(document.getElementById('submit_reply'));
	button_submit_array.forEach(function(currentValue, index, array){
		EventUtil.addHandler(currentValue, 'click', function(event){
			var temp = EventUtil.getTarget(EventUtil.getEvent(event)),
				// form
				form = util.parentElement(temp, 'form'),
				last = util.lastElement(temp, 'div'),
				str = '';
				textarea = document.createElement('textarea');
				textarea.style = 'display: none';
				textarea.name = 'content';
				for(var i = 0; i < temp_array.length; i++){
					if(temp_array[i].form === form){
						str = temp_array[i].editor.$txt.html();
						if(str.indexOf('<p><br></p>') !== -1){
							textarea.innerHTML = str.slice(0,str.indexOf('<p><br></p>'));
						}else{
							textarea.innerHTML = str;
						}
							form.appendChild(textarea);
						// 少了一个验证表单
						form.submit();
						break;
					}
				}
				
		});
	});
});