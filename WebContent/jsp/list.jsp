<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html><html lang=zh>
<head>
	<title>一笑千金</title>
	<%@ include file="jscss.jsp" %>
	<meta property="qc:admins" content="1540165517611011711162166375" />
</head>
<body>
<%@ include file="top.jsp" %>
<%@ include file="head.jsp" %>
<div class="i_main_bg">
	<div class="i_main">
    	<!--  左侧-->
    	<div class="i_main_left">
        	<div class="left_one bg_radius">
            	<div class="one_name">小贴士<div class="jiantou"><span class="jt_left"><a href="#"></a></span><span class="jt_right"><a href="#"></a></span></div></div>
                <div class="one_nr">神马？你居然还没有注册捧腹？注册的好处多多哦，可以参加捧腹活动赢取大奖，可以在个人中心收集喜爱的笑料，可以发图片发视频……<span class="color_red"><a href="#">快来注册吧！</a></span></div>
            </div><c:set var="pp" value="0"/>
            <c:forEach var="a"  items="${articles }"  varStatus="s" ><c:if test="${s.index%10==0 }"><c:set var="pp" value="${pp+1 }"/><div id="loadpage${pp }"></div></c:if><div class="left_two bg_radius page${pp }" <c:if test="${pp>1 }">style="display:none"</c:if>>
                <div class="two_name"><a href="/detail/${a.id }?pos=next" target="_blank">${a.title } </a></div>
                <div class="two_info">${a.summary }<br>
                <a href="/detail/${a.id }?pos=next" target="_blank"><c:choose><c:when test="${pp==1 }"><img src="${a.pic }" style="max-width: 559px;"/></c:when><c:otherwise><img data-src="${a.pic }" style="max-width: 559px;"/></c:otherwise></c:choose></a>
                <p class="gaoxiao color_99"><span>搞笑工厂</span><span>冷笑话</span></p>
                </div>
                <div class="zhichi_bg">
                	<div class="chizhi_left"><a href="javascript:void(0);" data="${a.id }?pos=up" class="color_1 updown up"><span class="one1"></span>${a.up }</a></div>
                    <div class="chizhi_left"><a href="javascript:void(0);" data="${a.id }?pos=down" class="color_1 updown down"><span class="one2"></span>${a.down }</a></div>
                    <div class="bshare-custom icon-medium"><a title="分享到" href="http://www.bShare.cn/" id="bshare-shareto" class="bshare-more">分享到</a><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/button.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script><a class="bshareDiv" onclick="javascript:return false;"></a><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
                </div>
            </div></c:forEach>
            <!-- 分页 -->
            <div id="loading" style="display: none;">正在加载..</div>
            <jsp:include page="/jsp/page.jsp">
            	<jsp:param value="${count }" name="count"/>
            	<jsp:param value="${page }" name="page"/>
            	<jsp:param value="/new" name="path"/>
            	<jsp:param value="/" name="first"/>
            </jsp:include>
            <script type="text/javascript">ad_show('5');</script>
        </div>
    	<!--  右侧-->
        <div class="i_main_right">
        	<script type="text/javascript">ad_show('1');</script>
        	<script type="text/javascript">ad_show('2');</script>
            <div class="right_name font14 color_66 fontbold">搞笑图片推荐</div>
            <div class="right_list">
            	<ul><c:forEach var="h" items="${hots }">
                	<li><a href="/detail/${h.id }"><img style="max-width: 90px;min-height:90px;" src="${h.pic }"></a></li>
                </c:forEach></ul>
                <div class="clear"></div>
            </div>
            <script type="text/javascript">ad_show('3');</script>
            <script type="text/javascript">ad_show('4');</script>
            <div class="right_botton_bg margin_top10">
            	<span class="top"><a href="#top"></a></span>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<script src="/static/js/list.js" type="text/javascript"></script>
<%@ include file="foot.jsp" %>
</body>
</html>