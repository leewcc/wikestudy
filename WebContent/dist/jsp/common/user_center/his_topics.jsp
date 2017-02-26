<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.Topic"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ta的话题</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>
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
<%
	int mid = Integer.parseInt(request.getParameter("id"));
	boolean mt = Boolean.parseBoolean(request.getParameter("type"));
%>

<%
	if(mt){
%>
		<jsp:include page="teacher_home.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="teacher_courses_see?id=<%=mid %>&type=true#stu_sidebar">他的课程</a></li>
			<li id="topic_head"><a href="#stu_sidebar">他的话题</a></li>
			<li id="no_note_head"><a href="teacher_articles_see?id=<%=mid %>&type=true#stu_sidebar">他的文章</a></li>
			<li id="no_mess_head"><a href="message_query?id=<%=mid %>&type=true#stu_sidebar">留言板</a></li>
		</ul>
	
<%
	}else{
%>
		<jsp:include page="student_home.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="stu_courses_see?id=<%=mid %>&flag=2#stu_sidebar">他的课程</a></li>
			<li id="topic_head"><a href="#stu_sidebar">他的话题</a></li>
			<li id="no_note_head"><a href="his_notes_see?id=<%=mid %>#stu_sidebar">他的笔记</a></li>
			<li id="no_mess_head"><a href="message_query?id=<%=mid %>&type=false#stu_sidebar">留言板</a></li>
		</ul>
<%
	}
%>
		<div id="main_area">
	<%--获取我的话题对象 --%>
<%
	PageElem<Topic> pe = (PageElem<Topic>)request.getAttribute("hisTopics");
	List<Topic> topics = pe.getPageElem();
%>
	<%--迭代话题集合,输出每个话题的内容 --%>
<%
	for(Topic topic : topics){
%>
			<div class="one_topic">
				<%--输出话题标题  话题内容 话题类型  发布时间  回复数量  浏览数量 --%>
				<h3><a href="../topic/topic_detail_get?topId=<%=topic.getTopId() %>&currentPage=1"><%=topic.getTopTit() %></a></h3>
				<br />
				<span>发布时间：<%=TimeToot.format(topic.getTopTime().getTime()) %></span>
				<a class="topic_label" href="../topic/topic_list_get?labId=<%=topic.getLabId() %>&currentPage=1&type=1"><%=topic.getLabelName() %></a>
<%
		if(topic.getSec() != null){
%>
			源自：<%=topic.getSec() %>
<%
		}
%>
				<span class="right"><%=topic.getTopAnsNum() %>人回答过</span>
				<span class="right"><%=topic.getTopReadNum() %>人浏览过</span>
			</div>
<%
	}
%>

	<%--输出分页数据 --%>
<%
		int cp = pe.getCurrentPage();
		int tp = pe.getTotalPage();              
%>
	       <div id="page">
<%  
    if(cp > 1){    
%>
              <a href="user_topics_show?id=<%=mid %>&type=<%=mt %>&currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="user_topics_show?id=<%=mid %>&type=<%=mt %>&currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="user_topics_show?id=<%=mid %>&type=<%=mt %>&currentPage=1" class="<%=selected1%>">1</a>
            <a href="user_topics_show?id=<%=mid %>&type=<%=mt %>&currentPage=2" class="<%=selected2%>">2</a>
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
            <a href="user_topics_show?id=<%=mid %>&type=<%=mt %>&currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
            <a href="user_topics_show?id=<%=mid %>&type=<%=mt %>&currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="user_topics_show?id=<%=mid %>&type=<%=mt %>&currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="user_topics_show?id=<%=mid %>&type=<%=mt %>&currentPage=<%=cp+1%>" id="nextPage">.</a>
<%
    }
%>
            </div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>