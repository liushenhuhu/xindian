package com.ruoyi.xindian.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName shipAddress
 */
@TableName(value ="shipAddress")
@Data
public class ShipaddressVo implements Serializable {
    /**
     * 地址id
     */
    @TableId(type = IdType.AUTO)
    private Integer addressId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户电话
     */
    private String patientPhone;

    /**
     * 用户姓名
     */
    private String patientName;

    /**
     * 街道地址
     */
    private String streetAddress;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String state;

    /**
     * 区
     */
    private String country;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 默认地址标识符
     */
    private Integer defaultFlag;

    /**
     * 删除标志位
     */
    private Integer delFlag;

    private String id;

    private String orderStatus;

    private String street;


    /**
     * 判断需不需要进行地址修改
     */
    private String isUpdate;

    /**
     * 快递公司
     */
    private String courierCompany;

    /**
     * 快递单号
     */
    private String courierNumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}