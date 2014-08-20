<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<div id="mask" ></div>
<div class="i_top_bg" id="top">
	<div id="msg_top" ></div>
	<form>
		<input id="go_url" type="hidden" value="/"></input>
	</form>
	<div id="nouser"  class="i_top"><a href="${config.system_control_url }/qqbind" class="qq">QQ登录</a></div>
	<div id="haveuser" class="i_top" style="display:none">
		您好！<span id="username"></span><a href="javascript:void(0);" onclick="loginout();">退出</a>
		<em class="margin_left10">目前拥有<em class="color_red englist_one"  id="top_score">--</em>金</em>
		<a href="/uc/exchange" class="margin_left10">兑换集分宝</a>
		<a href="/uc/score" class="margin_left10">个人中心</a>
		<a href="/lottery/draw" class="margin_left10 color_red englist_one">抽奖(<b id="top_drawtimes">0</b>)</a></div>
</div>
<script src="/static/js/top.js" type="text/javascript"></script>