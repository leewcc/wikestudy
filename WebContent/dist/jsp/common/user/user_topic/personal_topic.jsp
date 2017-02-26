<%@page import="com.wikestudy.model.util.InputUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wikestudy.model.pojo.Student"%>
<%@ page import="com.wikestudy.model.pojo.Teacher"%>
<%@ page import="com.wikestudy.model.util.TimeToot"%>
<%@ page import="com.wikestudy.model.pojo.Topic"%>
<%@ page import="com.wikestudy.service.manager.TopicManagerService"%>
<%@ page import="com.wikestudy.model.pojo.PageElem"%>
<%@ page import="sun.nio.cs.ext.ISCII91"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="com.wikestudy.model.pojo.Label"%>
<%--  话题管理侧边栏  --%>
 <%
 		
	Student s = (Student)request.getSession().getAttribute("s");
	Teacher tea = (Teacher)request.getSession().getAttribute("t");
	String photo = "";
	int type = request.getParameter("type") == null ? 1 : Integer.parseInt(request.getParameter("type"));//1-热门 2-最新

	photo = s == null ? (tea == null? "" : tea.getTeaPortraitUrl()) : s.getStuPortraitUrl();
	String side = request.getParameter("param_sidebar");
    if(side == null){
        side = "none";
    }
 %>
<div id="topic_side">
    <div id="side_nav">
        <div>
            <div><img class="person_img" src="<%= photo %>" alt="用户头像"/></div>
            <table>
                <tr>
                    <td><a id="side_nav_first"  <%=(side.equals("reply"))?"class='select'":""%> href="/wikestudy/dist/jsp/common/user/user_topic/my_replys_get">我的回复</a></td>
                    <td><a  <%=(side.equals("answer"))?"class='select'":""%> href="/wikestudy/dist/jsp/common/user/user_topic/my_answers_get">我的评论</a></td>
                    <td><a id="side_nav_last"  <%=(side.equals("collection"))?"class='select'":""%> href="/wikestudy/dist/jsp/common/user/user_topic/my_col_topics_get">我的关注</a></td>
                </tr>
            </table>
        </div>
    </div>
    <div id="side_form">
        <form action="/wikestudy/dist/jsp/common/topic/topic_query_by_key" method="post">
            <input type="text" name="content" id="topic_search" placeholder="搜索话题" value="搜索话题"/>
            <button type="submit"></button>
        </form>
    </div>
    <div id="side_label">
        <h1>热门标签</h1>
        <div id="label_main">
         <%-- 显示其他类型的链接 --%>
<%
    List<Label> labels = (List<Label>)request.getAttribute("labels");
    Iterator<Label> it = labels.iterator();
    Label label = null;
    while(it.hasNext()){
        label = it.next();
%>
            <a href="/wikestudy/dist/jsp/common/topic/topic_list_get?labId=<%=label.getLabId() %>&currentPage=1&type=<%=type%> "><%=label.getLabName() %></a>
<%
    }
%>
        </div>
    </div>
</div>