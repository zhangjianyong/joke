$("#up").mouseover(function(){$(this).find("span").attr("class","one3");$(this).attr("class","color_2");}).mouseout(function(){$(this).find("span").attr("class","one1");$(this).attr("class","color_1");});
$("#down").mouseover(function(){$(this).find("span").attr("class","one4");$(this).attr("class","color_3");}).mouseout(function(){$(this).find("span").attr("class","one2");$(this).attr("class","color_1");});
$("#up").on("click",function(){
	if(!_user){
		J_utils.login("login_div_a");
		return;
	}
	var t = $(this);
	if(t.attr("disabled")){
		return;
	}
	t.attr({"disabled":"disabled"});
	var aid = t.attr("data");
	$.ajax({
		url : J_utils.Config.website+"/up/"+aid,
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		success : function(result) {
			J_utils.log(result);
			if(result.code=="success"){
				//var left = parseInt(t.offset().left)+10, top =  parseInt(t.offset().top)-10;
				var left = 10, top =  0;
				 t.append('<div id="up_1">+1</\div>');
				  $('#up_1').css({'font-weight':'600','font-size':'20px','position':'absolute','z-index':'12', 'color':'green','left':left+'px','top':top+'px'}).animate({top:top-30,left:left+20},'slow',function(){
				   $(this).fadeIn('fast').remove();
				   var Num = parseInt(t.find('b').text());
				       Num++;
				    t.find('b').text(Num);
				    t.removeAttr("disabled");
				  });
			}else{
				var msg = $("<div style='display:none' class='zhichi_tishi'></div>");
				msg.append(result.msg);
				t.after(msg);
				msg.show('slow',function(){
					var tt = $(this);
					setTimeout(function(){
						tt.slideUp(1000,function(){tt.remove();t.removeAttr("disabled");});
					},2000);
				});
			}
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
			t.removeAttr("disabled");
		}
	});
});
$("#down").on("click",function(){
	if(!_user){
		J_utils.login("login_div_a");
		return;
	}
	var t = $(this);
	if(t.attr("disabled")){
		return;
	}
	t.attr({"disabled":"disabled"});
	var aid = t.attr("data");
	$.ajax({
		url : J_utils.Config.website+"/down/"+aid,
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		success : function(result) {
			J_utils.log(result);
			if(result.code=="success"){
				//var left = parseInt(t.offset().left)+10, top =  parseInt(t.offset().top)-10;
				var left = 10, top =  0;
				 t.append('<div id="down_1">+1</\div>');
				  $('#down_1').css({'font-weight':'600','font-size':'20px','position':'absolute','z-index':'12', 'color':'red','left':left+'px','top':top+'px'}).animate({top:top-30,left:left+20},'slow',function(){
				   $(this).fadeIn('fast').remove();
				   var Num = parseInt(t.find('b').text());
				       Num++;
				    t.find('b').text(Num);
				  });
			}else{
				var msg = $("<div style='display:none' class='zhichi_tishi'></div>");
				msg.append(result.msg);
				t.after(msg);
				msg.show('slow',function(){
					var tt = $(this);
					var st = setTimeout(function(){
						tt.slideUp(1000,function(){tt.remove();});
					},2000);
				});
			}
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});
	t.removeAttr("disabled");
});
$("#share").on("click",function(){
	var t = $(this);
	var aid = t.attr("data");
	var data = {};
	data["aid"] = aid;
	//已初始化弹层
	var sharediv = $("#sharediv");
	if(sharediv.length>0){
		J_utils.showMask();
        sharediv.show();
		return;
	}
	//生成弹层
	$.ajax({
		url : J_utils.Config.website+"share",
		type : "POST",
		dataType : "JSON",
		data:data,
		timeout : 3000,
		success : function(result) {
			if(result.result==false){
				J_utils.log(result.code+":"+result.msg);
			}
			J_utils.showMask();
			var div = $(result.content);
			$('body').append(div);
			div.show();
			$("#mask").on("click",function(){
				$("#sharediv").hide();
			});
			$(".close").on("click",function(){
				$("#sharediv").hide();
				$("#mask").hide();
			});
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log("login error");
		}
	});
});
//document.getElemetnById('divNode').scrollIntoView(true);
var offset ={};
offset['title']=0;
offset['pic']=0;
offset['next']=0;
offset['up']=300;
offset['down']=300;
var posV = $("#pos").val();
if(posV){
	var pos = $("#"+posV);
	if(pos.length>0){
		$("html,body").animate({scrollTop:pos.offset().top-offset[posV]},1000);
		var ct = setTimeout(function (){
			pos.click();
		},1000);
	}
}
$(".jiantou.info_top20").scrollFloatTop($("#content"));
$(".i_main_right").scrollFloatBottom($(".i_main_left"),125,125);
$(".share_button").each(function(i,e){
	var webid = $(e).attr("id");
	var id = $("#up").attr("data");
	var title=$("#title").text();
	var content=$("#content").text();
	var pic=$("#article_pic").attr("src");
	$(e).on("click",function(){
		J_utils.share(webid,title,J_utils.Config.website+"/article/"+id,content,pic);
	})
});