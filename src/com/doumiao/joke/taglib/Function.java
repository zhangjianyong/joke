package com.doumiao.joke.taglib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.doumiao.joke.lang.HttpClientHelper;

public class Function {
	private static final Log log = LogFactory.getLog(Function.class);
	public static String fetchUrl(String uri){
		HttpClient client = HttpClientHelper.getClient();
		HttpGet get = new HttpGet(uri);
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			log.error(e,e);
			return null;
		}
	}
}
