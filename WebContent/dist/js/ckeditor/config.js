/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	// 换行方式
	//设置文字
	config.language = 'zh-cn';
	//config.width = 655;
	config.width = '100%';
	config.height = 400;
	config.skin = 'kama';
	config.resize_enabled = false;
	config.enterMode = CKEDITOR.ENTER_BR;
	// 当输入：shift+Enter是插入的标签
	config.shiftEnterMode = CKEDITOR.ENTER_BR;
	//全屏功能
	//功能能否收缩
	CKEDITOR.config.toolbarCanCollapse = false;
	config.pasteFromWordRemoveStyles = true;
/*图片上传功能*/
	
	config.image_previewText=''; //预览区域显示内容
	config.filebrowserImageUploadUrl= "ckImage_upload"; //待会要上传的action或servlet
	// 去掉ckeditor“保存”按钮
	config.removePlugins = 'save';
	  config.toolbar_Full = [
	                         ['Source','-','Save','NewPage','Preview','-','Templates'],
	                         ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
	                         ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
	                         ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
	                         '/',
	                         ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
	                          ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
	                          ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
	                          ['Link','Unlink','Anchor'],
	                         ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
	                         '/',
	                          ['Styles','Format','Font','FontSize'],
	                          ['TextColor','BGColor','Maximize']
	                      ];
};
