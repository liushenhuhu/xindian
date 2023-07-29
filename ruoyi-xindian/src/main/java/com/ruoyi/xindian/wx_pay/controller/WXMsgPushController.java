package com.ruoyi.xindian.wx_pay.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.xindian.wx_pay.VO.WxCardInvoiceAuthurlVO;
import com.ruoyi.xindian.wx_pay.util.AjaxResult;
import com.ruoyi.xindian.wx_pay.util.OrderNoUtils;
import com.ruoyi.xindian.wx_pay.util.WXPayConstants;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 微信公众号模板消息推送
 */

@RestController
@RequestMapping("/wxMsg")
public class WXMsgPushController {

    @Autowired
    private WXPublicRequest wxPublicRequest;


    @GetMapping("/test")
    public AjaxResult test1(String tt) throws Exception {
        String wxAccessToken = wxPublicRequest.getWXAccessToken();
        String post = "https://api.weixin.qq.com/cgi-bin/openapi/rid/get?access_token="+wxAccessToken;
        JSONObject param = new JSONObject();
        param.put("rid", tt);
        String body = HttpUtil.createPost(post).body(JSONUtil.toJsonStr(param)).execute().body();
        return AjaxResult.success(body);
    }

    @GetMapping("/wxTest")
    public void test1() throws Exception {
        wxPublicRequest.dockerMsg("zs");
    }

    /**
     * 公众号消息推送
     */
    @GetMapping("/ww")
    public AjaxResult text() throws Exception {
//        WXPublicRequest.sendOrderMsg("你好，有一条新的问诊订单","otRV25MQPOOEn-qJmZve9im0Phno", "张三","这是一条测试消息");
        wxPublicRequest.sendMsg("测试","otRV25D_bnz4hjyXOUQoWWoieFVI","张三","这是","诊断完成");
//        wxPublicRequest. getCardInvoiceSeturl();
//        wxPublicRequest.checkCardInvoiceSetbizattrContact();
//        wxPublicRequest.getCgiBinTicket();
//        wxPublicRequest.setCardInvoiceSetbizattrContact();
//        return AjaxResult.success( wxPublicRequest.getInvoiceState("20230721173518132"));
        return AjaxResult.success();
    }

    @GetMapping("/getUrl")
    public AjaxResult getUrl(String orderId) throws Exception {

        WxCardInvoiceAuthurlVO authurl = wxPublicRequest.getAuthurl(orderId);
        if (authurl!=null){
            return AjaxResult.success(authurl);
        }
        return AjaxResult.error("订单不存在");
    }





}
