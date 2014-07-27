$("#draw").on("click",function(){
	if(!J_utils.login("login_div_a")){
		return;
	}
	$.ajax({
		url : J_utils.Config.website+"lottery/draw/go",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		success : function(result) {
			J_utils.log(result);
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});
	//checkScore();
});
(function checkScore(){
	$.ajax({
		url : J_utils.Config.website+"ucenter/interface/score",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		success : function(result) {
			J_utils.log(result);
			$("#top_score").text(result.content.s2);
			$("#top_drawtimes").text(result.content.drawtimes);
			$("#drawtimes").find("em").text(result.content.drawtimes);
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});	
})();
