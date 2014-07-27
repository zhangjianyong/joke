<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang=zh>
<head>
	<title>个人中心-账号绑定</title>
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
	    		<input type="hidden" id="uc_bar_position" value="binding"/>
	    	</form>
	    	<%@ include file="ucenter_bar.jsp" %>
	        <!--  右侧-->
	        <div class="zichan_right">
            	<div class="zichan_right_name font14 fontbold">我的资产</div>
                <div class="zc_right_main">
                    <p class="font14 line_height30">绑定支付宝账号后，可以将金币兑换成集分宝； </p>
                    <p class="line_height30 color_99">金库内的金，可以兑换成集分宝，集分宝可以还信用卡，缴水电煤费，淘宝购物抵现！<a href="/ucenter/exchange" class="color_red">兑换集分宝>></a></p>
                    <p class="zc_text_bg font14"><span>兑换集分宝：</span><span><input name="" type="text" class="zc_text"></span></p>
                    <p class="zc_text_bg font14"><span>兑换集分宝：</span><span><input name="" type="text" class="zc_text"></span></p>
                    <p class="zc_login_botton"><a href="#">登 录</a></p>
                </div>
            </div>
	        <div class="clear"></div>
	    </div>
	</div>
<script type="text/javascript" src="/static/js/score.js"></script>
<%@ include file="foot.jsp" %>
</div>
</body>
</html>