package com.ruoyi.xindian.report.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报告对象 report
 *
 * @author hanhan
 * @date 2022-10-05
 */
public class Report extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报告id */
    private Long reportId;

    /** 动态/静态心电id */
    @Excel(name = "动态/静态心电id")
    private String pId;

    /** 诊断状态 */
    @Excel(name = "诊断状态")
    private String diagnosisStatus;

    /** 诊断结论 */
    @Excel(name = "诊断结论")
    private String diagnosisConclusion;

    /** 诊断医生 */
    @Excel(name = "诊断医生")
    private String diagnosisDoctor;

    /** 报告时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报告时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportTime;

    /** 报告种类 */
    @Excel(name = "报告种类")
    private String reportType;

    public void setReportId(Long reportId)
    {
        this.reportId = reportId;
    }

    public Long getReportId()
    {
        return reportId;
    }
    public void setpId(String pId)
    {
        this.pId = pId;
    }

    public String getpId()
    {
        return pId;
    }
    public void setDiagnosisStatus(String diagnosisStatus)
    {
        this.diagnosisStatus = diagnosisStatus;
    }

    public String getDiagnosisStatus()
    {
        return diagnosisStatus;
    }
    public void setDiagnosisConclusion(String diagnosisConclusion)
    {
        this.diagnosisConclusion = diagnosisConclusion;
    }

    public String getDiagnosisConclusion()
    {
        return diagnosisConclusion;
    }
    public void setDiagnosisDoctor(String diagnosisDoctor)
    {
        this.diagnosisDoctor = diagnosisDoctor;
    }

    public String getDiagnosisDoctor()
    {
        return diagnosisDoctor;
    }
    public void setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
    }

    public Date getReportTime()
    {
        return reportTime;
    }
    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    public String getReportType()
    {
        return reportType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("reportId", getReportId())
                .append("pId", getpId())
                .append("diagnosisStatus", getDiagnosisStatus())
                .append("diagnosisConclusion", getDiagnosisConclusion())
                .append("diagnosisDoctor", getDiagnosisDoctor())
                .append("reportTime", getReportTime())
                .append("reportType", getReportType())
                .toString();
    }
}
