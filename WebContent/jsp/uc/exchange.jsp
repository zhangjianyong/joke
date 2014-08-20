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
	    		<input type="hidden" id="uc_bar_position" value="bar_exchange"/>
	    	</form>
	    	<%@ include file="uc_bar.jsp" %>
	        <!--  右侧-->
	        <div class="zichan_right">
            	<div class="zichan_right_name font14 fontbold">资产兑换</div>
                <div class="zc_right_main">
                    <p class="font14 line_height30">您的小金库有：<span id="score" class="color_red fontbold">${account.s2 }</span>金</p>
                    <div class="dh_main">
                        <p class="zc_text_bg font14"><span style="padding-left:28px;">支付宝：</span><span><input name="act" type="text" class="zc_text post_data" value="${alipayAccount.account }"></span><span></span></p>
                        <p class="zc_text_bg font14"><span>兑换集分宝：</span><span><input id="val" name="val" value="${account.s2 }" type="text" class="zc_text post_data" style="width:80px;"></span><span>&nbsp;个</span></p>
                        <p class="zc_login_botton"><a id="exchange" href="javascript:void(0);">立即兑换</a></p>
                    </div>
                </div>
            </div>
	        <div class="clear"></div>
	    </div>
	</div>
<script type="text/javascript" src="/static/js/exchange.js"></script>
<%@ include file="uc_foot.jsp" %>
</div>
</body>
</html>