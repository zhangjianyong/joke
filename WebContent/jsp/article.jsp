<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html><html lang=zh>
<head>
	<title>一笑千金-${article.title }</title>	
	<%@ include file="jscss.jsp" %>
</head>
<body>
<%@ include file="top.jsp" %>
<%@ include file="head.jsp" %>
<div class="i_main_bg">
	<div class="i_main">
    	<!--  左侧-->
    	<div class="i_main_left">
            <div class="left_two bg_radius margin_top0">
                 <div class="two_title">
						<div class="jiantou info_top20">
	                        <c:if test="${preId!=0 }"><span class="info_jiantou"><a id="next" class="pre" href="/article/${preId }?pos=next"></a></span></c:if>
	                        <c:if test="${nextId!=0 }"><span class="info_next"><a id="next" class="next" href="/article/${nextId }?pos=next">下一条</a></span></c:if>
	                        <div class="clear"></div>
						</div>
               	</div>
                <form>
                	<input id="pos" type="hidden" value="${param.pos }"/>
                </form>
                <c:if test="${article.type=='PIC' }"><div id="title" class="two_name">${article.title }</div>
	                <div id="content" class="two_info"><br><img src="${config.pic_domain }/${article.pic }" style="max-width: 559px;"/></div></c:if>
	            <c:if test="${article.type=='TEXT' }"><div id="title" class="two_name">${article.title }</div>
	                <div id="content" class="two_info">${article.content }<br></div></c:if>
	            <c:if test="${article.type=='ASHAMED' }"><div class="two_name"></div>
	                <div id="content" class="two_info">${article.content }<br></div></c:if>
                <div class="zhichi_bg">
                	<div class="chizhi_left"><a id="up" data="${article.id }" href="javascript:void(0);" class="color_1"><span class="one1"></span><b>${article.up }</b></a></div>
                    <div class="chizhi_left"><a id="down" data="${article.id }"  href="javascript:void(0);" class="color_1"><span class="one2"></span><b>${article.down }</b></a></div>
                    <div class="bshare_bg">
                    	<span><a id="share" href="javascript:void(0);" data="${article.id }">分享</a></span>
                    </div>
                </div>
                <script type="text/javascript">ad_show('10');</script>
            </div>
            <div class="info_gx_list bg_radius">
            	<ul>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <script type="text/javascript">ad_show('11');</script>
            <div class="info_advert_list">
            	<script type="text/javascript">ad_show('12');</script>
            	<script type="text/javascript">ad_show('13');</script>
            </div>
            <div class="info_gx_list bg_radius">
            	<ul>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                	<li><a href="#"><img src="/static/images/demoimg/p_10.gif"><span>妹子等公交等的好累</span></a></li>
                </ul>
                <div class="clear"></div>
            </div>
        </div>
    	<!--  右侧-->
        <div class="i_main_right">
        	<script type="text/javascript">ad_show('6');</script>
        	<script type="text/javascript">ad_show('7');</script>
            <div class="right_name font14 color_66 fontbold">搞笑图片推荐</div>
            <div class="right_list">
            	<ul><c:forEach var="h" items="${hots }">
                	<li><a href="/article/${h.id }"><img style="min-width: 90px;max-height:90px;" src="${h.content }"></a></li>
                </c:forEach></ul>
                <div class="clear"></div>
            </div>
        	<script type="text/javascript">ad_show('8');</script>
        	<script type="text/javascript">ad_show('9');</script>
            <div class="right_botton_bg margin_top10">
            	<span class="top"><a href="#top"></a></span>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<script src="${config.system_resource_url }/static/js/article.js" type="text/javascript"></script>
<%@ include file="foot.jsp" %>
</body>
</html>