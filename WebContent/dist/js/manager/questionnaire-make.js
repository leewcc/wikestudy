var quet_make = {
    change: ['zero','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'],
    createChoice :function(num) {
    	var parent = document.getElementById('choice_area'),
    	    text = '<fieldset>' + '<legend>选择题</legend>',
    	    i = 0;
    	for(i = 1; i <= num; i++){
    		text = text + '<div class="one_choice">'
    			    + '<div class="num">'
    				    + '<span>第' + i + '题</span>'  //下面一个button
    				    + '<button class="quebutton delete button delete_all" onclick="quet_make.addChoiceEvent(event)" type="button">删除</button>'
    			    + '</div>'
    				+ '<div class="question">'
    					+ '<label class="quelabel" for="' + i + 'option">'+ i +'. 请输入题目</label>'
    					+ '<textarea name="option" id="' + i+ 'option" class="queinput"></textarea>'
    					+ '<p>请填写选项内容</p>'
    					+ '<ol id="' + i + 'option" class="queol">'
    						+ '<li>' + '<input type="text" name="optionKey" />' + '</li>'
    						+ '<li>' + '<input type="text" name="optionKey" />' + '</li>'
    						+ '<li>' + '<input type="text" name="optionKey" />' + '</li>'
    						+ '<li>' + '<input type="text" name="optionKey" />' + '</li>'
    					+ '</ol>'
    					+ '<input id="' + i + 'optionKeyNum" type="hidden" name="optionKeyNum" value="4">'
    					+ '<input id="' + i + 'optionNum" type="hidden" name="optionNum" value="' + i + 'option">'
    					+ '<div class="quebutton">'//下面一个button
    						+ '<button class="quebuttonleft  button" type="button"  onclick="quet_make.addChoiceEvent(event)">添加</button>' 
    						+ '<button class="quebuttonright button" type="button"  onclick="quet_make.addChoiceEvent(event)">删除</button>' 
    					+ '</div>'
    					+ '<p>请选择正确的答案：</p>'
    					+ '<div class="choicebutton" id="' + i + 'choicebutton">'
                            + '<label><input type="radio" class="radioOption" name="' + i + 'option" value="A">' + 'A</label>' 
                            + '<label><input type="radio" class="radioOption" name="' + i + 'option" value="B">' + 'B</label>' 
                            + '<label><input type="radio" class="radioOption" name="' + i + 'option" value="C">' + 'C</label>' 
                            + '<label><input type="radio" class="radioOption" name="' + i + 'option" value="D">' + 'D</label>' 
                        + '</div>' 
                        + '<label class="quelabel" for="' + i + 'optionExplain">请输入答案解析：</label>' 
                        + '<textarea name="optionExplain" id="' + i + 'optionExplain" class="optionExplain"></textarea>' 
                    + '</div>'
                + '</div>';
    	}
    	text += '</fieldset>';
    	parent.innerHTML = text;
    },
    addChoiceEvent :function(event){
    	var target = EventUtil.getTarget(EventUtil.getEvent(event)),
    	    temp,
    	    tempLabel,
    	    tempInput,
            tempNum;
    	//删除按钮
    	if(target.innerHTML === '删除' && target.className == 'quebutton delete button delete_all'){
    		temp = target.parentNode.parentNode;
    		target.parentNode.parentNode.parentNode.removeChild(temp);
    	}else if(target.innerHTML === '添加' && target.className == 'quebuttonleft  button'){
    		temp = target.parentNode;
    		//加一个li,下面的选择加1
    		temp = util.lastElement(temp,'OL');
    		temp.innerHTML = temp.innerHTML + '<li>' + '<input type="text" name="optionKey" />' + '</li>';
    		//表示有多少个选择项的加1
    		temp = util.nextElement(temp,'INPUT');
    		temp.value  = parseInt(temp.value) + 1;
    		// 如果创建超过9个就阻止
    		if(temp.value > 9){
    			temp.value  = parseInt(temp.value) - 1;
    			temp = util.lastElement(temp,'OL');
    			temp.innerHTML = temp.innerHTML.slice(0, temp.innerHTML.lastIndexOf('<li>'));
    			alert('不能创建超过9个选项');
    			return ;
    			
    		}
    		//label+1
    		tempNum = temp.id.charAt(0);
    		tempLabel = document.createElement('label');
    		tempLabel.innerHTML = '<input type="radio" class="radioOption" name="' + tempNum + 'option" value="'
                                    + quet_make.change[parseInt(temp.value)] +'">' 
                                    + quet_make.change[parseInt(temp.value)];
            temp = util.nextElement(target.parentNode,'DIV');
    		temp.appendChild(tempLabel);
    	}else if(target.innerHTML === '删除' && target.className == 'quebuttonright button'){
    		//表示有多少个选择项的加1
    		temp = target.parentNode;
    		temp = util.lastElement(temp,'INPUT');
    		temp = util.lastElement(temp,'INPUT');
    		if(temp.value == '2'){
    			alert('不能再删除了');
    			return ;
    		}else{
    			temp.value  = parseInt(temp.value) - 1;
    			temp = util.lastElement(temp,'OL');
    			temp.removeChild(temp.lastElementChild);
    			temp = util.nextElement(target.parentNode, 'DIV');
    			temp.removeChild(temp.lastElementChild);
    		}
    	}
    },
    createJudge : function(num){
    	var parent = document.getElementById('judge_area'),
		    text = '<fieldset>' + '<legend>判断题</legend>',
		    i = 0;
    		for(i = 1; i <= num; i++){
    			text = text + '<div class="one_judge">'
                        +'<div class="num">'
                        +'<span>第' + i + '题</span>' 
                            + '<!-- <button type="button" class="delete  button delete_all"  onclick="quet_make.addJudgeEvent(event)">删除</button> -->'
                        + '</div>'
                        + '<div class="question">'
                            + '<label for="'+i+'judgeque" class="quelabel">请输入题目：</label>'
                            + '<textarea name="judge" id="' + i + 'judgeque" class="queinput"></textarea>'
                            + '<p>请选择正确选项：</p>'
                            + '<div class="quebutton">'
	                           + '<label><input type="radio" name="'+i+'judge" value="Y">' + '正确</label>'
	                           + '<label><input type="radio" name="'+i+'judge" value="N">' + '错误</label>'
                            + '</div>'
                            + '<input type="hidden" name="judgeNum" value="'+i+'judge">'
                            + '<label class="quelabel" for="' + i + 'judgeExplain">请输入答案解析：</label>'
                            + '<textarea name="judgeExplain"  id="'+i+'judgeExplain" ></textarea>'
                        + '</div>'
                    + '</div>';
    		}
    		text += '</fieldset>';
    		parent.innerHTML = text;
    },
    addJudgeEvent: function(event){
    	var target = EventUtil.getTarget(EventUtil.getEvent(event));
    	//删除按钮
    	if(target.innerHTML === '删除'){
    		target.parentNode.parentNode.parentNode.removeChild(temp);
    	}
    },
    createFill: function(num){
    	var parent = document.getElementById('fill_area'),
    	    text = '<fieldset>' + '<legend>填空题</legend>',
		    i = 0,
            button = null;
    	for(i = 1; i <= num; i++){
    		text = text + '<div class=one_fill>'
                    + '<div class=num>'
                    	+ '<span>第' + i + '题</span>'
                        + '<button type="button" class="delete button delete_all"  onclick="quet_make.addFillEvent(event)">删除</button>'
                    + '</div>'
                    + '<div class="question">'
                        + '<label class="quelabel" for="'+i+'fill">请输入填空题题目：</label>'
                        + '<textarea name="fill" id="'+i+'fill" class="queinput"></textarea>'
                        + '<label class="quelabel" for="'+i+'fillKey">请输入填空题答案：</label>'
                        + '<input type="text" name="fillKey" id="'+i+'fillKey" class="queinput">'
                        + '<label class="quelabel" for="'+i+'fillExplain">请输入填空题解析：</label>'
                        + '<textarea name="fillExplain" id="'+i+'fillExplain" class="queinput"></textarea>'
                    + '</div>'
                + '</div>';
    	}
    	text += '</fieldset>';
    	parent.innerHTML = text;
    	button = parent.getElementsByTagName('button');
    	for(i = 0; i < button.length; i++){
    		EventUtil.addHandler(button[i],'click',function(){
                addFillEvent(event);
            });
    	}
    },
    addFillEvent: function(event){
        var target = EventUtil.getTarget(EventUtil.getEvent(event));
        //删除按钮
        if(target.innerHTML === '删除' && target.className == 'delete button delete_all'){
            target.parentNode.parentNode.parentNode.removeChild(temp);
        }
    }, 
    checkCreateForm: function(){
    	var form = document.getElementById('create_form'),
    	    input = form.getElementsByTagName('input'),
    	    i = 0,
    	    num = [];
    		//少一个判断是否是数字的判断
    	do{
    		num[i] = parseInt(input[i].value);
    		if(isNaN(num[i]) || num[i] < 0){
    			alert('请输入有效的值');
    			num = [0,0,0];
    			return false;
    		}
    		i++;
    	}while(i < 3);
    	if(num[0] < 0 || num[1] < 0 || num[2] < 0){
            alert('错误的格式');
    		return false;
    	}else{
    		return num;
    	}
    }
};

(function(){
   var create = document.getElementById('create_button');
    /*给创建试卷的按钮一个时间*/
   EventUtil.addHandler(create,'click',function(event){
		var num = quet_make.checkCreateForm();
        /*第一步，判断数据时候正确*/
        if(num === false){
            return ;
        }
            quet_make.createChoice(num[0]);
            quet_make.createJudge(num[1]);
            quet_make.createFill(num[2]);
   });
       
})();

