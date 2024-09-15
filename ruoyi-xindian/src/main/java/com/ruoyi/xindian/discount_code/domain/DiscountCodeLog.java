package com.ruoyi.xindian.discount_code.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName discount_code_log
 */
@TableName(value ="discount_code_log")
@Data
public class DiscountCodeLog implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer logId;

    /**
     * 优惠码
     */
    private String promotionCode;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 优惠价
     */
    private Integer discount;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}