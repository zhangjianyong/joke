<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html><html lang=zh>
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<title>${config.system_website_name}-签到赚金币</title>
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
                               <p class="word font14">点评${config.score_use_per_draw }个笑话，可获得1次签到机会.每日最多${config.draw_count_per_day }次,当天有效.<br>
                               <span id="drawtimes" class="color_red fontbold">你还有<em>--</em>次签到机会！</span></p>
                           </li>
                           <li id="6"><span class="one2"></span><span class="one3"></span></li>
                           <li id="14"><span class="one2"></span><span class="one3"></span></li>
                           <li class="li_center li_bottom">
                               <p class="luck_botton" id="draw_button"><a id="divshow" href="javascript:void(0);">立即签到</a></p>
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
               	<div class="draw_name fontbold font14">今日签到排行</div><ul class="draw_list" style="overflow: hidden;width: 620px;height: 78px;" id="draw_list">
                   <c:forEach var="o" items="${draws }" varStatus="s">
                   	<li><span class="word">${o.nick }</span><span class="word1">${o.wealth }个${config.score_unit }</span><span class="word2">${o.time }</span></li>
                   </c:forEach></ul>
               </div>
               ${ads.ad16.content }
           </div>
           <div class="i_main_rightbg">
            <div class="i_main_right">
                <ul>
                    <li>${ads.ad17.content }</li>
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
                    <li>${ads.ad18.content }</li>
                </ul>
                <div class="clear"></div>
        	</div>
    	</div>
        <div class="clear"></div>
    </div>
</div>
<style type="text/css">
   #bg{ display:none; position: fixed;  top: 0px;  left: 0px;  right:0px; bottom:0px;  background-color: black;  z-index:1001;  -moz-opacity: 0.7;  opacity:.70;  filter: alpha(opacity=70);}  
   #draw_success{ display:none; position: absolute;  top: 25%;  left: 35%;  z-index:1002;  }  
</style>
<div id="bg"></div>
<div id="draw_success"  class="layer_bshare_bg" style="width:400px; height:auto;">
	<div class="layer_bshare_name font14 color_white">恭喜中奖<div class="bshare_close"><a href="javascript:void();"></a></div></div>
    <div id="draw_msg" class="layer_bshare_main" style="width:400px; font-size:14px; font-weight:bold; text-align:center;"></div>
    <div class="layer_bshare_main" style="width:400px; padding-bottom:30px;">
		<table width="100%" border="0">
			<tbody><tr>
			<td align="center">${ads.ad104.content }</td>
			</tr>
			</tbody>
		</table>
	</div>
</div>
<script src="${config.system_resource_url }/static/js/draw.js" type="text/javascript"></script>
<%@ include file="../foot.jsp" %>
</body>
</html>