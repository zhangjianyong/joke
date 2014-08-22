<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<style type="text/css">
	#sharediv{
	    background-color:#FFF;
	    border:1px solid #888;
	    display:true;
	    left:40%;
	    margin:-300px 0 0 -100px;
	    padding:12px;
	    position:fixed !important; 
	    position:absolute;
	    top:50%;
	    z-index:11;
	}
</style>
<div id="sharediv" class="layer_bshare_bg">
	<div class="layer_bshare_name font14 color_white">推上西班牙漫画家lucas的脑洞，总让人想不到的结局……<div class="bshare_close"><a class="close" href="javascript:void(0);"></a></div></div>
    <div class="layer_bshare_main">
    	<p>发送分享链接：</p>
        <p>成功生成分享链接，复制以下链接发给QQ、飞信好友吧！</p>
        <div class="layer_bshare_textbg">
        	<div style="display:none;" class="sucess">复制成功</div>
        	<input id="clip_text" type="text" class="text" value="${website }/article/${param.aid }/${uid }">
            <span class="botton"><a id="clip">复制链接</a></span>
        	<div class="clear"></div>
        </div>
        <p>分享到微博、社区：</p>
        <p><span class="bshare_span">将已生成的分享链接一键发送到：</span><span class="layer_bshare_icon"><a href="#" class="qq"></a><a href="#" class="kongjian"></a><a href="#" class="weibo"></a><a href="#" class="pyq"></a></span>
        </p><div class="clear"></div>
        <p></p>
        <div class="layer_bshare_yj">
        	<div class="bshare_jt"></div>
            分享有奖：<br>
            1. 浏览并评论5个笑话，就可以获得1次抽奖机会。<br>
            2. 每天最多可以获得3次抽奖机会。<br>
            3. 抽奖可以免费获得金币，金币可以直接兑换成钱。
        </div>
        <p class="color_red"><a class="close" href="javascript:void(0);">取消分享</a></p>
    </div>
</div>
<script type="text/javascript">
$('#clip').zclip({
	path:'${config.system_resource_url }/static/flash/ZeroClipboard.swf',
	copy:$('#clip_text').val(),
	afterCopy:function(){
		$('.sucess').slideDown(300).delay(800).slideUp(400);
	}
});
</script>