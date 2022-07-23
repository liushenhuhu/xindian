package com.ruoyi.xindian.alert_log.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 预警日志对象 alert_log
 * 
 * @author ruoyi
 * @date 2022-07-23
 */
public class AlertLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 预警类型 */
    @Excel(name = "预警类型")
    private String type;

    /** 事件名称 */
    @Excel(name = "事件名称")
    private String eventName;

    /** 事件说明 */
    @Excel(name = "事件说明")
    private String eventDescription;

    /** 患者信息 */
    @Excel(name = "患者信息")
    private String patientInfo;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    /** 家属电话 */
    @Excel(name = "家属电话")
    private String familyPhone;

    /** 所属医院 */
    @Excel(name = "所属医院")
    private String belongHospital;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setEventName(String eventName) 
    {
        this.eventName = eventName;
    }

    public String getEventName() 
    {
        return eventName;
    }
    public void setEventDescription(String eventDescription) 
    {
        this.eventDescription = eventDescription;
    }

    public String getEventDescription() 
    {
        return eventDescription;
    }
    public void setPatientInfo(String patientInfo) 
    {
        this.patientInfo = patientInfo;
    }

    public String getPatientInfo() 
    {
        return patientInfo;
    }
    public void setPatientPhone(String patientPhone) 
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone() 
    {
        return patientPhone;
    }
    public void setFamilyPhone(String familyPhone) 
    {
        this.familyPhone = familyPhone;
    }

    public String getFamilyPhone() 
    {
        return familyPhone;
    }
    public void setBelongHospital(String belongHospital) 
    {
        this.belongHospital = belongHospital;
    }

    public String getBelongHospital() 
    {
        return belongHospital;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("time", getTime())
            .append("type", getType())
            .append("eventName", getEventName())
            .append("eventDescription", getEventDescription())
            .append("patientInfo", getPatientInfo())
            .append("patientPhone", getPatientPhone())
            .append("familyPhone", getFamilyPhone())
            .append("belongHospital", getBelongHospital())
            .toString();
    }
}
