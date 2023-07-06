package com.ruoyi.xindian.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user_address
 */
@TableName(value ="user_address")
@Data
public class UserAddress implements Serializable {
    /**
     * 地址ID
     */
    @TableId(type = IdType.AUTO)
    private Integer addressId;

    /**
     * 关联到用户表的用户ID，用于标识地址所属的用户。
     */
    private Integer userId;

    /**
     * 收货人姓名
     */
    private String recipientName;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县/区域。
     */
    private String area;

    /**
     * 详细地址。
     */
    private String detailedAddress;

    /**
     * 邮政编码。
     */
    private String postalCode;

    /**
     * 是否为用户的默认地址，默认值为0（非默认）。
     */
    private Integer isDefault;

    /**
     * 记录创建时间
     */
    private Date createdTime;

    /**
     * 记录更新时间。
     */
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}