<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.List, com.wikestudy.model.pojo.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>选课</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<% 
	List<Label> labelList = (List<Label>)request.getAttribute("labelList");
	PageElem<Course> peCourse = (PageElem<Course>) request.getAttribute("peCourse");
	String grade = (String) request.getAttribute("grade");
	String labelId = (String) request.getAttribute("labelId");
	String type = (String) request.getAttribute("type");

	String grade0 = (grade.equals("0"))?"class='selected'":"";
	String grade1 = (grade.equals("1"))?"class='selected'":"";
	String grade2 = (grade.equals("2"))?"class='selected'":"";
	String grade3 = (grade.equals("3"))?"class='selected'":"";
	String grade4 = (grade.equals("4"))?"class='selected'":"";
%>
<jsp:include page="nav.jsp"></jsp:include>
<%--大框架 --%>
<div class="outside">
	<%-- 课程筛选div --%>
	<div class="wrapper">
		<div id="course_headA">
	      	<div id="course_headD">
	        	<div id="course_headC"></div>
	        	<h1 id="course_headB">选&nbsp;&nbsp;&nbsp;课</h1>
	      	</div>
	    </div>
		<div id="course_label">
			<%-- 年级结束 --%>
			<%-- 科目 --%>
			<div id="course_type">
				<span>科目：</span>
				<div>
					<a href="course_show?grade=<%=grade %>&page=1&labelId=0&type=<%=type %>" <%=labelId.equals("0")?"class='selected'":"" %> >全部</a>
<%
	for(Label label:labelList) {
%>
					<a href="course_show?grade=<%=grade %>&page=1&labelId=<%=label.getLabId() %>&type=<%=type %>" <%=labelId.equals(String.valueOf(label.getLabId()))?"class='selected'":"" %>><%=label.getLabName() %></a>
					<%} %>
				</div>
			<%-- 科目结束 --%>
			</div>
			<div id="course_grade">
				<span>年级：</span>
				<div>
					<a href="course_show?grade=0&page=1&labelId=<%= labelId %>&type=<%=type%>" <%=grade0%>>全部</a>
			        <a href="course_show?grade=1&page=1&labelId=<%= labelId %>&type=<%=type%>" <%=grade1%>>初一</a>
			        <a href="course_show?grade=2&page=1&labelId=<%= labelId %>&type=<%=type%>" <%=grade2%>>初二</a>
			        <a href="course_show?grade=3&page=1&labelId=<%= labelId %>&type=<%=type%>" <%=grade3%>>初三</a>
				</div>
			</div>
		</div>
		<%--课程筛选结束 --%>
		<%--热门， 最新 --%>
		<div id="select_sort">
			<a href="course_show?grade=<%=grade %>&page=1&labelId=<%=labelId %>&type=3" <%=type.equals("3")?"id='selected'":""%> >最新</a>
			<a href="course_show?grade=<%=grade %>&page=1&labelId=<%=labelId %>&type=2" <%=type.equals("2")?"id='selected'":""%> >热门</a>
		</div>
		<%--  热门， 最新标签结束 --%>
			
		<%-- Course --%>
		<div id="course_com">
<%
	int i = 1;
	for(Course cou: peCourse.getPageElem()) {
	String no_mr = (i++%4==0)?"no_mr":"";
%>
			<div class="one_course <%=no_mr%>">
				<div><a href="course_select?couId=<%=cou.getCouId() %>"><img class="course_img" src="<%=cou.getCouAllUrl() %>"/></a></div>
				<h3><%=cou.getCouName() %></h3>
				<div>
					<p><%=cou.getCouBrief() %></p>
					<div class="clear" style="text-align:left;">
						<span class="lfloat"><%=cou.getGrade() %></span>
						<span><%=cou.getCouStudyNum() %>人学习</span>
					</div>
				</div>
			</div>
<%} %>
		</div>

		<div id="page">
<%  
	int cp=(Integer)request.getAttribute("cp"); //当前页码
	int tp=(Integer) request.getAttribute("tp");//总页码
	String selected = "";
    if(cp > 1){    
%>
			<a href="course_show?grade=<%=grade %>&type=<%=type%>&cp=<%=cp>tp? 1:cp-1 %>&labelId=<%=labelId %>" id="previousPage" >.</a>
<%  }
    i = 1;
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
        	selected = (i == cp)?"selected":"";
%>
		<a href="course_show?grade=<%=grade %>&type=<%=type%>&cp=<%=i%>&labelId=<%=labelId %>" class="<%=selected%>" ><%=i %></a> 
<%
        }
    }else{ //分页大于10的情况
 			String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="course_show?grade=<%=grade %>&type=<%=type%>&cp=1&labelId=<%=labelId %>" class="<%=selected1%>">1</a>
            <a href="course_show?grade=<%=grade %>&type=<%=type%>&cp=2&labelId=<%=labelId %>" class="<%=selected2%>">2</a>
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
            <a href="course_show?grade=<%=grade %>&type=<%=type%>&cp=<%=i%>&labelId=<%=labelId %>" class="<%=selected%>"><%=i%></a>
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
            <a href="course_show?grade=<%=grade %>&type=<%=type%>&cp=<%=tp-1%>&labelId=<%=labelId %>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="course_show?grade=<%=grade %>&type=<%=type%>&cp=<%=tp%>&labelId=<%=labelId %>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
		 <a href="course_show?grade=<%=grade %>&type=<%=type%>&cp=<%=cp>=tp? tp:cp+1%>&labelId=<%=labelId %>" id="nextPage">.</a>
<%
    }
%>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body> 
</html>