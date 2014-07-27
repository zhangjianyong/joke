var userC = "user".getCookie();
if(userC)
if(userC){
	var user = eval('(' + JSON.parse(userC) + ')');
	$("#nouser").hide();
	$("#username").html(user.id);
	$("#haveuser").show();
};
if(userC){
	$.ajax({
		url : J_utils.Config.website+"ucenter/interface/score",
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
}
loginout_option.success=function(){
	 var go_url = $("#go_url").val();
     go_url = J_task.Config.website+"loginsuccess.action?go_url=" + encodeURIComponent(go_url);
     window.location.href = go_url;
};