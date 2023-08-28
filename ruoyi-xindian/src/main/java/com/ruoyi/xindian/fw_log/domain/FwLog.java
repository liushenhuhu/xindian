package com.ruoyi.xindian.fw_log.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 
 * @TableName fw_log
 */
@TableName(value ="fw_log")
@Data
public class FwLog implements Serializable {
    /**
     * 服务日志记录
     */
    @TableId(type = IdType.AUTO)
    private Integer fwLogId;

    /**
     * 操作人名称
     */
    @Excel(name = "用户电话")
    private String userName;

    /**
     * 操作ip地址
     */
    @Excel(name = "IP")
    private String ipaddr;

    /**
     * 操作地址
     */
    @Excel(name = "操作地址")
    private String loginLocation;

    /**
     * 操作内容
     */
    @Excel(name = "操作内容")
    private String msg;

    /**
     * 操作状态
     */
    @Excel(name = "操作状态",readConverterExp = "0=失败,1=成功")
    private String status;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date logTime;

    /**
     * 服务状态（1：增加服务次数，2：减少服务次数）
     */
    @Excel(name = "服务状态",readConverterExp = "1=增加服务次数,2=减少服务次数")
    private String fwStatus;

    /**
     * 服务次数（配合服务状态次数）
     */
    @Excel(name = "服务次数")
    private Integer fwNum;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String startTime;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}