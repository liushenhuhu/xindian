package com.ruoyi.xindian.order.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName user_address
 */
@TableName(value ="user_address")
@Data
public class UserAddressVo implements Serializable {
    /**
     * 地址ID
     */

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


    private String id;

    private String orderStatus;


    /**
     * 判断需不需要进行地址修改
     */
    private String isUpdate;
}