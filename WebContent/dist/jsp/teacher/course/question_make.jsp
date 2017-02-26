<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>问卷制作页面</title>
	<link type="image/x-icon" rel="shortcut icon"
		href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet"
		href="/wikestudy/dist/css/teacher/teacher.css" />
</head>
<body>
	<!-- /7/25 -->
	<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
	<div class="outside">
		<div class="wrapper">
			<div id="center_headA">
				<div id="center_headD">
					<div id="center_headC"></div>
					<h1 id="center_headB">个人中心</h1>
				</div>
			</div>
			<%-- include进老师简介 --%>
			<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
			<ul id="stu_sidebar">
				<li id="course_head"><a	href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
			<!-- 	<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/answer_situation.jsp#stu_sidebar">我的话题</a></li> -->
				<li id="no_topic_head"><a
					href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
				<li id="no_note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
				<li id="no_mess_head"><a href="/wikestudy/dist/jsp//student/common/my_message_query#stu_sidebar">留言板</a></li>
			</ul>
			
			<!-- 主要的功能 -->
			<!-- 生成题目 -->
			<div id="main_area">
				<div id="col_main">
								<!-- 使用json提交数据之后返回的错误结果，假如有就显示，没有就不显示 -->
					<span id="message_div"></span>
					<span id="error_div" style="font-color:red"></span>
					<form id="create_form">
						<fieldset>
							<legend>题目数量</legend>
							<label>	选择题：
							<input type="number" value="1" required /></label> 
							<label>	判断题：
							<input	type="number" value="1" required /></label> 
							<label>填空题：
							<input	type="number" value="1" required /></label>
							<button type="button" id="create_button">创建试卷</button>
						</fieldset>
					</form>
	
					<!-- 使用json提交的数据 -->
					<form id="my_form" method="post">
					<input type="hidden" id="method" value="post" name="method"/>
						<input type="hidden" value="<%=request.getAttribute("secId")%>"
							id="secId" name="secId" />
						<div id="choice_area">
							<fieldset>
								<legend>选择题</legend>
								<p class="center">当前没有题目</p>
							</fieldset>
						</div>
						<div id="judge_area">
							<fieldset>
								<legend>判断题</legend>
								<p class="center">当前没有题目</p>
							</fieldset>
						</div>
						<div id="fill_area">
							<fieldset>
								<legend>填空题</legend>
								<p class="center">当前没有题目</p>
							</fieldset>
						</div>
						<div class="center">
							<input type="hidden" value="commit" name="submit"/>
							<button type="button" class="submit" value="commit" onclick="submitData()">提交</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
	<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script>
	<script type="text/javascript" src="/wikestudy/dist/js/manager/questionnaire-make.js"></script>
	<!-- CHEN -->
	<script type="text/javascript">
		//使用json提交数据
		function submitData() {
			$.ajax({
				type: "post",
				dataType : "json",
				url: "/wikestudy/dist/jsp/teacher/course/question_make",
				async: false,
				data: $("#my_form").serialize(),
				success: function(data) {
					//取出所有的map里面的结果，如果map不为空则拼接到error_div中，如果map为空，则跳转到学生答题的预览页面
					showData(data);
				},
				error: function() {
					window.location.href="/wikestudy/dist/jsp/teacher/course/question_page?secId="+$('#secId').val()
				}
			});
		}
		
		function showData(data) {
			if(data['status']!=="success") {
				$('#message_div').html(data['message']);
				//拼接上map的内容
				/* if(data['errorMap']!=null) {
					var error_str="";
					var map=data['errorMap'];
					for (var s in map) {
						error_str=error_str+"<br/>"+map[s];
					}
					$('#error_div').html(error_str);
				} */
				
			}else {
				//跳转页面到questionnaire_make.jsp 
				//在这个jsp中ajax 请求后台试题的数据
				window.location.href="/wikestudy/dist/jsp/teacher/course/question_page?secId="+$('#secId').val()
			}
			
		}
	</script>
</body>
</html>