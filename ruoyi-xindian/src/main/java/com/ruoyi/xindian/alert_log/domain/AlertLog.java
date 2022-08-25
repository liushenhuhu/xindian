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
 * @date 2022-08-20
 */
public class AlertLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志id */
    private Long logId;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
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

    /** 患者id */
    @Excel(name = "患者id")
    private String pId;

    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String patientName;

    /** 患者身份证号 */
    @Excel(name = "患者身份证号")
    private String patientNumber;

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

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public Long getLogId()
    {
        return logId;
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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
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

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
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
                .append("logTime", getLogTime())
                .append("logType", getLogType())
                .append("eventName", getEventName())
                .append("eventDescription", getEventDescription())
                .append("pId", getpId())
                .append("patientName", getPatientName())
                .append("patientNumber", getPatientNumber())
                .append("patientPhone", getPatientPhone())
                .append("familyPhone", getFamilyPhone())
                .append("hospitalCode", getHospitalCode())
                .append("hospitalName", getHospitalName())
                .toString();
    }
}
