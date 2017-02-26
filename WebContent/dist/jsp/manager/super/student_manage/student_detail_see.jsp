<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.StudentView"%>
<%@page import="com.wikestudy.model.pojo.GraClass"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.AttenTopic"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@ page import="com.wikestudy.model.pojo.Teacher" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>观看某一个学生的细节</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
<div id="wrapper">
	<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_per">人员管理</span>
			<span id="sec_head">学生资料</span>
		</div>
	<%-- 获取学生,并将对应的信息输出到对应的元素上 --%>
<%
	Student s = (Student)request.getAttribute("student");
	int one = ((GraClass)application.getAttribute("one")).getClaClassNum();
	int two = ((GraClass)application.getAttribute("two")).getClaClassNum();
	int three = ((GraClass)application.getAttribute("three")).getClaClassNum();
	String grade = s.getStuGrade();
	int count = "1".equals(grade)?one : ("2".equals(grade) ? two : ("3".equals(grade) ? three : one));
	int cla = Integer.parseInt(s.getStuClass());

%>
		<div id="stu_dei">
			<p class="illegal">${message }</p>
			<form id="up_stu_bsa" action="student_base_update" method="post">
				<img class="person_img" src="<%=s.getStuPortraitUrl()  %>" />
				<div>
					<input type="hidden" value="<%=s.getStuId() %>" name="stuId" />
					<label for="stuName">名字：
						<input type="text" id="stuName" name="stuName" value="<%=s.getStuName() %>"/>
						<p class="illegal">${name }</p>
					</label>
					<input type="hidden" name="stuPhoto" value="<%=s.getStuPortraitUrl()%>"/>
					<label for="stuNumber">学号：
						<input type="number" id="stuNumber" name="stuNumber" value="<%=s.getStuNumber()%>"/>
						<p class="illegal">${num }</p>
					</label>
					<label for="stuGrade">年级：
						<select name="stuGrade" id="stuGrade">
							<option value="1" <%=grade.equals("1")? "selected" : "" %>>初一</option>
							<option value="2" <%=grade.equals("2")? "selected" : "" %>>初二</option>
							<option value="3" <%=grade.equals("3")? "selected" : "" %>>初三</option>
						</select>
						<p class="illegal">${grade }</p>
					</label>
					
					<label for="stuClass">班级：
						<select name="stuClass" id="stuClass">
			<%
				for(int i = 1; i <= count; i++){
					
%>
							<option value="<%=i%>" <%if(cla == i){ %> selected="selected" <%} %>><%=i %>班</option>
<% 
				}
%>			
						</select>
					</label>
					<label for="stuGender">性别：
						<select name="stuGender" id="stuGender">
							<option value="male" <%=s.getStuGender().equals("male")? "selected" : "" %> >男</option>
							<option value="female"  <%=s.getStuGender().equals("female")? "selected" : "" %> >女</option>
						</select>
					</label>
					<label>
						<span>学习时长：<%=InputUtil.inputTime(s.getStuStudyHour()) %></span>
						<span>讨论次数：<%=TimeToot.calculateHour(s.getStuDiscussionNum()) %>次</span>
					</label>
				</div>
				<label for="stuSignature" id="stu_lab"><span>个性签名：</span>
					<textarea name="stuSignature" id="stuSignature"><%=s.getStuSignature()%></textarea>
					<p class="illegal">${signature}</p>
				</label>		
					
				<div class="button_qun">
					<button type="submit">修改</button>
					<button type="button" onclick="window.location.href='stu_password_reset?stuId=<%=s.getStuId() %>'">密码重置</button>
					<button type="button" onclick="window.location.href='student_delete?stuId=<%=s.getStuId()%>'">删除</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
</body>
</html>