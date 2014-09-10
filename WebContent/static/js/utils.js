var eu = encodeURIComponent;
(function(w, j, f, un) {
	var m = {
		charset : "utf-8",
		domain : ".yixiaoqianjin.com",
		website : "http://www.yixiaoqianjin.com/"
	};
	w[f] = w[f] || {};
	f = w[f];
	var d = w.document;
	var p = Object.prototype.toString;
	var n = function(x, w, v, z) {
		if (!w || !x) {
			return x;
		}
		var u, y, t;
		if (z && (t = z.length)) {
			for (u = 0; u < t; u++) {
				y = z[u];
				if (y in w) {
					if (v || !(y in x)) {
						x[y] = w[y];
					}
				}
			}
		} else {
			for (y in w) {
				if (v || !(y in x)) {
					x[y] = w[y];
				}
			}
		}
		return x;
	};
	n(
			f,
			{
				_init : function() {
					f.Config = {
						debug : "true",
						timeout : 200
					};
					f.mix(f.Config, m);
				},
				regs : {
					alipay : /^(([a-zA-Z0-9])+([a-zA-Z0-9_\.\-])*\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4}))|(0{0,1}1[3458]{1}[0-9]{9})$/ig,
					bankaccount : /^([a-zA-Z0-9]|-)+$/ig,
					blank : /^\s*$/,
					cellphone : /^0{0,1}1[3458]{1}[0-9]{9}$/ig,
					email : /^([a-zA-Z0-9])+([a-zA-Z0-9_\.\-])*\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})$/ig,
					icard : /^(\d{18}|\d{15}|\d{17}x)$/ig,
					ihkcard : /^[a-z0-9]{1}\d{6,7}[a-z0-9]{1}$/ig,
					itwcard : /^[a-z]{1}\d{8,}$/ig,
					uname : /^[\u4e00-\u9fa5a-zA-Z_0-9]{3,25}$/g,
					url : /^http(s)?:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/ig,
					vercode : /^\d{6}$/,
					invalidpassword : /^[^\!\"\#\@\$\%\&\'\(\)\*\+\,\-\.\/\:\;\<\=\>\{\|\}\~\?\[\]\^\_\`0-9a-zA-Z]+/,
					painNumber : /^\d+$/,
	            	painChararter : /^[a-zA-Z]+$/,
	            	sameChararter : /^(.)\1+$/
				},
				isN : function(t) {
					return p.call(t) === "[object Number]";
				},
				iF : function(t) {
					return p.call(t) === "[object Function]";
				},
				iA : function(t) {
					return p.call(t) === "[object Array]";
				},
				iS : function(t) {
					return p.call(t) === "[object String]";
				},
				iPO : function(t) {
					return t && p.call(t) === "[object Object]" && !t.nodeType
							&& !t.setInterval;
				},
				isEmail : function(t) {
					f.regs.email.lastIndex = 0;
					return f.regs.email.test(t);
				},
				isName : function(t) {
					f.regs.uname.lastIndex = 0;
					return f.regs.uname.test(t);
				},
				isUrl : function(t) {
					f.regs.url.lastIndex = 0;
					return f.regs.url.test(t);
				},
				isPhone : function(t) {
					f.regs.cellphone.lastIndex = 0;
					return f.regs.cellphone.test(t);
				},
				isIe : function() {
					return !-[ 1, ] && !window.XMLHttpRequest;
				},
				random : function(n) {
					var uid = Math.random().toString(16).substr(2, n);
					while (uid.length < n) {
						uid = Math.random().toString(16).substr(2, n);
					}
					return uid;
				},
				mix : n,
				log : function(v, t, u) {
					if (f.Config.debug) {
						if (w.console && console.log) {
							console[t && console[t] ? t : "log"](v);
						}
					}
					return f;
				},
				regValid : function(reg, txt) {
					reg.lastIndex = 0;
					return reg.test(txt);
				},
				addFavorite : function (obj, msg) {
					var url = obj.rev || window.location.href, title = obj.title
							|| document.title;
					try {
						if (window.sidebar) {
							window.sidebar.addPanel(title, url, '');
						} else {
							if (window.opera && window.print) {
								obj.setAttribute('rel', 'sidebar');
								obj.setAttribute('href', url);
								obj.click();
							} else {
								window.external.AddFavorite(url, title);
							}
						}
					} catch (e) {
						alert(msg ? msg : '加入收藏失败，请使用Ctrl+D进行添加');
					}
					return false;
				},
				register : function() {
					var a = arguments, o = null, i, j, d, rt;
					for (i = 0; i < a.length; ++i) {
						d = a[i].split(".");
						rt = d[0];
						eval('if (typeof '
								+ rt
								+ ' == "undefined"){'
								+ rt
								+ ' = {add: function (k, v) { if (!this[k]) { this[k] = v;} return this;} };} o = '
								+ rt + ';');
						for (j = 1; j < d.length; ++j) {
							o[d[j]] = o[d[j]] || {};
							o = o[d[j]];
							o.add = function(k, v) {
								if (!this[k]) {
									this[k] = v;
								}
								return this;
							};
						}
					}
					return o;
				},
				share : function(webid, title,url, pic,summary) {
				    f.open('http://www.jiathis.com/send/?webid=' + webid + '&url=' + eu(url) + '&title=' + eu(title) + '&summary=' + eu(summary)+ '&pic=' + pic + '&uid=1961209');
				},
				open : function(url,target){
					if(target==undefined){
						target='_self';
					}else{
						target=(typeof target=='string'?target:"_blank");
					}
					if(document.all){
						var linka=document.createElement('a');
						linka.href=url;
						linka.target=target;
						document.body.appendChild(linka);
						linka.click();
					}else{
						window.open(url,target);
					}
				},
				login : function(page,params){
					var user = "user".getCookie();
					//已知用户
					if(user){
						return true;
					}
					var data = {};
					data["p"]=page;
					$.extend(data,params);
					//已初始化弹层
					var logindiv = $("#logindiv");
					if(logindiv.length>0){
//				        f.showMask();
						logindiv.show();
						return;
					}
					//生成弹层
					$.ajax({
						url : J_utils.Config.website+"login",
						type : "POST",
						dataType : "JSON",
						data:data,
						timeout : 3000,
						success : function(result) {
							if(result.result==false){
								f.log(result.code+":"+result.msg);
							}
//							f.showMask();
							var div = $(result.content);
							$('body').append(div);
							div.show();
//							$("#mask").on("click",function(){
//								$("#logindiv").hide();
//							});
						},
						error : function(xhr, ts, et) {
							xhr = null;
							f.log("login error");
						}
					});
					return false;
				}
			});
	f._init();
})(window, jQuery, "J_utils");
var trim = $.trim;
(function() {
	try {
		document.execCommand('BackgroundImageCache', false, true);
	} catch (err) {
	}
})();
(function(a) {
	a.fn.extend({
		check : function(k) {
			var c = {
				success : a.noop,
				error : a.noop,
				url : ""
			};
			var b = a.extend(c, k);
			return this.each(function() {
				var d = b;
				var e = a(this);
				e.wrap('<span class="wrap-verify"></span>').after("<s></s>");
				var h = e;
				var g = h.next();

				e.focus(function() {
					g.removeClass("ico_error").hide();
				}).blur(function() {
					var f = this.value.length;
					if (f > 0) {
						a.ajax({
							url : c.url + "?v=" + this.value,
							dataType : "json",
							jsonp : "jsoncallback",
							timeout : J_utils.Config.timeout,
							success : function(i) {
								if (i.msg == "001000") {
									g.removeClass("ico_error").show();
									if (typeof (d.success) === "function") {
										d.success();
									}
								} else {
									g.addClass("ico_error").show();
									if (typeof (d.error) === "function") {
										d.error();
									}
								}
							},
							error : function(xhr, ts, et) {
								xhr = null;
								alert(ts);
							}
						});
					}
					;
				});
			});
		}
	});
})(jQuery);
(function($) {
	$.fn.extend({
		check2 : function(k) {
			var c = {
				success : $.noop,
				error : $.noop,
				url : "",
				msg : Object
			};
			var b = $.extend(c, k);
			return this.each(function() {
				var d = b;
				var e = $(this);
				var tmp = null;
				e.focus(function() {
					tmp = e.val();
					removeMessage.call(e);
				}).blur(function() {
					var f = this.value.length;
					if (f > 0) {
					//if (f > 0 && tmp!=this.value) {
						$.ajax({
							url : c.url + "?v=" + this.value,
							dataType : "json",
							jsonp : "jsoncallback",
							timeout : J_utils.Config.timeout,
							success : function(result) {
								if (result.msg == "001000") {
									removeMessage.call(e);
									if (typeof (d.success) === "function") {
										d.success();
									}
								}else if(J_utils_notices[result.msg]){
									showMessage.apply($(J_utils_notices[result.msg]["ele"]), [J_utils_notices[result.msg].t,J_utils_notices[result.msg].txt] );
									if (typeof (d.error) === "function") {
										d.error();
									}
								} else {
									d.msg.html(J_utils_notices[000000].txt).slideDown();
									if (typeof (d.error) === "function") {
										d.error();
									}
								}
							},
							error : function(xhr, ts, et) {
								xhr = null;
								d.msg.html(J_utils_notices[000001].txt).slideDown();
							}
						});
					};
				});
			});
		}
	});
})(jQuery);
(function($) {
	$.fn.extend({
		check3 : function(k) {
			var c = {
				success : $.noop,
				error : $.noop,
				url : "",
				msg : Object
			};
			var b = $.extend(c, k);
			return this.each(function() {
				var d = b;
				var e = $(this);
				var f = this.value.length;
				if (f > 0) {
					$.ajax({
						url : c.url + "?v=" + this.value,
						dataType : "json",
						jsonp : "jsoncallback",
						timeout : J_utils.Config.timeout,
						success : function(result) {
							if (result.msg == "001000") {
								removeMessage.call(e);
								if (typeof (d.success) === "function") {
									d.success();
								}
							}else if(J_utils_notices[result.msg]){
								showMessage.apply($(J_utils_notices[result.msg]["ele"]), [J_utils_notices[result.msg].t,J_utils_notices[result.msg].txt] );
								if (typeof (d.error) === "function") {
									d.error();
								}
							} else {
								d.msg.html(J_utils_notices[000000].txt).slideDown();
								if (typeof (d.error) === "function") {
									d.error();
								}
							}
						},
						error : function(xhr, ts, et) {
							xhr = null;
							d.msg.html(J_utils_notices[000001].txt).slideDown();
						}
					});
				};
			});
		}
	});
})(jQuery);
function showMessage(status, text, getfocus) {
	this.next(".J_message_tip").remove().end().after(
			"<div class='message_{0} font12 J_message_tip'>{1}</div>".format(status,text));
	if (getfocus) {
		this.focus();
	}
};
function removeMessage() {
	this.next(".J_message_tip").remove();
};
(function($) {
	var defaults = {
		text : "处理中...",
		id : "J_toggle",
		getOriginalClass : false,
		size : "btn-m"
	};
	$.fn.toggleButton = function(options) {
		var settings = $.extend(true, {}, defaults, options);
		var id = settings.id;
		return this
				.each(function() {
					var $this = $(this);
					if ($("#{0}".format(id)).length == 0) {
						$this
								.after("<span id='{0}' style='display:none;' class='{1}'>{2}</span>"
										.format(
												id,
												settings.getOriginalClass ? $this
														.attr("class")
														: "btn {0} btn-waiting"
																.format(settings.size),
												settings.text));
					}
					exchange($this, $("#{0}".format(id)));
				});
		function exchange($btn, $spoofBtn) {
			$btn.is(":visible") ? $btn.hide() : $btn.css("display",
					"inline-block");
			$spoofBtn.is(":visible") ? $spoofBtn.hide() : $spoofBtn.css(
					"display", "inline-block");
		}
	};
	$.fn.toggleButton.defaults = defaults;
})(jQuery);

