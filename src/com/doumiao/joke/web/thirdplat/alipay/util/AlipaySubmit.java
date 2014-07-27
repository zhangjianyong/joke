package com.doumiao.joke.web.thirdplat.alipay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

import com.doumiao.joke.web.thirdplat.alipay.config.AlipayConfig;
import com.doumiao.joke.web.thirdplat.alipay.util.httpClient.HttpProtocolHandler;
import com.doumiao.joke.web.thirdplat.alipay.util.httpClient.HttpRequest;
import com.doumiao.joke.web.thirdplat.alipay.util.httpClient.HttpResponse;
import com.doumiao.joke.web.thirdplat.alipay.util.httpClient.HttpResultType;

/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.2
 *日期：2011-03-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipaySubmit {

    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayFunction.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = AlipayFunction.buildMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);

        return sPara;
    }

    /**
     * 构造提交表单HTML数据
     * @param sParaTemp 请求参数数组
     * @param gateway 网关地址
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod,
                                   String strButtonName) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + gateway
                      + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
       // sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\"></form>");
        sbHtml.append("</from><script>document.forms['alipaysubmit'].submit();</script>");

       // System.out.println(buildGetUrl(sParaTemp,gateway));
        
        return sbHtml.toString();
    }

    /**
     * 获得get方式的请求连接
     * @param sParaTemp 请求参数数组
     * @param gateway 网关地址
     * @return 请求连接
     */
    public static String buildGetUrl(Map<String, String> sParaTemp, String gateway,String encode[]){
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        if(encode!=null&&encode.length>0){
        	for(String ec : encode){
        		if(sPara.containsKey(ec)){
        			try {
						sPara.put(ec, URLEncoder.encode(sPara.get(ec),AlipayConfig.input_charset));
					} catch (UnsupportedEncodingException e) {
						
					}
        		}
        	}
        }
        if(sPara!=null){
        	return	gateway + "?"+AlipayFunction.createLinkString(sPara);
        }else{
        	return "";
        }
    }
    
    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }

    /**
     * 构造模拟远程HTTP的POST请求，获取支付宝的返回XML处理结果
     * @param sParaTemp 请求参数数组
     * @param gateway 网关地址
     * @return 支付宝返回XML处理结果
     * @throws Exception
     */
    public static String sendPostInfo(Map<String, String> sParaTemp, String gateway)
                                                                                    throws Exception {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setCharset(AlipayConfig.input_charset);

        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(gateway+"_input_charset="+AlipayConfig.input_charset);

        HttpResponse response = httpProtocolHandler.execute(request);
        if (response == null) {
            return null;
        }
        
        String strResult = response.getStringResult();

        return strResult;
    }
}
