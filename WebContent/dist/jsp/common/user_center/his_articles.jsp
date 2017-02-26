<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.Article"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ta的文章</title>	
    <link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
    <link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
    <jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../nav.jsp"></jsp:include>
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
	    <span id="here"></span>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="teacher_courses_see?id=<%=mid %>#here">他的课程</a></li>
			<li id="no_topic_head"><a href="user_topics_show?id=<%=mid %>&type=true#here">他的话题</a></li>
			<li id="note_head"><a href="teacher_articles_see?id=<%=mid %>#here">他的文章</a></li>
			<li id="no_mess_head"><a href="message_query?id=<%=mid %>&type=true#here">留言板</a></li>
		</ul>
    	<%--第二步：	文章展示区 --%>
    	<div id="main_area">
    	   <div id="art_show_main">
 <%
 	PageElem<Article> pe = (PageElem<Article>)request.getAttribute("articles");
 	List<Article> articles = pe.getPageElem();
 	for(Article a : articles){
 %>
             	<div class="one_art">
             		<h3><a href="/wikestudy/dist/jsp/common/article/article_one?artid=<%=a.getArtId() %>"><%=a.getArtTitle() %></a></h3>
             		<span>发布时间：<%=a.getTime() %></span>
             		<span><%=a.getArtClick() %>人浏览过</span>
         		</div>
 <%
 	}
 %>
            </div>
 
 
 	<%--第三步：	分页 --%>
<%
		int cp = pe.getCurrentPage();  
		int tp = pe.getTotalPage();              
%>
	       <div id="page">
<%  
    if(cp > 1){    
%>
                <a href="teacher_articles_see?id=<%=mid %>&currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
                <a href="teacher_articles_see?id=<%=mid %>&currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
                <a href="teacher_articles_see?id=<%=mid %>&currentPage=1" class="<%=selected1%>">1</a>
                <a href="teacher_articles_see?id=<%=mid %>&currentPage=2" class="<%=selected2%>">2</a>
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
                <a href="teacher_articles_see?id=<%=mid %>&currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
                <a href="teacher_articles_see?id=<%=mid %>&currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
                <a href="teacher_articles_see?id=<%=mid %>&currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
                <a href="teacher_articles_see?id=<%=mid %>&currentPage=<%=cp+1%>" id="nextPage">.</a>
<%
    }
%>

	        </div>
	    </div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
</body>
</html>