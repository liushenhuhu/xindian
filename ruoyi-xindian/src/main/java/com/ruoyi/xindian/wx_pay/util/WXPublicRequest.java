package com.ruoyi.xindian.wx_pay.util;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class WXPublicRequest {

//    @Resource
//    private WxMpService wxMpService;

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 发送给医生的公众号消息推送
     */
    public  void dockerMsg() throws Exception {

        String accessToken = getAccessToken();

        Set<String> userOpenId = getUserOpenId(accessToken);

    }




    public  void sendOrderMsg(String first,
                              String userOpenid,
                              String name,
                              String serviceName) {

        String OrderMsgTemplateId = "LjIAdodHTvUzCEg5COBLQvlAtyVheKJt9cFrAExVHPE";

        // 卡片详情跳转页，设置此值，当点击消息时会打开指定的页面
        String detailUrl = "https://baidu.com";

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String timeNow = sdf.format(date);
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(WXPayConstants.WX_PUBLIC_ID);
        wxStorage.setSecret(WXPayConstants.WX_PUBLIC_SECRET);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        // 此处的 key/value 需和模板消息对应
        List<WxMpTemplateData> wxMpTemplateDataList = Arrays.asList(
                new WxMpTemplateData("first",first , "#000000"),
                new WxMpTemplateData("keyword1", name),
                new WxMpTemplateData("keyword2", serviceName),
                new WxMpTemplateData("keyword3", timeNow),
                new WxMpTemplateData("remark", "点击查看详情")
        );
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(userOpenid)
                .templateId(OrderMsgTemplateId)
                .data(wxMpTemplateDataList)
                .url(detailUrl)
                .build();
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
        }

    }



    /**
     *获取token
     * @return
     * @throws Exception
     */
    public String getAccessToken()throws Exception{

        if(Boolean.TRUE.equals(redisTemplate.hasKey("wxToken"))){

            return redisTemplate.opsForValue().get("wxToken");
        }else{
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                    + WXPayConstants.WX_PUBLIC_ID
                    +"&secret="
                    + WXPayConstants.WX_PUBLIC_SECRET;
            String res=restTemplate.getForObject(url,String.class);
            JSONObject jsonObject = JSONObject.parseObject(res);
            String accessToken = jsonObject.getString("access_token");
            redisTemplate.opsForValue().set("wxToken",accessToken,20,TimeUnit.MINUTES);
            return accessToken;
        }

    }

    /**
     * 获取关注公众号的所有
     * @param accessToken
     * @return
     * @throws Exception
     */
    public Set<String> getUserOpenId(String accessToken)throws Exception{
        String usersGetUrl="https://api.weixin.qq.com/cgi-bin/user/get?access_token="
                +accessToken;
        JSONObject jsonObject = getResponse(usersGetUrl);
        Set<String> openIds =new HashSet<String>();
        Integer total=0,count=0;
        try {
            //关注该公众账号的总用户数
            total=(Integer) jsonObject.get("total");
            //拉取的 OPENID 个数，最大值为10000
            count=(Integer) jsonObject.get("count");
            if (count<total){
                //总关注用户数超过 默认数10000
                //需要 next_openid 参数 分批次 多次拉取
                openIds.addAll(getUsers(openIds,usersGetUrl, accessToken, (String)jsonObject.get("next_openid")));
            }else {
                //有关注者
                if (count>0){
                    //列表数据，OPENID的列表
                    JSONObject openIdDdta = (JSONObject)jsonObject.get("data");
                    JSONArray openIdArray= (JSONArray) openIdDdta.get("openid");
//                    JSONArray unionidArray= (JSONArray) openIdDdta.get("unionid");
//                    String unionidA = jsonObject.getString("unionid");

                    for (Object objects:openIdArray) {
                        openIds.add(objects.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openIds;
    }

    private Set<String> getUsers(Set<String>openIds,String url,String accessToken,String nextOpenId) {
        JSONObject data=getResponse(url);
        try {
            Integer count=(Integer) data.get("count");
            if(count>0){
                JSONObject openIdData=(JSONObject) data.get("data");
                JSONArray openIdArray= (JSONArray) openIdData.get("openid");
                for (Object objects:openIdArray) {
                    openIds.add(objects.toString());
                }
            }
            if(!nextOpenId.isEmpty()){
                return getUsers(openIds,url, accessToken, nextOpenId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openIds;
    }

    public JSONObject getResponse(String url){
        String res = HttpUtil.get(url);
        return JSONObject.parseObject(res);
    }



}
