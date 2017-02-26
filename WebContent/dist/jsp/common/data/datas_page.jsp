<%@page import="com.wikestudy.model.pojo.Data"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.model.pojo.DataView"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/common/home_page.css"/>
	<title>具体资料页面</title>
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<%--页面分为三部分 分别是课程简介  老师简介  资料目录 --%>
<%//获取课程视图对象
DataView dv = (DataView)request.getAttribute("datas");
%>
		<%--第一部分 课程简介 --%>
	<%
		Course c = dv.getCourse();
	%>
		<div id="intro">
			<div id="intro1">
				<img id="intro_img" class="course_img" src="<%=c.getCouAllUrl() %>" alt="课程封面" />
				<div id="intro_main">
					<h3><%=c.getCouName() %></h3>
					<span>微课关注度：<%=c.getCouStudyNum() %></span>
					<p>介绍：<%=c.getCouBrief() %></p>
				</div>
			</div>
		</div>
		<div id="coursemain">
			<span id="here"></span>
			<ul id="coursemain_list">
				<li id="_bricf"><a href="#"><%=InputUtil.input(dv.getBindingName()) %></a></li>
				<li id="_comment"><a href="data_get?courseId=<%=c.getCouId() %>">返回</a></li>
			</ul>
			<div id="coursemain_content">
<%
		List<Data> datas = dv.getDatas();
		if(datas.size() <= 0) {
			
%>
				<p class="no_p">当前没有可下载的资料</p>
<% 
		}else{
%>			
				<p class="illegal">${message }</p>
				<table>
					<tr>
						<th></th>
						<th>文件名</th>
						<th>大小</th>
						<th>下载量</th>
						<th></th>
					</tr>
<% 
			for(Data d : datas){
				String root = d.getDataRoot();
				int index = root.lastIndexOf(".");
%>			
					<tr>
						<td>
							<img src="/wikestudy/system_icon_get?filename=<%=index < 0 ? "blank" : root.substring(index + 1) %>" alt="图标" />
						</td>
						<td><%=InputUtil.input(d.getDataName()) %></td>
						<td><%=InputUtil.inputSize(d.getDataSize()) %></td>
						<td><%=d.getDataDownload() %></td>
						<td><button onclick="window.location.href='data_download?dataId=<%=d.getDataId() %>&id=${param.id }&type=${param.type }'">下载</button></td>
					</tr>
<%
			}
%>
				</table>
<%
		}
%>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
</body>
</html>