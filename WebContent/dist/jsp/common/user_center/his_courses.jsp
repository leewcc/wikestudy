<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ta的课程</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
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
	<%--第一步：	include进老师简介 --%>
		<jsp:include page="teacher_home.jsp"></jsp:include>
<%
	int mid = Integer.parseInt(request.getParameter("id"));
%>
			<ul id="stu_sidebar">
				<li id="course_head"><a href="#stu_sidebar">他的课程</a></li>
				<li id="no_topic_head"><a href="user_topics_show?id=<%=mid %>&type=true#stu_sidebar">他的话题</a></li>
				<li id="no_note_head"><a href="teacher_articles_see?id=<%=mid %>#stu_sidebar">他的文章</a></li>
				<li id="no_mess_head"><a href="message_query?id=<%=mid %>&type=true#stu_sidebar">留言板</a></li>
			</ul>

<%--查看已经创建好的课程, 以课程列表的方式展示 --%>
<%--课程列表展示 --%>
<%--基本功能：课程列表展示(分页); 已发布课程, 未发布课程; --%>
<%--先从后台拿去数据 --%>
<%
	String type = request.getParameter("type") == null ? "1" : request.getParameter("type");
	PageElem<Course> pe = (PageElem<Course>)request.getAttribute("courses");
%>
	
	<%--课程列表展示 --%>
			<div id="main_area" class="course_com">
				<div id="mana_cou">
<% 
	if(pe.getPageElem().size() == 0) {
%>
					<p style="text-align:center;">他还未发布任何课程</p>
<%
	}else{ %>
					<ul>
<%	int i = 1;
		for(Course cou: pe.getPageElem()) {
			String no_mr = (i++%4==0)?"no_mr":"";
%>
						<li class="one_cou <%=no_mr%>">
							<a href="/wikestudy/dist/jsp/common/course_select?couId=<%=cou.getCouId() %>"><img src="<%=cou.getCouAllUrl() %>"/></a>
							<h5><%=cou.getCouName() %></h5>
						</li>
<%
				}
			} 
%>
					</ul>
				</div>
	<%--选择页面按钮 --%>
<%
	int cp = pe.getCurrentPage();
	int tp = pe.getTotalPage();
%>
    			<div id="page">
<%  
    if(cp > 1){    
%>
         			<a href="teacher_courses_see?id=<%=mid %>&type=<%=type %>&page=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
         			<a href="teacher_courses_see?id=<%=mid %>&type=<%=type %>&page=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
			        <a href="teacher_courses_see?id=<%=mid %>&type=<%=type %>&page=1" class="<%=selected1%>">1</a>
			        <a href="teacher_courses_see?id=<%=mid %>&type=<%=type %>&page=2" class="<%=selected2%>">2</a>
<%
            i = cp - 2;
            if(i > 3){
%>
         			<span>...</span>
<%
    }else{
        i=3;
    }
    for(;i <= cp+2 && i < tp-1;i++){
        selected = (i == cp)?"selected":"";
%>
         			<a href="teacher_courses_see?id=<%=mid %>&type=<%=type %>&page=<%=i%>" class="<%=selected%>"><%=i%></a>
<%
    }
    if(i < tp - 1){
%>
         			<span>...</span>
<%
    }
    String selectedt1 = (cp== tp-1)?"selected": "";
    String selectedt2 = (tp== cp)?"selected": "";
%>
			        <a href="teacher_courses_see?id=<%=mid %>&type=<%=type %>&page=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
			        <a href="teacher_courses_see?id=<%=mid %>&type=<%=type %>&page=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
 		 			<a href="teacher_courses_see?id=<%=mid %>&type=<%=type %>&page=<%=cp+1%>&type" id="nextPage">.</a>
<%
    }
%>
     			</div>
    		</div> 
    	</div> 
    </div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>