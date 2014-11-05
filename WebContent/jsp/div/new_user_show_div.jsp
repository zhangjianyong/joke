<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
$("#mask").show();
$("#newuserdivclose").on("click",function(){
	$("#newusershowdiv").hide();
	$("#mask").hide();
});
</script>
</c:if>