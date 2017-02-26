<%@page import="sun.print.PeekGraphics"%>
<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.service.publicpart.CourseService"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@page import="com.wikestudy.model.pojo.Label"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>课程管理</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
<div id="wrapper">
	<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_cou">微课管理</span>
			<span id="sec_head">课程管理</span>
		</div>

<%
	String type = request.getParameter("type") == null ? "0" : request.getParameter("type");
	int lid = 0;
	try {
		lid = request.getParameter("label") == null ? 0 : Integer.parseInt(request.getParameter("label"));
	} catch (Exception e) { }
	
%>
	<%--第一部分：搜索区 --%>
		<form action="course_query_by_laybel_type" method="post" id="mana_lab">
			<fieldset>
				<legend>搜索标签相关课程</legend>
				<label>标签：
					<select id="label" name="label">
						<option value="0" <%=0 == lid? "selected" : "" %>>所有</option>
<%
	List<Label> labels = (List<Label>)request.getAttribute("labels");
	for(Label l : labels){
%>
						<option value="<%=l.getLabId() %>" <%=l.getLabId() == lid? "selected" : "" %>><%=l.getLabName() %></option>
<%
	}
%>
					</select>
				</label>
	
				<label>类型：
					<select id="type" name="type">
						<option value="0" <%="0".equals(type)? "selected" : "" %>>所有</option>
						<option value="1" <%="1".equals(type)? "selected" : "" %>>初一</option>
						<option value="2" <%="2".equals(type)? "selected" : "" %>>初二</option>
						<option value="3" <%="3".equals(type)? "selected" : "" %>>初三</option>
					</select>
				</label>
	
				<button id="search" onclick="search()"></button>
			</fieldset>
		</form>
	
		<div id="mana_con">
<% 
	PageElem<Course> pe = (PageElem<Course>)request.getAttribute("courses");
	List<Course> courses = pe.getPageElem();
	if(pe.getRows() >0){
%>
	<%--第二部分：课程展示区 --%>
			<p class="illegal">${message}</p>
			<table id="see_topic">
				<tr>
					<th>微课名</th>
					<th>微课关注度</th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
		
<%

	for(Course c : courses){
%>
				<tr>
					<td><%=c.getCouName() %></td>
					<td><%=c.getCouStudyNum() %>人</td>
					<td><input type="button" value="<%=c.isCouRelease()? "取消" : "发布" %>" onclick="window.location.href='/wikestudy/dist/jsp/manager/course/course_release?course=<%=c.getCouId() %>&label=${param.label }&currentPage=${param.currentPage }&type=${param.type}'"/></td>
					<td><input type="button" value="<%=c.isRecomment()? "取消" : "推荐" %>" onclick="window.location.href='/wikestudy/dist/jsp/manager/course/course_recomment?course=<%=c.getCouId() %>&label=${param.label }&currentPage=${param.currentPage }&type=${param.type}'"/></td>
					<td><button class="delete" type="button" onclick="window.location.href='course_delete?course=<%=c.getCouId() %>&label=${param.label }&currentPage=${param.currentPage }&type=${param.type}'"></button></td>
				</tr>
<%
	}
%>
			</table>

	<%--输出分页数据 --%>
<%
		int cp = pe.getCurrentPage();
		int tp = pe.getTotalPage();              
%>
	       <div id="page">
<%  
    if(cp > 1){    
%>
           	<a href="course_query_by_laybel_type?type=<%=type %>&label=<%=lid %>&currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  }
    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="course_query_by_laybel_type?type=<%=type %>&label=<%=lid %>&currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="course_query_by_laybel_type?type=<%=type %>&label=<%=lid %>&currentPage=1" class="<%=selected1%>">1</a>
            <a href="course_query_by_laybel_type?type=<%=type %>&label=<%=lid %>&currentPage=2" class="<%=selected2%>">2</a>
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
            <a href="course_query_by_laybel_type?type=<%=type %>&label=<%=lid %>&currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
            <a href="course_query_by_laybel_type?type=<%=type %>&label=<%=lid %>&currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="course_query_by_laybel_type?type=<%=type %>&label=<%=lid %>&currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="course_query_by_laybel_type?type=<%=type %>&label=<%=lid %>&currentPage=<%=cp+1%>" id="nextPage">.</a>
<%
    }
%>
<%
	}else{
%>
	<p class="no_p">当前没有任何课程</p>
<%
	}
%>
            </div>
       </div>
   </div>
</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>         
<script>
	function search() {
		var labelId = document.getElementById("label").value;
		var type = document.getElementById("type").value;
		
		var url ='/wikestudy/dist/jsp/manager/course/course_query_by_laybel_type?currentPage=1&label='+labelId+'&type='+type;
		
		window.location.href = url;
	}
	
</script>
</body>
</html>