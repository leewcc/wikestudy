<%@page import="com.sun.corba.se.impl.protocol.giopmsgheaders.Message"%>
<%@page import="com.wikestudy.model.pojo.Question"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="com.wikestudy.model.pojo.Question"%>
<html>
<head>
<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
<title>回答问题</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/dist/css/teacher/teacher.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
	      <div id="center_headD">
	        <div id="center_headC"></div>
	        <h1 id="center_headB"> 个人中心</h1>
	      </div>
	    </div>
<%-- 侧边有头像和侧边栏 --%>
	<jsp:include page="/dist/jsp/student/common/student_sidebar.jsp"></jsp:include>
	<%-- 中间的是主要内容区 --%>
<div id="main_area">
	<h1 id="answer_heading">测试题</h1>
<form action="AnswerFinish" method="post" id="update_question">
	<input type="hidden" id="secId" name="secId" value="${secId }">
	<%  
		/*获得所有的问题*/
		ArrayList<Question> oList= (ArrayList<Question>)request.getAttribute("option");
		ArrayList<Question> jList=(ArrayList<Question>) request.getAttribute("judge");
		ArrayList<Question> fList=(ArrayList<Question>) request.getAttribute("fill");
		/*输出选择题*/
		int num=0;//序号
		if(oList.size()<=0&&jList.size()<=0&&fList.size()<=0){
		%>
			<p style="line-height: 100px; text-align: center;">当前课时还没有考试</p>	
		<%
		}else{
		if(oList!=null) {
			%>
			<div id="choice_area">
			<h1>选择题</h1><hr>
			<input type="hidden" name="optionNum" value="<%=oList.size() %>" />
			
			<%
			for(Question q:oList) {
				%>
			<div class="one_choice">
			
				<p class="question_content">第<%=++num %>题：
				<%=q.getQueContent() %></p>
				<input type="hidden" value="<%=q.getQueId() %>" name="<%=num %>optionid">
				<%
					/*选择题选项*/
					String string=q.getQueOption();
					String []os=string.split(";next;");
					char A='A';	
					for(String s:os) {				
				%>
					<label class="choice_content"><input type="radio" value="<%=A %>" name="<%=num+"option"%>">
					<span><%=A %>. <%=s.substring(0,s.length() - 1) %></span></label>
				<% 	A++;}%>			
			</div>
				<%
			}
			%>
				</div>
			<%

		}
		/*输出判断题*/
		num=0;//序号归零
		if(jList!=null) {
			%>
		<div id="judge_area">
		<h1>判断题</h1>
		<input type="hidden" name="judgeNum" value="<%=jList.size() %>"/>
		<hr>
			<%
			for(Question q:jList) {
			%>
			<div class="one_judge">	
				<p class="question_content">第<%=++num %>题：
				<%=q.getQueContent() %></p>
				<input type="hidden" value="<%=q.getQueId() %>" name="<%=num %>judgeid">
				<label class="choice_content"><input type="radio" class="radio" value="Y"  name="<%=num + "judge"%>"><span>正确</span></label>
				<label class="choice_content"><input type="radio" class="radio" value="N" name="<%=num+"judge"%>"><span>错误</span></label>
				<!-- <input type="radio" value="Y"  name="<%=num+"judge"%>">Y -->
				<!-- <input type="radio" value="N" name="<%=num+"judge"%>">N<br/> -->
			</div>
			<%
			}
			
			%>
			</div>
			<%
		}
		
		/*输出填空题*/
		num=0;//序号归零
		if(fList!=null) {
			%>
			<div id="fill_area">
			<h1>填空题</h1><hr>
			<input type="hidden" name="fillNum" value="<%=fList.size() %>">
			<%
			for(Question q:fList) {
			%>
			<div class="one_fill">
				<p class="question_content">第<%=++num %>题：
				<%=q.getQueContent() %></p>
				<input type="hidden" value=<%=q.getQueId() %> name="<%=num %>fillKeyid">
				<label>在此填写答案：<textarea name="<%=num %>fillKey"></textarea></label>
			</div>
			<%
			}
			%>
			</div>
			<%
		}
	%>
		<div class="button_qun">
		<!-- 	<input class="button" type="button" value="查看答题卡"> -->
			<input class="button" type="submit" value="提交考卷" name="submit">
		</div>
	<%} %>
	</form>
	</div>
</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>

</body>
</html>