function preparePostData(postData, postDataSelector, options) {
	$(postDataSelector).each(function() {
		var $this = $(this);
		postData[$this.attr("name")] = trim($this.val());
	});
	postData.location = eu(window.location.href);
	postData.refurl = eu(document.referrer);
	$.fn.extend(postData, options);
};
var loginout_option={};
function loginout(){
	var defaults ={success:$.noop,error:$.noop};
	var settings = $.extend(true, {}, defaults, loginout_option);
	$.ajax({
		url : J_utils.Config.website+"loginout",
		type : "POST",
		dataType : "JSON",
		jsonp : "jsoncallback",
		success : function(result) {
			if (result.msg == "loginout_ok") {
				if(typeof(settings.success)=="function"){
					settings.success();
				}
			} else if(result.msg == "loginout_error"){
				if(typeof(settings.error)=="function"){
					settings.error();
				}
				J_utils.log("退出失败");
			}
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(ts);
		}
	});
};
String.prototype.format = function() {
	for (var temS = this, i = 0; i < arguments.length; ++i) {
		temS = temS.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
	}
	return temS;
};
String.prototype.setCookie = function(value, expiryDays, domain, path, secure) {
	var builder = [ this, "=", escape(value) ];
	if (expiryDays) {
		var date = new Date();
		date.setTime(date.getTime() + (expiryDays * 86400000));
		builder.push(";expires=");
		builder.push(date.toUTCString());
	}
	if (domain) {
		builder.push(";domain=");
		builder.push(domain);
	}
	if (path) {
		builder.push(";path=");
		builder.push(path);
	}
	if (secure) {
		builder.push(";secure");
	}
	document.cookie = builder.join("");
};
String.prototype.getCookie = function() {
	var re = new RegExp('\\b' + this + '\\s*=\\s*([^;]*)', 'i');
	var match = re.exec(document.cookie);
	return (match && match.length > 1 ? unescape(decodeURI(match[1])) : '');
};
String.prototype.delCookie = function() {
	document.cookie = this + "=; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
};
function StringBuilder() {
	this.strings = new Array();
}
StringBuilder.prototype.append = function(str) {
	this.strings.push(str);
	return this;
};
StringBuilder.prototype.toString = function() {
	return this.strings.join("");
};
String.prototype.format = function() {
	for (var temS = this, i = 0; i < arguments.length; ++i) {
		temS = temS.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
	}
	return temS;
};
Date.prototype.format = function(format){
	    var o = {
	    "M+" : this.getMonth()+1, // month
	    "d+" : this.getDate(),    // day
	    "h+" : this.getHours(),   // hour
	    "m+" : this.getMinutes(), // minute
	    "s+" : this.getSeconds(), // second
	    "q+" : Math.floor((this.getMonth()+3)/3),  // quarter
	    "S" : this.getMilliseconds() // millisecond
	    };
	    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	    for(var k in o)if(new RegExp("("+ k +")").test(format))
	    format = format.replace(RegExp.$1,
	    RegExp.$1.length==1 ? o[k] :
	    ("00"+ o[k]).substr((""+ o[k]).length));
	    return format;
};
(function(a) {
    a.fn.extend({checkverify: function(k) {
            var c = {success: null,error: null};
            var b = a.extend(c, k);
            return this.each(function() {
                var d = b;
                var e = a(this);
                e.wrap('<span class="wrap-verify"></span>').after("<s></s>").keyup(function() {
                    var h = e;
                    var g = h.next();
                    var f = this.value.length;
                    if (f >= 4) {
                        a.ajax({
                        	url: J_utils.Config.website+"checkCode.htm?code=" + this.value,
                        	dataType: "json",
                        	jsonp: "jsoncallback",
                        	timeout:J_utils.Config.timeout,
                        	success: function(i) {
                        		if (i.msg == "code_ok") {
                                    g.removeClass("ico_error").show();
                                    if (typeof (d.success) === "function") {
                                        d.success();
                                    }
                                } else if(i.msg == "code_error") {
                                    identifycode();
                                    g.addClass("ico_error").show();
                                    if (typeof (d.error) === "function") {
                                        d.error();
                                    }
                                }else if(i.msg == "code_empty") {
                                    g.addClass("ico_error").show();
                                }
                            },
                            error : function(xhr, ts, et) {
								xhr = null;
								alert(ts);
							}
                        });
                    } else {
                        g.removeClass("ico_error").hide();
                    }
                });
            });
        }});
})(jQuery);
function identifycode() {
    $("#codeimg").attr("src", J_utils.Config.website+"code.htm?" + (new Date()).getTime());
    $('#J_verify_code').val('');
}
$("#codeimg").on("click",identifycode);
function load (event){
	$.ajax({
		url : event.data.url,
		type : "POST",
		dataType : "JSON",
		jsonp : "jsoncallback",
		async:false,
		data : event.data.params,
		success : function(result) {
			if(result.success==true){
				event.data.showlist(result.content);
				$("#"+event.data.page_id).pagination(event.data,result.content);
			}else{
				$("#"+event.data.page_id).text(result.msg);
			}
		},
		error : function(xhr, ts, et) {
			xhr = null;
			J_utils.log(et);
		}
	});
};
$.fn.extend({pagination:function(option,pageInfo){
	var p = parseInt(pageInfo.page);
	//var per = o.per||(o.per<0||(o.per>20||10));
	var tp = parseInt(pageInfo.pagecount);
	var s=4;
	var hs=s/2;
	var pre = p-hs>2?p-hs:2;
	var end = p+hs>tp-1?tp-1:p+hs;
	var $this = $(this);
	$this.html('');
	if(tp<=0){
		return;
	}
/*	if(p>1){
		var $a1 = $("<a class='pages_text'>上一页</a>");
		$a1.attr("id",p-1);
		$this.append($a1);
	}*/
	var $a2 = $("<a>1</a>");
	$a2.attr("id",1);
	$this.append($a2);
	if(pre-1>1){
		$this.append($("<a>...</a>"));
	}
	for(var i=pre;i<=end;i++){
		var $a3 = $("<a></a>");
		$a3.attr("id",i);
		$a3.html(i);
		$this.append($a3);
	};
	if(tp-end>1){
		$this.append($("<a>...</a>"));
	}
	var $a4 = $("<a></a>");
	if(tp>1){
		$a4.attr("id",tp);
		$a4.html(tp);
		$this.append($a4);
	}
/*	if(p<tp){
		var $a5 = $("<a class='pages_next'>下一页</a>");
		$a5.attr("id",p+1);
		$this.append($a5);
	};*/
	$("#"+p).attr("class","hover");
	$this.children("a[class!='hover']").each(function(i,ele){
		$(ele).css("cursor","pointer");
		var l_o = $.extend(true,{},option);
		$.extend(l_o.params,{page:this.id});
		//J_utils.log(l_o);
		$(ele).on("click",l_o,load);
	});
}});
function ad_show(id){
$.ajax({
	url : J_utils.Config.website+"ad/"+id,
	type : "POST",
	dataType : "JSON",
	jsonp : "jsoncallback",
	async:false,
	success : function(ad) {
		document.open();
		if(!ad.tpl){
			document.write(ad.content);
			document.close();
			return;
		}
		var $div = $("<div></div>");
				$div.attr("id",'t_ad_div_' + ad.id);
				$div.css("width",ad.width);
				$div.css("height",ad.height);
		if(ad.closeable){
			var $close = $("<div></div>");
					$close.css('width','19px');
					$close.css('height','19px');
					$close.css('position','absolute');
			var $a = $("<a></a>");
					$a.attr('href','javascript:void(0);');
					$a.click($div.hide());
					$a.css('width','19px');
					$a.css('height','19px');
					$a.css('display','19px');
					$a.css('cursor','pointer');
					$a.css('background','url(http://ad.egou.com/adt/images/spe.gif) no-repeat left -62px');
			$close.append($a);
			$div.append($close);
		}
		var $iframe = $("<iframe></iframe>");
				$iframe.attr('id','t_iframe_'+ ad.id);
				$iframe.css("width",ad.width);
				$iframe.css("height",ad.height);
				$iframe.attr("scrolling",'no');
				$iframe.attr("frameborder",0);
				$iframe.attr("marginwidth",0);
				$iframe.attr("marginheight",0);
				$iframe.attr("src",J_utils.Config.website+'adshow/'+ad.id);
		$div.append($iframe);
		document.write($div.html());
		document.close();
	},
	error : function(xhr, ts, et) {
		xhr = null;
		J_utils.log(et);
	}
});
};
$.fn.scrollFloatTop=function(rang_ele){
	var t = $(this);
	var t_top = t.offset().top, t_left=t.offset().left, t_height = t.height(), t_pos = t.css("position"), t_margin_top = t.position().top, t_margin_left = t.position().left;
	$(window).scroll(function(){
		var top = $(this).scrollTop();
		var r_end = rang_ele?(rang_ele.offset().top + rang_ele.height()):0;
		if(top<=t_top){
			t.show();
			t.css({position: t_pos, top: t_margin_top, left: t_margin_left});
		}else if(r_end<=top+t_height && rang_ele){
			t.hide();
			//t.css({position: "fixed",top: r_end-t_height,left: t_left});
		}else{
			t.show();
			t.css({position: "fixed", top: 0, left: t_left});
		}
	});
};
$.fn.scrollFloatBottom=function(rang_ele,bottom,end){
	var t = $(this);
	var t_top = t.offset().top, t_left=t.offset().left, t_height = t.height(), t_pos = t.css("position"), t_margin_bottom = t.position().bottom, t_margin_left = t.position().left,
	w_height = $(window).height();
	$(window).scroll(function(){
		var top = $(this).scrollTop();
		var r_end = rang_ele?(rang_ele.offset().top + rang_ele.height()):0;
		if(w_height>=t_height){
			t.css({position: "fixed", top: 0, left: t_left});
			return;
		}
		if(r_end<=top+w_height && rang_ele){
			t.css({position: "fixed",bottom: top+w_height-r_end+end,left: t_left});
		}else if(t_height-w_height<=top-t_top-bottom){
			t.css({position: "fixed", bottom: bottom, left: t_left});
		}else{
			t.css({position: t_pos, bottom: t_margin_bottom, left: t_margin_left});
		}
	});
};