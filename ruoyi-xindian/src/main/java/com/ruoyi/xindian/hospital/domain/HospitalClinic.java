package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 诊室信息表对象 hospital_clinic
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class HospitalClinic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 诊室编号 */
    private Long id;

    /** 所属门诊 */
    @Excel(name = "所属门诊")
    private Long outpatientId;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("outpatientId", getOutpatientId())
            .append("address", getAddress())
            .toString();
    }
}
