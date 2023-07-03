package com.ruoyi.xindian.wx_pay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName t_refund_info
 */
@TableName(value ="t_refund_info")
@Data
public class RefundInfo implements Serializable {
    /**
     * 款单id
     */
    @TableId(type = IdType.AUTO)
    @Excel(name = "款单id")
    private Long id;

    /**
     * 商户订单编号
     */
    @Excel(name = "商户订单编号")
    private String orderNo;

    /**
     * 商户退款单编号
     */
    @Excel(name = "商户退款单编号")
    private String refundNo;

    /**
     * 支付系统退款单号
     */
    @Excel(name = "支付系统退款单号")
    private String refundId;

    /**
     * 原订单金额(分)
     */
    @Excel(name = "原订单金额(分)")
    private BigDecimal totalFee;

    /**
     * 退款金额(分)
     */
    @Excel(name = "退款金额(分)")
    private BigDecimal refund;

    /**
     * 退款原因
     */
    @Excel(name = "退款原因")
    private String reason;

    /**
     * 退款状态
     */
    @Excel(name = "退款状态")
    private String refundStatus;

    /**
     * 申请退款返回参数
     */
    @Excel(name = "申请退款返回参数")
    private String contentReturn;

    /**
     * 退款结果通知参数
     */
    @Excel(name = "退款结果通知参数")
    private String contentNotify;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}