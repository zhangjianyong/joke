<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="zichan_left">
	<ul>
    	<li><a id="bar_score"  href="/uc/score" class="J_uc_bar">我的资产</a></li>
    	<li><a id="bar_exchange" href="/uc/exchange" class="J_uc_bar">资产兑换</a></li>
    	<li></li>
    </ul>
    <div style="padding: 0 0 0 10px;">${ads.ad1.content }</div>
   	<div style="padding: 20px 0 0 10px;">${ads.ad2.content }</div>
</div>
<script type="text/javascript">
var selector = $("#uc_bar_position").val();
$(".J_uc_bar.hover").removeClass("hover");
$("#"+selector).addClass("hover");
</script>