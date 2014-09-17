<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html><html lang=zh>
<head>
	<title>${config.system_website_name}-发布笑话</title>	
	<%@ include file="jscss.jsp" %>
	<style>
		#dropzone {
		    background: #ccccc;
		    width: 150px;
		    height: 50px;
		    line-height: 50px;
		    text-align: center;
		    font-weight: bold;
		}
		#dropzone.in {
		    width: 600px;
		    height: 200px;
		    line-height: 200px;
		    font-size: larger;
		}
		#dropzone.hover {
		    background: lawngreen;
		}
		#dropzone.fade {
		    -webkit-transition: all 0.3s ease-out;
		    -moz-transition: all 0.3s ease-out;
		    -ms-transition: all 0.3s ease-out;
		    -o-transition: all 0.3s ease-out;
		    transition: all 0.3s ease-out;
		    opacity: 1;
		}
	</style>
</head>
<body>
<%@ include file="top.jsp" %>
<%@ include file="head.jsp" %>
<div class="i_main_bg">
	<div class="i_main">
    	<!--  左侧-->
    	<div class="pub_main_left">
        	<div class="pub_title_p"><span class="pub_title_word">笑话类别： </span><span class="pub_title_feilei"><input type="hidden" name="type" class="j_data" value="pic"><input name="t" type="radio" value="pic"> 图片笑话<input name="t" type="radio" value="text" class="margin_left10"> 文字笑话</span><div class="clear"></div></div>
        	<div class="pub_title_p margin_top30"><span class="pub_title_word">笑话标题： </span><span><input name="title" type="text"  class="zc_text pub_title_text j_data"></span><div class="clear"></div></div>
        	<div class="pub_title_p margin_top40" id="content_text" style="display:none;"><span class="pub_title_word">笑话内容： </span><span><textarea name="content" cols="" rows="" class="zc_text pub_title_textarea color_99">分享一切好笑的事情</textarea></span><div class="clear"></div></div>
            <div class="pub_pic">
                <div class="pub_pic_botton">
                	图片<input id="picpath" type="hidden" name="picpath" class="j_data"/>
            		<input id="fileupload" type="file" name="pic" data-url="${config.system_control_url }/upload" class="pub_pic_file">
            	</div>
                <div class="pub_pic_down" id="content_pic">
                	<div class="file">图片可以拖到此位置</div>
                	<img style="max-width: 200px;max-height: 200px;">
                </div>
            </div>
            <div class="pub_botton"><a id="publish" href="javascript:void(0);">马上投稿</a></div>
        </div>
    	<!--  右侧-->
        <div class="pub_main_right">
        <p class="font14 fontbold">投稿规则</p>
            为了营造良好的投稿秩序，今后恶意投稿将会被强制禁稿，每投一条封禁4小时，若累计10条之后的每条封禁一天，超过20条后直接封禁两周。<br/><br/>
            所谓无规矩不成方圆，我一笑千金部落也有必须要遵守的法典哦！<br/>
            1、一笑千金是笑话网站，请不要发表与笑话无关的内容欺骗小伙伴们的感情啦！<br/>
            2、不能涉及政治、色情、暴力、广告等违法违规内容；<br/>
            3、不能包含邮件地址、电话号码、地址等联系信息；<br/>
            4、文字笑话必须是原创，且字数小于200，身边的搞笑趣事、糗事等都可以，但是把其他网站的文字笑话发到一笑千金，是不可以喔！<br/>
            5、单张图片、动态图的不得超过1M；<br/>
            6、图片要清晰，尽量不要带有其他网站的水印；<br/>
            7、不要和以前的内容重复；<br/>
            8、一笑千金部落会严格按照规范，公平审稿；<br/>
            <p class="color_red"><a href="#">有问题请联系我们</a></p>
        </div>
        <div class="clear"></div>
    </div>
</div>
<script type="text/javascript" src="${config.system_resource_url }/static/js/jquery.iframe-transport.js" ></script>
<script type="text/javascript" src="${config.system_resource_url }/static/js/jquery.ui.widget.js" ></script>
<script type="text/javascript" src="${config.system_resource_url }/static/js/jquery.fileupload.js" ></script>
<script src="${config.system_resource_url }/static/js/publish.js" type="text/javascript"></script>
<%@ include file="foot.jsp" %>
</body>
</html>