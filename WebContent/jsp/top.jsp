<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<div id="mask" class="overDiv"></div>
<LINK href="${config.system_resource_url }/static/favicon.ico" type="image/x-icon" rel=icon>
<LINK href="${config.system_resource_url }/static/favicon.ico" type="image/x-icon" rel="shortcut icon">
<div id="favorite" style="height: 40px;overflow: hidden;background-color: #fff4db;text-align: center; line-height: 40px;display:none;">
		<div style="width:960px; margin: 0 auto; position: relative;"><p class="close_favorite" style="position: absolute; top:0; right:0;cursor: pointer;">关闭</p>按 <strong>Ctrl+D</strong>，把${config.system_website_name }放入收藏夹！<span><a href="javascript:void(0)" class="close_favorite" style="text-decoration: underline; color: #e02f2f">不再提醒</a></span></div>
</div>
<div class="index_top_bg" style="display:none;">
	<div class="index_top">
    	<ul>
        	<li><a href="/" class="pos index">首页</a></li>
            <li><a href="/pic" class="pos pic">搞笑图片</a></li>
            <li><a href="/text" class="pos text">爆笑文字</a></li>
            <li><a href="/ashamed" class="pos ashamed">糗事大全</a></li>
            <li><a href="/shortcut" target="_blank">保存到桌面</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>
<div class="i_top_bg" id="top">
	<div id="msg_top" ></div>
	<form>
		<input id="go_url" type="hidden" value="/"></input>
		<input id="top_s1" type="hidden" value=""></input>
	</form>
	<div id="nouser"  class="i_top">您好！<em class="top_user_name">朋友</em><a href="${config.system_control_url }/qqbind" class="qq">QQ登录</a><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2772231186&site=qq&menu=yes">联系客服</a></div>
	<div id="haveuser" class="i_top" style="display:none">
		您好！<em class="top_user_name"></em><a href="${config.system_control_url }/logout">退出</a>
		<em class="margin_left10">目前拥有<a href="/uc/score"><em class="color_red englist_one"  id="top_score">--</em></a> ${config.score_unit }</em>
		<a href="/uc/exchange" class="margin_left10">兑换集分宝</a>
		<a href="/uc/score" class="margin_left10">个人中心</a>
		<a href="/lottery/draw" class="margin_left10 color_red englist_one">签到(<b id="top_drawtimes">--</b>)</a>
		<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2772231186&site=qq&menu=yes">联系客服</a></div>
</div>