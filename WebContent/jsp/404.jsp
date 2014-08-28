<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<%response.setStatus(HttpServletResponse.SC_NOT_FOUND);%>
<!DOCTYPE html><html lang=zh>
<head>
	<title>${config.system_website_name}-404</title>
	<%@ include file="jscss.jsp" %>
</head>
<body>
<%@ include file="top.jsp" %>
<%@ include file="head.jsp" %>
<div class="error_bg">
	<p class="font20 fontbold">404 - 无法找到资源</p>
	<p>对不起，您访问的资源可能已被删除或不存在。</p>
    <p><em id="time">3</em>秒钟自动跳转到<a href="${config.system_website_url }">首页</a></p>
</div>
<script type="text/javascript">
var timer = setInterval(function(){
	var t = parseInt($("#time").text());
	if(t==0){
		window.location="${config.system_website_url}";
	}else{
		$("#time").text(t-1);
	}
},1000);
</script>
<%@ include file="foot.jsp" %>
</body>
</html>