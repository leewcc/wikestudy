<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>我的课程</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
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
	<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
	<!-- <span id="here"></span> -->
		<ul id="stu_sidebar">
			<li id="course_head"><a href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
			<li id="no_note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
			<li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
<%--查看已经创建好的课程, 以课程列表的方式展示 --%>
<%--课程列表展示 --%>
<%--基本功能：课程列表展示(分页); 已发布课程, 未发布课程; --%>
<%--先从后台拿去数据 --%>
<%
	String type = request.getParameter("type") == null ? "1" : request.getParameter("type");
	PageElem<Course> pe = (PageElem<Course>)request.getAttribute("courses");
%>
	<div id="main_area" class="course_com">
		<div id="choic_head">
<%
	if(type.equals("1")){
%>
		<h3 id="sel_head">未发布课程</h3>
		<a id="no_sel_head" href="my_courses_query?type=2&page=1">已发布课程</a>
<%	
	}else{
%>
		<a id="no_sel_head" href="my_courses_query?type=1&page=1">未发布课程</a>
		<h3 id="sel_head">已发布课程</h3>
<%	
	}
%>
	</div>
	<div id="add_art" style="margin: 10px 20px;">
	<p class="illegal">${message }</p>
		<button type="button" onclick="window.location.href='../course/course_to_select_create'">添加课程</button>
	</div>
	<%--课程列表展示 --%>
	<div id="mana_cou">
		<ul>
<%
	if(type.equals("1")){
%>

<%
	}
	if(pe.getPageElem().size() == 0) {%>
			<p style="text-align:center;">当前未创建课程</p>
<%
	}else{ 
		for(Course cou: pe.getPageElem()) {%>
			<li class="one_cou">
				<a href="../course/course_manage?couId=<%=cou.getCouId()%>&type=${param.type }&page=${param.page}">
					<img class="course_img" src="<%=cou.getCouAllUrl() %>"/>
				</a>
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
         <a href="my_courses_query?type=<%=type %>&page=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
         <a href="my_courses_query?type=<%=type %>&page=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
         <a href="my_courses_query?type=<%=type %>&page=1" class="<%=selected1%>">1</a>
         <a href="my_courses_query?type=<%=type %>&page=2" class="<%=selected2%>">2</a>
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
         <a href="my_courses_query?type=<%=type %>&page=<%=i%>" class="<%=selected%>"><%=i%></a>
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
         <a href="my_courses_query?type=<%=type %>&page=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
         <a href="my_courses_query?type=<%=type %>&page=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
 		 <a href="my_courses_query?type=<%=type %>&page=<%=cp+1%>&type" id="nextPage">.</a>
<%
    }
%>
     </div>
    </div>  
	</div>
	</div>
	<jsp:include page="/dist/jsp/common/footer.jsp"/>
	<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
	
	<script type="text/javascript">
		function adjustHeight(){
			var a = document.getElementById('create_cou').getElementsByTagName('img')[0];
			var c = document.getElementById('create_cou').getElementsByTagName('a')[0];
			var b = getElementsByClassName(document,'one_cou');
			if(b[0].clientHeigh < a.clientHeight){
				for(var i = 0; i < b.length; i++){
					b[i].style.height = a.clientHeight +'px';
				}
			}else{
				a.style.height = b[0].clientHeight +'px';
				c.style.height = b[0].clientHeight +'px';
			}
		}
		adjustHeight();
	</script>

</body>
</html>