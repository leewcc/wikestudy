<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page import="com.wikestudy.model.util.TimeToot"%>
<%@ page import="com.wikestudy.model.pojo.Topic"%>
<%@ page import="com.wikestudy.service.manager.TopicManagerService"%>
<%@ page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page import="sun.nio.cs.ext.ISCII91"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="com.wikestudy.model.pojo.Label"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>在线话题</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
    <jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<%
    int labId = 0;
    String labName = "";
    int type = request.getParameter("type") == null ? 1 : Integer.parseInt(request.getParameter("type"));//1-热门 2-最新
    Label l = (Label)request.getAttribute("label");
    if(l != null){
    	labId = l.getLabId();
    	labName = l.getLabName();
    }
%>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>  
<div class="outside">
	<div class="wrapper clear_float">
		<div id="topic_headA">
			<div id="topic_headB">
				<div id="topic_headC"></div>
    			<h1 id="topic_headD">在线交流</h1>
    		</div>
    	</div>
    	<jsp:include page="../user/user_topic/personal_topic.jsp"></jsp:include>
        <%-- 列表显示该类型下的话题 --%>
        <div id="topiclist">
            <div id="topic_nav">
                <ul>
<%
    String type1 = (type==1)?"selected":"";
    String type2 = (type==2)?"selected":"";
%>
                    <li><a class="<%=type1%>" href="topic_list_get?labId=<%=labId %>&currentPage=1&type=1">热门话题</a></li>
                    <li><a class="<%=type2%>" href="topic_list_get?labId=<%=labId %>&currentPage=1&type=2">最新话题</a></li>
                </ul>
                <a class="add_topic" href="/wikestudy/dist/jsp/common/user/user_topic/topic_add">发布话题<span>+</span></a>
            </div>
            <div id="list_content">
<%
    PageElem<Topic> pe = (PageElem<Topic>)request.getAttribute("topics");
    List<Topic> topics = pe.getPageElem();
    Iterator<Topic> tit = topics.iterator(); 
    Topic t = null;
    int cp = pe.getCurrentPage();
    int tp = pe.getTotalPage();
    if(topics.size() <= 0){
 %>
 		        <p class="guide_message">当前没有话题</p>
 <% 
    }else{
  
    while(tit.hasNext()){
        t = tit.next();
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
        				<h3><a href="topic_detail_get?topId=<%=t.getTopId() %>"><%=t.getTopTit() %><%=t.isTopIsUp()? "<span class='hot'>&nbsp;hot</span>" : "" %></a></h3>
        	            <div>
        	               <br />
        	               
        	                <div>
        	                    <span class="time">发布时间：<%=TimeToot.format(t.getTopTime().getTime()) %></span>
        	                    <span class="labels">
        	                    <a href="topic_list_get?labId=<%=t.getLabId() %>&currentPage=1">所属标签：<%=t.getLabelName() %></a></span>
        	                    <%-- 判断是否来自于课程，如果来自于课程则输出源自的课程，链接到对应的视频下 --%>         
        	                </div>
        	            </div>
        	    		<a  class="look_num" href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=1"><span class="look">{<%=t.getTopReadNum() %>}</span></a>
        	    		<a  class="ans_num" href="topic_detail_get?topId=<%=t.getTopId() %>&currentPage=1"><span class="look">{<%=t.getTopAnsNum() %>}</span></a>
        		   </div>
        	   </div>
<%  
		} 
    } 
 %>
<%--在测试分页中 --%>
                <div id="page">
<%  
    if(cp > 1){    
%>
                    <a href="topic_list_get?labId=<%=labId %>&currentPage=<%=cp-1%>&type=<%=type %>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
                    <a href="topic_list_get?labId=<%=labId %>&currentPage=<%=i%>&type=<%=type %>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
                    <a href="topic_list_get?labId=<%=labId %>&currentPage=1&type=<%=type %>" class="<%=selected1%>">1</a>
                    <a href="topic_list_get?labId=<%=labId %>&currentPage=2&type=<%=type %>" class="<%=selected2%>">2</a>
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
                    <a href="topic_list_get?labId=<%=labId %>&currentPage=<%=i%>&type=<%=type %>" class="<%=selected%>"><%=i%></a>
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
                    <a href="topic_list_get?labId=<%=labId %>&currentPage=<%=tp-1%>&type=<%=type %>" class="<%=selectedt1%>"><%=tp-1%></a>
                    <a href="topic_list_get?labId=<%=labId %>&currentPage=<%=tp%>&type=<%=type %>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
                    <a href="topic_list_get?labId=<%=labId %>&currentPage=<%=cp+1%>&type=<%=type %>" id="nextPage">.</a>
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
</body>
</html>