<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="com.wikestudy.model.pojo.Star"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>微课网首页</title>
	<meta http-equiv="X_UA_Compatible" content="chrome=5">
	<link rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="nav.jsp"></jsp:include>
<%--                                   开始内容界面                                        --%>
<div id="study_class" class="outside">
	<div id="study_temp" class="wrapper out_padding">
		<h1>微课学习</h1>
	<%-- 第一步：	获取热门课程数据  --%>
		<div id="class_cata" class="translucence">	
			<div id="cou_main">
<%
	PageElem<Course> pe = (PageElem<Course>)request.getAttribute("courses");
	List<Course> courses = pe.getPageElem();
	int i=0;
	for(Course c : courses){
		i++;
%>	
				<%--大概输出十二门的课程  --%>
				<div class="one_course <%=(i%4==0)? "qu":"" %>">
					<%-- 输入一门课程图像的图片--%>
					<a href="course_select?couId=<%=c.getCouId()%>"><img  class="course_img" src="<%=c.getCouAllUrl() %>"/></a>
					<%--一门课程的标题 --%>
					<h3><%=c.getCouName() %></h3>
					<%--一门课程的简介 --%>
					<p><%=c.getCouBrief() %></p>
					<div class="clear">
					<%--标签，提示初几的课程 --%>
						<span class="left"><%=c.getGrade() %></span>
						<%-- 课程的点击量--%>
						<span class="right"><%=c.getCouStudyNum() %>人学习</span>
					</div>
				</div>
<%}%>
				<a class="more"  href="/wikestudy/dist/jsp/common/course_show?grade=0&labelId=0&type=3">更多微课推荐>></a>
			</div>
		</div>
	</div>
</div>
<div class="outside jiange"></div>
<%--第二步：	展示一周之星 --%>
<div id="stu_star" class="outside">
	<div class="wrapper out_padding">
		<h1>一周学习之星</h1>
		<div id="star_con">
			<div id="star_main_con">
<%
	List<Star> stars = (List<Star>)request.getAttribute("stars");
	i = 0; 
	for(Star s : stars){
		i++;
		if(i == 1){
%>
				<div id="section1">
<% 
		}else if(i == 6){
%>
					<div id="section2">
<% 
		}
%>
				<%--循环输出10个 --%>
						<div class="stu">
							<a href="user_center/message_query?id=<%=s.getStarStuId() %>">
								<img  class="person_img" src="<%=InputUtil.inputPhoto(s.getStuPortraitUrl(),s.getStarStuId(),false) %>"/>
							</a>
							<h4><%=s.getStuName() %></h4>
							<p>已学习<%=InputUtil.inputTime(s.getStuStudyHour()) %></p>
						</div>
<%
		if(i == 5){
%>
					</div>
<% 
		}else if(i == 10){
%>
				</div>
<% 
		}			
	}	
%>
			</div>
			<div id="starLimg"></div>
			<div id="starRimg"></div>
		</div>
	</div>
</div>
</div>
	<%--页尾 --%>
<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/home_page.js"></script>
</body>
</html>