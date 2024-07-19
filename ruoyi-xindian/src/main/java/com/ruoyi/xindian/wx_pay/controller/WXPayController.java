package com.ruoyi.xindian.wx_pay.controller;





import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.domain.RefundInfo;
import com.ruoyi.xindian.wx_pay.enums.OrderStatus;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.service.RefundInfoService;
import com.ruoyi.xindian.wx_pay.service.WxPayService;
import com.ruoyi.xindian.wx_pay.util.HttpClientUtil;
import com.ruoyi.xindian.wx_pay.util.WXPayConstants;
import com.ruoyi.xindian.wx_pay.util.WXPayUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ruoyi.xindian.wx_pay.enums.OrderStatus.SUCCESS;


/**
 * @author wang
 * @version 1.0.0
 * @ClassName WXPayController.java
 * @Description 微信支付相关接口
 * @createTime 2023年07月09日 13:09:00
 */
@RestController
@RequestMapping(value = "/api/v1")
public class WXPayController {

    @Resource
    private WxPayService wxPayService;
    @Resource
    private OrderInfoService orderInfoService;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Resource
    private TokenService tokenService;

    @Resource
    private IPatientManagementService patientManagementService;

    @Resource
    private RefundInfoService refundsInfoService;

    /**
     * 调用支付接口
     *
     * @param orderId 商品id
     * @param request
     * @return
     */

