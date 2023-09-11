package com.ruoyi.xindian.patientCount.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.xindian.fw_log.domain.FwLog;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
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

    private String userNameAes;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectionTime;

    /** 检测次数 */
    @Excel(name = "剩余服务次数")
    private Long detectionNum;

    @TableField(exist = false)
    private String begindetectionTime;

    @TableField(exist = false)
    private String enddetectionTime;

    @TableField(exist = false)
    @Excel(name = "总服务次数")
    private Long totalNum;

    @TableField(exist = false)
    @Excel(name = "使用次数")
    private Long usesNum;

    @TableField(exist = false)
    @Excel(name = "购买次数")
    private Long payNum;

    @TableField(exist = false)
    private List<FwLog> fwLogs = new ArrayList<>();

    @TableField(exist = false)
    private VipPatient vipPatient;
}