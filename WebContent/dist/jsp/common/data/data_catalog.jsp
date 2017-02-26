<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.util.DataUtil"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="com.wikestudy.model.pojo.SectionView"%>
<%@page import="com.wikestudy.model.pojo.ChapterView"%>
<%@page import="com.wikestudy.model.pojo.CourseView"%>
<%@page import="com.wikestudy.model.pojo.CouChapter"%>
<%@page import="com.wikestudy.model.pojo.CouSection"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.model.pojo.Data"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>资料目录</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<%--页面分为三部分 分别是课程简介  老师简介  资料目录 --%>
<%//获取课程视图对象
CourseView cv = (CourseView)request.getAttribute("courseData");
%>
		<%--第一部分 课程简介 --%>
	<%
		Course c = cv.getCourse();
	%>
		<div id="intro">
			<div id="intro1">
				<img id="intro_img" class="course_img" src="<%=c.getCouAllUrl() %>" alt="课程封面" />
			</div>
			<div id="intro_main">
				<h3><%=c.getCouName() %></h3>
				<span>微课关注度：<%=c.getCouStudyNum() %></span>
				<p>简介：<%=c.getCouBrief() %></p>
			</div>
		</div>
		<div id="coursemain">
			<%--第三部分 资料目录 --%>
			<div id="coursemain_content">
	<%
		List<ChapterView> chaps = cv.getChapters();
		Iterator<ChapterView> chapIt = chaps.iterator();
		ChapterView chapV = null;
		int i  = 0;
		if(chaps == null || chaps.size() <= 0){
%>
				<p class="no_p">该课程未创建章节课时</p>
<%
		}else{
			while(chapIt.hasNext()){
				i++;
				chapV = chapIt.next();
				CouChapter chap = chapV.getChapter();
				List<CouSection> secL = chapV.getSections();
%>
				<div  class="one_content">
					<img src="/wikestudy/dist/images/common/images/round_03.png" alt="圆"/>
					<%--此处输出章节名 --%>
					<h3><a class="temp_a" href="data_query?id=<%=chap.getChaId() %>&type=true"><strong>第<%=i %>章  <%=chap.getChaName() %></strong></a></h3>
					<%--遍历资料集合和课时集合,输出他们的内容 --%>
					<ul>
<%
			Iterator<CouSection> secVIt = secL.iterator();
			int j = 0;
			while(secVIt.hasNext()){
				CouSection sec = secVIt.next();
				j++;
	%>
				<%--此处输出课时的数据 --%>
						<li><a href="data_query?id=<%=sec.getSecId() %>&type=false"><%=i + "—" + j%><%=sec.getSecName() %></a></li>	
				
		<% } %>
					</ul>	
				</div>
		<% } }%>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>