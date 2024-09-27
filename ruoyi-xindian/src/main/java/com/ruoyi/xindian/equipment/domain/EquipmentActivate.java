package com.ruoyi.xindian.equipment.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 设备归属激活
 * @TableName equipment_activate
 */
@TableName(value ="equipment_activate")
@Data
public class EquipmentActivate implements Serializable {
    /**
     * 设备id
     */
    @TableId(type = IdType.AUTO)
    private Integer equipmentId;

    /**
     * 设备编号
     */
    private String equipmentCode;

    /**
     * 设备激活状态
     */
    private String status;

    /**
     * 设备归属
     */
    private String affiliation;

    /**
     * 绑定手机号
     */
    private String patientPhone;

    /**
     * 在线状态
     */
    private String onlineStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 激活手机号
     */
    private String activatePhone;

    /**
     * 首次激活时间
     */
    private Date firstTime;

    /**
     * mac地址
     * */
    private String mac;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}