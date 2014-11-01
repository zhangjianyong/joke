$(window).scroll(function() {
	var t = $(window);
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
	$("#nouser").hide();
	$("#haveuser").show();
}