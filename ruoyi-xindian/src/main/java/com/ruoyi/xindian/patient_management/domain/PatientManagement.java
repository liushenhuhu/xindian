package com.ruoyi.xindian.patient_management.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 患者管理对象 patient_management
 *
 * @author hanhan
 * @date 2022-08-20
 */
public class PatientManagement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 患者管理id
     */
    private Long pId;

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
     * 患者来源
     */
    @Excel(name = "患者来源")
    private String patientSource;

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


    /**
     * 监测状态
     */
    @Excel(name = "监测状态")
    private String monitoringStatus;

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
     * 医院代号
     */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /**
     * 医院名称
     */
    @Excel(name = "医院名称")
    private String hospitalName;

    /**
     * 设备号
     */
    @Excel(name = "设备号")
    private String equipmentNumber;

    /**
     * 连接时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "连接时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date connectionTime;

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getpId() {
        return pId;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setConnectionTime(Date connectionTime) {
        this.connectionTime = connectionTime;
    }

    public Date getConnectionTime() {
        return connectionTime;
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

    public String getPatientSource() {
        return patientSource;
    }

    public void setPatientSource(String patientSource) {
        this.patientSource = patientSource;
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

    public String getMonitoringStatus() {
        return monitoringStatus;
    }

    public void setMonitoringStatus(String monitoringStatus) {
        this.monitoringStatus = monitoringStatus;
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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("pId", getpId())
                .append("patientNumber", getPatientNumber())
                .append("hospitalCode", getHospitalCode())
                .append("equipmentNumber", getEquipmentNumber())
                .append("connectionTime", getConnectionTime())
                .append("patientNumber", getPatientNumber())
                .append("patientAge", getPatientAge())
                .append("patientSex", getPatientSex())
                .append("patientSource", getPatientSource())
                .append("patientPhone", getPatientPhone())
                .append("familyPhone", getFamilyPhone())
                .append("monitoringStatus", getMonitoringStatus())
                .append("bedNumber", getBedNumber())
                .append("caseHistoryNumber", getCaseHistoryNumber())
                .append("hospitalName", getHospitalName())
                .append("patientName", getPatientName())
                .toString();
    }
}
