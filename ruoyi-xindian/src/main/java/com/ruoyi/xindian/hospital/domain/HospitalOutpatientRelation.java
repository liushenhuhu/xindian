package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院门诊关系表对象 hospital_outpatient_relation
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class HospitalOutpatientRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关系编号 */
    private Long id;

    /** 医院编号 */
    @Excel(name = "医院编号")
    private Long hospitalId;

    /** 门诊编号 */
    @Excel(name = "门诊编号")
    private Long outpatientId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHospitalId(Long hospitalId) 
    {
        this.hospitalId = hospitalId;
    }

    public Long getHospitalId() 
    {
        return hospitalId;
    }
    public void setOutpatientId(Long outpatientId) 
    {
        this.outpatientId = outpatientId;
    }

    public Long getOutpatientId() 
    {
        return outpatientId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("hospitalId", getHospitalId())
            .append("outpatientId", getOutpatientId())
            .toString();
    }
}
