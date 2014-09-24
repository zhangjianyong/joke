<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html><html lang=zh>
<head>
	<title>一笑千金</title>
	<%@ include file="../jscss.jsp" %>
</head>
<body>
<%@ include file="../top.jsp" %>
<%@ include file="../head.jsp" %>
<div class="i_main_bg">
	<div class="i_main">
    	<!--  左侧-->
            <div class="i_main_left">
                <!--  抽奖  -->
                <div class="layer_luck_bg">
                    <div class="layer_luck_list">
                        <ul>
                            <li id="0" class="boder_bg"><span class="one1"></span></li>
                            <li id="1"><span class="one2"></span><span class="one3"></span></li>
                            <li id="2"><span class="one2"></span><span class="one3"></span></li>
                            <li id="3"><span class="one2"></span><span class="one3"></span></li>
                            <li id="4"><span class="one2"></span><span class="one3"></span></li>
                            <li id="5"><span class="one2"></span><span class="one3"></span></li>
                            <li id="15"><span class="one2"></span><span class="one3"></span></li>
                            <li class="li_center">
                                <p class="word font14">点评${config.score_use_per_draw }次笑话，可获得一次抽奖机会...<br>
                                <span id="drawtimes" class="color_red fontbold">你还有<em>--</em>次抽奖机会！</span></p>
                            </li>
                            <li id="6"><span class="one2"></span><span class="one3"></span></li>
                            <li id="14"><span class="one2"></span><span class="one3"></span></li>
                            <li class="li_center li_bottom">
                                <p class="luck_botton"><a id="draw" href="javascript:void(0);">立即抽奖</a></p>
                            </li>
                            <li id="7"><span class="one2"></span><span class="one3"></span></li>
                            <li id="13"><span class="one2"></span><span class="one3"></span></li>
                            <li id="12"><span class="one2"></span><span class="one3"></span></li>
                            <li id="11"><span class="one2"></span><span class="one3"></span></li>
                            <li id="10"><span class="one2"></span><span class="one3"></span></li>
                            <li id="9"><span class="one2"></span><span class="one3"></span></li>
                            <li id="8"><span class="one1"></span></li>
                        </ul>
                        <div class="clear"></div>
                    </div>
                </div>
                <!-- 结束 抽奖  -->
                <script type="text/javascript">ad_show('16');</script>
            </div>
            <div class="i_main_right">
                <ul>
                    <li><script type="text/javascript">ad_show('17');</script></li>
                    <li><script type="text/javascript">ad_show('18');</script></li>
                </ul>
                <div class="clear"></div>
            </div>
        <div class="clear"></div>
    </div>
</div>
<script src="/static/js/draw.js" type="text/javascript"></script>
<%@ include file="../foot.jsp" %>
</body>
</html>