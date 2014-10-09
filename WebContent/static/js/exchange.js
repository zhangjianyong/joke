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
			$("#top_score").text(result.content.alipay);
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