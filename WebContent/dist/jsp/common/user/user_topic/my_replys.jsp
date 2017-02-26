<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.Comment"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.CommentView"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>我的回复</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/> 
	<link rel="stylesheet" type="text/css" href="/wikestudy/dist/css/wangEditor.min.css">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div id="wrapper">
		<jsp:include page="personal_topic.jsp">
		 	<jsp:param name="param_sidebar" value="reply"></jsp:param>
		</jsp:include>
	<%--获取我的回复集合 --%>
<%
	PageElem<Comment> pe = (PageElem<Comment>)request.getAttribute("myReplys");
	List<Comment> cv = pe.getPageElem();
%>
		<div id="my_ans">
			<div id="topic_area">
				<%--迭代我的回复,输出每条回复的内容 --%>
<%
	if(cv.size() <= 0){
%>
				<p class="guide_message">当前无回复信息</p>
<%
	}else{
	for(Comment c: cv){
%>
				<div class="one_com">
				<%--输出被评论者的头像 姓名 回复内容  回复时间 回复按钮  --%>
					<a class="a_img" href="/wikestudy/dist/jsp/common/user_center/user_topics_show?id=<%=c.getComUserId() %>&type=<%=c.isComUserEnum() %>">
						<img class="person_img" src="<%=InputUtil.inputPhoto(c.getPhoto(),c.getComUserId(),c.isComUserEnum()) %>" alt="用户头像"/>
					</a>
					<form action="comment_add" method="post">
						<input type="hidden" name="topic" value="<%=c.getComTopicId() %>" />
						<input type="hidden" name="binding"  value="<%=c.getComBinding() == 0? c.getComId() : c.getComBinding() %>"/>
						<input type="hidden" name="receiver"  value="<%=c.getComUserId() %>"/>
						<input type="hidden" name="type" value="<%=c.isComUserEnum() %>"/>
						<h3><%=c.getSender() %>：
						<a href="/wikestudy/dist/jsp/common/topic/topic_detail_get?topId=<%=c.getComTopicId() %>&currentPage=1#topic<%=c.getComTopicId() %>"><%=c.getComCon()%></a>
						</h3>		
						<span>发送时间：<%=TimeToot.format(c.getComTime().getTime()) %></span>
						<span>源自： <%=c.getTopTit() %></span>
						<button class="topic_reply" type="button" style="color:white;">回复</button>
						<div class="main_content hidden_textarea clear" >
							<div id="content<%=c.getComTopicId() %>">
							</div>
							<button class="reply_form" type="button">回复</button>
						</div>
					</form>
				</div>
<%
		}
	}
%>
			</div>
<%--输出分页数据 --%>
	<%
			int cp = pe.getCurrentPage();
			int tp = pe.getTotalPage();              
	%>
			<div id="page">
	<%  
	    if(cp > 1){    
	%>
        		<a href="my_replys_get?currentPage=<%=cp-1%>" id="previousPage">.</a>
	<%  }
	    int i = 1;
	    String selected = "";
	    if(tp <= 10){
	        for(i = 1; i <= tp; i++){
	            selected = (i == cp)?"selected":"";
	%>
           	 	<a href="/my_replys_get?currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
	<%
	        }
	    }else{ //分页大于10的情况
	        String selected1 = (1== cp)?"selected": "";
	        String selected2 = (2== cp)?"selected": "";
	%>
	            <a href="my_replys_get?currentPage=1" class="<%=selected1%>">1</a>
	            <a href="my_replys_get?currentPage=2" class="<%=selected2%>">2</a>
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
            	<a href="my_replys_get?currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
	            <a href="my_replys_get?currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
	            <a href="my_replys_get?currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
	<%        
	    }
	    if(cp < tp){
	%>
    			<a href="my_replys_get?currentPage=<%=cp+1%>" id="nextPage">.</a>
	<%}%>
           </div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/wangEditor.min.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/topic/reply.js"></script>
</body>
</html>