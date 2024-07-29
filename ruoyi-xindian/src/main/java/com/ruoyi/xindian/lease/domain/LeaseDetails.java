package com.ruoyi.xindian.lease.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 租赁详情
 * @TableName lease_details
 */
@TableName(value ="lease_details")
@Data
public class LeaseDetails implements Serializable {
    /**
     * 租赁详情id
     */
    @TableId(type = IdType.AUTO)
    private Long leaseDetailsId;

    /**
     * 租赁id
     */
    private Long leaseId;

    /**
     * 设备号
     */
    private String equipmentCode;

    /**
     * 设备类型
     */
    private String equipmentType;

    /**
     * 设备的状态（1=使用中，0=未使用）
     */
    private String status;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 保证金
     */
    private String cashPledge;

    /**
     * 租赁时间
     */
    private Date createTime;

    /**
     * 归还时间
     */
    private Date updateTime;
    @TableField(exist = false)
    private String userPhone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}