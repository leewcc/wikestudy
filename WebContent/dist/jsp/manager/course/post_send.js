// 推荐课程	
function relW(couId) {
	var recGrade = document.getElementById(couId).value;
	postUrl(  "RecommCourseServlet?couId=" + couId + "&grade=" + recGrade);
}

// 取消推荐课程
function nRelW(couId) {
	postUrl("RecommCourseServletNon?couId="+couId);
}

// 发送数据
function postUrl(url) {
	xmlHttp = createXMLHttpRequest();
	
	xmlHttp.open("GET", url, true);  // 异步处理返回   
	xmlHttp.onreadystatechange = handleStateChange; 
	xmlHttp.setRequestHeader("Content-Type",  
	        "application/x-www-form-urlencoded;");  
	xmlHttp.send();  
}
	
function handleStateChange() {
	if (xmlHttp.readyState == 4) 
        if (xmlHttp.status == 200) {
        	alert("操作成功");
        	window.location.reload();
        }
        	
}
	
function createXMLHttpRequest() {  
    var xmlHttp;  
    if (window.XMLHttpRequest) {  
        xmlHttp = new XMLHttpRequest();  
        if (xmlHttp.overrideMimeType)  
            xmlHttp.overrideMimeType('text/xml');  
    } else if (window.ActiveXObject) {  
        try {  
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");  
        } catch (e) {  
            try {  
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
            } catch (e) {  
            }  
        }  
    }  
    return xmlHttp;  
}  