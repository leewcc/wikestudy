<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.Topic"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div class="wrapper clear_float">
		<div id="topic_headA">
			<div id="topic_headB">
				<div id="topic_headC"></div>
				<h1 id="topic_headD">在线交流</h1>
			</div>
		</div>
        <jsp:include page="/dist/jsp/common/user/user_topic/personal_topic.jsp"></jsp:include>
        <div id="topiclist">
            <div id="list_content">
	<%--第一步：	获取搜索的话题 --%>
<%
	PageElem<Topic> pe = (PageElem<Topic>)request.getAttribute("topics");
	List<Topic> topics = pe.getPageElem();
	int num = pe.getRows();
%>
	            <h2>一共找到&nbsp;<%=num %>&nbsp;个结果</h2>

	<%--第二步：	迭代搜索的话题，输出话题信息 --%>
<%
	for(Topic t : topics){
%>
                <div class="one_topic">
                    <div class="writer">
                		<p>
                			<a href="/wikestudy/dist/jsp/common/user_center/user_topics_show?id=<%=t.getTopUserId()%>&type=<%=t.isTopUserEnum() %>">
                				<img  class="person_img" src="<%=InputUtil.inputPhoto(t.getPhoto(),t.getTopUserId(), t.isTopUserEnum()) %>">
                				<%=t.getUserName() %>
                			</a>
                		</p>
                	</div>
	                <div class="content_main">
                        <div>
                    		<h3><a href="topic_detail_get?topId=<%=t.getTopId() %>"><%=t.getTopTit() %></a></h3>
                    		<br/>
                    		<div>
                            	<span class="time">发布时间：<%=TimeToot.format(t.getTopTime().getTime()) %></span>
                              	<span class="labels">
                                <a href="topic_list_get?labId=<%=t.getLabId() %>&currentPage=1"><%=t.getLabelName() %></a></span>
                     		</div>
      
<%
			if(t.getSec() != null){
%>
			            <a href="#">源自：<%=t.getSec() %></a>
<%
			}
%>
	                </div>
                 	<a  class="look_num" href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=1"><span class="look">{<%=t.getTopReadNum() %>}</span></a>
            		<a  class="ans_num" href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=1"><span class="look">{<%=t.getTopAnsNum() %>}</span></a>
         		</div>
           </div>
		<%  }  %>



	<%--第三步：	输入分页数据 --%>
<%
	String key = request.getParameter("content");
	int cp = pe.getCurrentPage();
	int tp = pe.getTotalPage();
%>

            <div id="page">
<%  
    if(cp > 1){    
%>
                <a href="topic_query_by_key?content=<%=key %>&currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
                <a href="topic_query_by_key?content=<%=key %>&currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
                <a href="topic_query_by_key?content=<%=key %>&currentPage=1" class="<%=selected1%>">1</a>
                <a href="topic_query_by_key?content=<%=key %>&currentPage=2" class="<%=selected2%>">2</a>
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
                <a href="topic_query_by_key?content=<%=key %>&currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
                <a href="topic_query_by_key?content=<%=key %>&currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
                <a href="topic_query_by_key?content=<%=key %>&currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
                <a href="topic_query_by_key?content=<%=key %>&currentPage=<%=cp+1%>" id="nextPage">.</a>
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