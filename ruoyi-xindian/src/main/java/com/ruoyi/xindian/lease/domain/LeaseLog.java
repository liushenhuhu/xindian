package com.ruoyi.xindian.lease.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName lease_log
 */
@TableName(value ="lease_log")
@Data
public class LeaseLog implements Serializable {
    /**
     * 租赁日志
     */
    @TableId(type = IdType.AUTO)
    private Integer leaseLogId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否归还（1=租赁，0=归还）
     */
    private String status;

    /**
     * 设备号
     */
    private String equipmentCode;

    /**
     * 设备类型
     */
    private String equipmentType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}