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
 * @TableName t_payment_info
 */
@TableName(value ="t_payment_info")
@Data
public class PaymentInfo implements Serializable {
    /**
     * 支付记录id
     */
    @TableId(type = IdType.AUTO)
    @Excel(name = "支付记录id")
    private Long id;

    /**
     * 商户订单编号
     */
    @Excel(name = "商户订单编号")
    private String orderNo;

    /**
     * 支付系统交易编号
     */
    @Excel(name = "支付系统交易编号")
    private String transactionId;

    /**
     * 支付类型
     */
    @Excel(name = "支付类型")
    private String paymentType;

    /**
     * 交易类型
     */
    @Excel(name = "交易类型")
    private String tradeType;

    /**
     * 交易状态
     */
    @Excel(name = "交易状态")
    private String tradeState;

    /**
     * 支付金额(分)
     */
    @Excel(name = "支付金额(分)")
    private BigDecimal payerTotal;

    /**
     * 通知参数
     */
    @Excel(name = "通知参数")
    private String content;

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

    /**
     * 用户名称
     */
    @Excel(name = "用户名称")
    private String userName;

    /**
     * 购买信息
     */
    @Excel(name = "购买信息")
    private String orderTitle;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}