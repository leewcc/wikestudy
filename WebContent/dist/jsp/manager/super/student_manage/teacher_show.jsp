<%@page import="java.util.ArrayList"%>
<%@page import="com.wikestudy.model.pojo.Teacher"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>导入教师</title>
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
				<span id="sec_head">用户资料</span>
			</div>
		<div id="col_main">
			<div id="choic_head">
				<a id="no_sel_head" href="student_see">导入学生</a>
				<h3 id="sel_head">导入教师</h3>
			</div>
	<form action="tea_excel_upload" id="give_file" enctype="multipart/form-data" method="post" onsubmit="return submitT()">
		<div><input type="file" id="upload" name="upload" value="导入Excel" /></div>
		<button type="submit" >导入</button>
	</form>
	<form id="add_tea" action="teacher_add" method="post">
		<table>
			<thead>
				<tr>
					<th>身份</th>  
					<th>工号</th>   
					<th>姓名</th>  
					<th>密码</th>   
					<th>性别</th>   
					<th>简介</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
<%
			List<Teacher> tea=(ArrayList<Teacher>) request.getAttribute("teacher");
			if(tea!=null) {
			for(Teacher te:tea) {
				%>
				<tr>
					<td>
						<select class="tea_type" name="teaType">
							<option value="管理员"
				<% if("true".equals(String.valueOf(te.isTeaType()))) {%> selected <%}%> >管理员</option>
							<option	value="老师"
				<% if("false".equals(String.valueOf(te.isTeaType()))) {%> selected <%}%> >老师</option> 		
				</select></td>	
					<td><input class="tea_num" type="number" name="teaNumber" value="<%=te.getTeaNumber() %>" /></td>
					<td><input class="tea_name" type="text" name="teaName" value="<%=te.getTeaName() %>" /></td>
					<td><input class="tea_pass" type="password" name="teaPassword" value="<%=te.getTeaPassword() %>"/></td>
					<td><input class="tea_gender" type="text" name="teaGender" value="<%=te.getTeaGender() %>" /></td>
					<td><input class="tea_intro" type="text" name="teaIntroduction"  value="<%=te.getTeaIntroduction() %>"/></td>
					<td><button class="delect" type="button"></button></td>
				</tr>
				<%
			}
			%>
			
			<%
		}else{
%>
				<tr>
					<td>
						<select class="tea_type" name="teaType">
							<option value="管理员">管理员</option>
							<option	value="老师" selected>老师</option> 		
				</select></td>	
					<td><input class="tea_num" type="number" name="teaNumber" value="" /></td>
					<td><input class="tea_name" type="text" name="teaName" value="" /></td>
					<td><input class="tea_pass" type="password" name="teaPassword" value=""/></td>
					<td><input class="tea_gender" type="text" name="teaGender" value="" /></td>
					<td><input class="tea_intro" type="text" name="teaIntroduction"  value=""/></td>
					<td><button class="delect" type="button"></button></td>
				</tr>
<%
		}
		
%>
		</tbody>
			</table>
			<div id="button_gro">
					<button class="button" type="button" id="add" >添加新一行</button>
					<button class="button" type="button" id="reduce" >删除最后一行</button>
			</div>
			<button id="update_data" type="submit" >确&nbsp;&nbsp;&nbsp;定</button>
	</form>
	</div>
	</div>
	</div>
	
	<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script><script type="text/javascript" src="/wikestudy/dist/js/jquery-form.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
<script type="text/javascript">
function submitT() {
	var ul=document.getElementById("upload").value;
	var ext=ul.substring(ul.lastIndexOf(".")+1);
	if(ul==""||ul==null||(ext!="xls"&&ext!="xlsx")) {
		alert("请选择正确的文件");
		return false;
		} else {
		return true;
			}
	
}
$(document).ready(function(){
	$("#add").click(function(){
		var fragment = $("<tr></tr>");
		console.log(event.target);
		fragment.append('<td><select class="tea_type" name="teaType"><option value="管理员">管理员</option><option	value="老师" selected >老师</option></select></td>');
		fragment.append('<td><input class="tea_num" type="number" name="teaNumber" value="" /></td>');
		fragment.append('<td><input class="tea_name" type="text"  name="teaName" value="" /></td>');
		fragment.append('<td><input class="tea_pass" type="password" name="teaPassword" value="" /></td>');
		fragment.append('<td><input class="tea_gender" type="text" name="teaGender" value="" /></td>');
		fragment.append('<td><input class="tea_intro" type="text" name="teaIntroduction" value="" /></td>');
		var button = $('<td><button class="delect" type="button"></button></td>');
		button.click(function(){
			var count = document.getElementsByTagName("table")[0].getElementsByTagName("tr").length;
			if(count <= 2){
				alert("不能再删除了");
			}else{
				var e = $(event.target);
				e.parent().parent().remove();
			}
		});
		fragment.append(button);
		$("table").append(fragment);
	});
	$('#reduce').click(function(){
		var count = document.getElementsByTagName("table")[0].getElementsByTagName("tr").length;
		if(count <= 2){
			alert("不能再删除了");
		}else{
			$("table").children().children().last().remove();
		}
	});
	$('.delect').click(function(){
		var count = document.getElementsByTagName("table")[0].getElementsByTagName("tr").length;
		if(count <= 2){
			alert("不能再删除了");
		}else{
			var e = $(event.target);
			e.parent().parent().remove();
		}
	});
});
</script>
</body>
</html>