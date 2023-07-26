package com.ruoyi.xindian.wx_pay.util;


import cn.hutool.http.HttpUtil;
import cn.hutool.http.body.RequestBody;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.order.domain.Invoice;
import com.ruoyi.xindian.order.mapper.InvoiceMapper;
import com.ruoyi.xindian.wx_pay.VO.BizField;
import com.ruoyi.xindian.wx_pay.VO.UserField;
import com.ruoyi.xindian.wx_pay.VO.WxCardInvoiceAuthurlVO;
import com.ruoyi.xindian.wx_pay.VO.invoiceVO;
import com.ruoyi.xindian.wx_pay.domain.*;
import com.ruoyi.xindian.wx_pay.mapper.OrderInfoMapper;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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


    @Resource
    private OrderInfoMapper orderInfoMapper;


    @Resource
    private InvoiceMapper invoiceMapper;


    /**
     * 发送给医生的公众号消息推送
     */
    public  void dockerMsg(String name) throws Exception {

        String accessToken = getAccessToken();

        Set<String> userOpenId = getUserOpenId(accessToken);

        for (String next : userOpenId) {
            sendOrderMsg("你好，有一条新的问诊订单",next,name,"患者提交了一个心电订单");
        }
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
                .miniProgram(new WxMpTemplateMessage.MiniProgram("wx331beedb5dbfe460","/pages/grob/grob"))
                .build();
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
        }

    }


    public  void sendMsg(String first, String userOpenid, String name, String serviceName,String state) throws Exception {

        MessageTemplateEntity messageTemplateEntity = new MessageTemplateEntity();
        String time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date());
        messageTemplateEntity.setMessageData(new MessageValueEntity(time), new MessageValueEntity(first),new MessageValueEntity(state),new MessageValueEntity(name),new MessageValueEntity(serviceName));

//        List<WxMpTemplateData> wxMpTemplateDataList = Arrays.asList(
//                new WxMpTemplateData("date7",time),
//                new WxMpTemplateData("thing8",first),
//                new WxMpTemplateData("phrase4",state),
//                new WxMpTemplateData("thing11",name),
//                new WxMpTemplateData("thing3",serviceName)
//        );
        Map<String,Object> map = new HashMap<>();
        map.put("appid","wx50da6a0dfc5c45c0");
        map.put("pagepath","pages/record/index");
