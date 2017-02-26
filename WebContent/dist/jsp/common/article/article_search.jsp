<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page import="com.wikestudy.model.pojo.Article"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>搜索结果</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">	
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<div id="art_heading_a">
      		<div id="art_heading_d">
       		 	<div id="art_heading_c"></div>
        		<h1 id="art_heading_b">家校互动</h1>
     	 	</div>
   	 	</div>
	</div>
</div>
<div class="outside">
	<div class="wrapper" id="search_art">
	
<%--显示返回的内容 --%>
<%
		PageElem<Article> pe=(PageElem<Article>)request.getAttribute("search");
%>
		<h2>一共找到&nbsp;<%=pe.getRows() %>&nbsp;个结果</h2>
<%
		int tp = pe.getTotalPage();//获得总页数
		int cp = pe.getCurrentPage();//获得当前页数
		for(Article a: pe.getPageElem()) {
%>
		<div class="one_art">
			<a href="article_one?artid=<%=a.getArtId() %>">
			<h3><%=a.getArtTitle() %></h3></a>
			<span class="time"><%=a.getTime() %></span>
			<span class="look_num"><%=a.getArtClick() %></span>
		</div>
<%}%>
		<%--页数在此 --%>
	    <div id="page">
<%  
	String s=(String)request.getAttribute("artSearch");
    if(cp > 1){    
%>
	        <a href="article_search?cp=<%=cp-1%>&artSearch=<%=s %>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"class='selected'": "";
%>
	        <a href="article_search?cp=<%=i%>&artSearch=<%=s %>" <%=selected%>><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"class='selected'": "";
            String selected2  = (2== cp)?"class='selected'": "";
%>
	        <a href="article_search?cp=1&artSearch=<%=s %>" <%=selected1%>>1</a>
	        <a href="article_search?cp=2&artSearch=<%=s %>" <%=selected2%>>2</a>
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
        selected = (i == cp)?"class='selected'": "";
%>
	        <a href="article_search?cp=<%=i%>&artSearch=<%=s %>" <%=selected%>><%=i%></a>
<%
    }
    if(i < tp - 1){
%>
	        <span>...</span>
<%
    }
    String selectedt1 = (cp== tp-1)?"class='selected'": "";
    String selectedt2 = (tp== cp)?"class='selected'": "";
%>
	        <a href="article_search?cp=<%=tp-1%>&artSearch=<%=s %>" <%=selectedt1%>><%=tp-1%></a>
	        <a href="article_search?cp=<%=tp%>&artSearch=<%=s %>" <%=selectedt2%>><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
	 		<a href="article_search?cp=<%=cp>=tp? tp:cp+1 %>&artSearch=<%=s %>" id="nextPage">.</a>
<%
    }
%>
	    </div>
	    <form action="article_search" method="post">
		    <fieldset>
		    	<legend>搜索文章</legend>
				<lable>文章关键字<input id="art_search" type="text" name="artSearch" placeholder="搜索文章" value=""/></lable>
				<button type="submit">搜&nbsp;索</button>
			</fieldset>
		</form>
     </div>
 </div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
</body>
</html>