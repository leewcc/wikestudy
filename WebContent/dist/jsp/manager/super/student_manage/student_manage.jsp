<%@page import="com.wikestudy.model.pojo.GraClass"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.Student"%>
<%@page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page import="com.wikestudy.model.pojo.Teacher" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>查看学生列表</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/manager/manager.css"/>
</head>
<body>
<%
	int one = ((GraClass)application.getAttribute("one")).getClaClassNum();
	int two = ((GraClass)application.getAttribute("two")).getClaClassNum();
	int three = ((GraClass)application.getAttribute("three")).getClaClassNum();
	String grade = request.getParameter("stuGrade") == null ? "1" : request.getParameter("stuGrade");
	int count =  "1".equals(grade)?one : ("2".equals(grade) ? two : ("3".equals(grade) ? three : one));
	int cla = request.getParameter("stuClass") == null? 0 : Integer.parseInt(request.getParameter("stuClass"));
	
%>
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
					<h3 id="sel_head">查看学生</h3>
					<a id="no_sel_head" href="/wikestudy/dist/jsp/manager/super/student_manage/manager_reset?">查看教师</a>
				</div>
				<form id="query_stu" action="student_query_by_grade" method="post">
					<fieldset>
						<legend>按年级和班级搜索</legend>
						<label>年级：	
							<select name="stuGrade">
	<%
			String o1= "1".equals(grade) ? "selected" : "";
			String o2= "2".equals(grade) ? "selected" : "";
			String o3= "3".equals(grade) ? "selected" : "";
	%>
								<option value="1"  <%=o1 %>>初一</option>
								<option value="2" <%=o2 %>>初二</option>
								<option value="3" <%=o3 %>>初三</option>
							</select>
						</label>
						<label>班级：
							<select id="stu_class" name="stuClass">
								<option value="0" <%if(cla == 0){ %> selected="selected" <%} %>>所有</option>
<%

				for(int i = 1; i <= count; i++){		
%>
					  			<option value="<%=i%>" <%if(cla == i){ %> selected="selected" <%} %>><%=i %>班</option>
<% 
				}
%>				
							</select></label>
						<button id="search" type="submit">搜&nbsp;&nbsp;索</button>
					</fieldset>
				</form>
				<div id="stu_data">
				<p class="illegal">${message}</p>
				<table>
						<thead>
						<tr>
						<%-- 初始化标题行 姓名 学号 年级 班级 --%>
						<th class="stu_name">姓名</th>
						<th class="stu_num">学号</th>
						<th class="stu_grade" >年级</th>
			<th  id="stu_class">班级</th>
			<th>
				<button id="up_stu" type="button" onclick="window.location.href='grade_update?stuGrade=${param.stuGrade }&stuClass=${param.stuClass }&currentPage=${param.currentPage }'">一键升级</button>
				<button class="button" type="button" onclick="window.location.href='grade_rollback?stuGrade=${param.stuGrade }&stuClass=${param.stuClass }&currentPage=${param.currentPage }'">一键退级</button>
			</th>
			<th><button id="add_stu" type="button" onclick="window.location.href='student_see'">添加学生</button></th>
			</tr>
			</thead>
			<tbody>	
		<%-- 获取学生分页对象,如果有学生,则展示出来 --%>
		<%-- 从分页对象中获取学生集合 --%>
		<%-- 在获取学生集合的迭代器 --%>
		<%-- 若存在学生,则将学生信息展示出来 --%>
<%
		PageElem<Student> pe = (PageElem<Student>)request.getAttribute("pageElem");
		List<Student> stu = pe.getPageElem();
		Iterator<Student> it = stu.iterator();
		Student s = null;
		while(it.hasNext()){
			s = it.next();
%>
		<tr>
			<td>
				<a href="student_detail_see?stuId=<%=s.getStuId()%>&type=2"><%=s.getStuName() %></a>
			</td>
			<td><%=s.getStuNumber() %></td>
			<td><%=s.getGrade() %></td>
			<td><%=s.getStuClass() %></td>
			<td></td>
			<td class="stu_other">
			<button class="reset" type="button" onclick="window.location.href='stu_password_reset?stuId=<%=s.getStuId()%>&stuGrade=${param.stuGrade }&stuClass=${param.stuClass }&currentPage=${param.currentPage }'">
			重置密码
			</button>
			<button class="delect" type="button"  onclick="window.location.href='student_delete?stuId=<%=s.getStuId() %>&stuGrade=${param.stuGrade }&stuClass=${param.stuClass }&currentPage=${param.currentPage }'"></button></td>
		</tr>
<%
		}
%>
			</tbody>
		</table>
		<%-- 获取当前页数,获取总页数 --%>
		<%-- 如果当前页数不是第一页,则显示上一页按钮 --%>
		<%-- 如果当前页数不是最后一页,则显示下一页按钮 --%>
<%
		int cp = pe.getCurrentPage();
		int tp = pe.getTotalPage();
%>	
 	<div id="page">
<%  
    if(cp > 1){    
%>
              <a href="student_query_by_grade?stuGrade=<%=grade %>&stuClass=<%=cla%>&currentPage=<%=cp-1%>" id="previousPage">.</a>
<%  
	}

    int i = 1;
    String selected = "";
    if(tp <= 10){
        for(i = 1; i <= tp; i++){
            selected = (i == cp)?"selected":"";
%>
            <a href="student_query_by_grade?stuGrade=<%=grade %>&stuClass=<%=cla%>&currentPage=<%=i%>" class="<%=selected%>"><%=i %></a>
<%
        }
    }else{ //分页大于10的情况
            String selected1 = (1== cp)?"selected": "";
            String selected2 = (2== cp)?"selected": "";
%>
            <a href="student_query_by_grade?stuGrade=<%=grade %>&stuClass=<%=cla%>&currentPage=1" class="<%=selected1%>">1</a>
            <a href="student_query_by_grade?stuGrade=<%=grade %>&stuClass=<%=cla%>&currentPage=2" class="<%=selected2%>">2</a>
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
            <a href="student_query_by_grade?stuGrade=<%=grade %>&stuClass=<%=cla%>&currentPage=<%=i%>" class="<%=selected%>"><%=i%></a>
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
            <a href="student_query_by_grade?stuGrade=<%=grade %>&stuClass=<%=cla%>&currentPage=<%=tp-1%>" class="<%=selectedt1%>"><%=tp-1%></a>
            <a href="student_query_by_grade?stuGrade=<%=grade %>&stuClass=<%=cla%>&currentPage=<%=tp%>" class="<%=selectedt2%>"><%=tp%></a>
<%        
    }
    if(cp < tp){
%>
    <a href="student_query_by_grade?stuGrade=<%=grade %>&stuClass=<%=cla%>&currentPage=<%=cp+1%>" id="nextPage">.</a>
<%
    }
%>
            </div>



		<!-- 添加一键升级按钮-->
	</div>
	</div>
	</div>
	</div>
	<script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
	<script type="text/javascript" src="/wikestudy/dist/js/manager/manager.js"></script>
</body>
</html>