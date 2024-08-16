package com.ruoyi.xindian.wSuryvey.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName purchase_limitation
 */
@TableName(value ="purchase_limitation")
@Data
public class PurchaseLimitation implements Serializable {
    /**
     * 限购id
     */
    @TableId(type = IdType.AUTO)
    private Integer purchaseLimitationId;

    /**
     * 手机号
     */
    private String patientPhone;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 是否购买（0=未购买，1=已购买）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}