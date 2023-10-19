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
 *
 * @TableName visit_appointment
 */
@TableName(value ="visit_appointment")
@Data
public class VisitAppointment implements Serializable {
    /**
     * 预约编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 出诊编号
     */
    private Integer planId;

    /**
     * 患者手机号
     */
    private String patientPhone;

    /**
     * 陪诊人手机号
     */
    private String accompanyPhone;

    /**
     * 出诊时间
     */
    private Integer timePeriod;

    /**
     * 预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}