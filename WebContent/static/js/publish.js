if(!_user){
	var param = {};
	param['to']=J_utils.Config.website+"/publish";
	J_utils.login(param);
}
$("input[type='radio'][name='t'][value='pic']").attr("checked","checked");
$("input[type='radio'][name='t']").change(function(){
	var type = $(this).val();
	$("input[type='hidden'][name='type']").val(type);
	if(type=='pic'){
		$("#content_text").hide();
		$("textarea[name='content']").removeClass("j_data");
		$(".pub_pic").show();
		$("#picpath").addClass("j_data");
	}else if(type=='text'){
		$(".pub_pic").hide();
		$("#picpath").removeClass("j_data");
		$("#content_text").show();
		$("textarea[name='content']").addClass("j_data");
	}
});
$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        maxNumberOfFiles : 1,
        maxFileSize: 1000000,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        done: function (e, data) {
    		if(data.result.success){
	            $("#content_pic img").attr("src",J_utils.Config.website+"/tmp_pic"+data.result.content.fileName);
	            $("#picpath").val(data.result.content.fileName);
    		}else{
    			alert(data.result.msg);
    		}
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#content_pic .file').text(progress+'%');
            $('#content_pic .file').css('height',200*(100-progress));
        },
 
        dropZone: $('#content_pic')
    });
});
$("#publish").click(function(){
	var postData = {};
	preparePostData(postData,".j_data");
	$.ajax({
		url:J_utils.Config.website+"/i/publish",
		data:postData,
		type : "POST",
		dataType : "JSON",
//		crossDomain:true
	}).done(function(result){
		if(result.success){
			if(window.confirm(result.msg+",是否继续发布笑话?")){
				window.location = J_utils.Config.website+"/publish";
			}else{
				window.location = J_utils.Config.website;
				return false;
			}
		}else if(result.code=='login_no'){
			var param = {};
			param['to']=J_utils.Config.website+"/publish";
			J_utils.login(param);
		}else{
			alert(result.msg);
		}
	}).fail(function(result){
			J_utils.log(result);
		  alert("系统异常"); 
	});;
});