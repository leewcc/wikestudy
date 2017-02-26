<%@page import="com.wikestudy.model.pojo.Teacher"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sidebar">
		<ul id="first_level">
			<li><span class="fir_per">人员管理</span>
				<ul class="second_level">
					<li><a href="/wikestudy/dist/jsp/manager/super/student_manage/class_manage">班级管理</a></li>
					<li><a href="/wikestudy/dist/jsp/manager/super/student_manage/student_see">用户导入</a></li>
					<li><a href="/wikestudy/dist/jsp/manager/super/student_manage/student_query_by_grade?stuGrade=1&stuClass=0&currentPage=1">查看用户</a></li>
					<li><a href="/wikestudy/dist/jsp/manager/super/star_manage/star_see?currentPage=1">一周之星</a></li>
				</ul>
			</li>
			<li><span class="fir_art">文章管理</span>
			 <ul class="second_level">
				<li><a href="/wikestudy/dist/jsp/manager/article/article_manage">管理文章</a></li>
			</ul>
			</li>
			<li><span class="fir_topic">话题管理</span>
				<ul class="second_level">
					<li><a href="/wikestudy/dist/jsp/manager/topic/label_query">标签管理</a>
					<li><a href="/wikestudy/dist/jsp/manager/topic/topic_manage?labId=0">话题管理</a>
				</ul>
			
			</li>
			<li><span class="fir_cou">微课管理</span>
			 <ul class="second_level">
				<li><a href="/wikestudy/dist/jsp/manager/course/course_query_by_laybel_type?label=0&currentPage=1">微课管理</a></li>
<!-- 				<li><a href="/wikestudy/dist/jsp/Manager/course/create_course_f">课程创建</a></li>
				<li><a href="/wikestudy/dist/jsp/Manager/course/course_fir_change">课程更新</a></li>
				<li><a href="/wikestudy/dist/jsp/Manager/course/RelRecCourseServlet??type=3&flag=2&currentPage=1">课程发布</a></li>
				<li><a href="/wikestudy/dist/jsp/Manager/course/RelRecCourseServlet?type=1&flag=2&currentPage=1">课程推荐</a></li>
				<li><a href="/wikestudy/dist/jsp/Manager/course/RelRecCourseServlet?type=2&flag=2&currentPage=1">课程删除</a></li> -->
			</ul>
			</li>
			<li><a class="second_level" href="/wikestudy/dist/jsp/manager/page/manager_information">个人资料</a>
		<!--
		<a href="/wikestudy/dist/jsp/Manager/page/information_update.jsp">个人资料管理</a>
		<a href="/wikestudy/dist/jsp/Manager/student_manage/SeeStudent.jsp">手动添加</a>
		<a href="/wikestudy/dist/jsp/Manager/page/TeacherIn.jsp">批量导入教师</a>
		
		<a href="">课程管理</a>
		<a href="/wikestudy/dist/jsp/Manager/topic/label_query">标签管理</a>
		<a href="/wikestudy/dist/jsp/Manager/topic/topic_manage?labId=0&currentPage=1">话题管理</a>
		家校互动
		<a href="/wikestudy/dist/jsp/Manager/article/BeforeArticle">制作文章</a>
		<a href=""></a>-->
		</ul>
	</div>
<!-- 
				<li><a href="/wikestudy/dist/jsp/Manager/article/BeforeArticle">制作文章</a></li>
		<a href="/wikestudy/dist/jsp/Manager/page/ManagerDate.jsp">个人资料</a>-->