    @RequestMapping("prePay")
    public Map<String, Object> prePay(String  orderId,HttpServletRequest request){

//        获取token中发送请求的用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        Long userId = loginUser.getUser().getUserId();
        if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderPayId"+userId))){
            return AjaxResult.error("请勿重复点击");
        }
        redisTemplate.opsForValue().set("getOrderPayId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

        // 返回参数
        Map<String, Object> resMap = new HashMap<>();
        //获取请求ip地址
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if(ip.indexOf(",")!=-1){
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }


        try {
            System.out.println("productId= "+orderId);
            // 拼接统一下单地址参数
            Map<String, Object> paraMap = new HashMap<>();
            //查询订单信息
            OrderInfo order =  orderInfoService.createOrderByProductId(orderId);
            if (order==null){
                throw new ServiceException("订单不存在");
            }
            if(!order.getOrderStatus().equals(OrderStatus.NOTPAY.getType())){
                throw new ServiceException("订单不存在");
            }

            String body = order.getTitle();//订单介绍
            String orderNum = order.getOrderNo();//商户订单号，由随机数组成
            String openId = order.getOpenId();//获取到用户登录的openid

            BigDecimal price = order.getTotalFee();//价格

//       Integer price = 1;//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
            System.out.println("body= "+body);
//       body = new String(body.getBytes("ISO-8859-1"),"UTF-8").toString();
//       System.out.println("body= "+body);
            // 封装11个必需的参数
            paraMap.put("appid", WXPayConstants.APP_ID);
            paraMap.put("mch_id", WXPayConstants.MCH_ID);//商家ID
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            paraMap.put("body", body);     //购买订单介绍
            paraMap.put("out_trade_no", orderNum);//订单号
            paraMap.put("total_fee",price);    //测试改为固定金额
            paraMap.put("spbill_create_ip", ip);
            paraMap.put("notify_url",WXPayConstants.CALLBACK_URL);// 此路径是微信服务器调用支付结果通知路径
            paraMap.put("trade_type", "JSAPI");
            paraMap.put("openid", openId);
            paraMap.put("receipt", "Y");
            String sign = WXPayUtil.generateSignature(paraMap, WXPayConstants.PATERNER_KEY);//商户密码
            //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
            paraMap.put("sign", sign);
            //将所有参数(map)转xml格式
            String xml = WXPayUtil.mapToXml(paraMap);
//        xml = new String(xml.getBytes("ISO-8859-1"), "UTF-8");
//       String xml = new String(WXPayUtil.mapToXml(paraMap).getBytes(), "utf-8");
            System.err.println("xml=: "+xml);
            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
            String unifiedorder_url = WXPayConstants.UNIFIEDORDER_URL;//统一下单接口
            System.out.println("统一下单接口unifiedorder_url:"+unifiedorder_url);
            //发送post请求"统一下单接口"返回预支付id:prepay_id
            String xmlStr = HttpClientUtil.doPostXml(unifiedorder_url, xml);
            System.out.println("xmlStr:"+xmlStr);
            //以下内容是返回前端页面的json数据
            //预支付id
            String prepay_id = "";
            if (xmlStr.indexOf("SUCCESS") != -1) {
                Map<String, Object> map = WXPayUtil.xmlToMap(xmlStr);//XML格式字符串转换为Map
                prepay_id =  map.get("prepay_id").toString();
                System.err.println("prepay_id_1=  "+prepay_id);
            }
            System.err.println("prepay_id_2=  "+prepay_id);
            Map<String, Object> payMap = new HashMap<String, Object>();
            // 封装所需6个参数调支付页面
            payMap.put("appId", WXPayConstants.APP_ID);
            payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp()+"");//获取当前时间戳，单位秒
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id="+prepay_id);
            //生成带有 sign 的 XML 格式字符串
            String paySign = WXPayUtil.generateSignature(payMap, WXPayConstants.PATERNER_KEY);
            payMap.put("paySign", paySign);
            // 封装正常情况返回数据
            resMap.put("success",true);
            resMap.put("payMap",payMap);
        } catch (Exception e) {
            // 封装异常情况返回数据
            resMap.put("success",false);
            resMap.put("message","调用统一订单接口错误");
            e.printStackTrace();
        }
        return resMap;
    }



    /**
     * 支付成功回调
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("callBack")
    public AjaxResult callBack(HttpServletRequest request, HttpServletResponse response){
        AjaxResult ajaxResult = new AjaxResult();
        System.err.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
        InputStream is = null;
        String resXml = "";
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = WXPayUtil.inputStream2String(is);
            Map<String, Object> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
            if(notifyMap.get("return_code").equals("SUCCESS")){
                try {
                    wxPayService.processOrder(xml);
                    ajaxResult.success("支付回调成功，修改订单状态为支付成功",SUCCESS);
                    //告诉微信已经接收到消息
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    BufferedOutputStream out = new BufferedOutputStream(
                            response.getOutputStream());
                    out.write(resXml.getBytes());
                    out.flush();
                    out.close();
                    System.err.println("返回给微信的值："+resXml.getBytes());
                    is.close();
                }catch (Exception e){
                    ajaxResult.error("订单状态修改失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxResult;
    }





    /**
     * 调用退款接口，取消订
     * @param id 订单编号
     * @param reason 退款原因
     * @param response
     * @return
     */
    @GetMapping("refund")
    public Map<String, Object> refund(String id,String reason,HttpServletResponse response){

        // 返回参数
        Map<String, Object> resMap = new HashMap<>();
        String resXml = "";
        try {
            // 拼接统一下单地址参数
            Map<String, Object> paraMap = new HashMap<>();
            OrderInfo order =  orderInfoService.createOrderByOrderID(id);
            if (order.getOrderStatus().equals("服务")||order.getOrderStatus().equals("卡片")||order.getOrderStatus().equals("报告服务")||order.getOrderStatus().equals("周报")){
                throw new ServiceException("服务类型不可退");
            }
            String orderNum = order.getOrderNo();//商户订单号，由随机数组成
            BigDecimal price = order.getTotalFee();//金额

            //创建退款记录
            RefundInfo refundByOrderNo = refundsInfoService.createRefundByOrderNo(orderNum, reason);
//       Integer price = 1;//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
            System.out.println("订单号= "+orderNum);
            // 封装必需的参数
            paraMap.put("appid", WXPayConstants.APP_ID);
            paraMap.put("mch_id", WXPayConstants.MCH_ID);//商家ID
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            paraMap.put("out_trade_no", orderNum);//订单号
            paraMap.put("out_refund_no", refundByOrderNo.getRefundNo());//商户退款单号
            paraMap.put("total_fee",price);    //测试改为固定金额  订单金额
            paraMap.put("refund_fee",price);    //退款金额
//       paraMap.put("notify_url", WXPayConstants.notify_url);   //退款路径
            String sign = WXPayUtil.generateSignature(paraMap, WXPayConstants.PATERNER_KEY);//商户密码
            //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
            paraMap.put("sign", sign);
            //将所有参数(map)转xml格式
            String xml = WXPayUtil.mapToXml(paraMap);
            System.out.println("xml:"+xml);
            // 退款 https://api.mch.weixin.qq.com/secapi/pay/refund
            String refund_url = WXPayConstants.REFUND_URL;//申请退款路径接口
            System.out.println("refund_url:"+refund_url);
            //发送post请求"申请退款"
            String xmlStr = HttpClientUtil.doRefund(refund_url, xml);
            System.out.println("退款xmlStr:"+xmlStr);
            /*退款成功回调修改订单状态*/
            if (xmlStr.indexOf("SUCCESS") != -1) {
                Map<String, Object> map = WXPayUtil.xmlToMap(xmlStr);//XML格式字符串转换为Map
                if (map.get("result_code").equals("FAIL")){
                    if (map.get("err_code_des").equals("订单已全额退款")){
                        orderInfoService.updateRefundByOrdersNum(id);
                    }
                    throw new ServiceException((String) map.get("err_code_des"));
                }
                if(map.get("return_code").equals("SUCCESS")){
                    resMap.put("success",true);//此步说明退款成功
                    resMap.put("data","退款成功");
                    System.out.println("退款成功");
                    try {
                        //修改订单状态
                        orderInfoService.updateRefundByOrdersNum(id);
                        //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        BufferedOutputStream out = new BufferedOutputStream(
                                response.getOutputStream());
                        out.write(resXml.getBytes());
                        out.flush();
                        out.close();
                        //修改退款记录
                        refundsInfoService.updateRefund(xmlStr);
                        System.err.println("返回给微信的值："+resXml.getBytes());
                    }catch (Exception e){
                        resMap.put("fail","订单状态修改失败");
                    }
                }
            }else {
                //退款成功，但是修改失败
                resMap.put("success","fail");
                resMap.put("data","修改订单状态失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resMap;
    }


    /**
     * 查询订单知否支付
     * @param id
     * @return
     */
    public Boolean orderQuery(String id){

        // 返回参数
        String resXml = "";
        try {
            // 拼接统一下单地址参数
            Map<String, Object> paraMap = new HashMap<>();
            OrderInfo order =  orderInfoService.createOrderByOrderID(id);

            if (order==null){
                return false;
            }
            if (!order.getOrderStatus().equals(OrderStatus.NOTPAY.getType())){
                return false;
            }
            String orderNum = order.getOrderNo();//商户订单号，由随机数组成
            System.out.println("订单号= "+orderNum);
            // 封装必需的参数
            paraMap.put("appid", WXPayConstants.APP_ID);
            paraMap.put("mch_id", WXPayConstants.MCH_ID);//商家ID
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            paraMap.put("out_trade_no", order.getOrderNo());//订单号
            String sign = WXPayUtil.generateSignature(paraMap, WXPayConstants.PATERNER_KEY);//商户密码
            //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
            paraMap.put("sign", sign);
            //将所有参数(map)转xml格式
            String xml = WXPayUtil.mapToXml(paraMap);
            System.out.println("xml:"+xml);
            // 退款 https://api.mch.weixin.qq.com/secapi/pay/refund
            String orderQueryUrl = WXPayConstants.ORDER_QUERY_URL;//申请退款路径接口
            System.out.println("orderQueryUrl:"+orderQueryUrl);
            //发送post请求"查看订单状态"
            String xmlStr = HttpClientUtil.doPostXml(orderQueryUrl, xml);;
            System.out.println("订单 xmlStr:"+xmlStr);
            /*退款成功回调修改订单状态*/
            Map<String, Object> map = WXPayUtil.xmlToMap(xmlStr);//XML格式字符串转换为Map
            if (map.get("result_code").equals("SUCCESS")&&map.get("return_code").equals("SUCCESS")&&map.get("trade_state").equals("SUCCESS")){
                wxPayService.processOrder(xmlStr);
                return true;
            }
            redisTemplate.opsForValue().set("orderQuery:"+id,id,20, TimeUnit.SECONDS);
            return false;

        } catch (Exception e) {
         return false;
        }
    }

    /**
     * 查询订单知否支付
     * @param id
     * @return
     */
    public Boolean delOrderQuery(String id){

        // 返回参数
        String resXml = "";
        try {
            // 拼接统一下单地址参数
            Map<String, Object> paraMap = new HashMap<>();
            OrderInfo order =  orderInfoService.createOrderByOrderID(id);

            if (order==null){
                return false;
            }
            if (!order.getOrderStatus().equals(OrderStatus.NOTPAY.getType())){
                return false;
            }
            String orderNum = order.getOrderNo();//商户订单号，由随机数组成
            System.out.println("订单号= "+orderNum);
            // 封装必需的参数
            paraMap.put("appid", WXPayConstants.APP_ID);
            paraMap.put("mch_id", WXPayConstants.MCH_ID);//商家ID
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            paraMap.put("out_trade_no", orderNum);//订单号
            String sign = WXPayUtil.generateSignature(paraMap, WXPayConstants.PATERNER_KEY);//商户密码
            //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
            paraMap.put("sign", sign);
            //将所有参数(map)转xml格式
            String xml = WXPayUtil.mapToXml(paraMap);
            System.out.println("xml:"+xml);
            //关闭订单
            String orderQueryUrl = "https://api.mch.weixin.qq.com/pay/closeorder";//申请关闭订单接口
            //发送post请求"查看订单状态"
            String xmlStr = HttpClientUtil.doPostXml(orderQueryUrl, xml);;
            System.out.println("订单 xmlStr:"+xmlStr);
            /*退款成功回调修改订单状态*/
            Map<String, Object> map = WXPayUtil.xmlToMap(xmlStr);//XML格式字符串转换为Map
            if (map.get("result_code").equals("SUCCESS")&&map.get("return_code").equals("SUCCESS")&&map.get("return_msg").equals("OK")){
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


}
