package com.ruoyi.xindian.wx_pay.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 子订单，通过order_father来绑定主订单
 * @TableName t_suborder_order_info
 */
@TableName(value ="t_suborder_order_info")
@Data
public class SuborderOrderInfo implements Serializable {
    /**
     * 子订单id
     */
    @TableId
    @Excel(name = "子订单id")
    private Long suborderId;

    /**
     * 子订单对应的主订单id
     */
    @Excel(name = "子订单对应的主订单id")
    private String orderFather;

    /**
     * 商品id
     */
    @Excel(name = "商品id")
    private Long productId;

    /**
     * 商品购买数量
     */
    private Long sum;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Excel(name = "修改时间")
    private Date updateTime;

    @TableField(exist = false)
    private Product product;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}