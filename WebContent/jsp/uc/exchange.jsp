<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang=zh>
<head>
	<title>个人中心-我的资产</title>
	<%@ include file="../jscss.jsp" %>
</head>
<body>
<%@ include file="../top.jsp" %>
<%@ include file="../head.jsp" %>
<!--  主体部分  -->
<div class="i_main_bg">
	<div class="zichan_bg">
	    <div class="i_main zichan_main">
	        <!--  左侧-->
	        <form>
	    		<input type="hidden" id="uc_bar_position" value="exchange"/>
	    	</form>
	    	<%@ include file="ucenter_bar.jsp" %>
	        <!--  右侧-->
	        <div class="zichan_right">
            	<div class="zichan_right_name font14 fontbold">资产兑换</div>
                <div class="zc_right_main">
                    <p class="font14 line_height30">您的小金库有：<span class="color_red fontbold">${account.s2 }</span>金</p>
                    <div class="dh_main"><c:choose><c:when test="${not empty alipay }">
                    	<p class="dh_name">您绑定的支付宝真实姓名：${alipay.ext1 } <a  id="unbindalipay" href="javascript:void(0);" class="color_green">解除绑定</a> </p>
                        <p class="zc_text_bg font14"><span>兑换集分宝：</span><span><input name="" type="text" class="zc_text"></span><span>&nbsp;个</span></p>
                        <p class="zc_login_botton"><a href="#">立即兑换</a></p></c:when>
                        <c:otherwise><p class="dh_name"><a  href="/alipaybind" class="color_red fontbold">绑定</a>支付宝,可随时将积分对换成集分宝</p></c:otherwise>
                    </c:choose></div>
                </div>
            </div>
	        <div class="clear"></div>
	    </div>
	</div>
<script type="text/javascript" src="/static/js/exchange.js"></script>
<%@ include file="foot.jsp" %>
</div>
</body>
</html>