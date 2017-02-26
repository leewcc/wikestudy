<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="com.wikestudy.model.pojo.Comment"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.CommentView"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page import="com.wikestudy.model.pojo.Label"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.Topic"%>
<%@page import="com.wikestudy.model.pojo.TopicView"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>查看具体话题页面</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<link rel="stylesheet" type="text/css" href="/wikestudy/dist/css/wangEditor.min.css">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>  
<div class="outside">
	<div class="wrapper clear_float">
		<div id="topic_headA">
			<div id="topic_headB">
				<div id="topic_headC"></div>
				<h1 id="topic_headD">在线交流</h1>
			</div>
		</div>
	 	<jsp:include page="/dist/jsp/common/user/user_topic/personal_topic.jsp"></jsp:include>
	    <div id="topic_detail">
	<%-- 总体分为三部分 --%>
	<%-- 分别是话题主要内容区 评论区 回复区 --%>
	
<%
	//获取话题视图对象
	TopicView tv = (TopicView)request.getAttribute("topic");
%>

	
	<%-- 第一部分  话题主要内容区 --%>
<%
	//获取话题对象
	//根据话题对象的用户角色标志获取对应的角色的id,姓名,头像路径
	Topic t = tv.getTopic();
	boolean hasAtten = (boolean)request.getAttribute("hasAtten");
	String atten = hasAtten? "取消关注" : "关注";
%>	
	
			<div id="topic_det_main">
				<h2><%=t.getTopTit() %></h2>
				<a class="topic_pho" href="/wikestudy/dist/jsp/common/user_center/user_topics_show?id=<%=t.getTopUserId()%>&type=<%=t.isTopUserEnum() %>">
					<img class="person_img" src="<%=InputUtil.inputPhoto(t.getPhoto(),t.getTopUserId(),t.isTopUserEnum()) %>" alt="发布人头像" />
				</a>
				<div class="topic_intro">
					<p><%=t.getUserName() %></p>
					<span class="time"><%=TimeToot.format(t.getTopTime().getTime()) %></span>
					<a href="topic_list_get?labId=<%=t.getLabId() %>&currentPage=1&type=1"><%=t.getLabelName() %></a>
<%
		if(t.getSec() != null){
%>
			源自：<%=t.getSec() %>
<%
		}
%>
			     	<input type="button" name="attention" value="<%=atten%>" onclick="window.location.href='/wikestudy/dist/jsp/common/user/user_topic/attention_topic_show?topId=<%=t.getTopId() %>'" />
				</div>
				<p id="topic_con"><%=t.getTopCon() %></p>
				<br />
				<br />
			</div>
			<%-- 第二部分  评论区 --%>
			<form id="main_reply" action="../user/user_topic/comment_add" method="post">
				<input type="hidden" name="topic" value="<%=t.getTopId() %>" />
				<input type="hidden" name="binding" value="0" />
				<input type="hidden" name="receiver" value="<%=t.getTopUserId() %>"/>
				<input type="hidden" name="type" value="<%=t.isTopUserEnum() %>"/>
				<p class="illegal">${param.content}</p>
				<div id="content">
				</div>
				<button class="topic_reply_sub" type="button" id="submit_reply">评论</button>
			</form>
<%
	//获取评论回复分页对象
	PageElem<CommentView> cv = tv.comment_gets();
	List<CommentView> cvL = cv.getPageElem();
	Iterator<CommentView> cvIt = cvL.iterator();
	CommentView c = null;
