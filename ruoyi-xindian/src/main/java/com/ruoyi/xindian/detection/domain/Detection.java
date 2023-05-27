package com.ruoyi.xindian.detection.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * detection对象 detection
 * 
 * @author chenpeng
 * @date 2023-05-25
 */
public class Detection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long detectionId;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    /** 检测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "检测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectionTime;

    private String detectionPid;

    public void setDetectionId(Long detectionId) 
    {
        this.detectionId = detectionId;
    }

    public Long getDetectionId() 
    {
        return detectionId;
    }
    public void setPatientPhone(String patientPhone) 
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone() 
    {
        return patientPhone;
    }
    public void setDetectionTime(Date detectionTime) 
    {
        this.detectionTime = detectionTime;
    }

    public Date getDetectionTime() 
    {
        return detectionTime;
    }

    public void setDetectionPid(String detectionPid)
    {
        this.detectionPid = detectionPid;
    }

    public String getDetectionPid()
    {
        return detectionPid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detectionId", getDetectionId())
            .append("patientPhone", getPatientPhone())
            .append("detectionTime", getDetectionTime())
            .append("detectionPid", getDetectionPid())
            .toString();
    }
}
