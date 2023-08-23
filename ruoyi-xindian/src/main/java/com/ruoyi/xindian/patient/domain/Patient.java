package com.ruoyi.xindian.patient.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 患者对象 patient
 *
 * @author hanhan
 * @date 2022-08-20
 */
public class Patient extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 患者id
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    @Excel(name = "患者姓名")
    private String patientName;
    private String patientNameAes;

    /**
     * 患者身份证号
     */
    @Excel(name = "患者身份证号")
    private String patientCode;

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

    public Date getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(Date detectionTime) {
        this.detectionTime = detectionTime;
    }

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
    private String patientPhoneAes;

    /**
     * 家属电话
     */
    @Excel(name = "家属电话")
    private String familyPhone;

    /**
     * 最近连接设备号
     */
    @Excel(name = "最近连接设备号")
    private String equipmentId;

    /**
     * 监测状态
     */
    @Excel(name = "监测状态")
    private String monitoringStatus;

    /** 绑定状态 */
    @Excel(name = "绑定状态")
    private String bindingState;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDay;

    /**
     * 检测次数
     */
    private Long detectionNum;

    private String pId;



    private Date detectionTime;

    private Long hospitalId;

    private List<String> hospitalNameList = new ArrayList<>();

    public String getPatientNameAes() {
        return patientNameAes;
    }

    public void setPatientNameAes(String patientNameAes) {
        this.patientNameAes = patientNameAes;
    }

    public String getPatientPhoneAes() {
        return patientPhoneAes;
    }

    public void setPatientPhoneAes(String patientPhoneAes) {
        this.patientPhoneAes = patientPhoneAes;
    }

    public List<String> getHospitalNameList() {
        return hospitalNameList;
    }

    public void setHospitalNameList(List<String> hospitalNameList) {
        this.hospitalNameList = hospitalNameList;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    private List<AlertLog> alertLogList;

    public List<AlertLog> getAlertLogList() {
        return alertLogList;
    }

    public void setAlertLogList(List<AlertLog> alertLogList) {
        this.alertLogList = alertLogList;
    }

    public void setDetectionNum(Long detectionNum) {
        this.detectionNum = detectionNum;
    }

    public Long getDetectionNum() {
        return detectionNum;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSource(String patientSource) {
        this.patientSource = patientSource;
    }

    public String getPatientSource() {
        return patientSource;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setMonitoringStatus(String monitoringStatus) {
        this.monitoringStatus = monitoringStatus;
    }

    public String getMonitoringStatus() {
        return monitoringStatus;
    }

    public String getBindingState() {
        return bindingState;
    }

    public void setBindingState(String bindingState) {
        this.bindingState = bindingState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("patientId", getPatientId())
                .append("patientName", getPatientName())
                .append("patientCode", getPatientCode())
                .append("patientAge", getPatientAge())
                .append("patientSex", getPatientSex())
                .append("patientSource", getPatientSource())
                .append("patientPhone", getPatientPhone())
                .append("familyPhone", getFamilyPhone())
                .append("equipmentId", getEquipmentId())
                .append("monitoringStatus", getMonitoringStatus())
                .append("bindingState", getBindingState())
                .toString();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
