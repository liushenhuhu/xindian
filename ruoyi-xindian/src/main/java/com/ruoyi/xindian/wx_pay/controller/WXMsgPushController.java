package com.ruoyi.xindian.wx_pay.controller;

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


    /**
     * 公众号消息推送
     */
    @GetMapping("/ww")
    public AjaxResult text() throws Exception {
//        WXPublicRequest.sendOrderMsg("你好，有一条新的问诊订单","otRV25MQPOOEn-qJmZve9im0Phno", "张三","这是一条测试消息");
//        wxPublicRequest.sendOrderMsg("这是一条消息","o3aud50kcKiIIlwuDYCh_DjZjq1o","张三","这是一条测试消息");
//        wxPublicRequest. getCardInvoiceSeturl();
//        wxPublicRequest.checkCardInvoiceSetbizattrContact();
//        wxPublicRequest.getCgiBinTicket();
//        wxPublicRequest.setCardInvoiceSetbizattrContact();
//        return AjaxResult.success( wxPublicRequest.getInvoiceState("20230721173518132"));
        return AjaxResult.success( wxPublicRequest.setCardInvoiceContact());
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
