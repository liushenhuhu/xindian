package com.ruoyi.xindian.patient_management.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String pId;

    /**
     * 患者身份证号
     */
    @Excel(name = "患者身份证号")
    private String patientCode;

    /**
     * 医院代号
     */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /**
     * 设备号
     */
    @Excel(name = "设备号")
    private String equipmentCode;

    /**
     * 连接时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "连接时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date connectionTime;

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
     * 患者姓名
     */
    @Excel(name = "患者姓名")
    private String patientName;

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
     * 医院名称
     */
    @Excel(name = "医院名称")
    private String hospitalName;

    /** 心电种类（静态动态） */
    @Excel(name = "心电种类", readConverterExp = "静=态动态")
    private String ecgType;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public Date getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(Date connectionTime) {
        this.connectionTime = connectionTime;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getEcgType() {
        return ecgType;
    }

    public void setEcgType(String ecgType) {
        this.ecgType = ecgType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("pId", getpId())
                .append("patientCode", getPatientCode())
                .append("hospitalCode", getHospitalCode())
                .append("equipmentCode", getEquipmentCode())
                .append("connectionTime", getConnectionTime())
                .append("monitoringStatus", getMonitoringStatus())
                .append("bedNumber", getBedNumber())
                .append("caseHistoryNumber", getCaseHistoryNumber())
                .append("patientName",getPatientName())
                .append("hospitalName",getHospitalName())
                .append("ecgType", getEcgType())
                .toString();
    }
}
