<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.Message"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.MessageView"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>我的留言板</title>
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
		    
		    
	<%--判断用户类型，include进对应用户的东西 --%>
<%
		boolean ut = (boolean)request.getSession().getAttribute("userType");
		int uid = 0;
		if(!ut){
			uid = ((Student)request.getSession().getAttribute("s")).getStuId();
%>
		
		<jsp:include page="student_sidebar.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="/wikestudy/dist/jsp/student/course/course_center#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="my_topic_get#stu_sidebar">我的话题</a></li>
			<li id="no_note_head"><a href="/wikestudy/dist/jsp/student/course/course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="mess_head"><a href="my_message_query#stu_sidebar">留言板</a></li>
		</ul>
		
<%
		}else{
			uid = ((Teacher)request.getSession().getAttribute("t")).getTeaId();
%>		
		<jsp:include page="../../teacher/common/teacher_sidebar.jsp"></jsp:include>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="../../teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="my_topic_get#stu_sidebar">我的话题</a></li>
			<li id="no_note_head"><a href="../../teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
			<li id="mess_head"><a href="my_message_query#stu_sidebar">留言板</a></li>
		</ul>
<%
		}
%>

	
	<%--留言内容区 --%>	
		<div id="main_area">
<%
	
	PageElem<MessageView> pe = (PageElem<MessageView>)request.getAttribute("messages");
	List<MessageView> mv = pe.getPageElem();
%>
	<%--回复区 --%>
	<div id="reply_all">
		<form action="/wikestudy/dist/jsp/common/user/user_center/message_send" method="post">
			<input type="hidden" name="marster" value="<%=uid %>" />
			<input type="hidden" name="type" value="<%=ut %>" />
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
	<form action="/wikestudy/dist/jsp/common/user/user_center/message_send" method="post">
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
			<button type="button" class="delect" onclick="window.location.href='message_delete?messId=<%=mess.getMessId() %>'"></button>
			<button class="topic_reply" type="button" name="reply">回复</button>
				<div  class="hidden_textarea" >
					<textarea name="content"></textarea>
					<button class="topic_reply_sub" type="submit">回&nbsp;复</button>
				</div>
	</form>		
		<%--获取留言回复,迭代并输出回复的内容--%>
<%
		List<Message> rep = messV.getMessReply();
		for(Message mr : rep){
%>
			<%--输出留言内容 留言者头像  留言者姓名  留言内容  留言时间  回复按钮--%>
		<form action="/wikestudy/dist/jsp/common/user/user_center/message_send" method="post" class="second_borde">
			<input type="hidden" name="binding" value="<%=mess.getMessId() %>" />
			<input type="hidden" name="marster" value="<%=mr.getMessSenderId() %>" />
			<input type="hidden" name="type" value="<%=mr.isMessMasterMark() %>" />
			<a href="/wikestudy/dist/jsp/common/user_center/message_query?id=<%=mr.getMessSenderId() %>&type=<%=mr.isMessSenderMark() %>">
				<img src="<%=InputUtil.inputPhoto(mr.getPhoto(),mr.getMessSenderId(),mr.isMessSenderMark()) %>" alt="头像">
			</a>
			<div class="reply_main_main">
				<span class="person"><%=mr.getSender() %></span>
				<span class="time"><%=TimeToot.format(mr.getMessTime().getTime()) %></span>
				<p class="content"><%=mr.getMessContent() %></p>
			</div>
			<button type="button" class="delect" onclick="window.location.href='message_delete?messId=<%=mr.getMessId() %>'"></button>
			<button class="topic_reply" type="button" name="reply">回复</button>
			<div  class="hidden_textarea" >
				<textarea name="content"></textarea>
				<button class="topic_reply_sub" type="submit" name="reply">回复</button>
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
              <a href="my_message_query?currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="my_message_query?currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="my_message_query?currentPage=1" class="<%=selected1%>">1</a>
            <a href="my_message_query?currentPage=2" class="<%=selected2%>">2</a>
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
            <a href="my_message_query?currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
            <a href="my_message_query?currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="my_message_query?currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="my_message_query?currentPage=<%=cp+1%>" id="nextPage">.</a>
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
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script><script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script>
<script type="text/javascript">
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