<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html><html lang=zh>
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<title>${config.system_website_name}-一个看笑话还能赚金币的网站，笑话，搞笑图片，笑话大全，爆笑文字，冷笑话，幽默笑话，爆笑笑话，爆笑图片，搞笑笑话，内涵笑话，搞笑图集，gif动态图片，热门笑话，笑话吧</title>
	<%@ include file="jscss.jsp" %>
</head>
<body>
<%@ include file="top.jsp" %>
<%@ include file="head.jsp" %>
<div class="i_main_bg">
	<div class="i_main_list">
    	<ul>
        	<li><a href="${config.system_website_url }/${param.t }/today" id="list_top_today">今日最热</a></li>
        	<li><a href="${config.system_website_url }/${param.t }/new" id="list_top_new">最新</a></li>
        	<li><a href="${config.system_website_url }/${param.t }/week" id="list_top_week">7天最热</a></li>
        	<li><a href="${config.system_website_url }/${param.t }/month" id="list_top_month">30天最热</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <script type="text/javascript">$("#list_top_"+"${param.o}").addClass("hover");</script>
	<div class="i_main">
    	<!--  左侧-->
    	<div class="i_main_left">
        	<div class="left_one bg_radius">
            	<div class="one_name">小贴士</div>
                <div class="one_nr">神马？你居然还没有注册一笑千金？注册的好处多多哦，可以参加一笑千金的抽奖活动，每日看笑话，还能赚金币啊！<a style="color: red;" href="/lottery/draw" target="_blank">查看详情</a></div>
            </div><script type="text/javascript">ad_show('23');</script><c:set var="pp" value="0"/>
            <c:forEach var="a"  items="${articles }"  varStatus="s" ><c:if test="${s.index%10==0 }"><c:set var="pp" value="${pp+1 }"/><div id="loadpage${pp }"></div></c:if><div class="left_two bg_radius page${pp }" <c:if test="${pp>1 }">style="display:none"</c:if>>
                <div class="two_title">
                	<div class="title_more"><a href="/article/${a.id }" target="_blank">查看全文</a></div>
                	<span class="name_pic"><img src="${config.system_resource_url }/static${a.avatar }"></span>
                    <span class="padding_left10">${a.nick }</span>
                    <span class="padding_left10 englist color_99">${a.create_time }</span>
                </div>
                <c:if test="${a.type=='PIC' }"> <div class="two_name"><a href="/pic">【搞笑图片】</a><a href="/article/${a.id }" target="_blank">${a.title } </a></div><div class="two_info"><br>
	                <a href="/article/${a.id }" target="_blank"><c:choose><c:when test="${pp==1 }"><img src="${config.pic_domain }/article/0${a.pic }" style="max-width: 559px;"/></c:when><c:otherwise><img data-src="${config.pic_domain }/article/0${a.pic }" style="max-width: 559px;"/></c:otherwise></c:choose></a></div></c:if>
	            <c:if test="${a.type=='TEXT' }"> <div class="two_name"><a href="/text">【爆笑文字】</a><a href="/article/${a.id }" target="_blank">${a.title } </a></div><div class="two_info">${a.content }<br></div></c:if>
	            <c:if test="${a.type=='ASHAMED' }">
	                <div class="two_info"><a href="/ashamed">【糗事大全】</a>${a.content }<br></div></c:if>
                <div class="zhichi_bg">
                	<div class="chizhi_left"><a href="javascript:void(0);" data="${a.id }?pos=up" class="color_1 updown up"><span class="one1"></span>${a.up }</a></div>
                    <div class="chizhi_left"><a href="javascript:void(0);" data="${a.id }?pos=down" class="color_1 updown down"><span class="one2"></span>${a.down }</a></div>
                </div>
            </div>
            </c:forEach>
            <!-- 分页 -->
            <div id="loading" style="display: none;">正在加载..</div>
            <jsp:include page="/jsp/page.jsp">
            	<jsp:param value="${count }" name="count"/>
            	<jsp:param value="${page }" name="page"/>
            	<jsp:param value="/${param.t }/${param.o }" name="path"/>
            	<jsp:param value="/" name="first"/>
            </jsp:include>
        </div>
    	<!--  右侧-->
        <div class="i_main_right">
        	<c:if test="${param.adv eq 0 }">${ads.ad20.content }</c:if>
        	<c:if test="${param.adv ne 0 }">${ads.ad1.content }</c:if>
        	<div class="right_tuijian margin_top10">
            	<div class="right_name font14 color_66 fontbold">一笑推荐</div>
                <ul><c:forEach var="h" items="${hots }" end="0">
                	<li>
                    	<a href="/article/${h.id }" class="pic"><img src="${config.pic_domain }/article/90${h.pic }" height="62"></a>
                        <a href="/article/${h.id }" class="font14">${h.title }</a>
                        <p></p>
                        <div class="time"><em>${h.nick }</em>${h.create_time }</div>
                    </li></c:forEach>
                </ul>
                <div class="clear"></div>
            </div>
        	<c:if test="${param.adv eq 0 }">
	        	${ads.ad21.content }
	        	<div class="right_tuijian margin_top10"></div>
	        	${ads.ad22.content }
        	</c:if>
            <c:if test="${param.adv ne 0 }">
	        	${ads.ad2.content }
	        	<div class="right_tuijian margin_top10"></div>
	        	${ads.ad3.content }
        	</c:if>
            <div class="right_botton_bg margin_top10">
            	<span class="top"><a href="#top"></a></span>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<script src="${config.system_resource_url }/static/js/list.js" type="text/javascript"></script>
<%@ include file="foot.jsp" %>
</body>
</html>