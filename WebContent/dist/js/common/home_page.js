$(function(){
	// 把第二组隐藏
	$('#section2').fadeOut();

	// 学习之星左边按钮
	$('#starLimg').click(function(){
		if($('#section1').css('display') === 'none'){
			$('#section1').fadeIn(1);
			$('#section2').fadeOut(1);
		}else{
			$('#section2').fadeIn(1);
			$('#section1').fadeOut(1);
		}
	});
	// 学习之星右边按钮
	$('#starRimg').click(function(){
		if($('#section1').css('display') === 'none'){
			$('#section1').fadeIn(1);
			$('#section2').fadeOut(1);
		}else{
			$('#section2').fadeIn(1);
			$('#section1').fadeOut(1);
		}
	});
});