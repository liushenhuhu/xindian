package com.ruoyi.xindian.discount_code.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 
 * @TableName discount_code
 */
@TableName(value ="discount_code")
@Data
public class DiscountCode implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer promotionId;

    /** 优惠码 */
    @Excel(name = "优惠码")
    private String promotionCode;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 优惠价 */
    @Excel(name = "优惠价")
    private Integer discount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 停用状态
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}