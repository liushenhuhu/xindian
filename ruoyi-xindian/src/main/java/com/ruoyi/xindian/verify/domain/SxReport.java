package com.ruoyi.xindian.verify.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sx_report
 */
@TableName(value ="sx_report")
@Data
public class SxReport implements Serializable {
    /**
     * 善行生成报告id
     */
    @TableId(value = "sx_report_id", type = IdType.AUTO)
    private Integer sxReportId;

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
     * 上传开始时间
     */
    private String uploadStart;

    /**
     * 上传结束时间
     */
    private String uploadEnd;

    /**
     * 患者手机号
     */
    private String patientPhone;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}