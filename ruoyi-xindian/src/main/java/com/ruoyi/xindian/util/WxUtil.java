package com.ruoyi.xindian.util;


//import com.alibaba.druid.support.json.JSONUtils;
import org.apache.http.HttpResponse;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;

import java.util.*;

public class WxUtil {

    public static String queryToken(){
        String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
        String appId = "wx331beedb5dbfe460";
        String secret = "f4de7b81311cf445d058f0b66b21add8";
        tokenUrl = tokenUrl + "&appid=" + appId + "&secret=" + secret;
        String result = HttpUtils.sendGet(tokenUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String sccess_token = jsonObject.getString("access_token");
        return sccess_token;
    }
    public static HashMap<String,Object> formatParam(String value){
        HashMap<String, Object> data = new HashMap<>();
        data.put("value",value);
        return data;
    }

    public static void sendMsg(String token, String openId, String msg, String tis, String str_time){
        String appId = "wx331beedb5dbfe460";
        String secret = "f4de7b81311cf445d058f0b66b21add8";
        String msgUrl="https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
        msgUrl=msgUrl+"?access_token="+token;
        //模板
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("touser",openId);
        paraMap.put("template_id","2UCux_98U1UwQp5fR0sWN_oJM6uZ0hlSyi1GOL9qJqk");
        paraMap.put("access_token",token);
        paraMap.put("page","pages/login/login");
        //设置data
        HashMap<String, Object> data = new HashMap<>();
        data.put("thing1",formatParam(msg));
        data.put("thing3",formatParam(tis));
        data.put("time2",formatParam(str_time));
        paraMap.put("data",data);

        String jsonString = JSONUtils.toJSONString(paraMap);
        String result = HttpUtils.sendPost(msgUrl, jsonString);
        System.out.println(result);
    }

    public static void send(String telephone){
        String host = "https://dfsmsv2.market.alicloudapi.com";
        String path = "/data/send_sms_v2";
        String method = "POST";
        String appcode = "37a5b008bed84153ad0691d0c33fe42a";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:1234");
        bodys.put("phone_number", telephone);
        bodys.put("template_id", "CST_znbpn10214");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
