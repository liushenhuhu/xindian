package com.ruoyi.xindian.hospital.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 门诊预约医院
 * @TableName hospital_subscribe
 */
@TableName(value ="hospital_subscribe")
@Data
public class HospitalSubscribe implements Serializable {
    /**
     * 
     */
    private Integer subscribeId;

    /**
     * 医院id
     */
    private Long hospitalId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private Hospital hospital;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}