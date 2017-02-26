<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.wikestudy.model.pojo.*,  java.util.List" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>我的笔记</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/student/student.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"/>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
	      <div id="center_headD">
	        <div id="center_headC"></div>
	        <h1 id="center_headB"> 个人中心</h1>
	      </div>
	    </div>
	    <jsp:include page="../common/student_sidebar.jsp"></jsp:include>
			<%-- 中间的是主要内容区 --%>
		<ul id="stu_sidebar">
			<li id="no_course_head"><a href="/wikestudy/dist/jsp/student/course/course_center#stu_sidebar">我的课程</a></li>
			<li id="no_topic_head"><a href="../common/my_topic_get#stu_sidebar">我的话题</a></li>	
			<li id="note_head"><a href="course_note_f#stu_sidebar">我的笔记</a></li>
			<li id="no_mess_head"><a href="../common/my_message_query#stu_sidebar">留言板</a></li>
		</ul>
	<%--查询具体笔记内容(以课程为单位查询, 章节查询或编辑时间查询) --%>
	<div id="main_area">
<%
	PageElem<NoteView> pe = (PageElem<NoteView>) request.getAttribute("pe");
	if (pe == null || pe.getPageElem() == null) {
		System.out.println("程序代码出现bug");
	}
	String sort = (String) request.getAttribute("sort");
	Integer couId = (Integer) request.getAttribute("couId");
%>
		<div id="choice">
			<div id="choiceOne">
				 <a href="course_note_t?sort=1&page=1&couId=<%= couId %>">章节顺序</a>
				 <a href="course_note_t?sort=2&page=1&couId=<%= couId %>">编辑时间</a>
			</div>
			<div id="choiceTwo">
				<span>共<%=pe.getRows()%>个笔记</span>
				<a href="course_note_t?sort=<%=sort%>&page=<%=pe.getCurrentPage()-1%>&couId=<%=couId %>"  id="previousPage"></a>
				<span>
					<b><%=pe.getCurrentPage() %></b>/<b><%=pe.getTotalPage() %></b>
				</span>	
				<a href="course_note_t?sort=<%=sort%>&page=<%=pe.getCurrentPage()+1%>&couId=<%=couId %>"  id="nextPage"></a>
			</div>
		</div>
				<%-- 笔记列表展示 --%>
				<div id="noteArea">
				<%--判断是否有笔记 --%>
<%if (pe.getPageElem().size() == 0) {%>
				<p>当前课程未做笔记</p>
				
				<%}else {for(NoteView nv:pe.getPageElem()) { %>
					<div class="oneNote">
						<div class="heading"> <%--笔记对应课时题目 --%>
							<span class="play"></span>
							<span><%=nv.getSecNumber() %></span> 
						    <span><%=nv.getSecName()%></span>
						</div>
						<p class="pContent" ><%=nv.getNDContent()%></p>
						
						<%--隐藏区， 点编辑的时候才会展开--%>
						<div class="hide_note_div">
							<textarea><%=nv.getNDContent()%></textarea>
							<button onclick="deleteNote('<%=nv.getNDId()%>')">删除</button>
							<button class="save_note" onclick="saveNote('<%=nv.getNDId()%>')">保存</button>
						</div>
						<%-- --%>
						<div class="time"><span><%=nv.getNDReleTime().toString().substring(0, nv.getNDReleTime().toString().length()-2) %></span></div>
					</div>
				<%}} %>
				</div>
				<%--笔记列表展示完毕 --%>
			</div>
		</div>
	</div>
	<jsp:include page="/dist/jsp/common/footer.jsp"/>
<script type="text/script" src="/wikestudy/dist/js/common/common.js"></script>
<script type="text/javascript" src ="/wikestudy/dist/js/jquery-form.js"></script>
<script type="text/javascript" src ="note-save.js"></script>
	
</body>
</html>