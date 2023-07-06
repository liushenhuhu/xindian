package com.ruoyi.xindian.wx_pay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName t_product
 */
@TableName(value ="t_product")
@Data
public class Product implements Serializable {
    /**
     * 商Bid
     */
    @TableId(type = IdType.AUTO)
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 价格(分)
     */
    private BigDecimal price;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品图片
     */
    private String productUrl;

    /**
     * 商品折扣
     */
    private BigDecimal discount;

    /**
     * 状态（1：上架，2：下架，3：缺货）
     */
    private String state;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 销量
     */
    private Long sales;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}