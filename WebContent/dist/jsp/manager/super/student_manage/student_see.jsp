<%@page import="com.wikestudy.model.pojo.GraClass"%>
<%@page import="sun.nio.cs.ext.ISCII91"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="java.util.List"%>
<%@ page import="com.wikestudy.model.pojo.Teacher" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>学生信息导入</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
<%-- 获取请求域的学生集合 --%>
<%-- 如果学生集合存在元素,则迭代集合,将学生信息输出 --%>
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
				<h3 id="sel_head">导入学生</h3>
				<a id="no_sel_head" href="teacher_add">导入教师</a>
			</div>
			<p class="illegal">${message }</p>
			<form id="give_file" action="stu_excel_upload" enctype="multipart/form-data" method="post" onsubmit="return submitF()">
				<div><input id="upload" type="file" name="upload" accept="application/msexcel" value="导入Excel" /></div>
				<button type="submit">导入</button>
			</form>
<%
	List<Student> students = (List<Student>)request.getAttribute("students");
	Iterator<Student> it = null;
	int one = ((GraClass)application.getAttribute("one")).getClaClassNum();
	int two = ((GraClass)application.getAttribute("two")).getClaClassNum();
	int three = ((GraClass)application.getAttribute("three")).getClaClassNum();
	
%>
			<form id="add_stu" action="student_add" method="post">
				<table>
					<thead>
						<tr>
							<th>学号</th>
							<th>姓名</th>
							<th>年级</th>
							<th>班级</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
			<%-- 迭代学生集合,一个学生对象,生成一行记录 --%>
<%
	if(students !=null){
		it = students.iterator();
		if(it != null){
			Student s = null;
			while(it.hasNext()){
				s = it.next();
				String grade = s.getStuGrade();
				int count = "1".equals(grade)?one : ("2".equals(grade) ? two : ("3".equals(grade) ? three : one));
				int cla = Integer.parseInt(s.getStuClass());
%>		
				<%-- 根据学生对象的学号 姓名 年级 生成一行记录 --%>
				<tr>
					<td><input class="stu_num"  type="number"  name="<%="stuNumber"%>" value="<%=s.getStuNumber() %>" /></td>
					<td><input class="stu_name" type="text"  name="<%="stuName"%>" value="<%=s.getStuName() %>" /></td>
					<td>
						<select class="stu_grade" name="stuGrade">
							<option value="1"  <%="1".equals(grade)?"selected" : "" %>>初一</option>
							<option value="2"  <%="2".equals(grade)?"selected" : "" %>>初二</option>
							<option value="3"  <%="3".equals(grade)?"selected" : "" %>>初三</option>
						</select>
					</td>
					<td>
						<select class="stu_class" name="stuClass">
<%
							for(int i = 1; i <= count; i++){
					
%>
								<option value="<%=i%>" <%if(cla == i){ %> selected="selected" <%} %>><%=i %>班</option>
<% 
						}
%>			
					</select>
					</td>
					<td><button class="delect" type="button"></button></td>
				</tr>
<%	
				
			}
		}
%>			
			
<%
		}else{
%>
				<tr>
					<td><input class="stu_num"  type="number"  name="stuNumber" value="" /></td>
					<td><input class="stu_name" type="text"  name="stuName" value="" /></td>
					<td>
						<select class="stu_grade" name="stuGrade">
							<option value="1" selected >初一</option>
							<option value="2" >初二</option>
							<option value="3" >初三</option>
						</select>
					</td>
					<td>
						<select class="stu_class" name="stuClass">
							<option value="1" selected>1班</option>
<%
							for(int i = 2; i <= one; i++){
%>
								<option value="<%=i%>" ><%=i %>班</option>
<% 
						}
%>			
					</select>
					</td>
					<td><button class="delect" type="button"></button></td>
				</tr>
<%
		}
%>
		</tbody>
				</table>
				<div id="button_gro">
					<button  type="button" id="add" >添加新一行</button>
					<button  type="button" id="reduce" >删除最后一行</button>
				</div>
				<button id="update_data" type="submit" >确&nbsp;定</button>
		</form>
</div>
	</div>
	</div>
	
<script type="text/javascript" src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
<script type="text/javascript">
function submitF() {
	var ul = document.getElementById("upload").value;
	var ext = ul.substring(ul.lastIndexOf(".") + 1);
	if(ul === "" || ul === null || ( ext != "xls" && ext != "xlsx")) {
		alert("请选择正确的文件，点击左边的选入框");
		return false;
		} else {
		return true;
			}
}
$(document).ready(function(){
	$("#add").click(function(event){
		var fragment = $("<tr></tr>");
		var a = document.getElementsByTagName("TABLE")[0];
		var b = $(".stu_grade").eq(0).clone();
		var c = $('<td></td>');
		c.append(b);
		console.log(event.target);
		fragment.append('<td><input class="stu_num" type="number"  name="stuNumber" value="" /></td>');
		fragment.append('<td><input class="stu_name" type="text"  name="stuName"value="" /></td>');
		//这里应该添加选择框

		fragment.append(c);
		b = $(".stu_class").eq(0).clone();
		c = $('<td></td>');
		c.append(b);
		fragment.append(c);
		var button = $('<td><button class="delect" type="button"></button></td>');
		button.click(function(event){
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
	$('.delect').click(function(event){
		var count = document.getElementsByTagName("table")[0].getElementsByTagName("tr").length;
		if(count <= 2){
			alert("不能再删除了");
		}else{
			$(event.target).parent().parent().remove();
		}
	});
	//未完待续
	$('.stu_grade').change(function(event){
		var a = $(event.target).parent().next().child();
		$(event.target.value === "")
	});
});
</script>
</body>
</html>