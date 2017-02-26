<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.Article"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
    <link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
    <title>我的文章</title>
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
	<ul id="stu_sidebar">
		<li id="no_course_head"><a href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
		<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
		<li id="note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
		<li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
	</ul>
		
		
	<%--第二步：	文章展示区 --%>
	<div id="main_area">
		<div id="add_art">
			<button type="button" onclick="window.location.href='/wikestudy/dist/jsp/teacher/article/article_manage'">发布文章</button>
		</div>
		<div id="art_show_main">
 <%
 	PageElem<Article> pe = (PageElem<Article>)request.getAttribute("articles");
 	List<Article> articles = pe.getPageElem();
 	for(Article a : articles){
 %>
 	<div class="one_art">
 		<h3><a href="/wikestudy/dist/jsp/common/article/article_one?artid=<%=a.getArtId() %>"><%=a.getArtTitle() %></a></h3>
 		<span>发布时间：<%=a.getTime() %></span>
 		<span class="right"><%=a.getArtClick() %>人浏览过</span>
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
              <a href="/wikestudy/dist/jsp/teacher/article/my_article_query?currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="/wikestudy/dist/jsp/teacher/article/my_article_query?currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="/wikestudy/dist/jsp/teacher/article/my_article_query?currentPage=1" class="<%=selected1%>">1</a>
            <a href="/wikestudy/dist/jsp/teacher/article/my_article_query?currentPage=2" class="<%=selected2%>">2</a>
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
            <a href="/wikestudy/dist/jsp/teacher/article/my_article_query?currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
            <a href="/wikestudy/dist/jsp/teacher/article/my_article_query?currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="/wikestudy/dist/jsp/teacher/article/my_article_query?currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="/wikestudy/dist/jsp/teacher/article/my_article_query?currentPage=<%=cp+1%>" id="nextPage">.</a>
<%
    }
%>

	</div>
	</div>
	</div>
	</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<!--[if lt IE 9]>
  <script>
    function show(condition, message, time){
    if(condition){
      var target = document.body;
      if(!target){
        target = document.createElement('body');
        document.appendChild(target);
      }
      target.innerHTML = '<div id="popup" style="width: 100%;height: 2000px;position: fixed;top: 0;left: 0;background: rgba(0,0,0,0.4);background: black;"><div id="popup_area"style="position: absolute; width: 500px;padding: 40px;left: 50%;top: 50px;border-radius: 200px;border: 9px solid #EEE;margin-left: -290px;background: white;"><p>'+message+'</p></div></div>';

    }
  };

  show(true, '请下载最新版本的现代浏览器：譬如<a href="http://www.firefox.com.cn/download/">火狐浏览器</a>或<a href="http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html">谷歌浏览器</a>', 24*60*60*60*1000*60);
</script>
<![endif]-->
</body>
</html>