<%@page import="com.wikestudy.model.util.TimeToot"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page import="com.wikestudy.model.pojo.Topic"%>
<%@page import="com.wikestudy.model.pojo.Label"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>查看话题</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
	<%--该页面主要分为搜索栏   与话题展示区 --%>
	
	<%--第一部分 搜素栏 --%>
<%
	List<Label> labels = (List<Label>)request.getAttribute("labels");
	int labId = request.getParameter("labId") == null ? 0 : Integer.parseInt(request.getParameter("labId"));

%>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
<div id="wrapper">
	<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_top">话题管理</span>
			<span id="sec_head">查看话题</span>
		</div>
		
		<form id="mana_lab" action="topic_manage" method="post">
			<fieldset>
			<legend>搜索标签相关话题</legend>
			<label>标签：
				<select id="labId" name="labId">
					<option value="0">所有</option>
<%
			for(Label label : labels){
%>
					<option value="<%=label.getLabId()%>"  <%if(labId == label.getLabId()){ %> selected="selected"  <%} %>>
					<%=label.getLabName() %>
					</option>
<%
			}
%>
				</select></label>
				<button id="search" type="submit"></button>
				</fieldset>
		</form>
	<div id="mana_con">
	<%--第二部分 话题列表展示区 --%>
<%
	PageElem<Topic> pe = (PageElem<Topic>)request.getAttribute("topics");
	int cp = pe.getCurrentPage();
	int tp = pe.getTotalPage();
	List<Topic> topics = pe.getPageElem();
%>	
		<p class="illegal">${message }</p>
		<table id="see_topic">
			<tr>
				<th>话题名</th>
				<th>发布人</th>
				<th>浏览量</th>
				<th>回复量</th>
				<th>发布时间</th>
				<th></th>
				<th></th>
			</tr>
<%
			for(Topic topic : topics){
				String up = topic.isTopIsUp()? "取消置顶" : "置顶";
%>
			<tr>
				<td><a target="_blank" href="/wikestudy/dist/jsp/common/topic/topic_detail_get?topId=<%=topic.getTopId() %>&currentPage=1">
				<%=topic.getTopTit() %></a></td>
				<td><%=topic.getUserName() %></td>
				<td><%=topic.getTopReadNum() %></td>
				<td><%=topic.getTopAnsNum() %></td>
				<td><%=TimeToot.format(topic.getTopTime().getTime()) %></td>
				<td><button type="button" name="setUp" value="<%=up %>" 
				onclick="window.location.href='topic_setup?topId=<%=topic.getTopId()%>&topIsUp=<%=!topic.isTopIsUp()%>&labId=${param.labId }&currentPage=${param.currentPage }'" class="<%=topic.isTopIsUp()?"up":"cancel"%>"></button></td>
				<td><button type="button" class="delect" onclick="window.location.href='topic_delete?topId=<%=topic.getTopId()%>&labId=${param.labId }&currentPage=${param.currentPage }'"></button></td> 
			</tr>
<%
			}
%>
		</table>
	
	<%--分页数据 --%>
<div id="page">
<%  
    if(cp > 1){    
%>
              <a href="topic_manage?labId=<%=labId%>&currentPage=<%=cp-1 %> " id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="topic_manage?labId=<%=labId%>&currentPage=<%=i %>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="topic_manage?labId=<%=labId%>&currentPage=1" class="<%=selected1%>">1</a>
            <a href="topic_manage?labId=<%=labId%>&currentPage=2" class="<%=selected2%>">2</a>
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
            <a href="topic_manage?labId=<%=labId%>&currentPage=<%=i %>" class="<%=selected%>"><%=i%></a>
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
            <a href="topic_manage?labId=<%=labId%>&currentPage=<%=tp-1 %>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="topic_manage?labId=<%=labId%>&currentPage=<%=tp %>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="topic_manage?labId=<%=labId%>&currentPage=<%=cp+1 %>" id="nextPage">.</a>
<%
    }
%>
	        </div>	
		</div>
	</div>
</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>	
</body>
</html>