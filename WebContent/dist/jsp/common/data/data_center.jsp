<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.Label"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<title>资料中心</title>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"/>
<div class="outside">
	<div class="wrapper">
	<%--主题分为两部分 类型区  课程展示区 --%>
		<div id="data_headA">
			<div id="data_headB">
				<div id="data_headC"></div>
				<h1 id="data_headD">资料中心</h1>
			</div>
		</div>
	<%--第一部分:类型区--%>
<%
		int lid = request.getParameter("labId") == null ? 0 :Integer.parseInt(request.getParameter("labId"));
		String grade = request.getParameter("grade") == null ? "0" : request.getParameter("grade");
		boolean sort = request.getParameter("sort") == null? false : Boolean.parseBoolean(request.getParameter("sort"));
		List<Label> labels = (List<Label>)request.getAttribute("labels");
		Iterator<Label> labelIt = labels.iterator();

		String grade0 = (grade.equals("0"))?"selected":"";
		String grade1 = (grade.equals("1"))?"selected":"";
		String grade2 = (grade.equals("2"))?"selected":"";
		String grade3 = (grade.equals("3"))?"selected":"";
		String grade4 = (grade.equals("4"))?"selected":"";
%>
		<%--此处是输出标签分类 --%>
		<div id="data_lable">
			<div id="data_type">
				<span>分类：</span>
				<div>
					<a href="data_center?labId=0%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=1"  <%=lid==0?"class='selected'":"" %>>所有</a>
<%
		String selected = "";
		while(labelIt.hasNext()){
			Label label = labelIt.next();	
			selected = label.getLabId() == lid ?"class='selected'":"";		
%>		
			<a href="data_center?labId=<%=label.getLabId()%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=1" <%=selected%>><%=label.getLabName() %></a>			
<%}%>		
				</div>
			</div>
			
			<%--此处是输出课程类型 --%>
			<div id="data_grade">
				<span>年级：</span>
				<div>
					<a href="data_center?labId=<%=lid%>&grade=<%=0 %>&sort=<%=sort %>&currentPage=1" class="<%=grade0%>">所有</a>
					<a href="data_center?labId=<%=lid%>&grade=<%=1 %>&sort=<%=sort %>&currentPage=1" class="<%=grade1%>">初一</a>
					<a href="data_center?labId=<%=lid%>&grade=<%=2 %>&sort=<%=sort %>&currentPage=1" class="<%=grade2%>">初二</a>
					<a href="data_center?labId=<%=lid%>&grade=<%=3 %>&sort=<%=sort %>&currentPage=1" class="<%=grade3%>">初三</a>
				</div>
			</div>
		</div>
		<div id="select_sort">
<%
		if(sort){
%>
			<a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=true %>&currentPage=1" class="selected">最新</a>
			<a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=false %>&currentPage=1" >热门</a>
<%
	}else if(!sort){
%>	
			<a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=true %>&currentPage=1" >最新</a>
			<a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=false %>&currentPage=1" class="selected">热门</a>
<%
	}else{
%>
			<a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=true %>&currentPage=1">最新</a>
			<a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=false %>&currentPage=1">热门</a>
<% 
	}
%>
		</div>
		
		<%--第二部分 课程展示区 --%>
		<div id="course_com">
<%
		int z = 0;
		PageElem<Course> pe = (PageElem<Course>)request.getAttribute("courses");
		List<Course> courses = pe.getPageElem();
		Iterator<Course> it = courses.iterator();
		String no_pr = "";
		while(it.hasNext()){
			z++;
			if(z%4 == 0)  no_pr = "no_mr";
			else no_pr = "";
			Course c = it.next();
%>
		<%--此处输出课程的内容 --%>
		<%String url = "data_get?courseId=" + c.getCouId();  %>
			<div class="one_course <%=no_pr%>">
				<a href="<%=url %>">
					<img class="course_img" src="<%=c.getCouAllUrl() %>" alt="课程封面" />
				</a>
				<h3><%=c.getCouName() %></h3>
			</div>
	<% } %>	
		
		
		<%--此处是分页信息 --%>
		<div id="page">
<%
		int cp = pe.getCurrentPage();
		int tp = pe.getTotalPage();
        if(cp > 1){                 
%>
            <a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=<%=cp-1 %>"  id="previousPage">.</a>
<%  }
    int i = 1;
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
        	selected = (i == cp)?"selected":"";
%>
			<a  href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=<%=i %>" class="<%=selected%>"><%=i %></a> 
<%
        }
    }else{ //分页大于10的情况
 			String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=1" class="<%=selected1%>">1</a>
            <a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=2" class="<%=selected2%>">2</a>
<%
            i = cp - 2;
            if(i > 3){
%>
            <span>...</span>
<%
    }else{
    	i = 3;
    }
    for(; i <= cp + 2 && i < tp - 1; i++){
        selected = (i == cp)?"selected":"";
%>
            <a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=<%=i%>" class="<%=selected%>"><%= i%></a>
<%
    }
    if(i < tp - 1){
%>
            <span>...</span>
<%
    }
    String selectedt1 = (cp == tp-1)?"selected": "";
    String selectedt2 = (tp == cp)?"selected": "";
%>
            <a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=<%=cp-1 %>" class="<%=selectedt1%>"><%= tp - 1%></a>
            <a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=<%=cp-1 %>" class="<%=selectedt2%>"><%= tp%></a>
<%        
    }
    if(cp < tp){
%>
		 	<a href="data_center?labId=<%=lid%>&grade=<%=grade %>&sort=<%=sort %>&currentPage=<%=cp>=tp? tp:cp+1%>" id="nextPage">.</a>
<%
    }
%>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>