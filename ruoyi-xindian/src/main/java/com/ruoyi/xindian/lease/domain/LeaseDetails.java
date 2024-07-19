package com.ruoyi.xindian.lease.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName lease_details
 */
@TableName(value ="lease_details")
@Data
public class LeaseDetails implements Serializable {
    /**
     * 租赁详情id
     */
    @TableId(type = IdType.AUTO)
    private Integer leaseDetailsId;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}