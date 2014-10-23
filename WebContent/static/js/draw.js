var awards = [{"id":"0","k":"coin","v":"0"}, {"id":"1","k":"coin","v":"1"},
              {"id":"2","k":"coin","v":"5"}, {"id":"3","k":"coin","v":"10"},
              {"id":"4","k":"coin","v":"20"}, {"id":"5","k":"coin","v":"50"},
              {"id":"6","k":"coin","v":"100"}, {"id":"7","k":"coin","v":"1000"},
              {"id":"8","k":"coin","v":"0"}, {"id":"9","k":"coin","v":"2"},
              {"id":"10","k":"coin","v":"10"}, {"id":"11","k":"coin","v":"20"},
              {"id":"12","k":"coin","v":"50"}, {"id":"13","k":"coin","v":"1"},
              {"id":"14","k":"coin","v":"10"}, {"id":"15","k":"coin","v":"20"}];
for(i in awards){
	$("#"+awards[i].id).find(".one3").text(awards[i].v+"金币");
}
$("#divshow").on("click",function(){
	refreshcode();
	$("#draw_button").hide();
	$("#_code").show();
});
$("#_code img").on("click",function(){
	refreshcode();
});
function refreshcode(){
	$.ajax({
		url : J_utils.Config.website+"/code",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		success : function(result) {
			if(result.success){
				$("#_code img").attr("src",result.content);
				$("#_code img").attr("data-key",result.code);
			}
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});	
}

var i = 0, l = awards.length-1, al;
var drawing = false;
$("#_code a").on("click",function(){
	J_utils.log(drawing);
	if(drawing){
		return;
	}
	drawing = true;
	var code = $("#_code input").val();
	var key = $("#_code img").attr("data-key");
	if(!code){
		alert("请输入验证码");
		drawing = false;
		return;
	}
	al = "谢谢参与";
	var data = {};
	data["code"]=code;
	data["key"]=key;
	if(!_user){
		J_utils.login(data,"login_div_a");
		drawing = false;
		return;
	}
	var drawtimes = parseInt($("#drawtimes").find("em").text());
	if(drawtimes==0){
		alert("你目前没有抽奖机会.");
		drawing = false;
		return;
	}
	var t = 0;
	$.ajax({
		url : J_utils.Config.website+"/lottery/i/draw",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		data:data,
		async:true,
		success : function(result) {
			var flying;
			if(result.success){
				flying = setInterval(fly_blank,100);
				var n = i;
				for(var m=1; m<=l; m++){
					var n = n + 1;
					if(n > l){
						n = 0;
					}
					var award = awards[n];
					if(awards[n].k == result.content.key && awards[n].v == result.content.value){
						t = n;
						break;
					}
				}
				if(t!=0 && result.content.value!=0){
					al = "恭喜你获得"+result.content.name;
				}
				setTimeout(function(){
					clearInterval(flying);
					fly_target(i,t);
					checkScore();
				},3000);
			}else{
				alert(result.msg);
			}
		},
		error : function(xhr, ts, et) {
			xhr = null;
			alert(et);
			J_utils.log(et);
		}
	});
	$("#draw_button").show();
	$("#_code").hide();
	$("#_code input").val("")
	drawing = false;
});
function fly_target(i,t){
	var interval = 0,_interval = 0;
	var k = i-t>= 0 ? l+1+t-i : t - i;
	if(k>3){
		for(var j=1; j<=k - 3; j++){
			interval = 3*j*100;
			setTimeout(fly_blank,interval);
		}
		k = 3;
	}
	_interval = interval;
	for(var j=1; j<=k; j++){
		interval = parseInt(_interval) + parseInt(4*j*100);
		setTimeout(fly_blank,interval);
	}
	setTimeout(function(){
		alert(al);
	},interval);
}
function fly_blank(){
	if(++i > l){
		i = 0;
	}
	var _award = awards[i-1 >= 0 ? i-1 : l];
	var award = awards[i];
	$("#"+_award.id).removeClass("boder_bg");
	$("#"+award.id).addClass("boder_bg");
}
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
			$("#drawtimes").find("em").text(result.content.drawtimes);
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});	
};
checkScore();
$("#draw_page_score").text($("#top_score").text());