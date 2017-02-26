<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.Question"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
<title>测试题查看编辑页面</title>
<link type="image/x-icon" rel="shortcut icon"
	href="/images/system/small_logo.png">
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
			<%-- include老师简介 --%>
			<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
			<ul id="stu_sidebar">
				<li id="course_head"><a
					href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
				<li id="no_topic_head"><a
					href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
				<li id="no_note_head"><a
					href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
				<li id="no_mess_head"><a
					href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
			</ul>

			<!-- 主要页面 -->
			<div id="main_area">
			<span id="message_div"></span>
				<%
					if("error".equals("status")) {
						%>
				<h1><%=request.getAttribute("message") %></h1>		
						<%
					}else {
						%>
											
				<!-- 使用json提交数据之后返回的错误结果，假如有就显示，没有就不显示 -->
				<span id="message_div"></span>
				<span id="error_div"></span>
				<form  id="update_question">
					<input type="hidden" value="put" id="method" name="method"/>
					<input type="hidden" id="secId" name="secId"
						value="<%=(Integer) request.getAttribute("secId")%>">
					<%
						/*获得所有的问题*/
						ArrayList<Question> oList = (ArrayList<Question>) request
								.getAttribute("options");
						ArrayList<Question> jList = (ArrayList<Question>) request
								.getAttribute("judges");
						ArrayList<Question> fList = (ArrayList<Question>) request
								.getAttribute("fills");
						/*输出选择题*/
						int num = 1;//序号
						if (oList != null) {
					%>
					<div id="choice_area">
						<h1>选择题</h1>
						<hr>
						<%
							int i = 0;
								for (Question q : oList) {
						%>
						<div class="one_choice">
							<input type="hidden" value="chocie" id="question"/>
							<p class="question_content">
								第<%=num++%>题：<span class="illegal"><%=InputUtil.input(request.getAttribute("Ocontent"
							+ i))%></span>
							</p>
			
							<textarea name="option"><%=q.getQueContent()%></textarea>
							<input type="hidden" value="<%=q.getQueId()%>" name="id">
							<p>
								答案： <span class="illegal"><%=InputUtil.input(request.getAttribute("Ooption"
							+ i))%></span>
							</p>
							<%
								/*选择题选项*/
										String string = q.getQueOption();
										String[] os = string.split(";next;");
										char A = 'A';
										for (String s : os) {
							%>
							<label class="choice_content"><input type="radio"
								class="radio" value="<%=A%>" name="<%=num + "option"%>"
								<%if (A == q.getQueAnswer().charAt(0)) {%> checked <%}%>> <span
								class="choice_span"><%=A%>. </span><input type="text"
								value="<%=InputUtil.convert(s.substring(0,
								s.length() - 1))%>"
								class="input" name="optionKey"></label>
							<%
								A++;
										}
							%>
							<input type="hidden" name="optionKeyNum" value="<%=A - 'A'%>">
							<input type="hidden" name="optionNum" value="<%=num + "option"%>">

							<label>解析：<textarea name="optionExplain"><%=q.getQueExplain()%></textarea></label>
						</div>
						<%
							i++;
								}
						%>
					</div>
					<%
						} else {
					%>
					<p style="line-height: 100px; text-align: center;">当前没有选择题答案</p>
					<%
						}
						/*输出判断题*/
						num = 1;//序号归零
						if (jList != null) {
					%>
					<div id="judge_area">
						<h1>判断题</h1>
						<hr>
						<%
							int i = 0;
								for (Question q : jList) {
						%>
						<div class="one_judge">
							<input type="hidden" value="one_judge" id="question"/>
							<p class="question_content">
								第<%=num++%>题： <span class="illegal"><%=InputUtil.input(request.getAttribute("Jcontent"
							+ i))%></span>
							</p>
												<input type="hidden" value="<%=q.getQueId()%>" name="id">
							<textarea name="judge"><%=q.getQueContent()%></textarea>
							<p class="question_content">
								答案： <span><%=InputUtil.input(request.getAttribute("Janswer"
							+ i))%></span>
							</p>
							<label class="choice_content"><input type="radio"
								class="radio" value="Y" name="<%=num + "judge"%>"
								<%if ("Y".equals(q.getQueAnswer())) {%> checked <%}%>>正确</label>
							<label class="choice_content"><input type="radio"
								class="radio" value="N" name="<%=num + "judge"%>"
								<%if ("N".equals(q.getQueAnswer())) {%> checked <%}%>>错误</label>
							<input type="hidden" name="judgeNum" value="<%=num + "judge"%>">
							<label>解析：<textarea name="judgeExplain"><%=q.getQueExplain()%></textarea></label>
						</div>
						<%
							i++;
								}
						%>
					</div>
					<%
						} else {
					%>
					<p style="line-height: 100px; text-align: center;">当前没有判断题</p>
					<%
						}

						/*输出填空题*/
						num = 1;//序号归零
						if (jList != null) {
					%>
					<div id="fill_area">
						<h1>填空题</h1>
						<hr>
						<%
							int i = 0;
								for (Question q : fList) {
						%>
						<div class='one_fill'>
							<input type="hidden" value="fill" id="question"/>
							<input type="hidden" value="<%=q.getQueId()%>" name="id">
							<p class="question_content">
								第<%=num++%>题： <span class="illegal"><%=InputUtil.input(request.getAttribute("Fcontent"
							+ i))%></span>
							</p>
		
							<textarea name="fill"><%=q.getQueContent()%></textarea>
							<label>答案： <span><%=InputUtil.input(request.getAttribute("Fanswer"
							+ i))%></span>
								<input type="text" class="input" name="fillKey"
								value="<%=q.getQueAnswer()%>"></label> <label>解析：<textarea
									name="fillExplain"><%=q.getQueExplain()%></textarea></label>
						</div>
						<%
							i++;
								}
						%>
					</div>
					<%
						} else {
					%>
					<p style="line-height: 100px; text-align: center;">当前没有简答题</p>
					<%
						}
					%>
					<div class="button_qun">
						<input type="hidden" value="update" id="submit" name="submit"/>
						<button type="submit" id="create"   onclick="modify()" value="update">更&nbsp;新</button>
						<button type="button" onclick="window.location.href='question_delete?secId=${param.secId}'">删&nbsp;除</button>
					</div>
				</form>
						<%
					}
				%>
			</div>
		</div>
	</div>
	<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
	<script type="text/javascript"
		src="/wikestudy/dist/js/common/common.js"></script>
	<script type="text/javascript"
		src="/wikestudy/dist/js/manager/manager.js"></script>
	
	<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script><script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script>
	<script type="text/javascript">
	//使用json提交数据

		function modify() {
			$.ajax({
				type:"post",
				url: "/wikestudy/dist/jsp/teacher/course/question_make",
				dataType:"json",
				async:true,
				data:$("#update_question").serialize(),
				success:function(data) {
					//取出所有的map里面的结果，如果map不为空则拼接到error_div中，如果map为空，则跳转到学生答题的预览页面
					showData(data);
				},
				error:function() {
				}
			});
		}
		
		function showData(data) {
			if(data['status']=="error") {
				$('#message_div').html(data['message']);
				//拼接上map的内容
				if(data['errorMap']!=null) {
					var error_str="";
					var map=data['errorMap'];
					for (var s in map) {
						error_str=error_str+"<br/>"+map[s];
					}
					$('#error_div').html(error_str);
				}
				
			}else if(data['status']=="success"){
				//跳转页面到questionnaire_make.jsp 
				//在这个jsp中ajax 请求后台试题的数据
				window.location.href="/wikestudy/dist/jsp/teacher/course/question_page?secId="+$('#secId').val();
			}
			
		}
		

	</script>
</body>
</html>