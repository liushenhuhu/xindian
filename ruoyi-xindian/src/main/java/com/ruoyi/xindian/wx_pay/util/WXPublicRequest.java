package com.ruoyi.xindian.wx_pay.util;


import cn.hutool.http.HttpUtil;
import cn.hutool.http.body.RequestBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.wx_pay.domain.MessageTemplateEntity;
import com.ruoyi.xindian.wx_pay.domain.MessageValueEntity;
import com.ruoyi.xindian.wx_pay.domain.SendMessageVo;
import lombok.extern.log4j.Log4j2;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
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

        System.out.println(userOpenId);

    }


    /**
     * 微信公众号调用接口
     * @param first
     * @param userOpenid
     * @param name
     * @param serviceName
     */
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


    public  void sendMsg(String first, String userOpenid, String name, String serviceName) throws Exception {

        MessageTemplateEntity messageTemplateEntity = new MessageTemplateEntity();
        String time = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date());
        messageTemplateEntity.setMessageData(new MessageValueEntity("项目名称"), new MessageValueEntity(time), new MessageValueEntity("这是描述"));

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("touser", "wx4a6ad6d7e5e6xxxx"); //用户openid
        paramsMap.put("template_id", "h7eKu5vOSbbObOxg9psqYZ-NiRyiiPi9wMuWqExxxx"); //推送消息模板id
        paramsMap.put("data", messageTemplateEntity); //消息体：{{"thing1":"项目名称"},{"time2":"2022-08-23"},{"thing3":"这是描述"}}
        String wxAccessToken = getWXAccessToken();

        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON); //设置内容类型为json
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap, headers); //构建http请求实体

        //发送请求路径拼接获取到的access_token
        SendMessageVo sendMessageVo = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" +
                wxAccessToken, request, SendMessageVo.class);

        if (null == sendMessageVo) {
            throw new RuntimeException("推送消息失败");
        }
        if (sendMessageVo.getErrcode() != 0) {
            log.error("推送消息失败,原因：{}", sendMessageVo.getErrmsg());
            throw new RuntimeException("推送消息失败");
        }
        log.info("推送消息成功");

    }

    /**
     * 校验商户联系方式是否存在；
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean checkCardInvoiceSetbizattrContact() throws Exception {
        String wxAccessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("access_token",wxAccessToken ); //用户openid
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/setbizattr?action=get_contact&access_token=";
        HashMap<String,String> sendMessageVo=null;
        Object contact = null;
        try {
            sendMessageVo = restTemplate.postForObject(url+wxAccessToken, request, HashMap.class);
            contact = sendMessageVo.get("contact");
            log.info("微信获取自身的开票平台识别码，，返回数据:{}",sendMessageVo);
        }catch (Exception e){
            System.out.println(e);
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",sendMessageVo);
        }

        HashMap<String ,String> hashMap =(HashMap<String, String>) contact;

        String phone = hashMap.get("phone");
        return phone != null && !"".equals(phone);
    }

    /**
     * 设置商户联系方式
     */

    public void setCardInvoiceSetbizattrContact() throws Exception {

        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        HashMap<String ,String> hashMap = new HashMap<>();
        hashMap.put("time_out","12345" );
        hashMap.put("phone","15286981260" );
        paramsMap.put("access_token",accessToken );
        paramsMap.put("contact" ,hashMap);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/setbizattr?action=set_contact&access_token=";
        HashMap<String,String> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url+accessToken, request, HashMap.class);
            log.info("微信获取自身的开票平台识别码正常 。 返回数据:{}",sendMessageVo);
        }catch (Exception e){
            System.out.println(e);
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",sendMessageVo);
        }

    }

    /**
     * 获取自身的开票平台识别码
     * @return
     * @throws Exception
     */
    public String  getCardInvoiceSeturl() throws Exception {
        String wxAccessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("access_token",wxAccessToken ); //用户openid
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/seturl?access_token=";
        HashMap<String,String> sendMessageVo=null;
        String invoiceUrl = null;
        String errcode = null;
        try {
            sendMessageVo = restTemplate.postForObject(url+wxAccessToken, request, HashMap.class);
            invoiceUrl = sendMessageVo.get("invoice_url");
            errcode = sendMessageVo.get("errcode");
        }catch (Exception e){
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",sendMessageVo);
        }
        URL url1 = null;
        String s_pappid = null;
        if (Objects.equals(errcode, "ok")) {
          url1 = new URL(invoiceUrl);
            String query = url1.getQuery(); // 获取URL中的查询字符串部分

            if (query != null) {
                String[] params = query.split("&"); // 拆分参数
                for (String param : params) {
                    String[] keyValue = param.split("="); // 拆分键值对
                    String key = URLDecoder.decode(keyValue[0], "UTF-8"); // 解码键
                    String value = URLDecoder.decode(keyValue[1], "UTF-8"); // 解码值

                    if (key.equals("s_pappid")) {
                        s_pappid = value;
                        System.out.println("param1的值为：" + value);
                    }
                }
            }
        }
        return s_pappid;
    }
    /**
     *获取公众号token
     * @return
     * @throws Exception
     */
    public String getAccessToken()throws Exception{


        if(Boolean.TRUE.equals(redisTemplate.hasKey("wxToken"))){

            return redisTemplate.opsForValue().get("wxToken");
        }else{
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                    + WXPayConstants.WX_PUBLIC_ID+"&secret="+WXPayConstants.WX_PUBLIC_SECRET;
            String res=restTemplate.getForObject(url,String.class);
            JSONObject jsonObject = JSONObject.parseObject(res);
            String accessToken = jsonObject.getString("access_token");
            redisTemplate.opsForValue().set("wxToken",accessToken,20,TimeUnit.MINUTES);
            return accessToken;
        }

    }


    /**
     *获取小程序token
     * @return
     * @throws Exception
     */
    public String getWXAccessToken()throws Exception{

        if(Boolean.TRUE.equals(redisTemplate.hasKey("wxXToken"))){

            return redisTemplate.opsForValue().get("wxXToken");
        }else{
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                    + WXPayConstants.APP_ID
                    +"&secret="
                    + WXPayConstants.WX_SECRET;
            String res=restTemplate.getForObject(url,String.class);
            JSONObject jsonObject = JSONObject.parseObject(res);
            String accessToken = jsonObject.getString("access_token");
            redisTemplate.opsForValue().set("wxXToken",accessToken,20,TimeUnit.MINUTES);
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
