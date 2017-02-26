<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.GraClass"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>显示个人信息</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
	<%-- 获取学生的信息 --%>
<%
	Student s = ((Student)request.getSession().getAttribute("s"));
  	int id = 0;
	String name = "";
	String number = "";
	String grade = "";
	int stuCla = 0;
	String gender = "";
	String photo = "";
	String signature = "";
	String claS = "";
	long studyHour = 0;
	int discussNum = 0;
	
	//如果学生不为null,则将学生的信息赋值到对应元素
	if(s != null){
		id = s.getStuId();
		name = s.getStuName();
		number = s.getStuNumber();
		grade = s.getGrade();
		stuCla = Integer.parseInt(s.getStuClass());
		gender = s.getStuGender();
		photo = s.getStuPortraitUrl();
		studyHour = s.getStuStudyHour();
		discussNum = s.getStuDiscussionNum();
		
	}
%>
<% 
	//获取对应年级的班级对象
	GraClass cla = null;
	claS = s.getStuClass();
	if(claS.equals("1"))
		cla = (GraClass)application.getAttribute("one");
	else if(claS.equals("2"))
		cla = (GraClass)application.getAttribute("two");
	else
		cla = (GraClass)application.getAttribute("three");
%>
<jsp:include page="/dist/jsp/common/nav.jsp"/>  
	<%-- 学生主页,共三部分 --%>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
	      <div id="center_headD">
	        <div id="center_headC"></div>
	        <h1 id="center_headB"> 个人中心</h1>
	      </div>
	    </div>
		<%-- 侧边有头像和侧边栏 --%>
		<jsp:include page="../common/student_sidebar.jsp"></jsp:include>
			<%-- 中间的是主要内容区 --%>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="/wikestudy/dist/jsp/student/course/course_center#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="../common/my_topic_get#stu_sidebar">我的话题</a></li>	
			<li id="no_note_head"><a href="../course/course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="no_mess_head"><a href="../common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
	<%-- 中间的是主要内容区 --%>
		<div id="main_area">
			<h1 class="guide">修改资料</h1>
			<form id="stu_upda" action="base_update" method="post">
				<ul>
					<li>
						<p class="illegal">${message}</p>
						<input type="hidden" name="id" value="<%=id %>" />
					</li>
					<li>
						<p class="guide">头像</p>
						<a href="photo_update"><img src="<%=photo %>" alt="头像"/></a>
					</li>
					<li>
						<p class="guide">密码</p>
						<p>
						<span id="password">密码已隐藏</span>
						<a href="password_update">修改密码</a></p>
					</li>
					<li>
						<label for="cla">班级</label>
						<select id="cla" name="cla">
					<%--输出对应年级的班级可选数 --%>
<%
					int count = cla.getClaClassNum();
					for(int i = 1; i <= count; i++){
%>
							<option value="<%=i%>"  <%if(i == stuCla){ %> selected="selected" <%} %>>
							<%=i %>
							</option>
<%}%>
						</select>
					</li>
					<li>
						<label for="gender">性别</label>
						<select id="gender" name="gender">
							<option value="male">男</option>
							<option value="female">女</option>
						</select>
					</li>
					<li>
						<p class="illegal">${signature}</p>
						<label for="signature">简介</label>
						<textarea id="signature" name="signature"><%=s.getStuSignature() %></textarea>
					</li>
					<li>
						<!-- <button type="button" onclick="window.history.go(-1)">取消</button> -->
						<button type="submit" >提交修改</button>
					</li>
				</ul>
			</form>
		</div>
		</div>
	</div>
	<jsp:include page="/dist/jsp/common/footer.jsp"/>
</body>
</html>