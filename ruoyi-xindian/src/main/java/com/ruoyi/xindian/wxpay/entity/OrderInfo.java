package com.ruoyi.xindian.wxpay.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @author 陳樂
 * @version 1.0.0
 * @ClassName OrderInfo.java
 * @Description 预订单 千万不要使用驼峰，到时候可能引发一些奇葩的问题和支付失败
 * @createTime 2022年04月09日 13:00:00
 */
@XStreamAlias("xml")
@Data
public class OrderInfo {

    @XStreamAlias("appid")
    private String appid;// 小程序ID
    @XStreamAlias("mch_id")
    private String mch_id;// 商户号
    @XStreamAlias("nonce_str")
    private String nonce_str;// 随机字符串
    @XStreamAlias("sign_type")
    private String sign_type;//签名类型
    @XStreamAlias("sign")
    private String sign;// 签名
    @XStreamAlias("body")
    private String body;// 商品描述
    @XStreamAlias("out_trade_no")
    private String out_trade_no;// 商户订单号
    @XStreamAlias("total_fee")
    private int total_fee;// 标价金额 ,单位为分
    @XStreamAlias("spbill_create_ip")
    private String spbill_create_ip;// 终端IP
    @XStreamAlias("notify_url")
    private String notify_url;// 通知地址
    @XStreamAlias("trade_type")
    private String trade_type;// 交易类型
    @XStreamAlias("openid")
    private String openid;//用户标识

}
