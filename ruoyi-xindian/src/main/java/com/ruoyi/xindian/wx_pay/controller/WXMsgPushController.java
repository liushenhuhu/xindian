package com.ruoyi.xindian.wx_pay.controller;

import com.ruoyi.xindian.wx_pay.util.OrderNoUtils;
import com.ruoyi.xindian.wx_pay.util.WXPayConstants;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
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



    /**
     * 公众号消息推送
     */
    public static void main(String[] args) {

//        WXPublicRequest.sendOrderMsg("你好，有一条新的问诊订单","otRV25MQPOOEn-qJmZve9im0Phno", "张三","这是一条测试消息");
    }







}
