package com.ruoyi.xindian.verify.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sx_report
 */
@TableName(value ="sx_report")
@Data
public class SxReportVO implements Serializable {
    /**
     * 报告编号
     */
    private String orderId;

    /**
     * 设备号
     */
    private String sn;

    /**
     * 患者的id,唯一标识
     */
    private String userId;


    /**
     * 报告开始时间
     */
    private String startTime;

    /**
     * 患者手机号
     */
    private String patientPhone;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}