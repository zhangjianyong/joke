<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang=zh>
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<title>${config.system_website_name}-个人中心-我的资产</title>
	<%@ include file="../jscss.jsp" %>
</head>
<body>
<%@ include file="../top.jsp" %>
<%@ include file="../head.jsp" %>
<!--  主体部分  -->
<div class="i_main_bg">
	<div class="zichan_bg">
	    <div class="i_main zichan_main">
	        <!--  左侧-->
	        <form>
	    		<input type="hidden" id="uc_bar_position" value="bar_score"/>
	    	</form>
	    	<%@ include file="uc_bar.jsp" %>
	        <!--  右侧-->
	        <div class="zichan_right">
            	<div class="zichan_right_name font14 fontbold">我的资产</div>
                <div class="zc_right_main">
                    <p class="font14 line_height30">亲爱的会员 ${user.nick }（会员ID：${user.id }），欢迎你！</p>
                    <p class="font14 line_height30">你的小金库有：<a href="#" class="color_red fontbold">${account.s2 }</a> ${config.score_unit }</p>
                    <p class="line_height30 color_99">金库内的金，可以兑换成集分宝，集分宝可以还信用卡，缴水电煤费，淘宝购物抵现！<a href="/uc/exchange" class="color_red">兑换集分宝&gt;&gt;</a></p>
                    <div class="zhanghao_border">
                    	<table id="j_scorelogs" width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#f2f2f2" class="color_66">
                          <tr class="height37">
                            <td bgcolor="#fafafa">时间</td>
                            <td bgcolor="#fafafa">数量</td>
                            <td bgcolor="#fafafa">余额</td>
                            <td bgcolor="#fafafa">类型</td>
                            <%-- <td bgcolor="#fafafa">状态</td>--%>
                            <td bgcolor="#fafafa">备注</td>
                          </tr>
                        </table>
                    </div>
                    <div id="pagination" class="pages pages_top"></div>
                </div>
            </div>
	        <div class="clear"></div>
	    </div>
	</div>
<script type="text/javascript" src="/static/js/score.js"></script>
<%@ include file="../foot.jsp" %>
</div>
</body>
</html>