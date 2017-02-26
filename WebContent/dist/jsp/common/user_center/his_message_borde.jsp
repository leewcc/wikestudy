<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.Message"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.MessageView"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ta的留言板</title>
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
	<%--查看留言 --%>
<%
	
	int mid = Integer.parseInt(request.getParameter("id"));
	boolean mt = Boolean.parseBoolean(request.getParameter("type"));
	PageElem<MessageView> pe = (PageElem<MessageView>)request.getAttribute("messages");
	List<MessageView> mv = pe.getPageElem();
%>
	<%--输出用户信息 --%>
<%
	if(mt){
%>
		<jsp:include page="teacher_home.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="teacher_courses_see?id=<%=mid %>#stu_sidebar">他的课程</a></li>
			<li id="no_topic_head"><a href="user_topics_show?id=<%=mid %>&type=true#stu_sidebar">他的话题</a></li>
			<li id="no_note_head"><a href="teacher_articles_see?id=<%=mid %>#stu_sidebar">他的文章</a></li>
			<li id="mess_head"><a href="#stu_sidebar">留言板</a></li>
		</ul>
	
<%
	}else{
%>
		<jsp:include page="student_home.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="stu_courses_see?id=<%=mid %>&flag=2#stu_sidebar">他的课程</a></li>
			<li id="no_topic_head"><a href="user_topics_show?id=<%=mid %>&type=false#stu_sidebar">他的话题</a></li>
			<li id="no_note_head"><a href="his_notes_see?id=<%=mid %>#stu_sidebar">他的笔记</a></li>
			<li id="mess_head"><a href="#stu_sidebar">留言板</a></li>
		</ul>
<%
	}
%>
		<div id="main_area">
	
			<%--回复区 --%>
			<div id="reply_all">
				<form action="../user/user_center/message_send" method="post">
					<input type="hidden" name="marster" value="<%=mid %>" />
					<input type="hidden" name="type" value="<%=mt %>" />
					<textarea name="content"></textarea>
					<button class="topic_reply" type="submit" name="reply">留言</button>	
				</form>
			</div>
			<div id="reply_main">
	<%--迭代留言集合,输出留言内容 --%>
<%
	if(mv != null){
	for(MessageView messV : mv){
		Message mess = messV.getMessage();
%>
	<%--输出留言内容 留言者头像  留言者姓名  留言内容  留言时间  回复按钮--%>
				<div class="first_borde">
					<form action="../user/user_center/message_send" method="post">
						<input type="hidden" name="binding" value="<%=mess.getMessId() %>" />
						<input type="hidden" name="marster" value="<%=mess.getMessSenderId() %>" />
						<input type="hidden" name="type" value="<%=mess.isMessMasterMark() %>" />
						<a href="/wikestudy/dist/jsp/common/user_center/message_query?id=<%=mess.getMessSenderId() %>&type=<%=mess.isMessSenderMark() %>">
							<img class="person_img" src="<%=InputUtil.inputPhoto(mess.getPhoto(),mess.getMessSenderId(),mess.isMessSenderMark())%>" alt="头像">
						</a>
						<div class="reply_main_main">
							<span class="person"><%=mess.getSender() %></span>
							<span class="time"><%=TimeToot.format(mess.getMessTime().getTime()) %></span>
							<p class="content"><%=mess.getMessContent() %></p>
						</div>
						<button class="topic_reply" type="button" name="reply">回复</button>
						<div  class="hidden_textarea" >
							<textarea name="content"></textarea>
							<button class="topic_reply" type="submit" name="reply">回复</button>
						</div>
					</form>		
		<%--获取留言回复,迭代并输出回复的内容--%>
<%
		List<Message> rep = messV.getMessReply();
		for(Message mr : rep){
%>
		
			<%--输出留言内容 留言者头像  留言者姓名  留言内容  留言时间  回复按钮--%>
					<form action="../user/user_center/message_send" method="post" class="second_borde">
						<input type="hidden" name="binding" value="<%=mess.getMessId() %>" />
						<input type="hidden" name="marster" value="<%=mr.getMessSenderId() %>" />
						<input type="hidden" name="type" value="<%=mr.isMessMasterMark() %>" />
						<a href="message_query?id=<%=mr.getMessSenderId() %>&type=<%=mr.isMessSenderMark() %>">
							<img src="<%=InputUtil.inputPhoto(mr.getPhoto(),mr.getMessSenderId(),mr.isMessSenderMark()) %>" alt="头像">
						</a>
						<div class="reply_main_main">
							<span class="person"><%=mr.getSender() %></span>
							<span class="time"><%=TimeToot.format(mr.getMessTime().getTime()) %></span>
							<p class="content"><%=mr.getMessContent() %></p>
						</div>
						<button class="topic_reply" type="button" name="reply">回复</button>
						<div  class="hidden_textarea" >
							<textarea name="content"></textarea>
							<button class="topic_reply" type="submit" name="reply">回复</button>
						</div>
					</form>
<%
		}
		%>
				</div>
		<%
	}
	}else{
%>
				<p style="text-align: center;">当前没有任何留言。</p>
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
              <a href="message_query?id=<%=mid %>&type=<%=mt %>&currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="message_query?id=<%=mid %>&type=<%=mt %>>&currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="message_query?id=<%=mid %>&type=<%=mt %>&currentPage=1" class="<%=selected1%>">1</a>
            <a href="message_query?id=<%=mid %>&type=<%=mt %>&currentPage=2" class="<%=selected2%>">2</a>
<%
            i = cp - 2;
            if(i > 3){
%>
            <span>...</span>
<%
    }else{
        i = 3;
    }
    for(;i <= cp+2 && i < tp-1;i++){
        selected = (i == cp)?"selected":"";
%>
            <a href="message_query?id=<%=mid %>&type=<%=mt %>&currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
            <a href="message_query?id=<%=mid %>&type=<%=mt %>&currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="message_query?id=<%=mid %>&type=<%=mt %>&currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="message_query?id=<%=mid %>&type=<%=mt %>&currentPage=<%=cp+1%>" id="nextPage">.</a>
<%
    }
%>
           	 	</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js">
</script><script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script>
<script type="text/javascript">
		/* 这个之后可以修改得更好，去掉jQuery */
		function a(){
			$('.topic_reply').click(function(event){
				var $temp = $(event.target).next();
				if($temp.hasClass("hidden_textarea")){<%--如果有输入框--%>
					$('*').removeClass("show_textarea");
					$temp.addClass('show_textarea');
				}else if($temp.hasClass("show_textarea")){
					$temp.removeClass("show_textarea").addClass('hidden_textarea');
				}
			});
		}
		a();
</script>
</body>
</html>