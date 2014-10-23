$(window).scroll(function() {
	var t = $(window);
	J_utils.log(t.scrollTop());
	if(t.scrollTop()>100){
		$('.index_top_bg').show()
	}else{
		$('.index_top_bg').hide();
	}
});
if(!"favorite".getCookie()){
	$("#favorite").show();
}
$(".close_favorite").on("click",function(){
	"favorite".setCookie(true,24*3600,J_utils.Config.domain,"/",false);
	$("#favorite").slideUp(1000,function(){this.remove();});
});
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