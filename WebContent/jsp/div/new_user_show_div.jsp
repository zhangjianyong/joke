<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<c:if test="${firstcome==true }">
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
<div id="newusershowdiv" class="layer_pic_bg">
	<div class="layer_pic_top">
    	<div class="layer_pic_close"><a id="newuserdivclose" href="#"></a></div>
   		 <p class="layer_pic_botton"><a href="/shortcut"></a></p>
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
</script>
</c:if>