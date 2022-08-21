package com.ruoyi.xindian.inform.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报告对象 inform
 *
 * @author hanhan
 * @date 2022-08-20
 */
public class Inform extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报告id */
    private Long informId;

    /** 报告号 */
    @Excel(name = "报告号")
    private String informNumber;

    /** 患者管理id */
    @Excel(name = "患者管理id")
    private Long pId;

    /** 开始遥测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始遥测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTelemetryTime;

    /** 报告日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报告日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date informTime;

    /** 报告状态 */
    @Excel(name = "报告状态")
    private String informStatus;

    /**
     * 患者姓名
     */
    @Excel(name = "患者姓名")
    private String patientName;

    /**
     * 患者身份证号
     */
    @Excel(name = "患者身份证号")
    private String patientNumber;

    /**
     * 患者年龄
     */
    @Excel(name = "患者年龄 ")
    private String patientAge;

    /**
     * 患者性别
     */
    @Excel(name = "患者性别")
    private String patientSex;

    /**
     * 患者电话
     */
    @Excel(name = "患者电话")
    private String patientPhone;

    /**
     * 床位号
     */
    @Excel(name = "床位号")
    private String bedNumber;

    /**
     * 病历号
     */
    @Excel(name = "病历号")
    private String caseHistoryNumber;

    /**
     * 患者来源
     */
    @Excel(name = "患者来源")
    private String patientSource;

    /**
     * 医院代号
     */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /**
     * 医院名称
     */
    @Excel(name = "医院名称")
    private String hospitalName;

    public void setInformId(Long informId)
    {
        this.informId = informId;
    }

    public Long getInformId()
    {
        return informId;
    }
    public void setInformNumber(String informNumber)
    {
        this.informNumber = informNumber;
    }

    public String getInformNumber()
    {
        return informNumber;
    }
    public void setpId(Long pId)
    {
        this.pId = pId;
    }

    public Long getpId()
    {
        return pId;
    }
    public void setStartTelemetryTime(Date startTelemetryTime)
    {
        this.startTelemetryTime = startTelemetryTime;
    }

    public Date getStartTelemetryTime()
    {
        return startTelemetryTime;
    }
    public void setInformTime(Date informTime)
    {
        this.informTime = informTime;
    }

    public Date getInformTime()
    {
        return informTime;
    }
    public void setInformStatus(String informStatus)
    {
        this.informStatus = informStatus;
    }

    public String getInformStatus()
    {
        return informStatus;
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

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getCaseHistoryNumber() {
        return caseHistoryNumber;
    }

    public void setCaseHistoryNumber(String caseHistoryNumber) {
        this.caseHistoryNumber = caseHistoryNumber;
    }

    public String getPatientSource() {
        return patientSource;
    }

    public void setPatientSource(String patientSource) {
        this.patientSource = patientSource;
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
                .append("informId", getInformId())
                .append("informNumber", getInformNumber())
                .append("pId", getpId())
                .append("startTelemetryTime", getStartTelemetryTime())
                .append("informTime", getInformTime())
                .append("informStatus", getInformStatus())
                .append("patientNumber", getPatientNumber())
                .append("patientAge", getPatientAge())
                .append("patientSex", getPatientSex())
                .append("patientSource", getPatientSource())
                .append("patientPhone", getPatientPhone())
                .append("bedNumber", getBedNumber())
                .append("caseHistoryNumber", getCaseHistoryNumber())
                .append("hospitalCode", getHospitalCode())
                .append("hospitalName", getHospitalName())
                .toString();
    }
}
