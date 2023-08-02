package com.ruoyi.xindian.chatECG.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.xindian.chatECG.domain.Chat;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author lixinlong
 * 2023/7/24 17:30
 */
@RestController
@RequestMapping("/proxyRequest")
public class ChatECGController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    @GetMapping("/proxyRequest")
    public JSONObject proxyRequest(Chat chat){
        //定义发送数据
        JSONObject param = new JSONObject();
        param.put("prompt", chat.getText());
        param.put("history", JSON.parseArray(chat.getHistory()));
        //定义接收数据
        JSONObject result = new JSONObject();

        String url = "http://202.102.249.124:6025/";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        //请求参数转JOSN字符串
        StringEntity entity = new StringEntity(JSONObject.toJSONString(param), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = JSON.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", "连接错误！");
        }
            //关闭连接
        try {
            client.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        System.out.println(result);
        return result;
    }


}
