<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="zichan_left">
	<ul>
    	<li><a id="score"  href="/ucenter/score" class="J_uc_bar">我的资产</a></li>
    	<li><a id="exchange" href="/ucenter/exchange" class="J_uc_bar">资产兑换</a></li>
    	<!--<li><a id="binding" href="/ucenter/binding" class="J_uc_bar">兑换账号</a></li> -->
    </ul>
</div>
<script type="text/javascript">
var selector = $("#uc_bar_position").val();
$(".J_uc_bar.hover").removeClass("hover");
$("#"+selector).addClass("hover");
</script>