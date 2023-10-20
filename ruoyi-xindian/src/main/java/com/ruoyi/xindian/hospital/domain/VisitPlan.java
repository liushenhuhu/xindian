package com.ruoyi.xindian.hospital.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 *
 * @TableName visit_plan
 */
@TableName(value ="visit_plan")
public class VisitPlan implements Serializable {
    /**
     * 出诊编号
     */
    @TableId(type = IdType.AUTO)
    private Integer planId;

    /**
     * 医院编号
     */
    private Long hospitalId;

    /**
     * 医院code
     */
    @TableField(exist = false)
    private String hospitalCode;

    /**
     * 专科编号
     */
    private Long specialId;

    /**
     * 门诊编号
     */
    private Long outpatientId;

    /**
     * 诊室编号
     */
    private Long clinicId;

    /**
     * 医生手机号
     */
    private String doctorPhone;

    /**
     * 时间段 1：上午，2：下午
     */
    private Integer time;

    /**
     * 出诊日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date day;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;


    @TableField(exist = false)
    private Doctor doctor;

    @TableField(exist = false)
    private HashMap<Object ,Object> visitTimeMap = new HashMap<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public Long getOutpatientId() {
        return outpatientId;
    }

    public void setOutpatientId(Long outpatientId) {
        this.outpatientId = outpatientId;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public HashMap<Object, Object> getVisitTimeMap() {
        return visitTimeMap;
    }

    public void setVisitTimeMap(HashMap<Object, Object> visitTimeMap) {
        this.visitTimeMap = visitTimeMap;
    }
}