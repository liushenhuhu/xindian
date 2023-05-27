package com.ruoyi.xindian.wxpay.entity;

import lombok.Data;

/**
 * @author 陳樂
 * @version 1.0.0
 * @ClassName OrderReturnInfo.java
 * @Description 查询订单返回实体类
 * @createTime 2022年04月09日 13:01:00
 */
@Data
public class QueryReturnInfo {

    private String return_code;

    private String return_msg;

    private String result_code;

    private String err_code;

    private String err_code_des;

    private String appid;

    private String mch_id;

    private String nonce_str;

    private String sign;

    private String prepay_id;

    private String trade_type;

    private String device_info;

    private String openid;

    private String is_subscribe;

    private String trade_state;

    private String bank_type;

    private int total_fee;

    private int settlement_total_fee;

    private String fee_type;

    private int cash_fee;

    private String cash_fee_type;

    private int coupon_fee;

    private int coupon_count;

    private String coupon_type_$n;

    private String coupon_id_$n;

    private String transaction_id;

    private String out_trade_no;

    private String time_end;

    private String trade_state_desc;

}
