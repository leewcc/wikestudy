<%@page import="java.util.Map"%>
<%@page import="com.wikestudy.model.pojo.CouSection"%>
<%@page import="java.util.List"%>
<%@page import="com.wikestudy.model.pojo.CouChapter"%>
<%@page import="com.wikestudy.model.pojo.Course"%>
<%@page import="com.wikestudy.model.pojo.Label"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <jsp:include page="/dist/jsp/common/iehack.jsp"></jsp:include>
  <title> 课程</title>
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
        <li id="no_note_head"><a href="/wikestudy/dist/jsp/teacher/article/my_article_query#stu_sidebar">我的文章</a></li>
        <li id="no_mess_head"><a href="/wikestudy/dist/jsp/student/common/my_message_query#stu_sidebar">留言板</a></li>
      </ul>
      <div id="main_area">
      <%--1.课程具体管理页面2.修改课程名称, 封面, 简介 3.增加, 删除课程章节 (页面动态生成)4.资料管理接口添加--%>
      <%
        Course cou = (Course) request.getAttribute("cou"); 
        Map<CouChapter, List<CouSection>> map = (Map) request.getAttribute("map");
        List<Label> labList = (List<Label>) request.getAttribute("lab");
      %>
	      <div id="update_cou">
	
			<div class="back">
				<button onclick="window.location.href='/wikestudy/dist/jsp/teacher/common/my_courses_query?type=${param.type }&page=${param.page}'" type="button">返回</button>
			</div>
	
	        <div class="ud_cou_l">
	          <label for="couName">课程名称</label>
	        </div>
	        <div class="ud_cou_r">
	          <input id="couName" value="<%=cou.getCouName() %>"/>
	          <span id="errorCouName" style="display:none">课程名称不可为空(不可超出40个字符)</span>
	        </div>
	        
	        <div class="ud_cou_l">
	          <label>课程封面</label>
	        </div>
	        <div class="ud_cou_r">
	          <a href="photo_f_upload?imageUrl=<%=cou.getCouAllUrl() %> &couId=<%=cou.getCouId() %>&type=${param.type }&page=${param.page}">
	            <img id="couPricUrl" class="course_img" src='<%=(cou.getCouAllUrl().equals(""))?"/wikestudy/dist/images/common/img_17.png":cou.getCouAllUrl()%>'/>
	          </a>
	        </div>
	        
	        <div class="ud_cou_l">
	          <label for="grade">适合年级</label>
	        </div>
	        <div class="ud_cou_r">
	          <select id="grade">
	          <%if (("初一").equals(cou.getGrade())){ %>
	            <option value="1" selected="selected">初一</option>
	          <%}else {%>
	            <option value="1">初一</option>
	          <%}if (("初二").equals(cou.getGrade()) ){%>
	            <option value="2" selected="selected">初二</option>
	          <%}else{%>
	            <option value="2">初二</option>
	          <%}if (("初三").equals(cou.getGrade()) ){%>
	            <option value="3" selected="selected">初三</option>
	          <%}else {%>
	            <option value="3">初三</option>
	          <%}%>
	          </select>
	        </div>
	
	        <div class="ud_cou_l">
	          <label for="type">适合类型</label>
	        </div>
	        <div class="ud_cou_r">
	        <% if( labList != null && labList.size() > 0 ) { %>
	          <select id="type">
	          <% for(Label lab: labList) { 
	              if (lab.getLabId() == cou.getLabelId()) { %>
	            <option value="<%=lab.getLabId() %>" selected="selected"><%=lab.getLabName() %></option>
	          <% } else{ %>
	            <option value="<%=lab.getLabId() %>"><%=lab.getLabName() %></option>
	          <%}} %>
	          </select>
	          <% } else { %>
	            <p>当前未创建标签</p>
	          <% } %>
	        </div>
	
	        <div class="ud_cou_l">
	          <label for="couBrief">课程简介</label>
	        </div>
	
	        <div class="ud_cou_r">
	          <textarea id="couBrief"><%=cou.getCouBrief() %></textarea>
	        </div>
	
	        <div class="ud_cou_l">
	          <label for="couBrief">课程章节</label>
	        </div>
	        <div class="ud_cou_r">
	        <ul class="ul_cha">
	        <%if (!map.isEmpty()) {%>
	          
	          <% for(CouChapter cha: map.keySet()) { List<CouSection> csList = map.get(cha);%>
	            <li class="li_cha">
	              <div class="chapter">
	                <input type="hidden" class="chaNumber" value="<%=cha.getChaNumber() %>"/>
	                <span class="spanCha">第<%=cha.getChaNumber() %>章</span>
	                <input type="text" value="<%=cha.getChaName()%>"/>
	                <input type="hidden" class="chaId"  value="<%=cha.getChaId() %>"/>
	                <a href="datas_manage?binding=<%=cha.getChaId()%>&type=true&couId=<%=cou.getCouId() %>&type=${param.type }&page=${param.page}">上传资料</a> 
	                <a href="javascript:void(0)"  class="tog"></a>
	              </div>
	              <div>
	                <a href="javascript:void(0)" class="delChapter"></a>
                  <a href="javascript:void(0)" class="addChapter">插入章</a>  
                  <a href="javascript:void(0)" class="insertSec">新增课时</a>
	              </div>
	              <%if (csList != null || csList.size() > 0) { %>
	              <ul class="li_cha_ul">
	              	<%for (CouSection cs : csList) { %>
	                <li class="liSec">
	                  <div class="section">
	                    <span class="spanSec"><%=cha.getChaNumber() %>-<%=cs.getSecNumber() %></span>
	                    <input type="hidden" class="secNumber" value="<%=cs.getSecNumber() %>"/>
	                    <input type="text" value="<%=cs.getSecName()%>"/>
	                    <input type="hidden" class="secId" value="<%=cs.getSecId() %>"/>  
	                    <a href="question_make_by_tea?secId=<%=cs.getSecId() %>&couId=<%=cou.getCouId() %>">测试</a>  
	                    <a href="media_upload?secId=<%=cs.getSecId() %>&couId=<%=cou.getCouId() %>&type=${param.type }&page=${param.page}">视频</a>
	                    <a href="datas_manage?binding=<%=cs.getSecId()%>&type=false&couId=<%=cou.getCouId() %>&type=${param.type }&page=${param.page}">资料</a>	
	                  </div>
	                  <div>
	                    <a href="javascript:void(0)" class="delSection"></a>
                      <a href="javascript:void(0)" class="upSec"></a>
                      <a href="javascript:void(0)" class="downSec"></a>     
                      <a href="javascript:void(0)" class="addSection">插入课时</a>
	                  </div>
	                </li> 
	              	<% } %>
	              </ul>
	            <%}%>
	            </li>
	          <% } %>
	         
	        <% } %>
	         </ul>
	          <a class="insertCha">+&nbsp;添加章节</a>
	          <button type="button" onclick="saveInfor()">保存修改</button>
	          <button type="button" onclick="window.history.go(-1)">取消修改</button>
	        </div>
	        
	      </div>
	    </div>
    </div>
  </div>
  <jsp:include page="/dist/jsp/common/footer.jsp"></jsp:include>
  <script src="/wikestudy/dist/js/jquery-1.9.1.js"></script>
  <script src="/wikestudy/dist/js/jquery-form.js"></script>
  <script type="text/javascript" src="/wikestudy/dist/js/common/common.js"></script>
  <script type="text/javascript">
  var chaIdList = [];
  var secIdList = [];
  $(document).ready(function () {
    $("body").on("click", "a.tog", function() {
      $(this).parent().parent().children("ul").toggle(300);
      $(this).css("background: url('../../../images/common/course.png') no-repeat -82px 5px;");
    })
  // 最新的新建章节：在最后面添加章节(缺少 触发更新页面功能函数)
  $("body").on("click", "a.insertCha", function() {
  if ($("li.li_cha").size() == 0) {
  $("ul.ul_cha").append($("<li class='li_cha'>" +  
  "<div class='chapter'> " + 
  " <span class='spanCha'>第x章</span> " + 
  " <input type='hidden' class='chaNumber' value='0'> " + 
  //" <span>新增章节</span>+
  "<input type='text' value='新增章节'> " +  
  " <input type='hidden' class='chaId' value='-1'> " + 
  " <a href='javascript:void(0)' class='tog'></a>" + 
  "</div>" + 
  " <div> " + 
  //  " <a href='javascript:void(0)' class='upCha'></a>"  +
  //  " <a href='javascript:void(0)' class='downCha'></a> " + 
  " <a href='javascript:void(0)' class='delChapter'></a> " + 
  ' <a href="javascript:void(0)" class="addChapter">插入章</a>' +
  " <a href= 'javascript:void(0)' class='insertSec'>新增课时</a>" + 
  "</div><ul></ul>" + 
  "</li>"));
  } else {
  $("<li class='li_cha'>" +  
  "<div class='chapter'> " + 
  " <span class='spanCha'>第x章</span> " + 
  " <input type='hidden' class='chaNumber' value='0'> " + 
  //" <span>新增章节</span>"+
  "<input type='text' value='新增章节'> " +  
  " <input type='hidden' class='chaId' value='-1'> " + 
  " <a href='javascript:void(0)' class='tog'></a>" + 
  "</div>" + 
  " <div> " + 
  //  " <a href='javascript:void(0)' class='upCha'></a>"  +
  //  " <a href='javascript:void(0)' class='downCha'></a> " + 
  " <a href='javascript:void(0)' class='delChapter'></a> " + 
  " <a href= 'javascript:void(0)' class='insertSec'>新增课时</a>" + 
  "</div><ul></ul>"+
  "</li>").insertAfter(  $("li.li_cha").last() );
  };
  });
  // 最新：新建课时
  $("body").on("click", "a.insertSec", function() {
  var par = $(this).parent().parent();
  if ($(par).find(".liSec").size() == 0) {
  $(par).find("ul").append("<li class='liSec'>" + 
  "<div class='section'><span class='spanSec'>x-1</span><input type='hidden' class='secNumber' value='-1'> " +
  // "<span>新建课时</span>"+
  "<input type='text' value='新建课时'>" + 
  "<input type='hidden' class='secId' value='-1'> " +
  "</div>" +
  "<div>" + 
  "	<a href='javascript:void(0)' class='upSec'></a> " +
  "	<a href='javascript:void(0)' class='downSec'></a> " + 
  "	<a href='javascript:void(0)' class='addSection'>插入课时</a> " + 
  "	<a href='javascript:void(0)' class='delSection'></a> " + 
  "</div>" +
  "</li>");
  }
  else {
  $("<li class='liSec'>" + 
  "<div class='section'><span class='spanSec'>x-1</span><input type='hidden' class='secNumber' value='-1'> " +
  "<input type='text' value='新建课时'>" + 
  "<input type='hidden' class='secId' value='-1'> " +
  "</div>" +
  "<div>" + 
  "	<a href='javascript:void(0)' class='delSection'></a> " + 
  " <a href='javascript:void(0)' class='upSec'></a> " +
  " <a href='javascript:void(0)' class='downSec'></a> " + 
  " <a href='javascript:void(0)' class='addSection'>插入课时</a> " + 
  "</div>" +
  "</li>").insertAfter( $(par).find(".liSec").last() );
  }
  });
  $("body").on("click", "a.addChapter",function() {
  $("<li class='li_cha'>" +  
      "<div class='chapter'> " + 
        " <span class='spanCha'>第x章</span> " + 
        " <input type='hidden' class='chaNumber' value='0'> " + 
        " <input type='text' value='新增章节'> " +  
        " <input type='hidden' class='chaId' value='-1'> " + 
        " <a href='javascript:void(0)' class='tog'></a>" + 
      "</div>" + 
      " <div> " + 
        " <a href='javascript:void(0)' class='delChapter'></a> " + 
        " <a href='javascript:void(0)' class='addChapter'>插入章</a> " + 
        " <a href='javascript:void(0)' class='insertSec'>新增课时</a>" + 
      "</div>" +  
      "<ul>" +
      "<li class='liSec NoSec'>" +
       // "<div><a href='javascript:void(0)' class='addSection'>插入课时</a></div> " +
      "</li>").insertAfter( $(this).parent().parent() );
  });
  $("body").on("click", "a.delChapter",function() {
  var par = $(this).parent().parent();
  var chaId = $(par).find(".chaId");
  if (typeof(chaId) == "undefined" || chaId == null) 
  alert ("chaId is wrong");
  else if (chaId.attr("value") != -1)
  chaIdList.push(chaId.attr("value"));
  // $(par).remove();
  })
  $("body").on("click", "a.addSection",function() {

  var li =  $(this).parent().parent();
  $("<li class='liSec'>" + 
    "<div class='section'><span class='spanSec'>x-x</span><input type='hidden' class='secNumber' value='-1'> " +
      "<input type='text' value='新建课时'>" + 
      "<input type='hidden' class='secId' value='-1'> " +
    "</div>" +
    "<div>" + 
      "	<a href='javascript:void(0)' class='delSection'></a> " + 
      " <a href='javascript:void(0)' class='upSec'></a> " +
      " <a href='javascript:void(0)' class='downSec'></a> " + 
      " <a href='javascript:void(0)' class='addSection'>插入课时</a> " + 
    "</div>" +
  "</li>").insertAfter( li);
  })
  $("body").on("click", "a.delSection",function() {
  var par = $(this).parent().parent();
  var secId = $(par).find(".secId");
  if (typeof(secId) == "undefined" || secId == null) 
  alert ("secId is wrong");
  else if (secId.attr("value") != -1)
  secIdList.push(secId.attr("value"));
  })
  $("body").on("click", "a.upSec",function() {
  // 向上移动章节
  var nowEle = $(this).parent().parent();
  var preEle = $(this).parent().parent().prev();
  var classValue = $(preEle).attr("class");
  if (typeof(classValue) != "undefined" && classValue != null 
  && classValue.indexOf("liSec") > -1) {
  $(nowEle).insertBefore($(preEle));
  thisSecNode(this, -1);
  anoSecNode(preEle, 1);
  }
  })
  $("body").on("click", "a.downSec",function() {
  var nowEle = $(this).parent().parent();
  var preEle = $(this).parent().parent().next();
  var cV = $(preEle).attr("class");
  if (typeof(cV) != "undefined" && cV != null && cV.indexOf("liSec") > -1) {
  $(nowEle).insertAfter($(preEle));
  // 本节点向下 +1
  thisSecNode(this, 1);
  anoSecNode(preEle, -1);
  }
  })
  function thisNode(t, i){
  var span = $(t).parent().prev(".chapter").children(".spanCha")[0];
  var input = $(t).parent().prev(".chapter").children(".chaNumber")[0];
  var chaNumber = parseInt(input.value) + i;
  span.innerHTML = "第" + chaNumber+ "章";
  $(input).attr("value", chaNumber);

  // 更改课时的章节序号
  var secArr = $(t).parent().parent().find(".spanSec");	
  for (var y = 0; y < secArr.length; y++) {
  var s = secArr[y].innerHTML;
  secArr[y].innerHTML = chaNumber + s.substring(1, 3);
  }
  }
  function anotherNode(t, i) {
  var spanS = $($(t).children(".chapter")[0]).children(".spanCha")[0];
  var inputS = $($(t).children(".chapter")[0]).children(".chaNumber")[0]	;
  var chaNumberS = parseInt(inputS.value) + i;
  spanS.innerHTML = "第" + chaNumberS + "章";	
  $(inputS).attr("value", chaNumberS);
  // 更改课时的章节序号
  var secArr = $(t).find(".spanSec");	
  for (var y = 0; y < secArr.length; y++) {
  var s = secArr[y].innerHTML;

  secArr[y].innerHTML = chaNumberS + s.substring(1, 3);
  }
  }
  function thisSecNode(t, i) {
  var span = $(t).parent().prev(".section").children(".spanSec")[0];
  var input = $(t).parent().prev(".section").children(".secNumber")[0];

  var secNumber = parseInt(input.value) + i;
  span.innerHTML = span.innerHTML.charAt(0) + "-" + secNumber;
  $(input).attr("value", secNumber);    	
  }
  function anoSecNode(t, i) {
  var spanS = $($(t).children(".section")[0]).children(".spanSec")[0];
  var inputS = $($(t).children(".section")[0]).children(".secNumber")[0];
  var secNumberS = parseInt(inputS.value) + i;

  spanS.innerHTML = spanS.innerHTML.charAt(0) + "-" + secNumberS;
  $(inputS).attr("value", secNumberS);
  }
  // 当因移动章节位置时，更新章节序号
  $("body").on("click", "a.insertCha", function() {
  var ul = $(this).parent().parent().find(".ul_cha");
  update(ul);
  })
  $("body").on("click", "a.insertSec", function() {
  var ul = $(this).parent().parent().parent();
  update(ul);
  })
  function update(ul) {
  var li_cha = ul.children(".li_cha");
  for (var i = 0; i < li_cha.length; i++) {
  var divCha = $(li_cha[i]).children(".chapter")[0];
  var i1 = i+1;
  $($(divCha).children(".spanCha")[0]).html("第" + i1 + "章");
  $($(divCha).children(".chaNumber")[0]).attr("value", i1);

  var liSec = $($(li_cha[i]).children("ul")[0]).children(".liSec");
  for (var j = 0; j < liSec.length; j++) {
  var divSec = $(liSec[j]).children(".section")[0];
  var j1 = j+1;
  $($(divSec).children(".spanSec")[0]).html(i+1 + "-" + j1);
  $($(divSec).children(".secNumber")[0]).attr("value", j1);
  }
  }
  }
  // 不论是删除还是增加新内容，都要更新
  $("body").on("click", "a.upCha, a.downCha, a.delChapter, a.addChapter, a.upSec, a.downSec, a.delSection, a.addSection", function() {
  // 得到章节的ul标签
  var li =  $(this).parent().parent();
  var ul;
  if ($(li).attr("class").indexOf("liSec") > -1) {
  ul = $(li).parent().parent().parent();
  }
  else {
  ul = $(li).parent();
  }
  // 如果是删除章节
  if ($(this).attr("class").indexOf("delChapter") > -1 || $(this).attr("class").indexOf("delSection") > -1 
  || $(li).attr("class").indexOf("NoSec") > -1) {
  $(li).remove();
  }
  update(ul);
  })
  });
  var xmlHttp;

  function checkData(data, length) {
  if (data.replace(/(^\s*)|(\s*$)/g, "")=="") 
  return false;
  if (data.length > length)
  return false;

  return true;
  }

  function saveInfor() {
  var flag = true;

  var errMess = document.getElementsByTagName("span");
  for (var i = 0; i < errMess.length; i++) {
  if (errMess[i].className == "errorMess") {
  errMess[i].style.display = "none";
  }
  }

  document.getElementById("errorCouName").style.display="none";

  var couName = document.getElementById("couName").value;

  if (!checkData(couName, 40)) {
  document.getElementById("errorCouName").style.display="inline";
  flag = false;
  }

  var couPricUrl = document.getElementById("couPricUrl").getAttribute("src");
  var couBrief = document.getElementById("couBrief").value;
  //var couAnno = document.getElementById("couAnno").value; 暂时不要公告
  var couAnno = "无最新公告";
  var couGrade = document.getElementById("grade").value;
  var labIdT = document.getElementById("type");
  var labId;
  if (labIdT == null)
  labId = "-1";
  else
  labId = labIdT.value;
  var chaList = document.querySelectorAll(".chapter");
  var chaString = [];
  var i;
  for (i = 0; i < chaList.length; i++) {
  var j;
  var chaName;
  var chaId;
  var chaNumber;
  for (j = 0; j < chaList[i].childNodes.length; j++) {
  var childNode = chaList[i].childNodes;

  if (childNode[j].nodeName == "input" || childNode[j].nodeName == "INPUT") {

  if (childNode[j].getAttribute("type") == "text") {
  chaName = childNode[j].value;
  if (!checkData(chaName, 50)) {
  var err = document.createElement("span");
  err.className = "errorMess";
  err.innerHTML = "章节信息不能为空,且不可超过50字符";
  chaList[i].insertBefore(err, childNode[j+1]);
  flag = false;
  }
  } else if (childNode[j].getAttribute("type") == "hidden") {
  if (childNode[j].getAttribute("class").indexOf("chaId") > -1) {
  chaId =  childNode[j].value;	
  } else if (childNode[j].getAttribute("class").indexOf("chaNumber") > -1){
  chaNumber = childNode[j].value;
  }

  } 
  }
  }
  chaString.push({"chaName" :chaName, "chaId" : chaId, "chaNumber" : chaNumber});
  }
  var secList = document.querySelectorAll(".section");
  var secString = [];
  for (i = 0; i < secList.length; i++) {
  var j = 0;
  var secName;
  var secId;
  var secNumber;
  for (j = 0; j < secList[i].childNodes.length; j++) {
  var childNode = secList[i].childNodes;

  if (childNode[j].nodeName == "input" || childNode[j].nodeName == "INPUT") {
  if (childNode[j].getAttribute("type") == "text"){
  secName = childNode[j].value;
  if (!checkData(secName, 50)) {
  var err2 = document.createElement("span");
  err2.innerHTML = "课时信息不能为空,且不可超过50字符";
  err2.className="errorMess";
  secList[i].insertBefore(err2, childNode[j+1]);
  flag = false;
  }	
  }
  else if (childNode[j].getAttribute("type") == "hidden") 
  if (childNode[j].getAttribute("class").indexOf("secId") > -1) 
  secId = childNode[j].value;	
  else if (childNode[j].getAttribute("class").indexOf("secNumber") > -1)
  secNumber = childNode[j].value;			
  } 
  }
  var chaId = secList[i].parentNode.parentNode.parentNode.querySelector(".chaId").getAttribute("value");
  var chaNumber =  secList[i].parentNode.parentNode.parentNode.querySelector(".chaNumber").getAttribute("value");
  secString.push({"secName" : secName, "secId" : secId, "secNumber" : secNumber, "chaId" : chaId, "chaNumber" : chaNumber});
  }

  if (!flag) {
  return ;
  }
  var couId = '<%=cou.getCouId()%>';
  var json = {"couId":couId,"couName":couName, "couPricUrl" : couPricUrl, "couBrief" : couBrief,  
  "couAnno" : couAnno, "couGrade" : couGrade, "labId" : labId, 
  "upChaList" : chaString, "upSecList" : secString,
  "delChaList" : chaIdList, "delSecList" : secIdList};
  var data=JSON.stringify(json);
  //alert(json.couName +"" + json.couPricUrl + "" + json.couBrief + "" + json.couAnno);
  createXmlHttpRequest();
  doJSON(data);
  }
  function createXmlHttpRequest() {
  if (window.ActiveXObject) 
  xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
  else if (window.XMLHttpRequest) 
  xmlHttp = new XMLHttpRequest();
  }
  function handleStateChange() {
  if (xmlHttp.readyState == 4) {
  if (xmlHttp.status == 200) {
  alert("保存成功");
  window.location.href="/wikestudy/dist/jsp/teacher/common/my_courses_query";

  }else if (xmlHttp.status == 202) {
  alert("请填写完整课程名和章节信息(不超过40字)");
  }
  }
  }
  function handleStateChangeS() {
  if (xmlHttp.readyState == 4) 
  if (xmlHttp.status == 200) {
  //   	alert("保存成功");
  window.location.reload();
  }

  }
  function doJSON(json) {
  var url = "course_update";
  createXmlHttpRequest();
  xmlHttp.open("POST", url, true);
  xmlHttp.onreadystatechange = handleStateChange; 
  xmlHttp.setRequestHeader("Content-Type",
  "application/x-www-form-urlencoded");
  xmlHttp.send("json=" + json);
  }
  </script>
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