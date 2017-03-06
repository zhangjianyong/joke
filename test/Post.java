import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.doumiao.joke.lang.HttpClientHelper;
import com.doumiao.joke.schedule.Config;
import com.doumiao.joke.vo.Result;

public class Post {
	@Test
	public void t(){
		Map<String, String> params = new HashMap<String, String>(3);
		params.put("type", "down");
		params.put("articleId", "482505251");
		params.put("uid", "14091");
		Config.set("system_control_url", "http://control.yixiaoqianjin.com");
	    Result r = HttpClientHelper.controlPlatPost("/updown", params);
	    System.out.println(r.getMsg());
	}
}