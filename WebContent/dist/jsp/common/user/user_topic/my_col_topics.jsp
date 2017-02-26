<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.ColTopic"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>我的关注</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
    <jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<%-- 修改 09/09By诗婷  --%>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
    <div id="wrapper">
<%--第一步：获取我的关注 --%>
<%
	PageElem<ColTopic> pe = (PageElem<ColTopic>)request.getAttribute("cols");
	List<ColTopic> cols = pe.getPageElem();
%>
        <jsp:include page="personal_topic.jsp">
            <jsp:param name="param_sidebar" value="collection"/>
        </jsp:include>
        <div id="my_ans">
            <div id="topic_area">
<%--第二步：迭代输出我的关注话题 --%>
<%
	if(cols.size() <= 0) {
%>
		        <p class="guide_message">当前没关注任何话题</p>
<% 
	}else {
	for(ColTopic c : cols){
%>
                <div class="one_topic">
                	<div class="writer">
                		<a href="/dist/jsp/common/user_center/user_topics_show?id=<%=c.getSenderId() %>&type=<%=c.isSenderType() %>"><img  class="person_img" src="<%=InputUtil.inputPhoto(c.getSenderPhoto(),c.getSenderId(),c.isSenderType()) %>" alt="发表者头像"/></a>
                		<p><%=c.getSenderName() %></p>
                	</div>
                	<div class="content_main">
                        <div>
                            <h3><a href="/wikestudy/dist/jsp/common/topic/topic_detail_get?topId=<%=c.getColTopicId() %>&currentPage=1#topic<%=c.getColTopicId() %>"><%=c.getTopTit() %></a></h3>
                            <br />
                        </div>
                		<a  class="look_num" href="/wikestudy/dist/jsp/common/topic/topic_detail_get?topId=<%=c.getColTopicId() %>&currentPage=1#topic<%=c.getColTopicId() %>"><span class="look">{<%=c.getAnswerNum() %>}</span></a>
                		<a  class="ans_num" href="/wikestudy/dist/jsp/common/topic/topic_detail_get?topId=<%=c.getColTopicId() %>&currentPage=1#topic<%=c.getColTopicId() %>"><span class="look">{<%=c.getReadNum() %>}</span></a>
                		<div style="width: 100%;">
                            <span class="time">发布时间：<%=TimeToot.format(c.getTopTime().getTime()) %></span>
                            <%-- 判断是否来自于课程，如果来自于课程则输出源自的课程，链接到对应的视频下 --%> 
                       		<button type="button" style="margin-right: 0;" onclick="window.location.href='attention_topic_show?topId=<%=c.getColTopicId() %>'">取消关注</button>        
                         </div>
            	   </div>
        	   </div>
<%
		}
	}
%>
            </div>
<%--第三步：输出分页数据 --%>
<%--输出分页数据 --%>
<%
	int cp = pe.getCurrentPage();
	int tp = pe.getTotalPage();              
%>
	       <div id="page">
<%  
    if(cp > 1){    //上一页
%>
                <a href="my_col_topics_get?currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";  //相应页面
%>
                <a href="my_col_topics_get?currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
                <a href="my_col_topics_get?currentPage=1" class="<%=selected1%>">1</a>
                <a href="my_col_topics_get?currentPage=2" class="<%=selected2%>">2</a>
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
                <a href="my_col_topics_get?currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
                <a href="my_col_topics_get?currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
                <a href="my_col_topics_get?currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
                <a href="my_col_topics_get?currentPage=<%=cp+1%>" id="nextPage">.</a>
<%  }  %>			
			</div>
  	    </div>
   </div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>