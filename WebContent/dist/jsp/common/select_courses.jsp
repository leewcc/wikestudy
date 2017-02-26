<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.List, com.wikestudy.model.pojo.*, com.wikestudy.model.enums.Role" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>选课</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link rel="stylesheet" type="text/css" href="/wikestudy/dist/css/common/home_page.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<%
	String type = (String) request.getAttribute("type");
	Course cou = (Course) request.getAttribute("cou");
	Teacher teacher = (Teacher) session.getAttribute("t");
	Role role = (Role)session.getAttribute("role");
	System.out.println(role);
	StuCourse stuCourse = (StuCourse) request.getAttribute("stuCourse");
%>
<jsp:include page="nav.jsp"></jsp:include>
<%--大框架 --%>
<div class="outside">
	<%-- 课程筛选div --%>
	<div class="wrapper">
		<div id="selectHeader">
			<div></div>
			<%--这里要动态加载了 --%>
			<h1>选课&nbsp;>&nbsp;<%=cou.getCouGrade() %>&nbsp;>&nbsp;<%=cou.getCouName() %><span></span></h1>
		</div>
		<div id="intro">
			<div id="intro1">
				<img id="intro_img" class="course_img" src="<%=cou.getCouAllUrl() %>" alt="课程头像"/>
				<div id="intro_main">
					<h3><%=cou.getCouName() %></h3>
					<span>微课关注度：<%=cou.getCouStudyNum() %></span>
					<p>课程简介: 
						<% 	if (cou.getCouBrief().equals("\"\"") || cou.getCouBrief().equals("") ){ %>	
							<span>当前暂无介绍</span>
						<% } %>
					</p>
					<%-- 未登录 未选修该课程 --%>
					<%if (stuCourse != null || (Role.teacher).equals(role)){%>
					<div><!-- <a href="" class="button">开始学习</a> --></div>
					<%} else if (role == null || stuCourse == null) { %>
					<div><a href="course_selectT?couId=<%=cou.getCouId()%>" class="button">选修课程</a></div>
					<% } %>
				</div>
			</div>
		</div>
		<%-- 课程介绍 --%>
		<div id="coursemain">
			<span id="here"></span>
				<ul id="coursemain_list">
					<li id="_bricf" <%=type.equals("1")?"class='selected'":"" %>><a href="course_select?type=1&couId=<%= cou.getCouId() %>#here">课程简介</a></li>
					<li id="_note"  <%=type.equals("3")?"class='selected'":"" %>><a href="course_select?type=3&page=1&couId=<%= cou.getCouId() %>#here">讨论</a></li>
				</ul>
				<%--章节 --%>
				<div id="coursemain_content">
					<% if (type.equals("1")) { 
					List<ChapSec> csList = (List<ChapSec>) request.getAttribute("csList"); /* 章节信息拿取 */
					if(csList.size() <= 0){ %>
						<p class="no_p">当前没有课程章节</p>
					<% } else {
                    for (ChapSec cs:csList) {%>
					<div  class="one_content">
						<img src="/wikestudy/dist/images/common/images/round_03.png" alt="圆" />
						<h3><span>+</span><strong>第<%=cs.getCouChapter().getChaNumber() %>章  <%=cs.getCouChapter().getChaName() %></strong></h3>
						<ul>
							<% for(CouSection ccs :cs.getCouSection()) { %>
							<li><a href="/wikestudy/dist/jsp/student/course/media_show?secId=<%=ccs.getSecId() %>&chaId=<%=ccs.getChaId() %>&couId=<%=cou.getCouId() %>">第<%=ccs.getSecNumber() %>节  <%=ccs.getSecName() %></a></li>
							<% } %>
						</ul>	
					</div>
				<%	} } }else if (type.equals("2")) {  %>

				<%--评论区 --%>
				<% PageElem<DiscussView> peDisView = (PageElem<DiscussView>) request.getAttribute("peDisView");
					if (peDisView.getPageElem().size() != 0) {
				%>
				<ul id="cou_com">
				<% for (DiscussView dw: peDisView.getPageElem()) { %>
					<%--评论列表 ,还没弄好--%>
					<li>
						<%--个人主页与个人头像 --%>
						<a class="per_pho" href =""><img src="<%=dw.getStuPortraitUrl()%>" class="person_img"></a>
						<%--姓名, 评论与评论时间 --%>
						<div class="per_main">
							<div><a href=""><%=dw.getStuName() %></a><span class="time">时间:<%=dw.getNDReleTime() %></span></div><%--头像与姓名均可链接到个人主页 --%>
							<p><%=dw.getNDContent() %></p>
							<div><%--时间 --%>
							源自：
								<%if (dw.getSecName() != null && dw.getSecName()!= "") {%>
								<a href=""><%=dw.getSecName() %></a>
								<%} %>
						    </div>
						</div>
						<%--个人主页与个人头像 --%>
					</li>
				<%}
%>
				</ul>
<%
					} else {%>
				<p class="no_p">暂无评论</p>
				<%} %>
				<%--讨论 --%>
				<%}else if (type.equals("3")) {%>
					<div id="coursemain_question">
					<%PageElem<Topic> tpView = (PageElem<Topic>) request.getAttribute("peTopic");
					  if(tpView == null || tpView.getPageElem().size() == 0){%>
					 <p class="no_p">当前没有讨论</p>
					 <%}else { for (Topic tv: tpView.getPageElem()) {%>
						<div  class="oneQuestion">
							<img src="/wikestudy/dist/images/common/people_small.jpg" alt="用户头像" class="question_userImg"/><%-- 个人头像 --%>
							<div class="oneQuestion_main">
								<span class="name"><%=tv.getUserName() %></span>
								<span class="time"><%=tv.getTopTime() %></span>
								<p class="content"><%=tv.getTopTit() %></p>
								<div class="from">来自:<span>章节</span></div>
							</div>
						</div>
					<%}}%>
					</div>
				<%}%>
				</div>
			</div>		
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>	
</body>
</html>