package com.ruoyi.xindian.util;


//import com.alibaba.druid.support.json.JSONUtils;
import org.apache.http.HttpResponse;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;

import java.time.LocalDateTime;
import java.util.*;

public class WxUtil {

    //检测次数
    public static final int detection=3;

    //小程序token获取
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
    //公众号token获取
    public static String queryGZHToken(){
        String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
        String appId = "wx219c20026db0351e";
        String secret = "c55d24177dc7f5cff61d8bebfdaa7d51";
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

    //公众号消息
    public static void sendGZHMsg(String token, String openId, String msg, String str_time){
        String msgUrl="https://api.weixin.qq.com/cgi-bin/message/template/send";
        msgUrl=msgUrl+"?access_token="+token;
        //模板
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("touser",openId);
        paraMap.put("template_id","LjIAdodHTvUzCEg5COBLQvlAtyVheKJt9cFrAExVHPE");
        HashMap<String, Object> miniprogram = new HashMap<>();
        miniprogram.put("appid","wx331beedb5dbfe460");
        miniprogram.put("pagepath","pages/login/login");
        paraMap.put("miniprogram",miniprogram);

        //设置data
        HashMap<String, Object> data = new HashMap<>();
        data.put("keyword1",formatParam(msg));
        data.put("keyword2",formatParam("有患者请求您诊断心电图，请及时处理。"));
        data.put("keyword3",formatParam(str_time));
        paraMap.put("data",data);

        String jsonString = JSONUtils.toJSONString(paraMap);
        System.out.println(jsonString);
        String result = HttpUtils.sendPost(msgUrl, jsonString);
        System.out.println(result);
    }
    //发送短信
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
        bodys.put("template_id", "CST_lqgqs10283");


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

    /**
     * 通知患者设备绑定成功
     * @param telephone
     */
    public static void sendPatient12(String telephone){
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
        bodys.put("template_id", "CST_cldra10649");
        //【迈雅云】您的设备已绑定成功，请登录微信小程序「迈雅云」使用！

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

    //发送建议短信
    public static void sendAdvice(String telephone){
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
        bodys.put("template_id", "CST_fjcqk10274");


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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户采集12导的时候出现预警发送通知
     * @param telephone
     */
    public static void sendSubmitSXLog(String telephone){
        System.out.println(telephone);
        //【迈雅云】您或您的家人在使用心电衣设备时，检测出异常状态，请登录迈雅云查看异常情况。
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
        bodys.put("template_id", "CST_xrdxptvsdhdy10867");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 用户提交动态报告通知医生诊断
     * @param telephone
     */
    public static void sendSubmitAdviceSX(String telephone){
        System.out.println(telephone);
        //【迈雅科技】用户提交了一条诊断报告，请前往动态心电分析诊断系统进行诊断。
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
        bodys.put("template_id", "CST_jzajwbynjwbc10858");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 善行医师报告生成成功消息通知
     * @param telephone
     */
    public static void sendAdviceSX(String telephone){
        System.out.println(telephone);
        //【迈雅云】您的报告已经生成成功，请前往【迈雅云】小程序查看
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
        bodys.put("template_id", "CST_gbvoilxkhwob10821");


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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //医生诊断完后给患者提示短信
    public static void sendOK(String telephone){
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
        bodys.put("template_id", "CST_vhizs10284");


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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //小程序订阅消息
    public static void sendMsg(String token, String openId, String msg, String tis, String str_time){
        String msgUrl="https://api.weixin.qq.com/cgi-bin/message/template/send";
        msgUrl=msgUrl+"?access_token="+token;
        //模板
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("touser",openId);
        paraMap.put("template_id","LjIAdodHTvUzCEg5COBLQvlAtyVheKJt9cFrAExVHPE");
        paraMap.put("access_token",token);
        paraMap.put("page","pages/login/login");
        //miniprogram 跳转小程序时填写，格式如{ "appid": "pagepath": { "value": any } }
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
}
