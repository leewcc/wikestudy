<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>管理老师</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
	<div id="wrapper">
		<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
		<div id="column">
			<div id="col_head">
				<span id="fir_head_per">人员管理</span>
				<span id="sec_head">查看用户</span>
			</div>
		<div id="col_main">
			<div id="choic_head">
				<a id="no_sel_head"  href="student_query_by_grade?stuGrade=1&stuClass=0&currentPage=1">查看学生</a>
				<h3 id="sel_head">查看教师</h3>
			</div>
			<form action="manager_search" method="post" id="query_stu" >
			<fieldset>
					<legend>按人名搜索</legend>
			<label>姓名：<input type="text" name="search"/></label>
			<button id="search" type="submit">搜&nbsp;&nbsp;索</button>
			</fieldset>
		</form>
		<div id="stu_data">
		
		<table>
			<tr>
				<th>工号</th>
				<th>姓名</th>
				<th>性别</th>
				<th><a href="/wikestudy/dist/jsp/super/student_manage/teacher_show">添加老师</a></th>
			</tr>
	<% 
	PageElem<Teacher> pe=(PageElem<Teacher>)request.getAttribute("pe");
	List<Teacher> l=pe.getPageElem();
	for(Teacher tea:l) {
		%>
		<tr>
			<td><%=tea.getTeaNumber() %></td>
			<td><%=tea.getTeaName() %></td>
			<td><%=("male".equals(tea.getTeaGender())? "男":"女") %></td>
			<td>
				<button class="reset" type="button" onclick="window.location.href='manager_reset?option=reset&teaId=<%=tea.getTeaId() %>',alert(’重置密码为123456‘)">重置密码</button>
				<button class="delect" type="button" onclick="window.location.href='manager_reset?teaId=<%=tea.getTeaId() %>&option=delete'"></button>
			</td>
		</tr>
		<%
	}
	%>
	</table>
	<!-- </form> -->
	<%
		int cp = pe.getCurrentPage();
		int tp = pe.getTotalPage();
%>	
 	<div id="page">
<%  
    if(cp > 1){    
%>
              <a href="manager_reset?cp=<%=cp-1%>" id="previousPage">.</a>
<%  
	}

    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="manager_reset?cp=<%=i%>" <%=selected%>><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="manager_reset?cp=1" <%=selected1%>>1</a>
            <a href="manager_reset?cp=2" <%=selected2%>>2</a>
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
            <a href="manager_reset?cp=<%=tp-1%>"<%=selected%>><%=i%></a>
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
            <a href="manager_reset?cp=<%=tp-1%>" <%=selectedt1%>><%=tp-1%></a>
            <a href="manager_reset?cp=<%=tp%>" <%=selectedt2%>><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="manager_reset?cp=<%=cp+1%>" id="nextPage">.</a>
<%
    }
%>
            </div>
	
	</div>
	</div>
	</div>
	</div>
	<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
	<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
</body>
</html>