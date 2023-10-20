package com.ruoyi.xindian.hospital.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 诊室信息 对象 hospital_clinic
 *
 * @author ruoyi
 * @date 2023-10-19
 */
public class HospitalClinic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 诊室编号 */
    private Long id;

    /** 所属门诊 */
    @Excel(name = "所属门诊")
    private Long outpatientId;

    /** 诊室地址 */
    @Excel(name = "诊室地址")
    private String address;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtCreate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtModified;


    private HospitalOutpatient hospitalOutpatient;

    public HospitalOutpatient getHospitalOutpatient() {
        return hospitalOutpatient;
    }

    public void setHospitalOutpatient(HospitalOutpatient hospitalOutpatient) {
        this.hospitalOutpatient = hospitalOutpatient;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOutpatientId(Long outpatientId)
    {
        this.outpatientId = outpatientId;
    }

    public Long getOutpatientId()
    {
        return outpatientId;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setGmtCreate(Date gmtCreate)
    {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate()
    {
        return gmtCreate;
    }
    public void setGmtModified(Date gmtModified)
    {
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified()
    {
        return gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("outpatientId", getOutpatientId())
                .append("address", getAddress())
                .append("gmtCreate", getGmtCreate())
                .append("gmtModified", getGmtModified())
                .toString();
    }
}
