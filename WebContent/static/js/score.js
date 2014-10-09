var uc_account_status = {'UNCHECK':'待审核', 'CHECK':'已审核','UNPAY':'等待打款', 'PAY':'已打款','REJECT':'申请驳回'};
var wealtype = {'DRAW':'抽奖','UPDOWN':'顶沉','THIRDPLAT_EXCHANGE':'对换','DEDUCT':'作弊扣罚'};
var load_option = {
	page_id:'pagination',
	url : J_utils.Config.website+"/uc/i/thirdscorelog",
	params:{page:1},
	showlist:function(result){
		$("#j_scorelogs").find(".jifen_table_info").remove();
		$.each(result.list,function(k,v){
			var $tr = $("<tr class='jifen_table_info'></tr>");
			$tr.append("<td bgcolor='#ffffff'>"+new Date(v.create_time).format("yyyy-MM-dd hh:mm:ss")+"</td>");
			$tr.append("<td bgcolor='#ffffff' class='color_red fontbold'>"+v.wealth+"</td>");
			$tr.append("<td bgcolor='#ffffff' class='color_red fontbold'>"+v.wealth_balance+"</td>");
			$tr.append("<td bgcolor='#ffffff'>"+wealtype[v.wealth_type]+"</td>");
			$tr.append("<td bgcolor='#ffffff'>"+uc_account_status[v.status]+"</td>");
			$tr.append("<td bgcolor='#ffffff'>"+(v.remark||'')+"</td>");
			$tr.hide();
			$("#j_scorelogs").append($tr);
			$tr.fadeIn(k*200);
		});
	}
};
load({data:load_option});