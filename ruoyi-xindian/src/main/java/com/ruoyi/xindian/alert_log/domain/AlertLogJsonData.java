package com.ruoyi.xindian.alert_log.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 
 * @TableName alert_log_json_data
 */
@TableName(value ="alert_log_user")
@Data
public class AlertLogJsonData implements Serializable {

    /**
     * 日志id
     */
    private String logId;

//    /**
//     * 日志时间
//     */
//    private Date logTime;
//
//    /** 预警类型 */
//    private String logType;
//
//    /** 事件名称 */
//    private String eventName;
//
//    /** 事件说明 */
//    private String eventDescription;
//
//    /** 患者id */
//    private String pId;
    /**
     * 是否标注
     */
    private Integer anoStatus;

    /**
     * json标注数据
     */
    private String dataLabel;

//    /**
//     * 修改时间
//     */
//    private Date updateTime;

//    @TableField(exist = false)
//    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}