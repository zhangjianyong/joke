var user = "user".getCookie();
if(user){
	var user = eval('(' + JSON.parse(user) + ')');
	$(".top_user_name").html(user.nick);
};
var _user = "_user".getCookie();
if(_user){
	$.ajax({
		url : J_utils.Config.website+"/uc/i/score",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		success : function(result) {
			J_utils.log(result);
			$("#top_score").html(result.content.s2);
			$("#top_drawtimes").html(result.content.drawtimes);
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});
	$("#nouser").hide();
	$("#haveuser").show();
}