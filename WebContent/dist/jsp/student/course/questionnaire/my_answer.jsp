<%@page import="com.wikestudy.model.pojo.Question"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
<title>我的答题情况</title>
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
	<div id="main_area">
	<div id="update_question">
	
	<%  
		/*获得所有的问题*/
		ArrayList<Question> oList= (ArrayList<Question>)request.getAttribute("option");
		ArrayList<Question> jList=(ArrayList<Question>) request.getAttribute("judge");
		ArrayList<Question> fList=(ArrayList<Question>) request.getAttribute("fill");
		/*输出选择题*/
		int num=1;//序号
		if(oList!=null) {
			%>
			<div id="choice_area">
			<h1>选择题</h1><hr>
			<%
			for(Question q:oList) {
				%>
			<div class="one_choice">
				<p class="question_content">第<%=num++ %>题：
				<%=q.getQueContent() %></p>
				<p>我的答案：<span <%=q.getStuAnswer().equals(q.getQueAnswer())?"class='right_answer'":"class='error_answer'" %>><%=q.getStuAnswer() %></span></p>
				<%
					/*选择题选项*/
					String string=q.getQueOption();
					String []os=string.split(";next;");
					char A='A';	
					for(String s:os) {				
				%>
					<p><%=A %>
					<%=s.substring(0, s.length() - 1) %></p>
				<% 	A++;}%>		
				<p>正确答案：<%=q.getQueAnswer()%></p>
				<p>解析：<%=q.getQueExplain() %></p>
			</div>
		<%
			}
			%>
				</div>
			<%

		}
		/*输出判断题*/
		num=1;//序号归零
		if(jList!=null) {
			%>
			<div id="judge_area">
			<h1>判断题</h1>
			<hr>
				<%
			for(Question q:jList) {
			%>
			<div class="one_judge">	
				<p class="question_content">第<%=num++ %>题：
				<%=q.getQueContent() %></p>
				<p>我的答案：<span <%=q.getStuAnswer().equals(q.getQueAnswer())?"class='right_answer'":"class='error_answer'" %>><%="N".equals(q.getStuAnswer())? "错误" : "Y".equals(q.getStuAnswer())? "正确":"" %></span></p>
				<p>解析：<%=q.getQueExplain() %></p>
			</div>
			<%
			}
				
				%>
				</div>
				<%
			}
		
		/*输出填空题*/
		num=1;//序号归零
		if(jList!=null) {
			%>
			<div id="fill_area">
			<h1>填空题</h1><hr>
			<%
			for(Question q:fList) {
			%>
			<div class='one_fill'>
				<p class="question_content">第<%=num++ %>题：
				<%=q.getQueContent() %></p>
				<p>我的答案：<span <%=q.getStuAnswer().equals(q.getQueAnswer())?"class='right_answer'":"class='error_answer'" %>><%=q.getStuAnswer() %></span></p>
				<p>正确答案：<%=q.getQueAnswer() %></p>
				<p>解析：<%=q.getQueExplain() %></p>
			</div>
			<%
			}
			%>
			</div>
			<%
		}
	%>
</div>
</div>
</div>
	</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>