<%@page import="com.wikestudy.service.publicpart.TopicService"%>
<%@page import="com.wikestudy.model.pojo.Topic"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>我的话题</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>

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
		boolean ut = (boolean)request.getSession().getAttribute("userType");
		if(!ut){
%>
		<jsp:include page="student_sidebar.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="/wikestudy/dist/jsp/student/course/course_center#stu_sidebar">我的课程</a></li>
			<li id="topic_head"><a href="my_topic_get#stu_sidebar">我的话题</a></li>
			<li id="no_note_head"><a href="../course/course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="no_mess_head"><a href="my_message_query#stu_sidebar">留言板</a></li>
		</ul>
<%
		}else{
%>		
		<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="../../teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
			<li id="topic_head"><a href="my_topic_get#stu_sidebar">我的话题</a></li>
			<li id="no_note_head"><a href="../../teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
			<li id="no_mess_head"><a href="my_message_query#stu_sidebar">留言板</a></li>
		</ul>
<%
		}
%>
		<div id="main_area">
	<%--获取我的话题对象 --%>
<%
	PageElem<Topic> pe = (PageElem<Topic>)request.getAttribute("myTopics");
	if(pe!=null)  {
	List<Topic> topics = pe.getPageElem();
%>
			<%--迭代话题集合,输出每个话题的内容 --%>
			<div>
<%
	for(Topic topic : topics){
%>
	<div class="one_topic">
		<%--输出话题标题  话题内容 话题类型  发布时间  回复数量  浏览数量 --%>
		<h3><a href="../../common/topic/topic_detail_get?topId=<%=topic.getTopId() %>&currentPage=1"><%=topic.getTopTit() %></a></h3>
		<br />
		<br />
		<span>发布时间：<%=TimeToot.format(topic.getTopTime().getTime()) %></span>
		<a class="topic_label" href="../../common/topic/topic_list_get?labId=<%=topic.getLabId() %>&currentPage=1&type=1"><%=topic.getLabelName() %></a>
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
              <a href="my_topic_get?currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="my_topic_get?currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="my_topic_get?currentPage=1" class="<%=selected1%>">1</a>
            <a href="my_topic_get?currentPage=2" class="<%=selected2%>">2</a>
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
            <a href="my_topic_get?currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
            <a href="my_topic_get?currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="my_topic_get?currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="my_topic_get?currentPage=<%=cp+1%>" id="nextPage">.</a>
<%
    }
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