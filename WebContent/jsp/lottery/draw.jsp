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
	            <div class="left_one bg_radius">
	            	<div class="one_name">小贴士</div>
	                <div class="one_nr">金币可以用来兑换集分宝，1:1兑换。集分宝可以用来购物、电话充值、还信用卡等<br>
					您目前拥有:&nbsp;<em class="color_red englist_one" id="draw_page_score">--</em>&nbsp;${config.score_unit },&nbsp;<a style="color: red;" href="/uc/score">查看详情</a></div>
	            </div>
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
                                <p class="word font14">点评${config.score_use_per_draw }个笑话，可获得1次抽奖机会.每日最多${config.draw_count_per_day }次,当天有效.<br>
                                <span id="drawtimes" class="color_red fontbold">你还有<em>--</em>次抽奖机会！</span></p>
                            </li>
                            <li id="6"><span class="one2"></span><span class="one3"></span></li>
                            <li id="14"><span class="one2"></span><span class="one3"></span></li>
                            <li class="li_center li_bottom">
                                <p class="luck_botton" id="draw_button"><a id="divshow" href="javascript:void(0);">立即抽奖</a></p>
                                <p id="_code" style="display:none;width:260px;margin:20px auto 0; "><img style="float:left;cursor:pointer; "><input type="text" class="zc_text pub_title_text" style="width:50px; float:left; margin:0 10px;"><span class="pub_botton" style="float:left; padding:0;"><a>提交</a></span></p>
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
                <div class="draw_bg">
                	<div class="draw_name fontbold font14">中奖页面</div>
                    <ul class="draw_list"><c:forEach var="o" items="${draws }">
                    	<li><span class="word">${o.nick }抽中了</span><span class="word1">${o.wealth }个${config.score_unit }</span><span class="word2">${o.time }</span></li>
                    </c:forEach></ul>
                </div>
                <script type="text/javascript">ad_show('16');</script>
            </div>
            <div class="i_main_right">
                <ul>
                    <li><script type="text/javascript">ad_show('17');</script></li>
                    <div class="right_tuijian margin_top10">
		            	<div class="right_name font14 color_66 fontbold">一笑推荐</div>
		                <ul><c:forEach var="h" items="${hots }" end="0"><c:if test="${h.type eq 'PIC' }">
		                	<li>
		                    	<a href="/article/${h.id }" class="pic"><img src="${config.pic_domain }/article/90${h.pic }" height="62"></a>
		                        <a href="/article/${h.id }" class="font14">${h.title }</a>
		                        <p></p>
		                        <div class="time"><em>${h.nick }</em>${h.create_time }</div>
		                    </li></c:if><c:if test="${h.type eq 'TEXT' }"><li>
		                        <a href="/article/${h.id }" class="font14">${h.title }</a>
		                        <p>${h.content }</p>
		                        <div class="time"><em>${h.nick }</em>${h.create_time }</div>
		                    </li></c:if><c:if test="${h.type eq 'ASHAMED' }"><li>
		                        <a href="/article/${h.id }" class="font14"></a>
		                        <p>${h.content }</p>
		                        <div class="time"><em>${h.nick }</em>${h.create_time }</div>
		                    </li></c:if></c:forEach>
		                </ul>
		                <div class="clear"></div>
		            </div>
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