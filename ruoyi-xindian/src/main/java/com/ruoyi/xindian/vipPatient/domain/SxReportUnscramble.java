package com.ruoyi.xindian.vipPatient.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 动态心电图解读服务次数
 * @TableName sx_report_unscramble
 */
@TableName(value ="sx_report_unscramble")
@Data
public class SxReportUnscramble implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer sxReportId;

    /**
     * 患者电话
     */
    private String patientPhone;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 检测次数
     */
    private Long vipNum;

    /**
     * 创建日期
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}