%>
<%
	while(cvIt.hasNext()){
		c = cvIt.next();
		Comment com = c.comment_get();
		List<Comment> comReply = c.getComReply();
		Iterator<Comment> cit = comReply.iterator();
%>	<%-- 输出主评论的内容 --%>
			<div class="topic_reply_area">
				<form class="reply_main" action="../user/user_topic/comment_add" method="post">
					<input type="hidden" name="topic" value="<%=t.getTopId() %>" />
					<input type="hidden" name="binding" value="<%=com.getComId() %>" />
					<input type="hidden" name="receiver" value="<%=com.getComUserId() %>" />
					<input type="hidden" name="type" value="<%=com.isComUserEnum() %>" />
					<a class="topic_pho" href="/wikestudy/dist/jsp/common/user_center/user_topics_show?id=<%=com.getComUserId() %>&type=<%=com.isComUserEnum() %>">
						<img class="person_img" src="<%=InputUtil.inputPhoto(com.getPhoto(),com.getComUserId(),com.isComUserEnum()) %>" alt="头像" />
					</a>
					<div class="topic_intro">
						<span><%=com.getSender() %></span>
						<span class="time"><%=TimeToot.format(com.getComTime().getTime()) %></span>
						<button class="topic_reply" type="button" name="reply">回复</button>
						<p><%=com.getComCon() %></p>
					</div>
					<div class="hidden_textarea" >
						<div id="content<%=com.getComTopicId() %>">
							</div>
						<button class="topic_reply_sub" type="submit" name="reply">回复</button>
					</div>
				</form>
<%-- 迭代回复集合,显示对应评论的集合 --%>		
<%
		while(cit.hasNext()){
			Comment comRV = cit.next();
%>		 
				<form id="topic<%=t.getTopId() %>"  class="reply_sec" action="../user/user_topic/comment_add" method="post">
					<input type="hidden" name="topic" value="<%=t.getTopId() %>" />
					<input type="hidden" name="binding" value="<%=com.getComId() %>" />
					<input type="hidden" name="receiver" value="<%=comRV.getComUserId() %>" />
					<input type="hidden" name="type" value="<%=comRV.isComUserEnum() %>" />
		 			<a class="topic_pho" href="/wikestudy/dist/jsp/common/user_center/user_topics_show?id=<%=comRV.getComUserId() %>&type=<%=comRV.isComUserEnum() %>">
		 				<img class="person_img" src="<%=InputUtil.inputPhoto(comRV.getPhoto(),comRV.getComUserId(), comRV.isComUserEnum()) %>" alt="头像" />
		 			</a>
		 			<div class="topic_intro">
						<span><%=comRV.getSender() %></span>
						<span class="time"><%=TimeToot.format(com.getComTime().getTime()) %></span>
						<button class="topic_reply" type="button" name="reply">回复</button>
						<p><%=comRV.getComCon() %></p>
					</div>
					<div class="hidden_textarea" >
						<div id="content<%=comRV.getComTopicId() %>">
							</div>
						<button class="topic_reply_sub"  type="button" name="reply">回复</button>
					</div>
				</form>
	<%}%>
			</div>
<%}%>
<%--输出分页数据 --%>
	<%
			int cp = cv.getCurrentPage();
			int tp = cv.getTotalPage();              
	%>
			<div id="page">
	<%  
	    if(cp > 1){    
	%>
        		<a href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=<%=cp-1%>" id="previousPage">.</a>
	<%  }
	    int i = 1;
	    String selected = "";
	    if(tp <= 10){
	        for(i = 1; i <= tp; i++){
	            selected = (i == cp)?"selected":"";
	%>
            	<a href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
	<%
	        }
	    }else{ //分页大于10的情况
	            String selected1 = (1== cp)?"selected": "";
	            String selected2 = (2== cp)?"selected": "";
	%>
	            <a href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=1" class="<%=selected1%>">1</a>
	            <a href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=2" class="<%=selected2%>">2</a>
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
            	<a href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
	<%
	    }
	    if(i < tp - 1){
	%>
            	<span>...</span>
	<%
	    }
	    String selectedt1 = (cp == tp-1)?"selected": "";
	    String selectedt2 = (tp == cp)?"selected": "";
	%>
	            <a href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
	            <a href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
	<%        
	    }
	    if(cp < tp){
	%>
    			<a href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=<%=cp+1%>" id="nextPage">.</a>
	<% }%>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/wangEditor.min.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/topic/detail.js"></script>
</body>
</html>