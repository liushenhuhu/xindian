package com.ruoyi.xindian.weekReport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * weekReport对象 week_report
 *
 * @author chenpeng
 * @date 2024-07-19
 */
@Data
public class WeekReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 报告id
     */
    @Excel(name = "报告id")
    private String weekid;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private String patientPhone;

    /**
     * 医生电话
     */
    @Excel(name = "医生电话")
    private String doctorPhone;

    /**
     * 诊断状态0:未诊断，1：已诊断，2诊断中
     */
    @Excel(name = "诊断状态0:未诊断，1：已诊断，2：诊断中，3：已诊断")
    private Integer diagnosisStatus;

    /**
     * 诊断结论
     */
    @Excel(name = "诊断结论")
    private String diagnosisConclusion;

    @Excel(name = "报告时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date weekpdftime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    //是否存在数据
    private boolean hData;

    private Date updateTime;
}
