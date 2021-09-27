package org.jeecg.modules.util.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import lombok.SneakyThrows;

public class AccessTokenUtil {
    @SneakyThrows
    public static String getToken(){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dinguyfzku4kv0zfevhh");
        request.setAppsecret("xM3021o5fm8325P1q3jpyPWcMshOVjM4hP4i7nwgy62y0_9Gtenz1TCnHOplRutA");
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }
}
