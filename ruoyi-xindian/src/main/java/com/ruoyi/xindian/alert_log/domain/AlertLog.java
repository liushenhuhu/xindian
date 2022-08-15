package com.ruoyi.xindian.alert_log.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 预警日志对象 alert_log
 *
 * @author hanhan
 * @date 2022-08-15
 */
public class AlertLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志id */
    private String logId;

    /** 日志号 */
    @Excel(name = "日志号")
    private String logNumber;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date logTime;

    /** 预警类型 */
    @Excel(name = "预警类型")
    private String logType;

    /** 事件名称 */
    @Excel(name = "事件名称")
    private String eventName;

    /** 事件说明 */
    @Excel(name = "事件说明")
    private String eventDescription;

    /** 患者身份证号 */
    @Excel(name = "患者身份证号")
    private String patientNumber;

    /**
     * 患者姓名
     */
    @Excel(name = "患者姓名")
    private String patientName;


    /**
     * 患者电话
     */
    @Excel(name = "患者电话")
    private String patientPhone;

    /**
     * 家属电话
     */
    @Excel(name = "家属电话")
    private String familyPhone;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String hospitalName;



    public void setLogId(String logId)
    {
        this.logId = logId;
    }

    public String getLogId()
    {
        return logId;
    }
    public void setLogNumber(String logNumber)
    {
        this.logNumber = logNumber;
    }

    public String getLogNumber()
    {
        return logNumber;
    }
    public void setLogTime(Date logTime)
    {
        this.logTime = logTime;
    }

    public Date getLogTime()
    {
        return logTime;
    }
    public void setLogType(String logType)
    {
        this.logType = logType;
    }

    public String getLogType()
    {
        return logType;
    }
    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public String getEventName()
    {
        return eventName;
    }
    public void setEventDescription(String eventDescription)
    {
        this.eventDescription = eventDescription;
    }

    public String getEventDescription()
    {
        return eventDescription;
    }
    public void setPatientNumber(String patientNumber)
    {
        this.patientNumber = patientNumber;
    }

    public String getPatientNumber()
    {
        return patientNumber;
    }
    public void setHospitalCode(String hospitalCode)
    {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalCode()
    {
        return hospitalCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("logId", getLogId())
                .append("logNumber", getLogNumber())
                .append("logTime", getLogTime())
                .append("logType", getLogType())
                .append("eventName", getEventName())
                .append("eventDescription", getEventDescription())
                .append("patientNumber", getPatientNumber())
                .append("patientName", getPatientName())
                .append("patientPhone", getPatientPhone())
                .append("familyPhone", getFamilyPhone())
                .append("hospitalCode", getHospitalCode())
                .append("hospitalName", getHospitalName())
                .toString();
    }
}
