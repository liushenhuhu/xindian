package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院门诊表对象 hospital_outpatient
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class HospitalOutpatient extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 门诊编号 */
    private Long id;

    /** 门诊名称 */
    @Excel(name = "门诊名称")
    private String name;

    /** 所属专科 */
    @Excel(name = "所属专科")
    private Long specialId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSpecialId(Long specialId) 
    {
        this.specialId = specialId;
    }

    public Long getSpecialId() 
    {
        return specialId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("specialId", getSpecialId())
            .toString();
    }
}
