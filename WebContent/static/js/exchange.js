$("#exchange").on("click",function(){
	var data = {};
	preparePostData(data,".post_data");
	if(!window.confirm("请再次确认支付定账号是否有误:"+data.act)){
		return;
	}
	
	$.ajax({
		url : J_utils.Config.website+"/uc/i/alipay_exchange",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		data:data,
		success : function(result) {
			if(result.success){
				checkScore();
				alert("对换成功");
				load({data:load_option});
			}else{
				alert(result.msg);
			}
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});
});
function checkScore(){
	$.ajax({
		url : J_utils.Config.website+"/uc/i/score",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		success : function(result) {
			J_utils.log(result);
			$("#top_score").text(result.content.s2);
			$("#top_drawtimes").text(result.content.drawtimes);
			$("#score").text(result.content.alipay);
			$("#val").text(result.content.alipay);
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});	
};
if(_user){
	checkScore();
}
var uc_account_status = {'UNPAY':'等待打款', 'PAYED':'已打款','REJECT':'申请驳回'};
var plat = {'ALIPAY':'集分宝'};
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
			$tr.append("<td bgcolor='#ffffff'>"+plat[v.plat]+"</td>");
			$tr.append("<td bgcolor='#ffffff'>"+v.account+"</td>");
			$tr.append("<td bgcolor='#ffffff'>"+uc_account_status[v.status]+"</td>");
			$tr.append("<td bgcolor='#ffffff'>"+(v.remark||'')+"</td>");
			$tr.hide();
			$("#j_scorelogs").append($tr);
			$tr.fadeIn(k*200);
		});
	}
};
load({data:load_option});