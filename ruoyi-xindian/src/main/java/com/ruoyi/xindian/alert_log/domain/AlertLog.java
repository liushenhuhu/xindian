package com.ruoyi.xindian.alert_log.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

}
