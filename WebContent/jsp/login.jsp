<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<%response.setStatus(HttpServletResponse.SC_NOT_FOUND);%>
<!DOCTYPE html><html lang=zh>
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<title>${config.system_website_name}-登录</title>
	<%@ include file="jscss.jsp" %>
</head>
<body>
<%@ include file="top.jsp" %>
<%@ include file="head.jsp" %>
<div class="i_main_bg"></div>
<script type="text/javascript">
J_utils.login();
</script>
</body>
</html>