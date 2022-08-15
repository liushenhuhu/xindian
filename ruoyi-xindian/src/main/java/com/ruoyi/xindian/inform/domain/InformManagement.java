package com.ruoyi.xindian.inform.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报告管理对象 inform_management
 * 
 * @author hanhan
 * @date 2022-08-14
 */
public class InformManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报告号 */
    private String informId;

    /** 患者ID */
    @Excel(name = "患者ID")
    private String patientId;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String hospitalName;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 报告日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报告日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date informTime;

    /** 开始遥测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始遥测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTelemetryTime;

    /** 报告状态 */
    @Excel(name = "报告状态")
    private String informStatus;

    public void setInformId(String informId) 
    {
        this.informId = informId;
    }

    public String getInformId() 
    {
        return informId;
    }
    public void setPatientId(String patientId) 
    {
        this.patientId = patientId;
    }

    public String getPatientId() 
    {
        return patientId;
    }
    public void setHospitalName(String hospitalName) 
    {
        this.hospitalName = hospitalName;
    }

    public String getHospitalName() 
    {
        return hospitalName;
    }
    public void setHospitalCode(String hospitalCode) 
    {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalCode() 
    {
        return hospitalCode;
    }
    public void setInformTime(Date informTime) 
    {
        this.informTime = informTime;
    }

    public Date getInformTime() 
    {
        return informTime;
    }
    public void setStartTelemetryTime(Date startTelemetryTime) 
    {
        this.startTelemetryTime = startTelemetryTime;
    }

    public Date getStartTelemetryTime() 
    {
        return startTelemetryTime;
    }
    public void setInformStatus(String informStatus) 
    {
        this.informStatus = informStatus;
    }

    public String getInformStatus() 
    {
        return informStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("informId", getInformId())
            .append("patientId", getPatientId())
            .append("hospitalName", getHospitalName())
            .append("hospitalCode", getHospitalCode())
            .append("informTime", getInformTime())
            .append("startTelemetryTime", getStartTelemetryTime())
            .append("informStatus", getInformStatus())
            .toString();
    }
}
