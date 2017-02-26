<%@page import="com.wikestudy.model.pojo.ArticleType"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page import="com.wikestudy.model.pojo.Article"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>展示文章</title>
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
	
    <div id="art_com">
    	<div id="art_type">
<% 
		/*文章类型*/
	String selected = "";
	String aTypeIdString = request.getParameter("aTypeId");
	if(null == aTypeIdString) {
		aTypeIdString="1";
	}
	int aTypeId = Integer.parseInt(aTypeIdString);
	List<ArticleType> at = (ArrayList<ArticleType>)request.getAttribute("at");
	for(ArticleType a:at) {
		selected = (aTypeId ==a.getATypeId())?"selected":""; 
%>	
			<a href="article_show?cp=1&aTypeId=<%=a.getATypeId() %>" class="<%=selected %>"><%=a.getATypeName() %></a>
<%}%>
			<form action="article_search" method="post">
				<input id="art_search" type="text" name="artSearch" placeholder="搜索文章" value="搜索文章"/>
				<button type="submit"></button>
			</form>
		</div>
		<div id="art_main">
<%
	//文章的标题
	PageElem a=(PageElem) request.getAttribute("a");
	int k = 0;	
	List <Article> alist=(ArrayList)a.getPageElem();
	if(alist.size() > 0){
	for(Article ati:alist) {
%>	
			<div class='oneArticle <%=(k++)%2 == 0?"left":"right"%>'>
				<h4>
					<a href="article_one?artid=<%=ati.getArtId() %>"><%=ati.getArtTitle() %></a>
				</h4>
				<span class="time left">发布时间：<%=ati.getTime() %></span>
				<span class="look right">点击量:<%=ati.getArtClick() %></span>
			</div>
<%}}else{%>
			<p class="guide_message">当前没有文章。</p>
<%}%>
		</div>
		<%--显示页码 --%>
		<div id="page">
<%  
	
	int cp=(Integer)request.getAttribute("cp");
	int tp=(Integer) request.getAttribute("tp");
    if(cp > 1){    
%>
				<a href="article_show?cp=<%=cp>tp? 1:cp-1 %>&aTypeId=<%=aTypeId %>" id="previousPage" >.</a>
<%  }
    int i = 1;
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
        	selected = (i == cp)?"selected":"";
%>
				<a href="article_show?cp=<%=i%>&aTypeId=<%=aTypeId %>" class="<%=selected%>" ><%=i %></a> 
<%
        }
    }else{ //分页大于10的情况
 			String selected1 = (1 == cp)?"selected": "";
            String selected2 = (2 == cp)?"selected": "";
%>
	            <a href="article_show?cp=1&aTypeId=<%=aTypeId %>" class="<%=selected1%>">1</a>
	            <a href="article_show?cp=2&aTypeId=<%=aTypeId %>" class="<%=selected2%>">2</a>
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
            	<a href="article_show?cp=<%=i%>&aTypeId=<%=aTypeId %>" class="<%=selected%>"><%=i%></a>
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
	            <a href="article_show?cp=<%=tp-1%>&aTypeId=<%=aTypeId %>" class="<%=selectedt1%>"><%=tp-1%></a>
	            <a href="article_show?cp=<%=tp%>&aTypeId=<%=aTypeId %>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
	 		<a href="article_show?cp=<%=cp>=tp? tp:cp+1%>&aTypeId=<%=aTypeId %>" id="nextPage">.</a>
<%
    }
%>
		</div>
	</div>
</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>