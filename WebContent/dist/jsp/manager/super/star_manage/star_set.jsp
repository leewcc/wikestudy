<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.Record"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.Star"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>一周之星</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
	<%--分为两部分 显示已设置的一周之星 未设置的学生列表 --%>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
	<div id="wrapper">
		<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
		<div id="column">
			<div id="col_head">
				<span id="fir_head_per">人员管理</span>
				<span id="sec_head">一周之星</span>
			</div>
	<%--第一部分 已设置的一周之星 --%>
	<div id="star_selected">
	<form action="star_delete" method="post">
<%
	List<Star> stars = (List<Star>)request.getAttribute("stars");
	int count = 0;
	if(stars == null){
%>
		<p style="text-align: center;">当前没有任何被选中的学生</p>
<%
	}else{
		count = stars.size();
%>
		<p>你选择了他们作为一周之星,一共选择了<%=count %>个学生，你还能选择<%=10-count %>个学生。</p>
				<div id="star_main">
<%
		for(Star star : stars){
%>
					<div class="one_stu">
						
						<img src="<%=star.getStuPortraitUrl() %>" /> 
	<!-- 					<img src="/wikestudy/dist/images/common/all.png"/> -->
						<input type="checkbox" name="starId" value="<%=star.getStarId() %>"/>
						<label><%=star.getStuName() %></label>
					</div>
<%
		}
	}
%>
			</div>
			<p class="illegal">${message }</p>
			
			<button type="submit" name="delete">删&nbsp;除</button>
			<button type="button" onclick="window.location.href='star_confirm'">确认</button>
		
		</form>
		</div>
	
	<%--第二部分 学生列表 --%>
		<div id="star_no_selected">
		<h2>请选择学生</h2>
			<form action="star_set" method="post">
				<table>
					<thead>
						<tr>
							<th id="stu_name">姓名</th>
							<th id="stu_time">学习时长</th>
							<th id="stu_topic">讨论次数</th>
							<th ></th>
						</tr>
					</thead>
					<tbody>
<%
	PageElem<Record> pe = (PageElem<Record>)request.getAttribute("students");
	List<Record> students = pe.getPageElem();
	for(Record s : students){
%>	
						<tr>
							<td><%=s.getName() %></td>
							<td><%=InputUtil.inputTime(s.getStudyTime()) %></td>
							<td><%=s.getDisNum() %></td>
							<td class="stu_choic"><input type="checkbox" name="stuId" value="<%=s.getUserId()%>"></td>
						</tr>
<%
	}
	int cp = pe.getCurrentPage();
	int tp = pe.getTotalPage();
%>
					</tbody>
				</table>
				<input type="hidden" name="currentPage" value="<%=cp %>" />
				<button id="set_top" type="submit" style="display: block;">设&nbsp;置</button>
			</form>	

		<div id="page">	
		<%--分页数据 --%>
<%
		
        if(cp > 1){                 
%>
            <a href="star_see?currentPage=<%=cp-1%>"  id="previousPage">.</a>
<%  
	}
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="star_see?currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="star_see?currentPage=1" class="<%=selected1%>">1</a>
            <a href="star_see?currentPage=2" class="<%=selected2%>">2</a>
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
            <a href="star_see?currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
            <a href="star_see?currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="star_see?currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
        if(cp < tp){
%>
            <a href="star_see?currentPage=<%=cp+1%>"  id="nextPage">.</a>
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