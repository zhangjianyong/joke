$("#exchange").on("click",function(){
	var data = {};
	preparePostData(data,".post_data");
	$.ajax({
		url : J_utils.Config.website+"uc/i/alipay_exchange",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		data:data,
		success : function(result) {
			checkScore();
			alert("对换成功");
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});
});
function checkScore(){
	$.ajax({
		url : J_utils.Config.website+"uc/i/score",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		success : function(result) {
			J_utils.log(result);
			$("#top_score").text(result.content.s2);
			$("#top_drawtimes").text(result.content.drawtimes);
			$("#score").text(result.content.s2);
			$("#val").text(result.content.s2);
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});	
};