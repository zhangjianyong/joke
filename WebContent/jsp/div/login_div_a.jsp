<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<style type="text/css">
	#logindiv{
	    display:block;
	    left:50%;
	    margin:-143px 0 0 -243px;
	    position:fixed !important; 
	    position:absolute;
	    top:50%;
	    z-index:11;
	}
</style>
<div id="logindiv" class="layer_login_nr">
	<div id="logindivclose" class="layer_login_close"><a href="#"></a></div>
    <p class="font20">欢迎加入一笑千金 </p>
    <p class="font14 color_66">一笑千金里收录了最全最搞笑的笑话专辑，看笑话还能抽奖...</p>
    <p class="layer_login_qq"><a href="${config.system_control_url }/qqbind?t=${param.to }" target='_blank'></a></p>
    <p class="font16">请使用QQ账号登录 </p>
</div>
<script type="text/javascript">
$("#logindivclose").click(function(){
	$("#mask").hide();
	$("#logindiv").hide();
});
</script>