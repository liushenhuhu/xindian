package com.ruoyi.xindian.patientCount.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 用户信息表
 * @TableName sys_user
 */

@Data
@TableName("sys_user")
public class PatientCount implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(value="user_id",type = IdType.AUTO)
    private Long userId;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String phonenumberAes;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectionTime;

    /** 检测次数 */
    @Excel(name = "剩余服务次数")
    private Long detectionNum;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date begindetectionTime;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date enddetectionTime;

    @TableField(exist = false)
    @Excel(name = "总服务次数")
    private Long totalNum;

    @TableField(exist = false)
    @Excel(name = "使用次数")
    private Long usesNum;
}