package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户病历对象 user_case
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class UserCase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录编号 */
    private Long id;

    /** 就诊卡编号 */
    @Excel(name = "就诊卡编号")
    private Long cardId;

    /** 预约编号 */
    @Excel(name = "预约编号")
    private Long appointmentId;

    /** 医生编号 */
    @Excel(name = "医生编号")
    private Long doctorId;

    /** 病例详情 */
    @Excel(name = "病例详情")
    private String content;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCardId(Long cardId) 
    {
        this.cardId = cardId;
    }

    public Long getCardId() 
    {
        return cardId;
    }
    public void setAppointmentId(Long appointmentId) 
    {
        this.appointmentId = appointmentId;
    }

    public Long getAppointmentId() 
    {
        return appointmentId;
    }
    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cardId", getCardId())
            .append("appointmentId", getAppointmentId())
            .append("doctorId", getDoctorId())
            .append("content", getContent())
            .toString();
    }
}
