<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html><html lang=zh>
<head>
	<title>${config.system_website_name}</title>
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
                <div class="one_nr">神马？你居然还没有注册一笑千金？注册的好处多多哦，可以参加一笑千金的抽奖活动，每日看笑话，还能赚金币啊！</div>
            </div><c:set var="pp" value="0"/>
            <c:forEach var="a"  items="${articles }"  varStatus="s" ><c:if test="${s.index%10==0 }"><c:set var="pp" value="${pp+1 }"/><div id="loadpage${pp }"></div></c:if><div class="left_two bg_radius page${pp }" <c:if test="${pp>1 }">style="display:none"</c:if>>
                <div class="two_title">
                	<div class="title_more"><a href="/article/${a.id }" target="_blank">查看全文</a></div>
                	<span class="name_pic"><img src="${config.system_resource_url }/static/images/demoimg/i_01.gif"></span>
                    <span class="padding_left10">${a.member_nick }</span>
                    <span class="padding_left10 englist color_99">${a.create_time }</span>
                </div>
                <c:if test="${a.type=='PIC' }"> <div class="two_name"><a href="/article/${a.id }" target="_blank">${a.title } </a></div><div class="two_info"><br>
	                <a href="/article/${a.id }" target="_blank"><c:choose><c:when test="${pp==1 }"><img src="${config.pic_domain }/article/0${a.pic }" style="max-width: 559px;"/></c:when><c:otherwise><img data-src="${config.pic_domain }/article/0${a.pic }" style="max-width: 559px;"/></c:otherwise></c:choose></a></div></c:if>
	            <c:if test="${a.type=='TEXT' }"> <div class="two_name"><a href="/article/${a.id }" target="_blank">${a.title } </a></div><div class="two_info">${a.content }<br></div></c:if>
	            <c:if test="${a.type=='ASHAMED' }">
	                <div class="two_info">${a.content }<br></div></c:if>
                <div class="zhichi_bg">
                	<div class="chizhi_left"><a href="javascript:void(0);" data="${a.id }?pos=up" class="color_1 updown up"><span class="one1"></span>${a.up }</a></div>
                    <div class="chizhi_left"><a href="javascript:void(0);" data="${a.id }?pos=down" class="color_1 updown down"><span class="one2"></span>${a.down }</a></div>
                </div>
            </div></c:forEach>
            <!-- 分页 -->
            <div id="loading" style="display: none;">正在加载..</div>
            <jsp:include page="/jsp/page.jsp">
            	<jsp:param value="${count }" name="count"/>
            	<jsp:param value="${page }" name="page"/>
            	<jsp:param value="/${param.t }/${param.o }" name="path"/>
            	<jsp:param value="/" name="first"/>
            </jsp:include>
            <div class="left_advert"><img src="/static/images/demoimg/p_04.gif"></div>
        </div>
    	<!--  右侧-->
        <div class="i_main_right">
        	<div class="right_advert"><img src="/static/images/demoimg/p_05.gif"></div>
        	<div class="right_advert margin_top10"><img src="/static/images/demoimg/p_06.gif"></div>
            <div class="right_name font14 color_66 fontbold">搞笑图片推荐</div>
            <div class="right_list">
            	<ul><c:forEach var="h" items="${hots }">
                	<li><a href="/article/${h.id }"><img style="max-width: 90px;min-height:90px;" src="${config.pic_domain }/article/90${h.pic }"></a></li>
                </c:forEach></ul>
                <div class="clear"></div>
            </div>
            <div class="right_advert margin_top10"><img src="/static/images/demoimg/p_07.gif"></div>
        	<div class="right_advert margin_top10"><img src="/static/images/demoimg/p_09.gif"></div>
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