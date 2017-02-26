<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.wikestudy.model.pojo.Question"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
	<title>学生回答的情况</title>
	<link type="image/x-icon" rel="shortcut icon" href="/wikestudy/dist/images/system/small_logo.png">
	<link type="text/css" rel="stylesheet" href="/wikestudy/dist/css/teacher/teacher.css"/>
</head>
<body>
<jsp:include page="/dist/jsp/common/nav.jsp"></jsp:include>
<div class="outside">
	<div class="wrapper">
		<div id="center_headA">
	      <div id="center_headD">
	        <div id="center_headC"></div>
	        <h1 id="center_headB"> 个人中心</h1>
	      </div>
	    </div>
	<%--第一步：	include进老师简介 --%>
	<jsp:include page="/dist/jsp/teacher/common/teacher_sidebar.jsp"></jsp:include>
	<ul id="stu_sidebar">
		<li id="course_head"><a href="/wikestudy/dist/jsp/teacher/common/my_courses_query#stu_sidebar">我的课程</a></li>
		<li id="no_topic_head"><a href="/wikestudy/dist/jsp/student/common/my_topic_get#stu_sidebar">我的话题</a></li>
		<li id="no_note_head"><a href="/wikestudy/dist/jsp/student/article/my_article_query#stu_sidebar">我的文章</a></li>
		<li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
	</ul>
	<div id="main_area">
	<div id="update_quetion" class="see_question">
		<p class="guide_message">答题情况</p>
<%  
	/*获得所有的问题*/
	ArrayList<Question> oList= (ArrayList<Question>)request.getAttribute("option");
	ArrayList<Question> jList=(ArrayList<Question>) request.getAttribute("judge");
	ArrayList<Question> fList=(ArrayList<Question>) request.getAttribute("fill");
	/*输出选择题*/
	int num=1;//序号
	//没有人答题
	if(oList==null&&jList==null&&fList==null) {
		%>
		<p style="line-height: 100px; text-align: center;">当前没有题目</p>
		<%
	} else {
		if(oList!=null) {
		%>
		<div id="choice_area">
		<h1>选择题</h1><hr>
		<input type="hidden" name="optionNum" value="<%=oList.size() %>" />
		<%
		for(Question q:oList) {
	%>
			<div class="one_choice">
				<p class="question_content">第<%=num++ %>题：
				<%=q.getQueContent() %></p>
				<%
					/*选择题选项*/
					String string=q.getQueOption();
					String []os=string.split(";next;");
					char A='A';
					for(String s:os) {
				
				%>
				<label class="question_tea"><%=A %>. <%=s %></label>
				<% 	A++;
				}%>		
				<p>正确答案：<%=q.getQueAnswer() %></p>		
				<p>解析：<%=q.getQueExplain() %></p>
				<p><span>答题人数：<%=q.getQuePersonNum() %></span>
				<%if(q.getQuePersonNum()==0) q.setQuePersonNum(1); %>
					<span>正确率<%=InputUtil.inputNum(q.getQueCorrectNum()/(double)q.getQuePersonNum()) %></span>
				</p>
				</div>
				<%
			}
			%>
			</div>
			<%
		}
		%>
		
		<%
	}
		/*输出判断题*/
		num=1;//序号归零
		if(jList!=null) {
			%>
			<div id="judge_area">
			<h1>判断题</h1>
			<hr>
				<%
			for(Question q:jList) {
			%>
		<div class="one_judge">	
			<p class="question_content">第<%=num++ %>题：<%=q.getQueContent() %></p>
			<p>答案：<%="Y".equals(q.getQueAnswer())?"正确" : "错误"%></p> 
			<p>解析:<%=q.getQueExplain() %></p>
			<p><span>答题人数：<%=q.getQuePersonNum() %></span>
			<%if(q.getQuePersonNum()==0) q.setQuePersonNum(1); %>
			<span>正确率：<%=InputUtil.inputNum(q.getQueCorrectNum()/(double)q.getQuePersonNum()) %></span></p>
			</div>
			<%
			}
			%>
		</div>
		<%
		}	
		%>
		<% 
		/*输出填空题*/
		num=1;//序号归零
		if(fList!=null) {
			%>
			<div id="fill_area">
			<h1>填空题</h1><hr>
			<input type="hidden" name="fillNum" value="<%=fList.size() %>">
			<%
			for(Question q:fList) {
			%>
			<div class='one_fill'>
				<p class="question_content">第<%=num++ %>题：
				<%=q.getQueContent() %></p>
				<p>答案：<%=q.getQueAnswer() %></p>
				<p>解析：<%=q.getQueExplain() %></p>
				<p><span>答题人数：<%=q.getQuePersonNum() %></span>
				<%if(q.getQuePersonNum()==0) q.setQuePersonNum(1); %>
				<span>正确率：<%=InputUtil.inputNum(q.getQueCorrectNum()/(double)q.getQuePersonNum()) %></span></p>
			</div>
			<%
			}%>
			</div>
			<%
		}
	%>
	</div>
	</div>
	</div>
	</div>
	<jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>

	<script type="text/script" src="/wikestudy/js/common/common.js"></script>
	<!--[if lt IE 9]>
  <script>
    function show(condition, message, time){
    if(condition){
      var target = document.body;
      if(!target){
        target = document.createElement('body');
        document.appendChild(target);
      }
      target.innerHTML = '<div id="popup" style="width: 100%;height: 2000px;position: fixed;top: 0;left: 0;background: rgba(0,0,0,0.4);background: black;"><div id="popup_area"style="position: absolute; width: 500px;padding: 40px;left: 50%;top: 50px;border-radius: 200px;border: 9px solid #EEE;margin-left: -290px;background: white;"><p>'+message+'</p></div></div>';

    }
  };

  show(true, '请下载最新版本的现代浏览器：譬如<a href="http://www.firefox.com.cn/download/">火狐浏览器</a>或<a href="http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html">谷歌浏览器</a>', 24*60*60*60*1000*60);
</script>
<![endif]-->
</body>
</html>