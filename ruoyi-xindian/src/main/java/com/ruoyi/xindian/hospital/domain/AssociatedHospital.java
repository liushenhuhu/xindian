package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院关联对象 associated_hospital
 * 
 * @author chenpeng
 * @date 2023-03-10
 */
public class AssociatedHospital extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 医院关联id */
    private Long hId;

    /** 医院id */
    @Excel(name = "医院id")
    private Long hospitalId;

    /** 下级医院id */
    @Excel(name = "下级医院id")
    private Long lowerLevelHospitalId;

    public void sethId(Long hId) 
    {
        this.hId = hId;
    }

    public Long gethId() 
    {
        return hId;
    }
    public void setHospitalId(Long hospitalId) 
    {
        this.hospitalId = hospitalId;
    }

    public Long getHospitalId() 
    {
        return hospitalId;
    }
    public void setLowerLevelHospitalId(Long lowerLevelHospitalId) 
    {
        this.lowerLevelHospitalId = lowerLevelHospitalId;
    }

    public Long getLowerLevelHospitalId() 
    {
        return lowerLevelHospitalId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hId", gethId())
            .append("hospitalId", getHospitalId())
            .append("lowerLevelHospitalId", getLowerLevelHospitalId())
            .toString();
    }
}
