package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医生对象 doctor
 * 
 * @author hanhan
 * @date 2022-12-03
 */
public class Doctor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long doctorId;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String doctorName;

    /** 医生电话 */
    @Excel(name = "医生电话")
    private String doctorPhone;

    /** 科室代号 */
    @Excel(name = "科室代号")
    private String departmentCode;

    /** 擅长 */
    @Excel(name = "擅长")
    private String skill;

    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

    /** 服务内容 */
    @Excel(name = "服务内容")
    private String serviceContent;

    /** 适用人群 */
    @Excel(name = "适用人群")
    private String applicablePopulation;

    /** 收费价格 */
    @Excel(name = "收费价格")
    private String chargePrice;

    /** 在线状态 */
    @Excel(name = "在线状态")
    private String onlineState;

    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }
    public void setDoctorName(String doctorName) 
    {
        this.doctorName = doctorName;
    }

    public String getDoctorName() 
    {
        return doctorName;
    }
    public void setDoctorPhone(String doctorPhone) 
    {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorPhone() 
    {
        return doctorPhone;
    }
    public void setDepartmentCode(String departmentCode) 
    {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentCode() 
    {
        return departmentCode;
    }

    public void setSkill(String skill)
    {
        this.skill = skill;
    }

    public String getSkill()
    {
        return skill;
    }
    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public String getIntroduction()
    {
        return introduction;
    }
    public void setServiceContent(String serviceContent)
    {
        this.serviceContent = serviceContent;
    }

    public String getServiceContent()
    {
        return serviceContent;
    }
    public void setApplicablePopulation(String applicablePopulation)
    {
        this.applicablePopulation = applicablePopulation;
    }

    public String getApplicablePopulation()
    {
        return applicablePopulation;
    }
    public void setChargePrice(String chargePrice)
    {
        this.chargePrice = chargePrice;
    }

    public String getChargePrice()
    {
        return chargePrice;
    }
    public void setOnlineState(String onlineState)
    {
        this.onlineState = onlineState;
    }

    public String getOnlineState()
    {
        return onlineState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("doctorId", getDoctorId())
                .append("doctorName", getDoctorName())
                .append("doctorPhone", getDoctorPhone())
                .append("departmentCode", getDepartmentCode())
                .append("skill", getSkill())
                .append("introduction", getIntroduction())
                .append("serviceContent", getServiceContent())
                .append("applicablePopulation", getApplicablePopulation())
                .append("chargePrice", getChargePrice())
                .append("onlineState", getOnlineState())
                .toString();
    }
}