//
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("touser", userOpenid); //用户openid
        paramsMap.put("miniprogram", map); //用户openid
        paramsMap.put("template_id", "LXUKc7XBotVpT_w7wDx1RpNF1PpNEJa3t6gaMsP7_Cw"); //推送消息模板id
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
     * 商户获取授权页ticket
     */
    public String getCgiBinTicket() throws Exception {
        String wxAccessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("access_token",wxAccessToken ); //用户openid
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=wx_card&access_token=";
        HashMap<String,String> responseMap=null;
        String cgiBinTicket = null;
        String expire = null;
        try {
            responseMap = restTemplate.postForObject(url+wxAccessToken, request, HashMap.class);
            cgiBinTicket = (String)responseMap.get("ticket");
            expire = responseMap.get("expires_in");
            log.info("微信获取自身的开票平台识别码，，返回数据:{}",responseMap);
        }catch (Exception e){
            System.out.println(e);
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",responseMap);
        }
        return cgiBinTicket;
    }

    /**
     * 通过订单编号获取链接
     */
    public WxCardInvoiceAuthurlVO getAuthurl(String orderId) throws Exception {

        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if (orderInfo!=null){

            //获取自身的开票平台识别码s_pappid
            String cardInvoiceSeturl = getCardInvoiceSeturl();
            //商户获取授权页ticket
            String cgiBinTicket = getCgiBinTicket();

            WxCardInvoiceAuthurlVO cardInvoiceAuthurl = getCardInvoiceAuthurl(cardInvoiceSeturl, cgiBinTicket, orderInfo.getId(), orderInfo.getTotalFee());

            redisTemplate.opsForValue().set("invoice:"+orderId,orderId ,30 ,TimeUnit.MINUTES);

            return cardInvoiceAuthurl;
        }
        return null;
    }


    public Boolean getInvoiceState(String orderId) throws Exception {
//获取自身的开票平台识别码s_pappid
        String s_pappid = getCardInvoiceSeturl();
        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("access_token",accessToken );
        paramsMap.put("order_id", orderId);
        paramsMap.put("s_pappid", s_pappid);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/getauthdata?access_token=";
        invoiceVO sendMessageVo=null;

        try {
            sendMessageVo = restTemplate.postForObject(url+accessToken, request, invoiceVO.class);
            log.info("微信商户获取小程序授权页链接返回结果：{}",sendMessageVo);
        }catch (Exception e){
            System.out.println(e);
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",sendMessageVo);
        }
        String errmsg = null;
        if (sendMessageVo != null) {
            errmsg = sendMessageVo.getErrmsg();
        }
        if (errmsg != null && errmsg.equals("ok")) {
            Invoice invoice = new Invoice();
            invoice.setOrderId(orderId);

            invoice.setPrice(new BigDecimal(sendMessageVo.getMoney()));
            invoice.setOpenId(sendMessageVo.getOpenid());


            BizField bizField = sendMessageVo.getUser_auth_info().getBiz_field();
            if (bizField != null) {
                invoice.setUserAuthInfo("biz_field");
                invoice.setTitle(bizField.getTitle());
                invoice.setTaxNo(bizField.getTax_no());
                invoice.setAddr(bizField.getAddr());
                invoice.setPhone(bizField.getPhone());
                invoice.setBankType(bizField.getBank_type());
                invoice.setBankNo(bizField.getBank_no());

            } else {
                UserField userField = sendMessageVo.getUser_auth_info().getUser_field();
                invoice.setUserAuthInfo("user_field");
                invoice.setTitle(userField.getTitle());
                invoice.setTaxNo(userField.getTax_no());
                invoice.setAddr(userField.getAddr());
                invoice.setPhone(userField.getPhone());
                invoice.setBankType(userField.getBank_type());
                invoice.setBankNo(userField.getBank_no());
            }
            invoice.setCreateTime(new Date());
            invoice.setUpdateTime(new Date());
            invoice.setState("待处理");
            invoiceMapper.insert(invoice);
            return true;
        }
        return false;
    }

    /**
     *商户获取小程序授权页链接
     */
    public WxCardInvoiceAuthurlVO getCardInvoiceAuthurl( String s_pappid, String ticket, String order_id, BigDecimal money) throws Exception {

        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("access_token",accessToken );
        paramsMap.put("timestamp",  WXPayUtil.getCurrentTimestamp()+"");
        paramsMap.put("source", "wxa");
        paramsMap.put("type", "1");
        paramsMap.put("s_pappid", s_pappid);
        paramsMap.put("ticket", ticket);
        paramsMap.put("order_id", order_id);
        paramsMap.put("money", money);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/getauthurl?access_token=";
        HashMap<String,String> sendMessageVo=null;
        String appid = null;
        String auth_url = null;
        try {
            sendMessageVo = restTemplate.postForObject(url+accessToken, request, HashMap.class);
            appid =sendMessageVo.get("appid");
            auth_url = sendMessageVo.get("auth_url");
            log.info("微信商户获取小程序授权页链接返回结果：{}",sendMessageVo);
        }catch (Exception e){
            System.out.println(e);
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",sendMessageVo);
        }

//        HttpRestResponse response = null;
//        String appid = null;
//        String auth_url = null;
//        try {
//            response = httpRestClient.postData("https://api.weixin.qq.com/card/invoice/getauthurl?access_token=" + accessToken, request);
//            LOGGER.info("微信商户获取小程序授权页链接返回结果：{}", response.getData());
//            Map<?, ?> responseMap = response.toObject(Map.class);
//            appid = (String)responseMap.get("appid");
//            auth_url = (String)responseMap.get("auth_url");
//        } catch (Exception e) {
//            LOGGER.error("微信商户获取小程序授权页链接异常，请求参数：{}，返回数据:{}, e:{}", requestDataStr.toJSONString(), response, e.getMessage());
//        }
//
//        //添加到缓存待授权微信发票卡券集合，统一处理
//        Timestamp validTime = null;//设置当前时间后6分钟有效期
//        String wxInvoiceAuthOrderListCacheKey = null;//设置缓存key
//        String wxInvoiceAuthOrderListCache = redisClient.get(wxInvoiceAuthOrderListCacheKey);
//        if(null != wxInvoiceAuthOrderListCache) {
//            List<WxInvoiceAuthOrderListCacheVO> wxInvoiceAuthOrderList = JSONUtil.toList(wxInvoiceAuthOrderListCache.getBytes(), WxInvoiceAuthOrderListCacheVO.class);
//            wxInvoiceAuthOrderList.add(new WxInvoiceAuthOrderListCacheVO(tenantId, order_id, validTime));
//            redisClient.set(wxInvoiceAuthOrderListCacheKey, JSONUtil.toJson(wxInvoiceAuthOrderList));
//        }

        return new WxCardInvoiceAuthurlVO(appid, auth_url);
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
     * 上传微信卡卷
     */
    public void setWXCardInvoice(String orderId,String sMediaId) throws Exception {
        OrderInfo orderInfo = orderInfoMapper.searchAllById(orderId);
        String accessToken = getAccessToken();
        Invoice invoice = invoiceMapper.selectById(orderId);
        //微信卡包id
        String  cardid = setCardInvoiceContact();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        HashMap<String ,Object> hashMap = new HashMap<>();
        HashMap<String ,Object> hashMap2 = new HashMap<>();
        hashMap2.put("nonce_str",WXPayUtil.generateNonceStr());
        hashMap.put("billing_no",invoice.getBillingNo() );
        hashMap.put("billing_code",invoice.getBillingCode() );
        hashMap.put("tax",invoice.getTax() );
        hashMap.put("s_pdf_media_id",sMediaId);
        hashMap.put("fee",invoice.getPrice());
        hashMap.put("title",invoice.getTitle());
        hashMap.put("fee_without_tax",invoice.getPrice());

        hashMap.put("billing_time",OrderNoUtils.getNoN(invoice.getBillingTime()) );

        List<Map<String,Object>> mapList = new ArrayList<>();
        for (SuborderOrderInfo c : orderInfo.getSuborderOrderInfos()){
            Map<String,Object> map = new HashMap<>();
            map.put("price",c.getProductPrice());
            map.put("num" ,c.getSum());
            map.put("name",c.getProductName());
            mapList.add(map);
        }

        hashMap.put("info",mapList);
        paramsMap.put("access_token",accessToken );
        hashMap2.put("user_card" ,hashMap);
        paramsMap.put("card_ext",hashMap2);
        paramsMap.put("order_id",orderId);
        paramsMap.put("card_id",cardid);
        paramsMap.put("appid",WXPayConstants.WX_PUBLIC_ID);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/insert?access_token=";
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
     * 更新发票卡券状态
     */
    public Boolean setCardState(String carId,String code) throws Exception {

        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("access_token",accessToken);
        paramsMap.put("card_id",carId);
        paramsMap.put("code",code);
        paramsMap.put("reimburse_status","INVOICE_REIMBURSE_CANCEL");
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/platform/updatestatus?access_token=";
        HashMap<String,String> sendMessageVo=null;
        String cardId=null;
        try {
            sendMessageVo = restTemplate.postForObject(url+accessToken, request, HashMap.class);
            log.info("微信获取自身的开票平台识别码正常 。 返回数据:{}",sendMessageVo);
            cardId= sendMessageVo.get("errmsg");
        }catch (Exception e){
            System.out.println(e);
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",sendMessageVo);
        }
        if (cardId != null && cardId.equals("ok")) {
            return true;

        }
        return false;
    }



    /**
     * 设置微信卡卷
     */
    public String setCardInvoiceContact() throws Exception {

        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        HashMap<String ,Object> hashMap = new HashMap<>();
        HashMap<String ,Object> hashMap2 = new HashMap<>();
        hashMap2.put("type","河南省增值税普通发票" );
        hashMap2.put("payee","河南迈雅科技有限公司" );
        hashMap.put("logo_url","url" );
        hashMap.put("title","河南迈雅科技" );
        paramsMap.put("access_token",accessToken );

        hashMap2.put("base_info" ,hashMap);
        paramsMap.put("invoice_info",hashMap2);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/platform/createcard?access_token=";
        HashMap<String,String> sendMessageVo=null;
        String cardId=null;
        try {
            sendMessageVo = restTemplate.postForObject(url+accessToken, request, HashMap.class);
            log.info("微信获取自身的开票平台识别码正常 。 返回数据:{}",sendMessageVo);
           cardId= sendMessageVo.get("card_id");
        }catch (Exception e){
            System.out.println(e);
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",sendMessageVo);
        }
        return cardId;
    }

    /**
     * 上传pdf格式文件
     */
    public String setCardUpload(MultipartFile file) throws Exception {

        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("access_token",accessToken );
        paramsMap.put("pdf",file);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        String url = "https://api.weixin.qq.com/card/invoice/platform/setpdf?access_token=";
        HashMap<String,String> sendMessageVo=null;
        String sMediaId=null;
        try {
            sendMessageVo = restTemplate.postForObject(url+accessToken, request, HashMap.class);
            log.info("微信获取自身的开票平台识别码正常 。 返回数据:{}",sendMessageVo);
          sMediaId = sendMessageVo.get("s_media_id");
        }catch (Exception e){
            System.out.println(e);
            log.error("微信获取自身的开票平台识别码异常，，返回数据:{}",sendMessageVo);
        }
        return sMediaId;
    }




    /**
     * 获取自身的开票平台识别码
     * @return
     * @throws Exception
     */
    public String  getCardInvoiceSeturl() throws Exception {
        if (Boolean.TRUE.equals(redisTemplate.hasKey("getCardInvoiceSeturl"))){
            return redisTemplate.opsForValue().get("getCardInvoiceSeturl");
        }
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
            errcode = sendMessageVo.get("errmsg");
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
        if (s_pappid != null) {
            redisTemplate.opsForValue().set("getCardInvoiceSeturl",s_pappid,24,TimeUnit.HOURS);
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
