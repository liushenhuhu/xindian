package com.ruoyi.xindian.order.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 
 * @TableName invoice
 */
@TableName(value ="invoice")
@Data
public class Invoice implements Serializable {
    /**
     * 订单编号
     */
    @TableId
    @Excel(name = "订单id")
    private String orderId;

    /**
     * 订单价格
     */
    @Excel(name = "订单价格")
    private BigDecimal price;

    /**
     * 抬头类型
     */
    @Excel(name = "抬头类型")
    private String userAuthInfo;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String title;

    /**
     * 税号
     */
    @Excel(name = "税号")
    private String taxNo;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String addr;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;

    /**
     * 银行类型
     */
    @Excel(name = "银行类型")
    private String bankType;

    /**
     * 银行卡号
     */
    @Excel(name = "银行卡号")
    private String bankNo;

    /**
     * 
     */
    private String customFieldKey;

    /**
     * 
     */
    private String customFieldValue;

    /**
     * openid
     */
    @Excel(name = "openid")
    private String openId;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @Excel(name = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String state;

    /**
     * 发票pdf的地址
     */
    @Excel(name = "发票pdf的地址")
    private String invoiceUrl;


    /**
     * 发票的号码
     */
    private String billingNo;

    /**
     * 发票的代码
     */
    private String billingCode;

    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 校验码，发票pdf右上角
     */
    private String checkCode;

    /**
     * 发票报销状态
     */
    private String status;

    /**
     * 发票的开票时间
     */
    private Date billingTime;

    @TableField(exist = false)
    private String bilTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}