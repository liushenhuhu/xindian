package com.ruoyi.xindian.wx_pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    /**
     * 未支付
     */
    NOTPAY("未支付"),


    /**
     * 支付成功
     */
    SUCCESS("已支付"),

    /**
     * 已关闭
     */
    CLOSED("超时已关闭"),

    /**
     * 已取消
     */
    CANCEL("用户已取消"),

    /**
     * 退款中
     */
    REFUND_PROCESSING("退款中"),

    /**
     * 已退款
     */
    REFUND_SUCCESS("已退款"),
    /**
     * 已发货
     */
    DELIVER_GOODS("已发货"),

    /**
     * 已发货
     */
    ORDER_STATUS("交易成功"),

    /**
     * 退款申请
     */
    ORDER_BACK("退款申请"),

    /**
     *
     */
    SERVE_ORDER("服务"),
    /**
     * 退款异常
     */
    REFUND_ABNORMAL("退款异常");

    /**
     * 类型
     */
    private final String type;
}
