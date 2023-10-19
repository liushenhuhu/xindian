package com.ruoyi.xindian.hospital.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName visit_allocation
 */
@TableName(value ="visit_allocation")
@Data
public class VisitAllocation implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer slotId;

    /**
     * 分配开始时间
     */
    private String startTime;

    /**
     * 分配结束时间
     */
    private String endTime;

    /**
     * 1：上午，2：下午
     */
    private Integer category;

    @TableField(exist = false)
    private Integer status = 0;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}