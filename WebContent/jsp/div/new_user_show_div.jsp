<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${firstcome==false }">
<style type="text/css">
	#newusershowdiv{
	    display:block;
	    left:50%;
	    margin:-200px 0 0 -280px;
	    position:fixed !important; 
	    position:absolute;
	    top:50%;
	    z-index:11;
	}
</style>
<div id="newusershowdiv" class="layer_news_main">
	<div class="layer_news_name"><p class="font20">一笑千金签到领金币</p><p class="font14 fontbold">全拼域名（YiXiaoQianJin.com）</p></div>
    <div class="layer_news_info">
    	<a id="newuserdivclose" class="layer_news_close"></a>
    	<p>“一笑千金”是一个笑话网站，每日签到可以领取金币（金币1:1兑换为集分宝，集分宝可以<span class="fontbold color_red">兑换电话卡、还信用卡、淘宝购物</span></p>
        <p class="font14 fontbold padding_top20">签到流程：</p>
        <p class="font14 fontbold margin_top10">1.点评笑话</p>
        <p>每点评10条笑话，即可获得1次签到机会。</p>
        <div class="padding_top5"><span class="layer_pic"></span>（找到每条笑话下面的大拇指图标，随便点一下就可以了）<div class="clear"></div></div>
        <p class="font14 fontbold margin_top10">2.进入签到页面，即可签到</p>
        <p>点击页面右上角的“签到”，即可进去签到页面。签到100%可以获得金币（集分宝）。</p>
        <p class="font14 fontbold padding_top20">3.每日多次签到</p>
        <p>每人每天可以有3次签到机会。</p>
        <p class="layer_botton_news"><a href="/shortcut"></a></p>
    </div>
</div>

<script type="text/javascript">
$("#mask").show();
$("#newuserdivclose").on("click",function(){
	$("#newusershowdiv").hide();
	$("#mask").hide();
});
</script>
</c:if>