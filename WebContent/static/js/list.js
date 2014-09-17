var login = getParam("login");
var to = getParam("to");
if(login){
	var data={};
	data['to']=to;
	J_utils.login(data);
}

$(".updown.up").mouseover(function(){$(this).find("span").attr("class","one3");$(this).attr("class","color_2");}).mouseout(function(){$(this).find("span").attr("class","one1");$(this).attr("class","color_1");});
$(".updown.down").mouseover(function(){$(this).find("span").attr("class","one4");$(this).attr("class","color_3");}).mouseout(function(){$(this).find("span").attr("class","one2");$(this).attr("class","color_1");});
$(".updown").on("click",function(){
	var t = $(this);
	var article = t.attr("data");
	var data = {};
	data["t"]=J_utils.Config.website+"article/"+article;
	if(!J_utils.login("login_div_a",data)){
		return;
	}else{
		window.open(data.t);
	}
});
$(function(){
	var page = 2;
	$(window).scroll(function() {
		var loadpage = $("#loadpage"+page);
		var top =  $(document).scrollTop();
		if(loadpage.length > 0){
			var loadpagetop = loadpage.offset().top;
			if(top+600>loadpagetop){
				J_utils.log(page);
				$("#loading").show();
				var do_class = "page"+page;
				$("."+do_class).find("img").each(function(){
					var data_src = $(this).attr("data-src");
					J_utils.log(data_src);
					if(data_src){
						$(this).attr("src",data_src);
						$(this).attr("data-src","");
					}
				});
				$("."+do_class).show();
				//$("#pagination").hide();
				page++;
			}
			$("#loading").hide();
		}
	});
});
$(".i_main_right").scrollFloatBottom($(".i_main_left"),125,125);