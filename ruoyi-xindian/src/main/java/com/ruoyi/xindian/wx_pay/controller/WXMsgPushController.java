package com.ruoyi.xindian.wx_pay.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.utils.sign.AesUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
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

    @Resource
    private AesUtils aesUtils;

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
        wxPublicRequest.dockerMsg();
    }

    /**
     * 公众号消息推送
     */
    @GetMapping("/ww")
    public AjaxResult text() throws Exception {
        wxPublicRequest.sendSXDetectionMsg("o3aud5xxBfdxArqeIj12idYIxstE","王金辉","17638341690","20200222222222","心电衣检测");
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


    @GetMapping("/aes")
    public AjaxResult aes(String id) throws Exception {
        String encrypt = aesUtils.encrypt(id);
        System.out.println(encrypt);
        return AjaxResult.success(encrypt);
    }
    @GetMapping("/aesd")
    public AjaxResult aesd(String id) throws Exception {
        String encrypt = aesUtils.decrypt(id);
        System.out.println(encrypt);
        return AjaxResult.success(encrypt);
    }

    /**
     * 出现预警时，根据传递的fatherPhone向添加的用户发送公众号通知
     * @param fatherPhone
     * @return
     * @throws Exception
     */
    @GetMapping("/alertInfoToOfficialAccount")
    public AjaxResult alertInfoToOfficialAccount(String fatherPhone,String info,String username) throws Exception {
        wxPublicRequest.alertInfoToOfficialAccount(fatherPhone,info,username);
        return AjaxResult.success();
    }
}
