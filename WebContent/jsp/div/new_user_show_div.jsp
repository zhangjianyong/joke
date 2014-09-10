<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<c:if test="${firstcome==true }">
<style type="text/css">
	#newusershowdiv{
	    display:block;
	    left:50%;
	    margin:-300px 0 0 -216px;
	    position:fixed !important; 
	    position:absolute;
	    top:50%;
	    z-index:11;
	}
</style>
<div id="newusershowdiv" class="layer_pic_bg">
	<div class="layer_pic_top">
    	<div class="layer_pic_close"><a id="newuserdivclose" href="#"></a></div>
    </div>
    <div class="layer_pic_info">
        <p class="font16">笑呵呵赚金币</p>
        <p>一笑千金赚积分说明：<br>
    	1.	每日查看并评论${config.score_use_per_draw }个笑话（赞或踩即可），即可获得一次抽奖机会。<br>
    	2.	每日每人最多获得${config.draw_count_per_day }次抽奖机会；抽奖机会当日有效。<br>
    	3.	奖品最高为1000个金币，可以用来购物、电话充值、还信用卡等。</p>
        <p class="layer_pic_botton"><a id="addfavorite" href="javascript:void(0);"></a></p>
    </div>
</div>
<script type="text/javascript">
var h = '${config.system_website_url }';
var l = '${config.system_website_name }';
$("#mask").show();
$("#newuserdivclose").on("click",function(){
	$("#newusershowdiv").hide();
	$("#mask").hide();
});
$("#addfavorite").on("click",function(){
	try{
	    window.external.addFavorite(h, l);
	}catch (e){
	    try{
	        window.sidebar.addPanel(h, l, "");
	    }catch (e){
	        alert("你的浏览器不支持这个功能，请使用Ctrl+D进行添加");
	    }
	}
});
</script>
</c:if>