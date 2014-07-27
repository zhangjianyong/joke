$("#unbindalipay").on("click",function(){
	$.ajax({
		url : J_utils.Config.website+"unbind/alipay",
		type : "POST",
		dataType : "JSON",
		timeout : 3000,
		async:true,
		success : function(result) {
			window.location.reload();
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});
});