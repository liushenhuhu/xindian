package com.ruoyi.xindian.weekDetectionTime.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * weekDetectionTime对象 week_detection_time
 * 
 * @author chenpeng
 * @date 2024-07-22
 */
public class WeekDetectionTime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    /** 生成周报最新数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生成周报最新数据时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectionTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("patientPhone", getPatientPhone())
            .append("detectionTime", getDetectionTime())
            .toString();
    }
}
