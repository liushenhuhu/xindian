package com.ruoyi.xindian.label.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 
 * @TableName alert_log_audit
 */
@TableName(value ="alert_log_audit")
public class AlertLogAudit implements Serializable {
    /**
     * 日志id
     */
    @TableId
    @Excel(name = "日志id")
    private String logId;

    /** 噪声 */
    @Excel(name = "噪声")
    private Integer logNoise;

    /** 噪声等级 */
    @Excel(name = "噪声等级")
    private String logNoiseLevel;

    /** 患者管理id */
    @Excel(name = "患者管理id")
    private String pId;

    /** 账号id */
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

    /**
     * 是否标注
     */
    private Integer anoStatus;

    /**
     * 数据标签
     */
    private String dataLabel;

    /**
     * 点标签
     */
    private String pointLabel;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getLogNoise() {
        return logNoise;
    }

    public void setLogNoise(Integer logNoise) {
        this.logNoise = logNoise;
    }

    public String getLogNoiseLevel() {
        return logNoiseLevel;
    }

    public void setLogNoiseLevel(String logNoiseLevel) {
        this.logNoiseLevel = logNoiseLevel;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public Integer getAnoStatus() {
        return anoStatus;
    }

    public void setAnoStatus(Integer anoStatus) {
        this.anoStatus = anoStatus;
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public String getPointLabel() {
        return pointLabel;
    }

    public void setPointLabel(String pointLabel) {
        this.pointLabel = pointLabel;
    }
}