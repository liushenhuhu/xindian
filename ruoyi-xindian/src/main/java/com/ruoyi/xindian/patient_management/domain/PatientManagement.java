package com.ruoyi.xindian.patient_management.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.patient.domain.Patient;
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
     * 患者
     */
    private Patient patient;

    /**
     * 患者身份证号
     */
    @Excel(name = "患者身份证号")
    private String patientCode;

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
     * 医院
     */
    private Hospital hospital;

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

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public void setConnectionTime(Date connectionTime) {
        this.connectionTime = connectionTime;
    }

    public Date getConnectionTime() {
        return connectionTime;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
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
                .append("patient",getPatient())
                .append("hospital",getHospital())
                .toString();
    }
}
