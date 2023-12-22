package com.ruoyi.xindian.label.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName alert_log_audit
 */
@TableName(value ="alert_log_audit")
@Data
public class AlertLogAudit implements Serializable {
    /**
     * 日志id
     */
    @TableId
    private String logId;

    /**
     * 噪声
     */
    private Integer logNoise;

    /**
     * 噪声等级
     */
    private String logNoiseLevel;

    /**
     * 患者管理id
     */
    private String pId;

    /**
     * 账号id
     */
    private Integer userId;

    /**
     * 预警类型
     */
    private String logType;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 事件说明
     */
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
}