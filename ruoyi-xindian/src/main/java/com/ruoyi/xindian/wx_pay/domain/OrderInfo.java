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
 * @TableName t_order_info
 */
@TableName(value ="t_order_info")
@Data
public class OrderInfo implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    @Excel(name = "订单编号")
    private Long id;

    /**
     * 订单标题
     */
    @Excel(name = "订单标题")
    private String title;

    /**
     * 商户订单编号
     */
    @Excel(name = "商户订单编号")
    private String orderNo;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long userId;

    /**
     * 支付产品id
     */
    @Excel(name = "支付产品id")
    private Long productId;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String body;

    /**
     * 订单金额(分)
     */
    @Excel(name = "订单金额")
    private BigDecimal totalFee;

    /**
     * 订单二维码连接
     */
    @Excel(name = "订单二维码连接")
    private String codeUrl;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    private String orderStatus;

    /**
     * 用户标识
     */
    @Excel(name = "用户标识")
    private String openId;

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