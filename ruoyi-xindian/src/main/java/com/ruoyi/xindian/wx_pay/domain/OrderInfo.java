package com.ruoyi.xindian.wx_pay.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Excel(name = "订单编号")
    private String id;

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

    /**
     * 子订单信息
     */
    @Excel(name = "购买商品")
    @TableField(exist = false)
    List<SuborderOrderInfo> suborderOrderInfos = new ArrayList<>();

    @Excel(name = "用户电话")
    private String patientPhone;
    @Excel(name = "用户姓名")
    private String patientName;
    @Excel(name = "街道地址")
    private String streetAddress;
    @Excel(name = "街道")
    private String street;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "省份")
    private String state;
    @Excel(name = "县")
    private String country;
    @Excel(name = "邮政编码")
    private String postalCode;

    /**
     * 订单当前状态
     */
    private String orderState;

    /**
     * 快递公司
     */
    private String courierCompany;

    /**
     * 快递单号
     */
    private String courierNumber;


    private String pId;


    private Integer delFlag;

    private String  remark;
    /**
     * 用户信息
     */
    @Excel(name = "用户信息")
    @TableField(exist = false)
    private SysUser sysUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}