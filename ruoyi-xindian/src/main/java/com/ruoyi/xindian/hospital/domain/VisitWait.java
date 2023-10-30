package com.ruoyi.xindian.hospital.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.xindian.patient.domain.Patient;
import lombok.Data;

/**
 * 门诊候诊
 * @TableName visit_wait
 */
@TableName(value ="visit_wait")
public class VisitWait implements Serializable {
    /**
     * 候诊id
     */
    private Long waitId;

    /**
     * 预约管理id
     */
    private String appointmentId;

    /**
     * 医院id
     */
    private Long hospitalId;

    /**
     * 医生手机号
     */
    private String doctorPhone;

    /**
     * 患者手机号
     */
    private String patientPhone;

    @TableField(exist = false)
    private String patientName;

    /**
     * 排班id
     */
    private Integer slotId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private String patientSex;

    @TableField(exist = false)
    private Integer patientAge;

    @TableField(exist = false)
    private String hospitalClinicName;


    @TableField(exist = false)
    private String hospitalName;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthBay;

    @TableField(exist = false)
    private String outpatientType;

    @TableField(exist = false)
    private Long status;


    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date periodDay;

    @TableField(exist = false)
    private String doctorName;
    @TableField(exist = false)
    private String visitDate;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Date getPeriodDay() {
        return periodDay;
    }

    public void setPeriodDay(Date periodDay) {
        this.periodDay = periodDay;
    }

    public String getOutpatientType() {
        return outpatientType;
    }

    public void setOutpatientType(String outpatientType) {
        this.outpatientType = outpatientType;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getWaitId() {
        return waitId;
    }

    public void setWaitId(Long waitId) {
        this.waitId = waitId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getHospitalClinicName() {
        return hospitalClinicName;
    }

    public void setHospitalClinicName(String hospitalClinicName) {
        this.hospitalClinicName = hospitalClinicName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Date getBirthBay() {
        return birthBay;
    }

    public void setBirthBay(Date birthBay) {
        this.birthBay = birthBay;
    }
}