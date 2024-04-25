package com.ruoyi.xindian.alert_log.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 预警日志对象 alert_log
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志id */
    private String logId;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date logTime;

    /** 预警类型 */
    @Excel(name = "预警类型")
    private String logType;

    /** 事件名称 */
    @Excel(name = "事件名称")
    private String eventName;

    /** 事件说明 */
    @Excel(name = "事件说明")
    private String eventDescription;

    /** 患者id */
    @Excel(name = "患者id")
    private String pId;

    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String patientName;

    /** 患者身份证号 */
    @Excel(name = "患者身份证号")
    private String patientCode;

    /**
     * 患者电话
     */
    @Excel(name = "患者电话")
    private String patientPhone;

    /**
     * 家属电话
     */
    @Excel(name = "家属电话")
    private String familyPhone;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String hospitalName;

    /** 是否标注 */
    @Excel(name = "是否标注")
    private Integer anoStatus;

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

    /**
     * 心电种类（静态动态）
     */
    @Excel(name = "心电种类", readConverterExp = "静态动态")
    private String ecgType;

    private Integer userId;

    /** 医生电话 */
    @Excel(name = "医生电话")
    private String doctorPhone;


    private String myocardiumType;

    private Integer value;

    private String name;

    private String redisKey;


    private String type;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

}
