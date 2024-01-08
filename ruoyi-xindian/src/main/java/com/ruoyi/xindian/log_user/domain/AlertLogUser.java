package com.ruoyi.xindian.log_user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 标注分配对象 alert_log_user
 * 
 * @author hanhan
 * @date 2022-12-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertLogUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志id */
    @Excel(name = "日志id")
    private String logId;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date logTime;

    /** 噪声 */
    @Excel(name = "噪声")
    private Long logNoise;

    /** 噪声等级 */
    @Excel(name = "噪声等级")
    private String logNoiseLevel;

    /** 账号id */
    @Excel(name = "账号id")
    private Long userId;

    /** 预警类型 */
    @Excel(name = "预警类型")
    private String logType;

    /** 事件名称 */
    @Excel(name = "事件名称")
    private String eventName;

    /** 事件说明 */
    @Excel(name = "事件说明")
    private String eventDescription;

    /** 是否标注 */
    @Excel(name = "是否标注")
    private Long anoStatus;

    /** 患者id */
//    @Excel(name = "患者id")
    private String pId;

    /**
     * 患者年龄
     */
//    @Excel(name = "患者年龄 ")
    private String patientAge;

    /**
     * 患者性别
     */
//    @Excel(name = "患者性别")
    private String patientSex;

    private Integer isSuspected;

    public Integer getIsSuspected() {
        return isSuspected;
    }

    public void setIsSuspected(Integer isSuspected) {
        this.isSuspected = isSuspected;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Long getLogNoise() {
        return logNoise;
    }

    public void setLogNoise(Long logNoise) {
        this.logNoise = logNoise;
    }

    public String getLogNoiseLevel() {
        return logNoiseLevel;
    }

    public void setLogNoiseLevel(String logNoiseLevel) {
        this.logNoiseLevel = logNoiseLevel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Long getAnoStatus() {
        return anoStatus;
    }

    public void setAnoStatus(Long anoStatus) {
        this.anoStatus = anoStatus;
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
}
