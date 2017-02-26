<%@page import="java.util.Iterator"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.Label"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>标签管理</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
	<!-- 获取标签集合,迭代标签集合并输出到页面上 -->
<%
	List<Label> labels = (List<Label>)request.getAttribute("labels");
	Iterator<Label> it = labels.iterator();
	Label l = null;
%>
	<jsp:include page="/dist/jsp/manager/page/nav.jsp"></jsp:include>
	<%-- 如果集合元素为空,则不输出 --%>
	<%-- 如果不为空,则输出标签元素 --%>

	<%-- 迭代输出标签元素 --%>
<div id="wrapper">
	<jsp:include page="/dist/jsp/manager/page/sidebar.jsp"></jsp:include>
	<div id="column">
		<div id="col_head">
			<span id="fir_head_top">话题管理</span>
			<span id="sec_head">标签管理</span>
		</div>
		<div id="col_main">
			<div  id="mana_type">
			<table >
				<tr>
					<th id="type_name">标签名</th>
					<th>标签描述</th>
					<th id="type_num">话题数量</th>
					<th id="type_cou">课程数量</th>
					<th></th>
					<th><button type="button" id="create" onclick="window.location.href='type_create'" ></button></th>
				</tr>
	<%
		if(labels.size() <= 0){	
	%>
			<label>当前没有任何标签</label>
	<%
			return ;
		}
	%>
<%
	while(it.hasNext()){
		l = it.next();
%>
		<tr>
		<form action="label_update" method="post">
				<input type="hidden" name="labId" value="<%=l.getLabId() %>"/>
					<td><a href="topic_manage?labId=<%=l.getLabId()%>&currentPage=1"><%=l.getLabName() %></a></td>
					<td><input type="text" name="labDes" value="<%=l.getLabCib() %>"/></td>
					<td><%=l.getTopicCount() %></td>
					<td><%=l.getCourseCount() %></td>
					<td><button type="submit" class="reset">修改</button></td>
					<td><button type="button" class="delect" onclick="window.location.href='label_delete?labId=<%=l.getLabId()%>&currentPage=1'"></button></td>
		</form>
		</tr>
		
<%
	}
%>
			</table>

		</div>
		</div>
	</div>	
</div>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
</body>
</html>